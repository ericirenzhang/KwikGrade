package models;

import java.io.Serializable;

public class SubCategory implements Serializable {
	String name;
	double weight;
	double rawFinalScore;
	double pointsGained;
	double totalPoints;

	public SubCategory(String name) {
		this.name = name;
	}

	public SubCategory(String name, double weight, double pointsGained, double totalPoints) {
		this.name = name;
		this.weight = weight;
		this.pointsGained = pointsGained;
		this.totalPoints = totalPoints;
		this.rawFinalScore = this.pointsGained / this.totalPoints;
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


	public void updateRawFinalScore() {
		this.rawFinalScore = (this.pointsGained / this.totalPoints) * 100;
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
		this.updateRawFinalScore();
	}

	public void setTotalPoints(double totalPoints){
		this.totalPoints = totalPoints;
		this.updateRawFinalScore();
	}

	public double getRawFinalScore() {
		return this.rawFinalScore;
	}

	public void setRawFinalScore(double rawFinalScore) {
		this.rawFinalScore = rawFinalScore;
	}

	/**
	 * Returns the raw score for a SubCategory times the weight.
	 *
	 * @return
	 */
	public double getWeightedFinalScore()
	{
		return (pointsGained/totalPoints) * weight * 100;
	}
}
