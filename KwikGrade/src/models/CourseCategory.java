package models;

import java.io.Serializable;
import java.util.ArrayList;

public class CourseCategory implements Serializable {
	String name;
	double weight;
	int numOfSubCat; // We need to determine the number of sub categories each Category has through the GUI
	ArrayList<SubCategory> subCategoryList;

	// TODO: delete this empty constructor when we can properly instantiate CourseCategory
	public CourseCategory() {

	}

	public CourseCategory(String name, double weight, ArrayList<SubCategory> subCategoryList) {
		this.name = name;
		this.weight = weight;
		this.subCategoryList = subCategoryList;
	}
	
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return this.name;
	}
	
	public void setWeight(double weight){
		this.weight = weight;
	}
	public double getWeight(){
		return this.weight;
	}
	
	public void setNumOfsubCat(int numOfSubCat){
		this.numOfSubCat = numOfSubCat;
	}
	public int getNumOfSubCat(){
		return this.numOfSubCat;
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
			result+=subCategoryList.get(i).calcWeightedValue();
		}
		return result*getWeight();
	}
}