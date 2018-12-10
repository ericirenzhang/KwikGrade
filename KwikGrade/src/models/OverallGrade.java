package models;

import models.CourseCategory;
import models.Student;

import java.io.Serializable;
import java.util.ArrayList;

public class OverallGrade implements Serializable {
	private double overallGrade;
	private ArrayList<CourseCategory> categoryList = new ArrayList<CourseCategory>();

	//generic constructor
	public OverallGrade() {
		overallGrade = 0.0;
		categoryList = new ArrayList<CourseCategory>();
	}

	//constructor if someone wants to define all variables
	public OverallGrade(double overallGrade, ArrayList<CourseCategory> categoryList) {
		this.overallGrade = overallGrade;
		this.categoryList = categoryList;
	}
  
	//constructor if someone wants to define the categorylist
	public OverallGrade(ArrayList<CourseCategory> categoryList) {
		this.overallGrade = 0.0;
		this.categoryList = categoryList;
	}
	
	//adds a CourseCategory object to the OverallGrade object
	public void addCourseCategory(String name, double weight) {
		CourseCategory categoryToAdd = new CourseCategory(name, weight);
		this.categoryList.add(categoryToAdd);
		this.updateOverallGrade();
	}

	public void addCourseCategory(CourseCategory courseCategory) {
		this.categoryList.add(courseCategory);
		this.updateOverallGrade();
	}
	
	public void balanceAssignWeights() {
		for(int i = 0; i < this.categoryList.size(); i++) {
			this.categoryList.get(i).balanceWeightSubcategory();
		}
	}

	public void updateOverallGrade() {
		this.overallGrade = 0;
		for(int i = 0; i < this.categoryList.size(); i++) {
			this.categoryList.get(i).updateCategoryFinalWeightedScore();
			this.overallGrade += this.categoryList.get(i).getCategoryFinalWeightedScore();
		}
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
	
	//this makes a copy of the overall grade object with a new object, so the references are not the same
	//Actually I think this is OK...I did some googling, basically only way to make a new object is to make a NEW one, then just grab attributes from the old one
	public static OverallGrade copyOverallGrade(OverallGrade overallGradeObj) {
		double overallGradeNew = overallGradeObj.getOverallGrade();
		ArrayList<CourseCategory> newCategoryList = new ArrayList<CourseCategory>();

		// Clone the category.
		for(int i = 0; i < overallGradeObj.getCourseCategoryList().size(); i++) {
			String newCategoryName = overallGradeObj.getCourseCategoryList().get(i).getName();
			double newCategoryWeight = overallGradeObj.getCourseCategoryList().get(i).getWeight();
			CourseCategory clonedCategory = new CourseCategory(newCategoryName, newCategoryWeight, new ArrayList<SubCategory>());

			// Clone the subcategories
			for(int j = 0; j < overallGradeObj.getCourseCategoryList().get(i).getSubCategoryList().size(); j++) {
				SubCategory originalSubCategory = overallGradeObj.getCourseCategoryList().get(i).getSubCategoryList().get(j);
				String newSubCategoryName = originalSubCategory.getName();
				double newSubCategoryWeight = originalSubCategory.getWeight();
				double newSubCategoryPointsGained = originalSubCategory.getPointsGained();
				double newSubCategoryTotalPoints = originalSubCategory.getTotalPoints();
				SubCategory clonedSubCategory = new SubCategory(newSubCategoryName, newSubCategoryWeight, newSubCategoryPointsGained, newSubCategoryTotalPoints);
				clonedCategory.addSubCategory(clonedSubCategory);
			}
			newCategoryList.add(clonedCategory);
		}
		OverallGrade newOverallGrade = new OverallGrade(overallGradeNew, newCategoryList);
		return newOverallGrade;
	}

	public void deleteCategory(String categoryName) {
		for(int i = 0; i < categoryList.size(); i++) {
			CourseCategory currCategory = categoryList.get(i);
			if(currCategory.getName().equals(categoryName)) {
				categoryList.remove(currCategory);
				break;
			}
		}
	}
	
	//function that rounds (instead of truncating) a double to specified number of decimal places
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}

	//==========================
	// Getters
	//==========================
	public double getOverallGrade() {
		return round(this.overallGrade, 2);
	}
	
	public int getNumOfCategories(){
		return this.categoryList.size();
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
    
   public void addCategoryList(CourseCategory categoryList) {
		this.categoryList.add(categoryList);
	}

	public void setCategoryList(ArrayList<CourseCategory> categoryList) {
		this.categoryList = categoryList;
		this.updateOverallGrade();
	}
}

