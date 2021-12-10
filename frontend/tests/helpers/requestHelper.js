const log = require('npmlog');

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