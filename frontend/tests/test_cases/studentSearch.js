import LoginPage from '../page_objects/LoginPage';
import StudentSearchPage from '../page_objects/StudentSearchPage';
import MainMenu from '../page_objects/MainMenu';
import StudentProfilePage from '../page_objects/StudentProfilePage';
import { base_url, credentials, test_pen } from '../config/constants';
import { ClientFunction, t } from 'testcafe';

const log = require('npmlog');
const bad_pen = '121212121';
const login = new LoginPage();
const searchPage = new StudentSearchPage();
const mainMenu = new MainMenu();
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
    await searchPage.selectPenSearchTab();
    await searchPage.studentSearch("121212121");
    await t.expect(searchPage.searchMessage.innerText).contains('Student cannot be found', 'Student cannot be found error message did not occur', {timeout: 2000});
    
    await searchPage.clearSearchInput();
    
    // testing good pen search
    log.info("Testing search for existing student");
    await searchPage.studentSearch(test_pen);
    await t.expect(getLocation()).contains('/student-profile');

    // testing pen bad pen search from top menu
    // TODO: awaiting resolution for bugfix https://gww.jira.educ.gov.bc.ca/browse/GRAD2-874
    //log.info("Testing bad pen search from top menu");
    //await mainMenu.selectSearchByPenInput(bad_pen);
    //await t.expect(searchPage.searchMessage.innerText).contains('Student cannot be found', 'Student cannot be found error message did not occur', {timeout: 2000});

    // testing good pen search from main menu
    log.info("Testing good pen search from top menu");
    await mainMenu.selectSearchByPenInput(test_pen);
    await t.expect(getLocation()).contains('/student-profile');

    // testing advanced search
    log.info("Testing advanced search reset");
    await mainMenu.selectStudentSearchLink();

    // TODO: tests adv search

});

