#!/bin/sh

echo "running testcafe..."
testcafe 'chromium:headless --no-sandbox --disable-dev-shm-usage' -- test1.js