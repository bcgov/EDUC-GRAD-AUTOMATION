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
        dev_base_url: 'https://dev.grad.gov.bc.ca'
      },
    credential: {
        user: 'genericuser5',
        pass: 'xW67Kn@lo'
    }
});

module.exports = nconf;