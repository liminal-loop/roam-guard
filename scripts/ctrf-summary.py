#!/usr/bin/env python3
import argparse
import json
import sys


def format_summary(data):
    s = data["results"]["summary"]
    print(f'**Total:** {s["tests"]} | **Passed:** {s["passed"]} | **Failed:** {s["failed"]} | **Skipped:** {s["skipped"]}')
    print(f'**Duration:** {s["time"]["duration"]}ms')
    print(f'**Suites:** {s["suites"]}')


def format_details(data):
    print("| Test | Status | Duration |")
    print("|---|---|---|")
    for t in data["results"]["tests"]:
        if t["status"] == "passed":
            icon = "\u2705"
        elif t["status"] == "failed":
            icon = "\u274C"
        else:
            icon = "\u23ED\uFE0F"
        print(f'| {icon} {t["name"]} | {t["status"]} | {t["duration"]}ms |')


def main():
    parser = argparse.ArgumentParser(
        description="Print CTRF report summary to stdout"
    )
    parser.add_argument(
        "--input",
        default="build/test-results/ctrf-report.json",
        help="Path to CTRF JSON report",
    )
    parser.add_argument(
        "--format",
        choices=["summary", "details"],
        default="summary",
        help="Output format",
    )
    args = parser.parse_args()

    try:
        with open(args.input) as f:
            data = json.load(f)
    except FileNotFoundError:
        print(f"Error: CTRF report not found: {args.input}", file=sys.stderr)
        sys.exit(1)
    except json.JSONDecodeError as e:
        print(f"Error: invalid CTRF report: {e}", file=sys.stderr)
        sys.exit(1)

    if args.format == "summary":
        format_summary(data)
    else:
        format_details(data)


if __name__ == "__main__":
    main()
