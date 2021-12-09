import loginPage from '../page_objects/loginPage';
import StudentSearchPage from '../page_objects/StudentSearchPage';
import studentProfilePage from '../page_objects/studentProfilePage';
import { base_url, credentials, test_pen } from '../config/constants';
import { ClientFunction } from 'testcafe';

const log = require('npmlog');
const login = new loginPage();
const searchPage = new StudentSearchPage();
const profilePage = new studentProfilePage();

fixture `grad-login-admin`
    .page(base_url)
    .beforeEach(async t => {
        await login.staffLogin(credentials.adminCredentials, base_url);
        await t.maximizeWindow();
    });

test('Pen Search', async t => {
    const getLocation = ClientFunction(() => document.location.href);
    await t.click(searchPage.searchTab);
    await t
        .typeText(searchPage.searchInput, test_pen)
        .click(searchPage.searchSubmit)
        .expect(getLocation()).contains('/student-profile');
    //await searchPage.searchByPen(test_pen);
});

