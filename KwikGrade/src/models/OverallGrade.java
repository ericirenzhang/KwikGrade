package models;

import models.CourseCategory;
import models.Student;

import java.util.ArrayList;

public class OverallGrade {
	double overallGrade;
	int numOfCategories; //The user does not enter this value. Need to calculate from GUI
	ArrayList<CourseCategory> categoryList;
	
	public OverallGrade() {
		overallGrade = 0.0;
		numOfCategories = 0;
		categoryList = new ArrayList<CourseCategory>();
	}
	
	public OverallGrade(double overallGrade, int numOfCategories, ArrayList<CourseCategory> categoryList) {
		this.overallGrade = overallGrade;
		this.numOfCategories = numOfCategories;
		this.categoryList = categoryList;
	}
	//==========================
	// Getters
	//==========================
	
	public double getOverallGrade() {
		return this.overallGrade;
	}
	
	public int getNumOfCategories() {
		return this.numOfCategories;
	}
	
	public ArrayList<CourseCategory> getCategoryList() {
		return this.categoryList;
	}
	
	//==========================
	// Setters
	//==========================
	
	public void setOverallGrade(double overallGrade) {
		this.overallGrade = overallGrade;
	}
	
	public void setNumOfCategories(int numOfCategories) {
		this.numOfCategories = numOfCategories;
	}
	
	public void setCategoryList(CourseCategory categoryList) {
		this.categoryList.add(categoryList);
	}
	
	//Overall Grade calculator
	
	public double calcOverallGrade() {
		int n = getNumOfCategories();
		for(int i=0;i<n;i++){
			
			//TODO: Define CourseCategory and use correct constructor
			CourseCategory course = new CourseCategory(); //Here we need to instantiate CourseCategory objects from GUI
			categoryList.add(course);
		}
		for(int i=0;i<categoryList.size();i++){
			overallGrade += categoryList.get(i).calcGradeCategory();
		}
		return overallGrade;
	}
}

