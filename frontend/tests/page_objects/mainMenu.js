import { Selector, t } from 'testcafe';

class MainMenu {

    constructor() {
        this.selectStudentLink = Selector('#select-student-route');
        this.programsLink = Selector('#programs-route');
        this.coursesLink = Selector('#courses-route');
        this.assessmentsLink = Selector('#assessments-route');
        this.schoolsLink = Selector('#schools-route');
        this.psiLink = Selector('#psi-route');
        this.codesLink = Selector('#codes-route');
        this.profileLink = Selector('#profile-route');
        this.searchByPenInput = Selector('#search-by-pen-header');
        this.searchByPenButton = Selector('#search-by-pen-header + button');
        this.dashBoardLink = Selector('#dashboard-route');
        this.logoutLink = Selector('#logout-route');
    }

    // select search by pen input
    async selectSearchByPenInput(pen){
        await t.typeText(this.searchByPenInput, pen).click(this.searchByPenButton);
    }

    // select the student search link
    async selectStudentSearchLink(){
        await t.click(this.selectStudentLink);
    }

    // select the programs link
    async selectProgramsLink(){
        await t.click(this.programsLink);
    }

    // select the courses
    async selectCoursesLink(){
        await t.click(this.coursesLink);
    }

    // select the assessments link
    async selectAssessmentsLink(){
        await t.click(this.assessmentsLink);
    }

    // select the schools link
    async selectSchoolsLink(){
        await t.click(this.schoolsLink);
    }

    // select the psi link
    async selectPSILink(){
        await t.click(this.psiLink);
    }

    // select the codes link
    async selectCodesLink(){
        await t.click(this.codesLink);
    }

    // select the profile link
    async selectProfileLink(){
        await t.click(this.profileLink);
    }

    // select the dashboard link
    async selectDashBoardLink(){
        await t.click(this.dashBoardLink);
    }

    // select the logout link
    async selectLogoutLink(){
        await t.click(this.logoutLink);
    }

}

export default MainMenu;