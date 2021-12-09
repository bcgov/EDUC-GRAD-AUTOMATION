import { Selector, t } from 'testcafe';
const log = require('npmlog');

class StudentSearchPage {

    constructor() {
        this.searchTab = Selector('a[role=tab]').withExactText('PEN search');
        this.advSearchTab = Selector('a[role=tab]').withExactText('Advanced search');
        this.searchInput = Selector('.search > input');
        this.searchSubmit = Selector('.search > button');
        this.searchMessage = Selector('#search-results-message');
        //TODO: add adv search selectors
        
    }
};

export default StudentSearchPage;