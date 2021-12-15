#!/bin/bash

# path to this script
SCRIPT=$(readlink -f "$0")
# parent dir
SCRIPT_PATH=$(dirname "$SCRIPT")
# .env file
ENV_FILE=$SCRIPT_PATH/.env
# executable
GRADT=$SCRIPT_PATH/target/grad-trax-test-suite.jar

# if .env file exists, load env vars
if [ -f "$ENV_FILE" ]; then
  export $(grep -v '^#' $ENV_FILE | xargs)
fi

# launch app with args
java -jar $GRADT "$@"