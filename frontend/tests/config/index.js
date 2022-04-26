'use strict';
const nconf = require('nconf');
const dotenv = require('dotenv');
const path = require('path');
dotenv.config();

//switch to local if running locally
const env = process.env.NODE_ENV;
//const env = 'local'

nconf.argv()
  .env()
  .file({ file: path.join(__dirname, `${env}.json`) });

nconf.defaults({
    url: {
        base_url: process.env.BASE_URL
      },
    adminCredential: {
        user: process.env.TEST_ADMIN_USERNAME,
        pass: process.env.TEST_ADMIN_PASSWORD
    },
    test: {
        pen: process.env.TEST_PEN,
        api_html_status_threshold: process.env.API_HTML_STATUS_CLASS_THRESHOLD
    },
    token: {
        token_client_id: process.env.TOKEN_CLIENT_ID,
        token_client_secret: process.env.TOKEN_CLIENT_SECRET,
        token_endpoint: process.env.TOKEN_ENDPOINT
    }
});

module.exports = nconf;