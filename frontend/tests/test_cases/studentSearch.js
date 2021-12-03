import loginPage from '../page_objects/loginPage';
import studentSearchPage from '../page_objects/studentSearchPage';
import { base_url, keycloakAdminCredentials, test_pen } from '../config/constants';

const log = require('npmlog');
const login = new loginPage();
const searchPage = new studentSearchPage();

fixture `grad-login-admin`
    .page(base_url)
    .beforeEach(async t => {
        await t.maximizeWindow();
    });

test('Pen Search', async t => {
    await login.staffLogin(keycloakAdminCredentials, base_url);
    await searchPage.searchByPen(test_pen);
    // additional search tests
});