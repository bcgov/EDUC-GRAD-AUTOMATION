const restUtils = require("../helpers/restUtils");
const constants = require('../config/constants');

/**
 * Exposes methods for communication with edx-api end-points
 * @type {{getAllMinistryTeams(*=): Promise<*>}}
 */
const edxApiService = {

    async studentSearch(token) {
        const endpoint = 'api/v1/student/studentsearch';
        const url = `${constants.base_url}${endpoint}`;
        const responseBody = await restUtils.getData(token, url, '');
        return responseBody;
    }
}

module.exports = edxApiService;
