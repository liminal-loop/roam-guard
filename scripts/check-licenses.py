import json
import re
import subprocess
import sys
import xml.etree.ElementTree as ET
from pathlib import Path
from urllib.request import urlopen, Request

ALLOWED_LICENSES = [
    "apache", "Apache-2.0", "Apache License 2.0", "Apache Software License",
    "The Apache License", "Apache 2.0",
    "MIT", "The MIT License",
    "BSD", "BSD 2-Clause", "BSD 3-Clause", "The BSD License",
    "CC0", "Public Domain",
    "Unlicense", "The Unlicense",
    "Mozilla Public License", "MPL",
    "Eclipse Public License", "EPL",
    "Bouncy Castle",
    "CDDL",
    "ICU",
    "JSON",
]

KNOWN_ALLOWED = [
    "org.jetbrains.kotlin:", "org.jetbrains:annotations",
    "androidx.", "com.google.android.",
    "com.google.dagger:", "com.google.devtools.ksp",
    "com.google.guava:", "com.google.code.findbugs",
    "com.google.errorprone:", "com.google.auto.value",
    "com.squareup:javapoet", "com.squareup:kotlinpoet",
    "javax.inject:", "javax.annotation:",
    "net.sf.kxml:", "org.checkerframework:",
    "com.google.flogger:", "io.reactivex.rxjava3:",
    "org.jetbrains.kotlinx:", "org.robolectric:",
    "io.mockk:", "junit:junit", "org.hamcrest:hamcrest",
    "net.bytebuddy:", "org.objenesis:",
    "org.jacoco:", "org.jetbrains.intellij.deps:",
    "com.github.ajalt:", "com.github.jnr:",
    "org.ow2.asm:", "com.google.auto.factory:",
    "org.reactivestreams:", "com.google.testparameterinjector:",
    "com.google.truth:", "com.google.common.truth:",
    "com.google.protobuf:", "com.google.code.gson:",
    "io.github.davidmorgan:",
    "com.squareup.okio:",
    "com.squareup:kotlinpoet",
    "com.google.auto:",
    "org.tensorflow:",
    "androidx.datastore:datastore-core-okio",
    "dev.rikka.shizuku:",
]


def get_dependency_tree():
    print("Fetching dependency tree...")
    result = subprocess.run(
        ["./gradlew", ":app:dependencies", "--configuration", "debugRuntimeClasspath"],
        capture_output=True, text=True, timeout=300
    )
    if result.returncode != 0:
        print(f"WARNING: gradlew returned {result.returncode}")
    return result.stdout + result.stderr


def parse_dependencies(text):
    deps = set()
    # Parse lines like:
    # +--- org.jetbrains.kotlin:kotlin-stdlib:2.0.0
    # |    +--- org.jetbrains:annotations:13.0 -> 23.0.0
    # \--- androidx.annotation:annotation:1.1.0 -> 1.8.0
    pattern = re.compile(r'[\\+\\|\\ ]+--- ([^:]+):([^:]+):([^ ]+)')
    for line in text.split('\n'):
        m = pattern.search(line)
        if m:
            group, name, version = m.group(1), m.group(2), m.group(3).split(' -> ')[-1]
            if group != "project" and not group.startswith("project "):
                deps.add((group, name, version))
    return sorted(deps)


def check_maven_license(group, name, version):
    path = f"{group.replace('.', '/')}/{name}/{version}/{name}-{version}.pom"
    urls = [
        f"https://repo1.maven.org/maven2/{path}",
        f"https://dl.google.com/dl/android/maven2/{path}",
    ]
    for url in urls:
        try:
            req = Request(url, headers={"User-Agent": "RoamGuard/1.0"})
            with urlopen(req, timeout=10) as resp:
                xml = resp.read().decode("utf-8")
            root = ET.fromstring(xml)
            ns = {"": "http://maven.apache.org/POM/4.0.0"}
            licenses = root.findall(".//licenses/license/name", ns)
            if licenses:
                return [lic.text.strip() for lic in licenses if lic.text]
        except Exception as e:
            continue
    return None


def is_allowed(group, name):
    coord = f"{group}:{name}"
    for prefix in KNOWN_ALLOWED:
        if coord.startswith(prefix):
            return True
    return False


def main():
    text = get_dependency_tree()
    deps = parse_dependencies(text)
    print(f"Found {len(deps)} unique dependencies")
    print()

    failed = False
    report = {"dependencies": [], "status": "unknown"}
    for group, name, version in deps:
        if is_allowed(group, name):
            print(f"  ✓ {group}:{name}:{version} (known allowed)")
            report["dependencies"].append({
                "group": group, "name": name, "version": version, "status": "allowed"
            })
            continue

        licenses = check_maven_license(group, name, version)
        if licenses is None:
            print(f"  ⚠ {group}:{name}:{version} (license lookup failed)")
            report["dependencies"].append({
                "group": group, "name": name, "version": version,
                "status": "unknown", "licenses": []
            })
            continue

        license_names = [l.strip() for l in licenses]
        all_allowed = True
        for lic in license_names:
            matched = any(allowed.lower() in lic.lower() for allowed in ALLOWED_LICENSES)
            if not matched:
                print(f"  ❌ {group}:{name}:{version} — {lic}")
                all_allowed = False
                failed = True

        if all_allowed:
            print(f"  ✓ {group}:{name}:{version} ({', '.join(license_names)})")
            report["dependencies"].append({
                "group": group, "name": name, "version": version,
                "status": "allowed", "licenses": license_names
            })
        else:
            report["dependencies"].append({
                "group": group, "name": name, "version": version,
                "status": "forbidden", "licenses": license_names
            })

    report["status"] = "failed" if failed else "passed"
    report["total"] = len(deps)

    # Write report
    output_path = Path("build/reports/licenses/licenses.json")
    output_path.parent.mkdir(parents=True, exist_ok=True)
    output_path.write_text(json.dumps(report, indent=2))
    print(f"\nReport written to {output_path}")

    if failed:
        print("\n❌ License compliance FAILED")
        sys.exit(1)
    else:
        print("\n✅ License compliance PASSED")


if __name__ == "__main__":
    main()
