#!/usr/bin/env python

import csv
import argparse

parser = argparse.ArgumentParser(description='Fix csv files provided by shopatron.')
parser.add_argument('-o', '--out', help='file to output the fixed csv data (defaults to overwriting the original file)',
        metavar='OUT-FILE', dest='outfile')
parser.add_argument('infile', help='csv input file to fix', metavar='IN-FILE')

args = parser.parse_args()

if args.outfile is None:
    args.outfile = args.infile

with open(args.infile, 'rb') as csv_in:
    reader = csv.reader(csv_in, delimiter=',', quotechar="'")
    with open(args.outfile, 'wb') as csv_out:
        writer = csv.writer(csv_out, delimiter=',', quotechar='"')
        writer.writerows(reader)
