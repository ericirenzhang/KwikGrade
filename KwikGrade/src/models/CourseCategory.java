package models;

import java.io.Serializable;
import java.util.ArrayList;

public class CourseCategory implements Serializable {
	private String name;
	private double weight;
	private ArrayList<SubCategory> subCategoryList;

	// TODO: delete this empty constructor when we can properly instantiate CourseCategory
	public CourseCategory() {

	}

	public CourseCategory(String name, double weight, ArrayList<SubCategory> subCategoryList) {
		this.name = name;
		this.weight = weight;
		this.subCategoryList = subCategoryList;
	}
	
	public CourseCategory(String name, double weight) {
		this.name = name;
		this.weight = weight;
		this.subCategoryList = new ArrayList<SubCategory>();
	}
	
	public double calcGradeCategory(){
		int n = getNumOfSubCat();
		for(int i=0;i<n;i++){
			SubCategory sub = new SubCategory(); //Here we need to instantiate SubCategory objects from GUI
			subCategoryList.add(sub);
		}
		double result = 0.0;
		
		for(int i=0;i<subCategoryList.size();i++)
		{
			result+=subCategoryList.get(i).getWeightedValue();
		}
		return result*getWeight();
	}

	public void addSubCategory(SubCategory subCategory) {
		this.subCategoryList.add(subCategory);
	}
	
	//==========================
	// Getters
	//==========================
	public double getWeight(){
		return this.weight;
	}
	public String getName(){
		return this.name;
	}
	public ArrayList<SubCategory> getSubCategoryList() { return this.subCategoryList; }
	public int getNumOfSubCat(){
		return this.subCategoryList.size();
	}

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
