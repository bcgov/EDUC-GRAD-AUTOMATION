const config = require( '../config/index');

module.exports.base_url = config.get('url:base_url');
module.exports.test_pen = config.get('test:pen');
module.exports.credentials = Object.freeze({
    adminCredentials: {
      username: config.get('adminCredential:user'),
      password: config.get('adminCredential:pass')
    }
  });