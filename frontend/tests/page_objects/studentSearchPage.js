import { Selector, t } from 'testcafe';

class StudentSearchPage {

    constructor() {
        // search elements
        this.searchTab = Selector('a[role=tab]').withExactText('PEN search');
        this.advSearchTab = Selector('a[role=tab]').withExactText('Advanced search');
        this.searchInput = Selector('.search > input');
        this.searchSubmit = Selector('.search > button');
        this.searchMessage = Selector('#search-results-message');
        
        // advanced search elements
        this.legalSurnameInput = Selector('#legal-surname-input');
        this.legalGivenInput = Selector('#legal-given-input');
        this.legalMiddleInput = Selector('#legal-middle-input');
        this.legalGenderSelect = Selector('#legal-gender-select');
        this.birthDateFrom = Selector('#datepicker-birthdate-from');
        this.birthDateTo = Selector('#datepicker-birthdate-to');
        this.usualSurnameInput = Selector('#usual-surname-input');
        this.usualGivenInput = Selector('#usual-given-input');
        this.usualMiddleInput = Selector('#usual-middle-input');
        this.localIdInput = Selector('#local-id-input');
    }

    // select basic search tab
    async selectPenSearchTab(){
        await t.click(this.searchTab);
    }

    // select the Advanced search tab
    async selectAdvancedSearchTab(){
        await t.click(this.advSearchTab);
    }

    // clear the basic search input
    async clearSearchInput(){
        await t.click(this.searchInput).pressKey('ctrl+a delete');
    }

    // PEN search (basic)
    async studentSearch(pen){
        await t
        .typeText(this.searchInput, pen)
        .click(this.searchSubmit);
    }
};

export default StudentSearchPage;