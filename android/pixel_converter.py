#! /usr/bin/python
# -*- encoding: utf-8 -*-

"""
Convert pixel to dip.
Unit dip width is 320. Unit pixel width is 480.
Usage: python pixel_converter.py [-h|--help] [pixelargs, ]
"""

UNIT_PIXEL_WIDTH = 480
UNIT_DIP_WIDTH = 320
UNIT_DENSITY = float(320) / float(480)
NumericError = "Error: Input types are not numeric."

import sys
import getopt

def main():
    # parse command line options
    if (len(sys.argv) == 1) :
        print __doc__
        sys.exit(2)
        
    try:
        opts, args = getopt.getopt(sys.argv[1:], "h", ["help"])
    except getopt.error, msg:
        print msg
        print __doc__
        sys.exit(2)

    # process options
    for o, a in opts:
        if o in ("-h", "--help"):
            print __doc__

    for arg in args:
        printResult(toInt(arg))
    sys.exit(0)

def printResult(pixel):
    print "Convert result: %4.d px -> %6.2f dp" % (pixel, getDip(pixel))

def getDip(pixel):
    return pixel * UNIT_DENSITY

def toInt(intStr):
    try:
        if intStr == "":
            print NumericError
            sys.exit(2)
        return int(intStr)
    except error:
        print NumericError
        sys.exit(2)

if __name__ == "__main__":
    main()
