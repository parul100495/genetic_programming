
import java.util.HashSet;
import java.util.Set;

public class FitnessValue 
{
	public FitnessValue(){}
	FitnessEval fev = new FitnessEval();
	
	void NoLeaves(Node n1, Set<String> num_features){
		if(n1==null){
			return;
		}
		else{
			if(n1.left != null)
			{
				NoLeaves(n1.left, num_features);
			}
			if(n1.right!= null)
			{
				NoLeaves(n1.right, num_features);
			}
			if(n1.left == null && n1.right == null)
			{
				num_features.add(n1.data);
			}
		}
		return;
	}
	
	Double fitness_value(Node n1, Double[][] sample, int classifier,int attributes, double alpha) 
	{

		int fval = 0;

		int row = (int) Math.floor((sample.length));
		int column = sample[0].length;
		double ca_of_tree;
		Set<String> features = new HashSet<String>();
		
		//int feature_no;
		double overall;
		NoLeaves(n1,features);
		
		for (int i = 0; i < row; i++) 
		{
			ca_of_tree = fev.fitness_eval(n1, sample[i]);
			
			overall = ((alpha * (ca_of_tree / 100)) + ((1 - alpha) * (features.size() / attributes)));

			// flp = false;
			if (sample[i][0] == null) 
			{
			}
			if (overall >= 0.0 && sample[i][column - 1] == classifier) 
			{
				fval++;
			} 
			else if (overall < 0.0 && sample[i][column - 1] != classifier) 
			{
				fval++;
			}
			
		}
	//	 for (String temp : features) {
		//        System.out.print(temp + "  ");
		  //   }
		// System.out.println();
		return (double) ((fval * 100) / sample.length);
	}

}
