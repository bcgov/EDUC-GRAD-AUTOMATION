#!/bin/sh

git clone $GIT_REPO
cd EDUC-GRAD-TEST-AUTOMATION/frontend
npm install
npm run smoke-test