import { Selector, t } from 'testcafe';
const log = require('npmlog');

class StudentProfilePage {

    constructor() {
        // tabs
        this.GRADTab = Selector('a[role=tab]').withText('GRAD');
        this.coursesTab = Selector('a[role=tab]').withText('Courses');
        this.assessmentsTab = Selector('a[role=tab]').withText('Assessments');
        this.examsDetailsTab = Selector('a[role=tab]').withText('Exams details');
        this.optionalProgramsTab = Selector('a[role=tab]').withText('Optional Programs');
        this.notesTab = Selector('a[role=tab]').withText('Notes');
        this.auditHistoryTab = Selector('a[role=tab]').withText('Audit history');
    }

    async selectGRADTab() {
        await t.click(this.GRADTab);
    }

    async selectCoursesTab() {
        await t.click(this.coursesTab);
    }

    async selectAssessmentsTab() {
        await t.click(this.assessmentsTab);
    }

    async selectExamsDetailsTab() {
        await t.click(this.examsDetailsTab);
    }

    async selectOptionalProgramsTab() {
        await t.click(this.optionalProgramsTab);
    }

    async selectNotesTab() {
        await t.click(this.notesTab);
    }

    async selectAuditHistoryTab() {
        await t.click(this.auditHistoryTab);
    }

};

export default StudentProfilePage;