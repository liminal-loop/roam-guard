import sys
import xml.etree.ElementTree as ET

def parse_kover_report(path):
    tree = ET.parse(path)
    root = tree.getroot()
    line_rate = float(root.get("line-rate", "0"))
    branch_rate = float(root.get("branch-rate", "0")) if root.get("branch-rate") else None
    return line_rate, branch_rate

def main():
    reports = sys.argv[1:] if len(sys.argv) > 1 else [
        "domain/build/reports/kover/report.xml",
        "data/build/reports/kover/report.xml",
    ]

    print("| Module | Line Coverage | Branch Coverage |")
    print("|---|---|---|")
    total_lines = 0
    total_covered = 0
    for report in reports:
        module = report.split("/")[0]
        try:
            line_rate, branch_rate = parse_kover_report(report)
            line_pct = f"{line_rate * 100:.1f}%"
            branch_pct = f"{branch_rate * 100:.1f}%" if branch_rate else "N/A"
            print(f"| `{module}` | {line_pct} | {branch_pct} |")
        except FileNotFoundError:
            print(f"| `{module}` | N/A | N/A |", file=sys.stderr)

if __name__ == "__main__":
    main()
