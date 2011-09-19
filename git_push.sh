#!/bin/sh

sh ./android/clear_script.sh
git add .
git commit -m "$1"
git push