import { Selector, t } from 'testcafe';

const log = require('npmlog');

class loginPage {

    constructor() {
        this.username = Selector('#user')
        this.password = Selector('#password')
        this.submitButton = Selector('input[name="btnSubmit"]')
        this.penSearch = Selector('#search-by-pen')
    };

    async staffLogin(credentials, url) {
        try {
            await t.typeText(this.username, credentials.username, { timeout: 20000 })
                .typeText(this.password, credentials.password, { timeout: 20000 })
                .click(this.submitButton);
            await t.expect((this.penSearch).exists).ok({ timeout: 20000 });
            log.info("Staff user successfully logged in:    " + credentials.username);
        } catch (error) {
            log.error;
        }
    };

};
export default loginPage;
