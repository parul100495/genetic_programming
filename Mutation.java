import java.util.ArrayList;
import java.util.Random;


public class Mutation 
{
	public Mutation(){}
	CloneTree clone = new CloneTree();
	FitnessValue fv = new FitnessValue();
	ConstraintTree ct = new ConstraintTree();
	MakeConstraintTree mct = new MakeConstraintTree();
	TreeAddresses ta = new TreeAddresses();
	ReqNode rqn = new ReqNode();
	
	Node genMutation(Node parent, int numOfInputs) 
	{
		Node child = new Node();
		child = clone.clone_tree(parent);

		ArrayList<Node> TNA = new ArrayList<Node>();
		String[] functionSet = { "+", "-", "*", "/", "^", "sin", "cos" };
		String[] CosSinSet = { "sin", "cos" };
		TNA = ta.tree_addresses(child, TNA);
		Random randomizer = new Random();
		// String val = String.valueOf(TNA[]);

		// int randomNum = 0 + (1 - 0) * randomizer.nextInt();
		int randomNum = randomizer.nextInt((1 - 0) + 1) + 0;

		Node val = TNA.get(randomizer.nextInt(((TNA.size() - 1) - 0) + 1) + 0);
		Node nd = rqn.req_node(child, val);
		if (randomNum == 1) 
		{
			// If Function Node
			if (nd.data.equals("+") || nd.data.equals("-")|| nd.data.equals("*") || nd.data.equals("/")|| nd.data.equals("^")) 
			{
				nd.data = String.valueOf(functionSet[(randomizer.nextInt(((4) - 0) + 1) + 0)]);
			} 
			else if (nd.data.equals("sine") || nd.data.equals("cosine")
					|| nd.data.equals("sin") || nd.data.equals("cos")) 
			{
				nd.data = String.valueOf(CosSinSet[(randomizer.nextInt(((CosSinSet.length - 1) - 0) + 1) + 0)]);
				// nd.data = String.valueOf(CosSinSet[(randomizer.nextInt(((2) -
				// 0) + 1) + 0)]);
			} 
			else 
			{
				nd.data = "f" + (randomizer.nextInt(numOfInputs));
			//		System.out.println(nd.data);
			}
		} 
		else 
		{
			if (nd.left != null)
				nd.left = null;
			if (nd.right != null)
				nd.right = null;
			mct.generateConsTree(nd, 1, 5, 4);
		}
		return child;
	}

	Classifier mutation(Classifier c, int classes, Double[][] sample,int numOfInputs, double alpha) 
	{
		// Mutate for every tree present in the classifier
		//Stack<Node> stack = new Stack<Node>();

		int iter = 0;
		Node[] Parent = new Node[classes];
		for (int v = 0; v < classes; v++) 
		{
			Parent[v] = clone.clone_tree(c.tree[v]);
		
		}
		Node[] Children = new Node[classes];
		Double[] Parent_Fitness = new Double[classes];
		Double[] Children_Fitness = new Double[classes];
		for (int k = 0; k < classes; k++) 
		{
			Parent_Fitness[k] = fv.fitness_value(c.tree[k], sample, k, numOfInputs, alpha);
			// System.out.println("\t\t Parent fitnesss"+Parent_Fitness[k]);
		}

		
		for (int i = 0; i < classes; i++) 
		{
			Children[i] = genMutation(Parent[i], numOfInputs);
			iter = 0;
			while (iter != 30) 
			{
				Children_Fitness[i] = fv.fitness_value(Children[i], sample, i,
						numOfInputs, alpha);
				if (Children_Fitness[i] > Parent_Fitness[i]) {
					// Replace the generated offspring with parent in classifier
					c.tree[i] = clone.clone_tree(Children[i]);
					// Update the fitness
					c.fitness[i] = Children_Fitness[i];
					break;
				} 
				else 
				{
					// Mutate Again
					Children[i] = genMutation(Parent[i], numOfInputs);
				}
				iter++;
			}
		}
		// Update the Cumulative Fitness
		c.cummulative_fitness = 0.0;
		for (int u = 0; u < classes; u++) {
			c.cummulative_fitness += c.fitness[u];
		}
		c.cummulative_fitness /= classes;
		return c;
	}
}
