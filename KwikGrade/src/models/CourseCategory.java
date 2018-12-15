package models;

import java.io.Serializable;
import java.util.ArrayList;

public class CourseCategory implements Serializable {
	private String name;
	private double weight;
	private double categoryFinalWeightedScore;
	private ArrayList<SubCategory> subCategoryList;

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

	// Rebalances SubCategory Weights when a new SubCategoryItem has been added
	public void balanceWeightSubcategory() {
		int subCatSize = this.subCategoryList.size();
		double gradeWeight = 0.0;

		if(subCatSize != 0) {
			gradeWeight = 1.0/subCatSize;
		}

		for(int i = 0; i < subCatSize; i++) {
			subCategoryList.get(i).setWeight(gradeWeight);
		}
	}
	
	public void addSubCategory(SubCategory subCategory) {
		this.subCategoryList.add(subCategory);
		this.updateCategoryFinalWeightedScore();
	}

	/**
	 * Recomputes the Category's Final Weighted Score.
	 *
	 * i.e. When a new SubCategory has been added or CourseCategory weighting gets updated, the final raw score
	 * needs to be recomputed.
 	 */
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

	public void setSubCategoryList(ArrayList<SubCategory> subCategoryList) {
		this.subCategoryList = subCategoryList;
		this.updateCategoryFinalWeightedScore();
	}
}
