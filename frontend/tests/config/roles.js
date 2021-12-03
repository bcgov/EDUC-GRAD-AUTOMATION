import { Role } from 'testcafe';
import { base_url, keycloakAdminCredentials } from './constants';

const adminUser = Role(base_url + '/?login=noidir', async t => {
    await t
        .typeText('#username', keycloakAdminCredentials.username)
        .typeText('#password', keycloakAdminCredentials.password)
        .click('#kc-login');
});

module.exports.adminUser = adminUser;