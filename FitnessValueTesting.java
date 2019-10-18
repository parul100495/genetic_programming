import java.util.HashSet;
import java.util.Set;

public class FitnessValueTesting 
{
	public FitnessValueTesting(){}
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
	
	Double fitness_value_testing(Node n1, Double[][] sample, int classifier, int attributes, double alpha) 
	{
	/*	Double tp, tn, fp, fn;
		
		tn = truenegatives(n1, sample, classifier);
		fp = falsepositives(n1, sample, classifier);
		fn = falsenegatives(n1, sample, classifier);
		tp = truepositives(n1, sample, classifier);
		
		System.out.println(tn + " " + tp + " " + fp + " " + fn );
		return (double) (tp + tn);
		// return (double)fv;
	*/
	
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
		// for (String temp : features) {
		       // System.out.println(temp);
		 //    }
		return (double) ((fval * 100) / sample.length);
	}
	
	Double truepositives(Node n1, Double[][] sample, int classifier) 
	{
		int tp = 0;
		int row = (int) Math.floor((sample.length));
		int column = sample[0].length;
		for (int i = 0; i < row; i++) 
		{
			//flp = false;
			if (sample[i][0] == null) 
			{}
			if (fev.fitness_eval(n1, sample[i]) >= 0.0 && sample[i][column - 1] == classifier) 
			{
				tp++;
				//flp = true;
			} 
		}
		return (double) ((tp * 100) / (row-1));
		// return (double)fv;
	}
	
	Double truenegatives(Node n1, Double[][] sample, int classifier) 
	{
		int tn = 0;
		int row = (int) Math.floor((sample.length));
		int column = sample[0].length;
		for (int i = 0; i < row; i++) 
		{
			//flp = false;
			if (sample[i][0] == null) 
			{}
			if (fev.fitness_eval(n1, sample[i]) < 0.0 && sample[i][column - 1] != classifier) 
			{
				tn++;
				//flp = true;
			} 
		}
		if(tn==0){
			tn = 1;
		}
		return (double) ((tn * 100) / (row-1));
		// return (double)fv;
	}
	
	Double falsepositives(Node n1, Double[][] sample, int classifier) 
	{
		int fp = 0;
		//boolean flp;
		int row = (int) Math.floor((sample.length));
		int column = sample[0].length;
		for (int i = 0; i < row; i++) 
		{
			//flp = false;
			if (sample[i][0] == null) 
			{}
			if (fev.fitness_eval(n1, sample[i]) < 0.0 && sample[i][column - 1] == classifier) 
			{
				fp++;
				//flp = true;
			} 
		}
		return (double) ((fp * 100) / (row-1));
		// return (double)fv;
	}
	
	Double falsenegatives(Node n1, Double[][] sample, int classifier) 
	{
		int fn = 0;
		//boolean flp;
		int row = (int) Math.floor((sample.length));
		int column = sample[0].length;
		for (int i = 0; i < row; i++) 
		{
			//flp = false;
			if (sample[i][0] == null) 
			{}
			if (fev.fitness_eval(n1, sample[i]) >= 0.0 && sample[i][column - 1] != classifier) 
			{
				fn++;
				//flp = true;
			}
		}
		return (double) ((fn * 100) / (row-1));
		// return (double)fv;
	}
	
}
