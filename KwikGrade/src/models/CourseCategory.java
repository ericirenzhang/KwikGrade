package models;

import java.io.Serializable;
import java.util.ArrayList;

public class CourseCategory implements Serializable {
	private String name;
	private double weight;
	private double categoryFinalWeightedScore;
	private ArrayList<SubCategory> subCategoryList;

	// TODO: delete this empty constructor when we can properly instantiate CourseCategory
	public CourseCategory() {

	}

	public CourseCategory(String name, double weight, ArrayList<SubCategory> subCategoryList) {
		this.name = name;
		this.weight = weight;
		this.subCategoryList = subCategoryList;
		this.updateCategoryFinalWeightedScore();
	}
	
	public CourseCategory(String name, double weight) {
		this.name = name;
		this.weight = weight;
		this.subCategoryList = new ArrayList<SubCategory>();
	}

	public void addSubCategory(SubCategory subCategory) {
		this.subCategoryList.add(subCategory);
		this.updateCategoryFinalWeightedScore();
	}

	public void updateCategoryFinalWeightedScore() {
		categoryFinalWeightedScore = 0;
		for(int i = 0; i < subCategoryList.size(); i++) {
			categoryFinalWeightedScore += subCategoryList.get(i).getWeightedFinalScore();
		}
		this.categoryFinalWeightedScore *= this.weight;
	}
	
	//==========================
	// Getters
	//==========================
	public double getCategoryFinalWeightedScore() {
		return this.categoryFinalWeightedScore;
	}

	public double getWeight(){
		return this.weight;
	}
	public String getName(){
		return this.name;
	}
	public ArrayList<SubCategory> getSubCategoryList() { return this.subCategoryList; }

	//==========================
	// Setters
	//==========================
	public void setName(String name){
		this.name = name;
	}
	public void setWeight(double weight){
		this.weight = weight;
	}
}
