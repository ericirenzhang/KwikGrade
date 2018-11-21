package models;

import java.util.ArrayList;

public class KwikGrade {
    private ArrayList<Course> activeCourses;
    private ArrayList<Course> closedCourses;

    public KwikGrade() {
        this.activeCourses = new ArrayList<>();
        this.closedCourses = new ArrayList<>();
    }

    /**
     * Adds courses to the active list. Only active course list can have courses added to it
     * @param courseNum
     * @param courseTerm
     * @param courseTitle
     * @param importedStudentsList
     */
    public void addCourse(String courseNum, String courseTerm, String courseTitle, ArrayList<Student> importedStudentsList , OverallGrade ugCourseCategory, OverallGrade gradCourseCategory) {
        if(importedStudentsList.size() == 0) {
            Course courseToAdd = new Course(courseNum, courseTerm, courseTitle, ugCourseCategory, gradCourseCategory);
            activeCourses.add(courseToAdd);
        }
        else {
            Course courseToAdd = new Course(courseNum, courseTerm, courseTitle, importedStudentsList, ugCourseCategory, gradCourseCategory);
            activeCourses.add(courseToAdd);
        }
    }

    /**
     * Moves course at closeIndex from activeCourses into closedCourses.
     * @param closeIndex
     */
    public void closeCourse(int closeIndex) {
        activeCourses.get(closeIndex).setIsOpen(false);
        closedCourses.add(activeCourses.get(closeIndex));
        activeCourses.remove(closeIndex);
    }

    public void deleteCourse(int deleteIndex) {
        activeCourses.remove(deleteIndex);
    }

    public ArrayList<Course> getActiveCourses() {
        return this.activeCourses;
    }

    public ArrayList<Course> getClosedCourses() {
        return this.closedCourses;
    }

    public void setActiveCourses(ArrayList<Course> activeCourses) {
        this.activeCourses = activeCourses;
    }

    public void setClosedCourses(ArrayList<Course> closedCourses) {
        this.closedCourses = closedCourses;
    }
}
