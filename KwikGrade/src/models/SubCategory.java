package models;

public class SubCategory {
	String name;
	double weight;
	double value;
	double pointsGained;
	double totalPoints;

	public SubCategory(String name, double weight, double value, double pointsGained, double totalPoints) {
		this.name = name;
		this.weight = weight;
		this.value = value;
		this.pointsGained = pointsGained;
		this.totalPoints = totalPoints;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	public String getName()
	{
		return this.name;
	}
	
	public void setWeight(double weight)
	{
		this.weight = weight;
	}
	public double getWeight()
	{
		return this.weight;
	}
	
	public void setPointsGained(double pointsGained)
	{
		this.pointsGained = pointsGained;
	}
	public double getPointsGained()
	{
		return this.pointsGained;
	}
	
	public void setTotalPoints(double totalPoints)
	{
		this.totalPoints = totalPoints;
	}
	public double getTotalPoints()
	{
		return this.totalPoints;
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
