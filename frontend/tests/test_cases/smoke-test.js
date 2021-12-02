import loginPage from '../page_objects/loginPage';
import studentSearchPage from '../page_objects/studentSearchPage';
import { base_url, keycloakAdminCredentials, test_pen } from '../config/constants';

const log = require('npmlog');
const login = new loginPage();
const searchPage = new studentSearchPage();

fixture `grad-login`
    .page(base_url)
    .beforeEach(async t => {
        await t.maximizeWindow()
    });

test('login', async t => {

    await login.staffLogin(keycloakAdminCredentials, base_url);
    log.info('login complete.');
    await searchPage.searchByPen(test_pen);
    log.info('pen search complete.');
});
