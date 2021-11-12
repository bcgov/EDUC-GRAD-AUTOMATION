import loginPage from '../page_objects/loginPage';
import studentSearchPage from '../page_objects/studentSearchPage';
import { dev_base_url, keycloackCredentials, test_pen } from '../config/constants';

const log = require('npmlog');
const login = new loginPage();
const searchPage = new studentSearchPage();

fixture `grad-login`
    .page(dev_base_url)
    .beforeEach(async t => {
        await t.maximizeWindow()
    });

test('login', async t => {

    await login.staffLogin(keycloackCredentials, dev_base_url);
    log.info('login complete.');
    await searchPage.searchByPen(test_pen);
    log.info('pen search complete.');
});
