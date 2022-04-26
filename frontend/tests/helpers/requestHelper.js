const log = require('npmlog');

/**
 * Used for checking if any back-end calls to apis have failed.
 * Usage example: log.info(apiCallsFailed(requestLogger, api_html_status_threshold)));
 *
 * @param requestLogger - a request logger instance: https://testcafe.io/documentation/402668/reference/test-api/requestlogger
 * @param statusCodeClassThreshold - the upper limit to the status response class, for example 399 only logs requests for 100, 200 and 300 series response codes
 * @returns {string|string}
 */
function apiCallsFailed(requestLogger, statusCodeClassThreshold){
    var response = [];
    if(requestLogger.requests.length < 1){
        response.push("responses array empty");
    }
    requestLogger.requests.forEach(r => {
        if(r.response === undefined){
             response.push("Response is undefined for: " + r.request.url + " perhaps response is delayed.\n");
        } else if(r.response.statusCode > statusCodeClassThreshold) {
            response.push(r.request.url + " returned " + r.response.statusCode + "\n");
        }
    });
   return (response.length > 0) ? response.join("") : "No errors";
}

/**
 * Same as apiCallsFailed only for successful calls
 * @param requestLogger
 * @param statusCodeClassThreshold
 * @returns {boolean}
 */
function apiCallsPass(requestLogger, statusCodeClassThreshold){
    log.info("Testing api Calls Pass. Size: " + requestLogger.requests.length);
    requestLogger.requests.forEach(r => {
        if(r.response === undefined){
             log.error("Response is undefined for: " + r.request.url + " perhaps response is delayed.");
             return false;
        } else if(r.response.statusCode > statusCodeClassThreshold) {
            log.info(r.request.url + " returned " + r.request.statusCode);
            return false;
        }
    });
    return true;
}

export {apiCallsPass, apiCallsFailed};