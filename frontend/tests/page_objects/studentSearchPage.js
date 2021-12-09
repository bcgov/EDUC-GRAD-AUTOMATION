import { Selector, t } from 'testcafe';
import { ClientFunction } from 'testcafe';
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

    async searchByPen(pen) {
        const getLocation = ClientFunction(() => document.location.href);
        // select PEN search tab
        await t.click(this.searchTab);
        await t
            .typeText(this.searchInput, pen)
            .click(this.searchSubmit)
            .expect(getLocation()).contains('/student-profile');
    }

    async searchByPenNotFound(pen) {
        await t.click(this.searchTab);
        await t
            .typeText(this.searchInput, "234534534")
            .click(this.searchSubmit)
            .expect(Selector('#search-results-message').innerText).contains('Student cannot be found', 'The student was not found', {timeout: 2000});
    }

};

export default StudentSearchPage;