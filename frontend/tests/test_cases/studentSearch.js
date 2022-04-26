import LoginPage from '../page_objects/LoginPage';
import StudentSearchPage from '../page_objects/StudentSearchPage';
import MainMenu from '../page_objects/MainMenu';
import { base_url, credentials, test_pen, api_html_status_threshold } from '../config/constants';
import { ClientFunction, RequestLogger  } from 'testcafe';
import { apiCallsFailed } from '../helpers/requestHelper';

const log = require('npmlog');
const bad_pen = '121212121';
const login = new LoginPage();
const searchPage = new StudentSearchPage();
const mainMenu = new MainMenu();
const requestLogger = RequestLogger(/api\/v1/, {logResponseBody: true, logResponseHeaders: true});

fixture `grad-login-admin`
    .page(base_url)
    .requestHooks(requestLogger)
    .beforeEach(async t => {
        await login.staffLogin(credentials.adminCredentials, base_url);
        await t.maximizeWindow();
    }).afterEach(() => log.info(apiCallsFailed(requestLogger, api_html_status_threshold)));

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
    await t.typeText(searchPage.searchInput, test_pen)
           .click(searchPage.searchSubmit)
           .wait(2000)
           .expect(requestLogger.contains(r => r.response.statusCode > api_html_status_threshold)).notOk();
           
    await t.expect(getLocation()).contains('/student-profile');

    // testing pen bad pen search from top menu
    // TODO: awaiting resolution for bugfix https://gww.jira.educ.gov.bc.ca/browse/GRAD2-874
    /**log.info("Testing bad pen search from top menu");
    await t.typeText(mainMenu.searchByPenInput, bad_pen)
           .click(mainMenu.searchByPenButton)
           .expect(requestLogger.contains(r => r.response.statusCode > api_html_status_threshold)).notOk();
    
    await t.expect(searchPage.searchMessage.innerText).contains('Student cannot be found', 'Student cannot be found error message did not occur', {timeout: 2000});


    // testing good pen search from main menu
    log.info("Testing good pen search from top menu");
    await mainMenu.selectSearchByPenInput(test_pen);
    await t.expect(getLocation()).contains('/student-profile');

    // testing advanced search
    log.info("Testing advanced search reset");
    await mainMenu.selectStudentSearchLink();
     **/

    // TODO: tests adv search

});

