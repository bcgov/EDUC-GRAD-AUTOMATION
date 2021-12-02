import { Role } from 'testcafe';
import { base_url, keycloakAdminCredentials } from './constants';

const adminUser = Role(base_url, async t => {
    await t
        .typeText('#user', keycloakAdminCredentials.username)
        .typeText('#password', keycloakAdminCredentials.password)
        .click('input[name="btnSubmit"]');
});

module.exports.adminUser = adminUser;