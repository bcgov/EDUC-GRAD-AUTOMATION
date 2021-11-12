import loginPage from '../page_objects/loginPage';
import { dev_base_url, keycloackCredentials } from '../config/constants';

const log = require('npmlog');
const login = new loginPage();

fixture `grad-login`
    .page(dev_base_url)
    .beforeEach(async t => {
        await t.maximizeWindow()
    });

test('login', async t => {

    await login.staffLogin(keycloackCredentials, dev_base_url);
    log.info('test complete');

});
