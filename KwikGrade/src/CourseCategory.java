import java.util.ArrayList;

public class CourseCategory {
	String name;
	double weight;
	int num_of_subCat; // We need to determine the number of sub categories each Category has through the GUI
	ArrayList<SubCategory> subCategoryList = new ArrayList<SubCategory>();
	
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
	
	public void setNum_of_subCat(int num_of_subCat)
	{
		this.num_of_subCat = num_of_subCat;
	}
	public int getNum_of_subCat()
	{
		return this.num_of_subCat;
	}
	
	
	public double Calc_Grade_Category()
	{
		int n = getNum_of_subCat();
		for(int i=0;i<n;i++)
		{
			SubCategory sub = new SubCategory(); //Here we need to instantiate SubCategory objects from GUI
			subCategoryList.add(sub);
		}
		double result = 0.0;
		for(int i=0;i<subCategoryList.size();i++)
		{
			result+=subCategoryList.get(i).CalcValue();
		}
		return result*getWeight();
	}
}
