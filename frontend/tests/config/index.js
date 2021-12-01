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
        base_url: 'https://dev.grad.gov.bc.ca'
      },
    credential: {
        user: process.env.TEST_USERNAME,
        pass: process.env.TEST_PASSWORD
    },
    test: {
        pen: '126187343'
    }
});

module.exports = nconf;