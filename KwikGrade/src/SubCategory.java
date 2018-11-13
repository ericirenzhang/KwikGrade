public class SubCategory {
	String name;
	double weight;
	double value;
	double pointsGained;
	double totalPoints;
	
	public SubCategory() {
		
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
	
	public void setPointsGained(double pointsGained){
		this.pointsGained = pointsGained;
	}
	public double getPointsGained(){
		return this.pointsGained;
	}
	
	public void setTotalPoints(double totalPoints){
		this.totalPoints = totalPoints;
	}
	public double getTotalPoints(){
		return this.totalPoints;
	}
	
	public double CalcValue(){
		return (pointsGained/totalPoints)*weight;
	}
	
	public double CalcActualGrade(){
		return (pointsGained/totalPoints);
	}
}
