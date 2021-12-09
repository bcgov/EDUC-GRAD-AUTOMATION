import loginPage from '../page_objects/loginPage';
import studentSearchPage from '../page_objects/studentSearchPage';
import studentProfilePage from '../page_objects/studentProfilePage';
import { base_url, credentials, test_pen } from '../config/constants';

const log = require('npmlog');
const login = new loginPage();
const searchPage = new studentSearchPage();
const profilePage = new studentProfilePage();

fixture `grad-login-admin`
    .page(base_url)
    .beforeEach(async t => {
        log.info("Before each...");
        await t.maximizeWindow();
    });

test('Pen Search', async t => {
    log.info("starting test");
    await login.staffLogin(credentials.adminCredentials, base_url);
    await searchPage.searchByPen(test_pen);
    await profilePage.selectCoursesTab();
    await profilePage.selectGRADTab();
    await profilePage.selectAssessmentsTab();
    await profilePage.selectExamsDetailsTab();
    await profilePage.selectOptionalProgramsTab();
    await profilePage.selectNotesTab();
    await profilePage.selectAuditHistoryTab();
    // additional search tests
});