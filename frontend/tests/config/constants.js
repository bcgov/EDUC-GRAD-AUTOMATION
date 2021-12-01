const config = require( '../config/index');

module.exports.base_url = config.get('url:base_url');
module.exports.test_pen = config.get('test:pen');
module.exports.keycloackCredentials = Object.freeze({
    username: config.get('credential:user'),
    password: config.get('credential:pass')
  });