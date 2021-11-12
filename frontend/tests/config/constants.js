const config = require( '../config/index');

module.exports.dev_base_url = config.get('url:dev_base_url');

module.exports.keycloackCredentials = Object.freeze({
    username: config.get('credential:user'),
    password: config.get('credential:pass')
  });