package models;

import java.io.Serializable;

public class SubCategory implements Serializable {
	String name;
	double weight;
	double value;
	double pointsGained;
	double totalPoints;

	// TODO: Get rid of this empty constructor once CourseCategory can properly instantiate SubCategory with parameters
	public SubCategory() {

	}

	public SubCategory(String name, double weight, double value, double pointsGained, double totalPoints) {
		this.name = name;
		this.weight = weight;
		this.value = value;
		this.pointsGained = pointsGained;
		this.totalPoints = totalPoints;
	}
	
	//==========================
	// Getters
	//==========================
	public String getName(){
		return this.name;
	}
	
	public double getWeight(){
		return this.weight;
	}
	
	public double getPointsGained(){
		return this.pointsGained;
	}
	
	public double getTotalPoints(){
		return this.totalPoints;
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

	public void setPointsGained(double pointsGained){
		this.pointsGained = pointsGained;
	}

	public void setTotalPoints(double totalPoints){
		this.totalPoints = totalPoints;
	}

	/**
	 * Returns the raw score for a SubCategory times the weight.
	 *
	 * @return
	 */
	public double calcWeightedValue()
	{
		return (pointsGained/totalPoints)*weight;
	}
	
	public double calcRawScore()
	{
		return (pointsGained/totalPoints);
	}
}
