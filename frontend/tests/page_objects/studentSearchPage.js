import { Selector, t } from 'testcafe';
const log = require('npmlog');

class studentSearchPage {

    constructor() {
        this.searchTab = Selector('a[role=tab]').withExactText('PEN search');
        this.advSearchTab = Selector('a[role=tab]').withExactText('Advanced search');
        this.searchInput = Selector('.search > input');
        this.searchSubmit = Selector('.search > button');
        //TODO: add adv search selectors
        
    }

    async searchByPen(pen) {
        // select PEN search tab
        await t.click(this.searchTab);
        await t.typeText(this.searchInput, pen)
            .click(this.searchSubmit);
    }

};

export default studentSearchPage;