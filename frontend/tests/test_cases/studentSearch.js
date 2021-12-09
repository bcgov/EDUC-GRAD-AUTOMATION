import LoginPage from '../page_objects/LoginPage';
import StudentSearchPage from '../page_objects/StudentSearchPage';
import StudentProfilePage from '../page_objects/StudentProfilePage';
import { base_url, credentials, test_pen } from '../config/constants';
import { ClientFunction } from 'testcafe';

const log = require('npmlog');
const login = new LoginPage();
const searchPage = new StudentSearchPage();
const profilePage = new StudentProfilePage();

fixture `grad-login-admin`
    .page(base_url)
    .beforeEach(async t => {
        await login.staffLogin(credentials.adminCredentials, base_url);
        await t.maximizeWindow();
    });

test('Pen Search', async t => {
    const getLocation = ClientFunction(() => document.location.href);
    // testing bad pen search
    log.info("Testing student does not exist");
    await t.click(searchPage.searchTab);
    await t
        .typeText(searchPage.searchInput, "234534534")
        .click(searchPage.searchSubmit)
        .expect(searchPage.searchMessage.innerText).contains('Student cannot be found', 'Student cannot be found error message did not occur', {timeout: 2000});
    // clear text from input
    await t.click(searchPage.searchInput).pressKey('ctrl+a delete');
    
    // testing good pen search
    log.info("Testing search for existing student");
    await t.click(searchPage.searchTab);
    await t
        .typeText(searchPage.searchInput, test_pen)
        .click(searchPage.searchSubmit)
        .expect(getLocation()).contains('/student-profile');
    
    // testing advanced search
    log.info("Testing advanced search");
    await t.click(searchPage.advSearchTab);
    
});

