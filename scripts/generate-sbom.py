import json
import re
import subprocess
import uuid
from pathlib import Path


def get_dependencies():
    result = subprocess.run(
        ["./gradlew", ":app:dependencies", "--configuration", "releaseRuntimeClasspath"],
        capture_output=True, text=True, timeout=300
    )
    text = result.stdout + result.stderr
    deps = set()
    pattern = re.compile(r'[\\+\\|\\ ]+--- ([^:]+):([^:]+):([^ ]+)')
    for line in text.split('\n'):
        m = pattern.search(line)
        if m:
            group, name, version = m.group(1), m.group(2), m.group(3).split(' -> ')[-1]
            if group != "project" and not group.startswith("project "):
                deps.add((group, name, version))
    return sorted(deps)


def main():
    deps = get_dependencies()
    components = []
    for group, name, version in deps:
        purl = f"pkg:maven/{group}/{name}@{version}"
        components.append({
            "type": "library",
            "bom-ref": purl,
            "purl": purl,
            "name": f"{group}:{name}",
            "version": version,
            "group": group,
        })

    bom = {
        "bomFormat": "CycloneDX",
        "specVersion": "1.5",
        "serialNumber": f"urn:uuid:{uuid.uuid4()}",
        "version": 1,
        "metadata": {
            "component": {
                "type": "application",
                "name": "RoamGuard",
                "version": "1.0.0"
            }
        },
        "components": components
    }

    output_path = Path("app/build/reports/bom.json")
    output_path.parent.mkdir(parents=True, exist_ok=True)
    output_path.write_text(json.dumps(bom, indent=2))
    print(f"SBOM generated: {output_path} ({len(components)} components)")


if __name__ == "__main__":
    main()
