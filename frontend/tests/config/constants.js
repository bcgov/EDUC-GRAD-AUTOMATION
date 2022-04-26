const config = require( '../config/index');

module.exports.base_url = config.get('url:base_url');
module.exports.test_pen = config.get('test:pen');
module.exports.api_html_status_threshold = config.get('test:api_html_status_threshold');
module.exports.credentials = Object.freeze({
    adminCredentials: {
      username: config.get('adminCredential:user'),
      password: config.get('adminCredential:pass')
    }
  });

//Token related config
module.exports.token_client_id = config.get('token:token_client_id');
module.exports.token_client_secret = config.get('token:token_client_secret');
module.exports.token_endpoint = config.get('token:token_endpoint');