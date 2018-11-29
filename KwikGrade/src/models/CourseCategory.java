package models;

import java.io.Serializable;
import java.util.ArrayList;

public class CourseCategory implements Serializable {
	private String name;
	private double weight;
	private int numOfSubCat; // We need to determine the number of sub categories each Category has through the GUI
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
	
	public void addSubCategory(String name, double weight, double value, double pointsGained, double totalPoints) {
		this.subCategoryList.add(new SubCategory(name, weight, value, pointsGained, totalPoints));
	}
	
	public double calcGradeCategory(){
		int n = subCategoryList.size();
//		for(int i=0 ; i < n ; i++){
//			SubCategory sub = new SubCategory(); //Here we need to instantiate SubCategory objects from GUI
//			subCategoryList.add(sub);
//		}
		double result = 0.0;
		
		for(int i=0 ; i<subCategoryList.size() ; i++)
		{
			result = result + subCategoryList.get(i).calcWeightedValue();
			//System.out.println("weight is "+subCategoryList.get(i).getWeight()+" points gained "+subCategoryList.get(i).getPointsGained()+" total points "+subCategoryList.get(i).getTotalPoints());
		}
		System.out.println("This category name is "+name+" and the points contrib is "+result);

		return result*getWeight();
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
	public int getNumOfSubCat(){
		return this.numOfSubCat;
	}
	
	public ArrayList<SubCategory> getSubCategoryList() {
		return this.subCategoryList;
	}
	
	//==========================
	// Setters
	//==========================
	public void setName(String name){
		this.name = name;
	}
	public void setNumOfsubCat(int numOfSubCat){
		this.numOfSubCat = numOfSubCat;
	}
	public void setWeight(double weight){
		this.weight = weight;
	}
}
