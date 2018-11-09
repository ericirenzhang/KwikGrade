
public class SubCategory {
	String name;
	double weight;
	double value;
	double points_gained;
	double total_points;
	
	public SubCategory() {
		
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
	
	public void setPointsGained(double points_gained)
	{
		this.points_gained = points_gained;
	}
	public double getPointsGained()
	{
		return this.points_gained;
	}
	
	public void setTotalPoints(double total_points)
	{
		this.total_points = total_points;
	}
	public double getTotalPoints()
	{
		return this.total_points;
	}
	
	public double CalcValue()
	{
		return (points_gained/total_points)*weight;
	}
	
	public double CalcActualGrade()
	{
		return (points_gained/total_points);
	}
}
