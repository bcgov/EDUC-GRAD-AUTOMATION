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
        //for (let i = 0; i < 10; i++) {
            try {
                await t.typeText(this.username, credentials.username, { timeout: 20000 })
                    .typeText(this.password, credentials.password, { timeout: 20000 })
                    .click(this.submitButton);
                await t.expect((this.penSearch).exists).ok({ timeout: 20000 });
                log.info("Staff user successfully logged in:    " + credentials.username);
                //break;
            } catch (error) {
                log.error;
                //await t.eval(() => location.reload())
                //log.warn("Element not found, Refreshing the page")

                //await t.navigateTo(url)
                //log.info("Navigating to student search")

                //await t.expect((this.submitButton).exists).ok({ timeout: 20000 })
            }
        //}
    };

};
export default loginPage;
