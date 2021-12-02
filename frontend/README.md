# Frontend end-to-end testing

For end-to-end testing, we are using testcafe, a node.js based front-end testing suite. In order to run 
any of the tests found in this directory, first install testcafe. More information on installation, etc.
can be found [here](https://testcafe.io/documentation/402635/getting-started).

## Versions:

|element     |version     |
|------------|------------|
|node        |v12.22.1    |
|npm         |7.12.1      |
|testcafe    |1.17.0     |

## Running the tests:

The tests require certain environment variables to be set:

```
TEST_ADMIN_USERNAME (a valid user credential for logging into GRAD)
TEST_ADMIN_PASSWORD (valid password)
```

There are multiple ways to do this. For example:

Add a `.env` (gitignored) file to the root of this directory with your environment variables in the following format:

```
<KEY>=<VALUE>
<KEY2>=<VALUE2>
...
```

or you can write a .bat or .sh script to set the values at runtime. This can be handy for triggering multiple tests.

Example shell script:

```
# simple shell script for automating tests
# set env vars
export TEST_ADMIN_USERNAME=<myTestUserName>
export TEST_ADMIN_PASSWORD=<myTestPassword>
echo "-- running smoke test"
# run a test
npm run smoke-test
```

Tests can be run from the root of this directory by calling `npm run <testname>` to invoke scripts defined in the [package.json](package.json) file. Tests can also be called explicitly by invoking the test like: `testcafe chrome:headless --incognito ./tests/test_cases/smoke-test.js`, etc.
