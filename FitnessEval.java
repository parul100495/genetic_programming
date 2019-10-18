
public class FitnessEval 
{
	public FitnessEval(){}
	Double fitness_eval(Node root, Double[] data1) 
	{

		if (root == null)
			return 0.0;
		if (root.data.charAt(0) == '+')
			return fitness_eval(root.left, data1)+ fitness_eval(root.right, data1);
		else if (root.data.charAt(0) == '*')
			return fitness_eval(root.left, data1)* fitness_eval(root.right, data1);
		else if (root.data.charAt(0) == '-')
			return fitness_eval(root.left, data1)- fitness_eval(root.right, data1);
		else if (root.data.charAt(0) == '/')
			return fitness_eval(root.left, data1)/ fitness_eval(root.right, data1);
		else if (root.data.charAt(0) == 'f') 
		{
			String parts[] = root.data.split("f");
			return data1[Integer.parseInt(parts[1])];
		}
		else if (root.data == "sin") 
		{
			return Math.sin(fitness_eval(root.left, data1));
		} 
		else if (root.data == "cos") 
		{
			return Math.cos(fitness_eval(root.left, data1));
		} 
		else if (root.data.charAt(0) == '^') 
		{
			return Math.pow(fitness_eval(root.left, data1),fitness_eval(root.right, data1));
		}
		else 
		{
			return Double.parseDouble(root.data.trim());
		}
	}
}
		

