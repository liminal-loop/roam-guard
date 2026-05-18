#!/usr/bin/env python3
"""
CTRF (Common Test Report Format) Converter for Android Instrumentation Tests.

Converts JUnit XML test results produced by Android Instrumentation tests
into CTRF JSON format as specified by https://ctrf.io.

Usage:
    python3 scripts/ctrf-converter.py \
        --input app/build/outputs/androidTest-results/connected/ \
        --output build/test-results/ctrf-report.json
"""

import argparse
import json
import os
import sys
from xml.etree import ElementTree
from datetime import datetime, timezone


def parse_junit_xml(filepath):
    tree = ElementTree.parse(filepath)
    root = tree.getroot()
    testsuite = root if root.tag == "testsuite" else root.find("testsuite")
    if testsuite is None:
        return [], {}

    suite_name = testsuite.get("name", "unknown")
    tests = testsuite.get("tests", "0")
    failures = testsuite.get("failures", "0")
    errors = testsuite.get("errors", "0")
    skipped = testsuite.get("skipped", "0")
    time = float(testsuite.get("time", "0"))

    summary = {
        "name": suite_name,
        "total": int(tests),
        "passed": int(tests) - int(failures) - int(errors),
        "failed": int(failures) + int(errors),
        "skipped": int(skipped),
        "time": time,
    }

    results = []
    for testcase in testsuite.iter("testcase"):
        class_name = testcase.get("classname", "")
        test_name = testcase.get("name", "")
        test_time = float(testcase.get("time", "0"))

        failure = testcase.find("failure")
        error = testcase.find("error")
        skipped_elem = testcase.find("skipped")

        status = "passed"
        message = None
        trace = None

        if failure is not None:
            status = "failed"
            message = failure.get("message", "")
            trace = failure.text or ""
        elif error is not None:
            status = "failed"
            message = error.get("message", "")
            trace = error.text or ""
        elif skipped_elem is not None:
            status = "skipped"

        results.append({
            "name": test_name,
            "suite": suite_name,
            "classname": class_name,
            "status": status,
            "duration": test_time * 1000,
            "message": message,
            "trace": trace,
        })

    return results, summary


def find_junit_xmls(directory):
    xml_files = []
    for root_dir, _dirs, files in os.walk(directory):
        for f in files:
            if f.endswith(".xml"):
                xml_files.append(os.path.join(root_dir, f))
    return sorted(xml_files)


def convert(args):
    input_dir = args.input
    output_path = args.output

    if not os.path.isdir(input_dir):
        print(f"Error: input directory not found: {input_dir}", file=sys.stderr)
        sys.exit(1)

    all_results = []
    total_summary = {
        "total": 0,
        "passed": 0,
        "failed": 0,
        "skipped": 0,
        "time": 0.0,
    }
    suites = []

    xml_files = find_junit_xmls(input_dir)
    if not xml_files:
        print(f"Warning: no JUnit XML files found in {input_dir}", file=sys.stderr)

    for xml_file in xml_files:
        results, summary = parse_junit_xml(xml_file)
        all_results.extend(results)
        total_summary["total"] += summary["total"]
        total_summary["passed"] += summary["passed"]
        total_summary["failed"] += summary["failed"]
        total_summary["skipped"] += summary["skipped"]
        total_summary["time"] += summary["time"]
        suites.append(summary)

    ctrf_report = {
        "results": {
            "tool": {
                "name": "AndroidJUnitRunner",
                "version": "1.0",
            },
            "summary": {
                "tests": total_summary["total"],
                "passed": total_summary["passed"],
                "failed": total_summary["failed"],
                "skipped": total_summary["skipped"],
                "pending": 0,
                "time": {
                    "start": datetime.now(timezone.utc).isoformat(),
                    "end": datetime.now(timezone.utc).isoformat(),
                    "duration": total_summary["time"] * 1000,
                },
                "suites": len(suites),
            },
            "tests": all_results,
        }
    }

    os.makedirs(os.path.dirname(output_path), exist_ok=True)
    with open(output_path, "w") as f:
        json.dump(ctrf_report, f, indent=2)

    print(f"CTRF report written to {output_path}")
    print(f"Total: {total_summary['total']}, "
          f"Passed: {total_summary['passed']}, "
          f"Failed: {total_summary['failed']}, "
          f"Skipped: {total_summary['skipped']}")


def main():
    parser = argparse.ArgumentParser(
        description="Convert Android JUnit XML results to CTRF JSON"
    )
    parser.add_argument(
        "--input",
        required=True,
        help="Directory containing JUnit XML files",
    )
    parser.add_argument(
        "--output",
        default="build/test-results/ctrf-report.json",
        help="Output path for CTRF JSON report",
    )
    args = parser.parse_args()
    convert(args)


if __name__ == "__main__":
    main()
