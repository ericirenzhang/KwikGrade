package models;

import models.CourseCategory;
import models.Student;

import java.io.Serializable;
import java.util.ArrayList;

public class OverallGrade implements Serializable {
	private double overallGrade;
	private int numOfCategories; //The user does not enter this value. Need to calculate from GUI
	private ArrayList<CourseCategory> categoryList = new ArrayList<CourseCategory>();

	//generic constructor
	public OverallGrade() {
		overallGrade = 0.0;
		numOfCategories = 0;
		categoryList = new ArrayList<CourseCategory>();
	}

	//constructor if someone wants to define all variables
	public OverallGrade(double overallGrade, int numOfCategories, ArrayList<CourseCategory> categoryList) {
		this.overallGrade = overallGrade;
		this.numOfCategories = numOfCategories;
		this.categoryList = categoryList;
	}
  
	//constructor if someone wants to define the categorylist
	public OverallGrade(ArrayList<CourseCategory> categoryList) {
		this.overallGrade = 0.0;
		this.numOfCategories = categoryList.size();
		this.categoryList = categoryList;
	}
	
	//adds a CourseCategory object to the OverallGrade object
	public void addCourseCategory(String name, double weight) {
		CourseCategory categoryToAdd = new CourseCategory(name, weight);
		this.categoryList.add(categoryToAdd);
	}

	//checks if the weights of all the CourseCategories equal to 1.0
	public boolean checkSumOfWeight(ArrayList<CourseCategory> categoryList) {
		double totalWeight = 0.0;
		for(int i = 0; i < categoryList.size(); i++) {
			totalWeight = totalWeight + (categoryList.get(i).getWeight());
		}
		if(totalWeight == 1.0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	//computes the overall grade for the course
	public void calcOverallGrade() {
		int n = categoryList.size();
//		for(int i=0 ; i < n ; i++){
//			
//			//TODO: Define CourseCategory and use correct constructor
//			CourseCategory course = new CourseCategory(); //Here we need to instantiate CourseCategory objects from GUI
//			categoryList.add(course);
//		}
		for(int i=0;i<categoryList.size();i++){
			overallGrade += categoryList.get(i).calcGradeCategory();
			System.out.println("the (overallgrade) category name is "+categoryList.get(i).getName()+" and points are "+categoryList.get(i).calcGradeCategory());
		}
		this.overallGrade = overallGrade;
	}
	
	
	//this makes a copy of the overall grade object with a new object, so the references are not the same
	//This is ugly as hell. Only needed to get it to work to support a deadline, but will be cleaned up
	public static OverallGrade copyOverallGrade(OverallGrade overallGradeObj) {
		double overallGradeNew = overallGradeObj.getOverallGrade();
		ArrayList<CourseCategory> newCategoryList = new ArrayList<CourseCategory>();
		
		for(int i = 0; i < overallGradeObj.getCourseCategoryList().size(); i++) {
			String newCategoryName = overallGradeObj.getCourseCategoryList().get(i).getName();
			double newCategoryWeight = overallGradeObj.getCourseCategoryList().get(i).getWeight();
			newCategoryList.add(new CourseCategory(newCategoryName, newCategoryWeight, new ArrayList<SubCategory>() ));
		}
		OverallGrade newOverallGrade = new OverallGrade(overallGradeNew, newCategoryList.size(), newCategoryList);
		return newOverallGrade;
	}
	
	//==========================
	// Getters
	//==========================
	public double getOverallGrade() {
		return this.overallGrade;
	}
	
	public int getNumOfCategories(){
		return this.numOfCategories;
	}
	
	public ArrayList<CourseCategory> getCourseCategoryList(){
		return this.categoryList;
	}
	
	//==========================
	// Setters
	//==========================
	public void setOverallGrade(double overallGrade) {
		this.overallGrade = overallGrade;
	}
	
	public void setNumOfCategories(int numOfCategories){
		this.numOfCategories = numOfCategories;
	}
    
   public void setCategoryList(CourseCategory categoryList) {
		this.categoryList.add(categoryList);
	}

}

