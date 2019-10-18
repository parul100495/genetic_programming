import java.util.ArrayList;
import java.util.Random;


public class RandomSubTreeInterchange 
{
	public RandomSubTreeInterchange(){}
	ConstraintTree ct = new ConstraintTree();
	//SwapSubTrees sst = new SwapSubTrees();
	TreeAddresses ta = new TreeAddresses();
	PrintTree tt = new PrintTree();
	Node[] random_subtree_interchange(Node p1, Node p2) 
	{

		ArrayList<Node> TNA1 = new ArrayList<Node>();
		ArrayList<Node> TNA2 = new ArrayList<Node>();
		ArrayList<Node> TerminalNodes1 = new ArrayList<Node>();
		ArrayList<Node> FunctionalNodes1 = new ArrayList<Node>();
		ArrayList<Node> TerminalNodes2 = new ArrayList<Node>();
		ArrayList<Node> FunctionalNodes2 = new ArrayList<Node>();

		ArrayList<Node> SinCosNodes1 = new ArrayList<Node>();
		ArrayList<Node> SinCosNodes2 = new ArrayList<Node>();

		TNA1 = ta.tree_addresses(p1, TNA1);
		TNA2 = ta.tree_addresses(p2, TNA2);
		Random randomizer = new Random();
		// Prevent roots from interchanging
		int k;

		for (k = 0; k < TNA1.size(); k++) 
		{
			if ((TNA1.get(k).left == null) && (TNA1.get(k).right == null)) 
			{
				// Terminal Node
				TerminalNodes1.add(TNA1.get(k));

			} 
			else 
			{
				if (TNA1.get(k).data.equals("sin")|| TNA1.get(k).data.equals("cos")
						|| TNA1.get(k).data.equals("sine") || TNA1.get(k).data.equals("cosine")) 
				{
					SinCosNodes1.add(TNA1.get(k));
				} 
				else 
				{
					// Function node
					FunctionalNodes1.add(TNA1.get(k));
				}
			}
		}

		for (k = 0; k < TNA2.size(); k++) 
		{
			if ((TNA2.get(k).left == null) && (TNA2.get(k).right == null)) 
			{
				// Terminal Node
				TerminalNodes2.add(TNA2.get(k));
			} 
			else 
			{
				if (TNA2.get(k).data.equals("sin")
						|| TNA2.get(k).data.equals("cos")
						|| TNA2.get(k).data.equals("sine")
						|| TNA2.get(k).data.equals("cosine")) 
				{
					SinCosNodes2.add(TNA2.get(k));
				} 
				else 
				{
					// Function node
					FunctionalNodes2.add(TNA2.get(k));
				}
			}
		}

		// Generate a random number double between 0 and 1
		double randomNum = 0 + (1 - 0) * randomizer.nextDouble();

		if ((FunctionalNodes1.size() > 1) && (FunctionalNodes2.size() > 1)) 
		{
			if (randomNum < 0.25) 
			{
				// Swap two terminal nodes
				Node nd1 = TerminalNodes1.get(0 + (int) (Math.random() * ((TerminalNodes1.size() - 1) - 0)) + 1);
				Node nd2 = TerminalNodes2.get(0 + (int) (Math.random() * ((TerminalNodes2.size() - 1) - 0)) + 1);
				
				if (nd1.parent == null || nd2.parent == null);
				else{
					Node parent1 = nd1.parent;
					Node parent2 = nd2.parent;
					// Change the parent pointer
					nd1.parent = parent2;
					nd2.parent = parent1;

					// Change the Parent's left or right pointer respectively
					if ((parent1.left == nd1) && (parent2.left == nd2)) {
						parent1.left = nd2;
						parent2.left = nd1;
					}
					if ((parent1.left == nd1) && (parent2.right == nd2)) {
						parent1.left = nd2;
						parent2.right = nd1;
					}
					if ((parent1.right == nd1) && (parent2.left == nd2)) {
						parent1.right = nd2;
						parent2.left = nd1;
					}
					if ((parent1.right == nd1) && (parent2.right == nd2)) {
						parent1.right = nd2;
						parent2.right = nd1;
					}
				}

			}
			// Search for two sine or cosine nodes
			else if ((SinCosNodes1.size() > 0) && (SinCosNodes2.size() > 0)
					&& randomNum < 0.5 && randomNum > 0.35) 
			{
				Node nd1 = SinCosNodes1.get(0 + (int) (Math.random() * ((SinCosNodes1.size() - 1) - 0)));
				Node nd2 = SinCosNodes2.get(0 + (int) (Math.random() * ((SinCosNodes2.size() - 1) - 0)));
				
				if (nd1.parent == null || nd2.parent == null);
				else{
					Node parent1 = nd1.parent;
					Node parent2 = nd2.parent;
					// Change the parent pointer
					nd1.parent = parent2;
					nd2.parent = parent1;

					// Change the Parent's left or right pointer respectively
					if ((parent1.left == nd1) && (parent2.left == nd2)) {
						parent1.left = nd2;
						parent2.left = nd1;
					}
					if ((parent1.left == nd1) && (parent2.right == nd2)) {
						parent1.left = nd2;
						parent2.right = nd1;
					}
					if ((parent1.right == nd1) && (parent2.left == nd2)) {
						parent1.right = nd2;
						parent2.left = nd1;
					}
					if ((parent1.right == nd1) && (parent2.right == nd2)) {
						parent1.right = nd2;
						parent2.right = nd1;
					}
				}
				
			} 
			else if (randomNum > 0.25 && randomNum < 0.55) 
			{
				// Swap a function node with terminal node
				Node nd1 = FunctionalNodes1.get(1 + (int) (Math.random() * ((FunctionalNodes1.size() - 1) - 1)));
				Node nd2 = TerminalNodes2.get(0 + (int) (Math.random() * ((TerminalNodes2.size() - 1) - 0)) + 1);
				
				if (nd1.parent == null || nd2.parent == null);
				else{
					Node parent1 = nd1.parent;
					Node parent2 = nd2.parent;
					// Change the parent pointer
					nd1.parent = parent2;
					nd2.parent = parent1;

					// Change the Parent's left or right pointer respectively
					if ((parent1.left == nd1) && (parent2.left == nd2)) {
						parent1.left = nd2;
						parent2.left = nd1;
					}
					if ((parent1.left == nd1) && (parent2.right == nd2)) {
						parent1.left = nd2;
						parent2.right = nd1;
					}
					if ((parent1.right == nd1) && (parent2.left == nd2)) {
						parent1.right = nd2;
						parent2.left = nd1;
					}
					if ((parent1.right == nd1) && (parent2.right == nd2)) {
						parent1.right = nd2;
						parent2.right = nd1;
					}
				}
				
			} 
			else if (randomNum > 0.55 && randomNum < 0.85) 
			{
				Node nd1 = TerminalNodes1.get(0 + (int) (Math.random() * ((TerminalNodes1.size() - 1) - 0)) + 1);
				Node nd2 = FunctionalNodes2.get(1 + (int) (Math.random() * ((FunctionalNodes2.size() - 1) - 1)));
				
				if (nd1.parent == null || nd2.parent == null);
				else{
					Node parent1 = nd1.parent;
					Node parent2 = nd2.parent;
					// Change the parent pointer
					nd1.parent = parent2;
					nd2.parent = parent1;

					// Change the Parent's left or right pointer respectively
					if ((parent1.left == nd1) && (parent2.left == nd2)) {
						parent1.left = nd2;
						parent2.left = nd1;
					}
					if ((parent1.left == nd1) && (parent2.right == nd2)) {
						parent1.left = nd2;
						parent2.right = nd1;
					}
					if ((parent1.right == nd1) && (parent2.left == nd2)) {
						parent1.right = nd2;
						parent2.left = nd1;
					}
					if ((parent1.right == nd1) && (parent2.right == nd2)) {
						parent1.right = nd2;
						parent2.right = nd1;
					}
				}
				
			} 
			else 
			{
				// Swap two Function nodes
				Node nd1 = FunctionalNodes1.get(1 + (int) (Math.random() * ((FunctionalNodes1.size() - 1) - 1)));
				Node nd2 = FunctionalNodes2.get(1 + (int) (Math.random() * ((FunctionalNodes2.size() - 1) - 1)));
				
				if (nd1.parent == null || nd2.parent == null);
				else{
					Node parent1 = nd1.parent;
					Node parent2 = nd2.parent;
					// Change the parent pointer
					nd1.parent = parent2;
					nd2.parent = parent1;

					// Change the Parent's left or right pointer respectively
					if ((parent1.left == nd1) && (parent2.left == nd2)) {
						parent1.left = nd2;
						parent2.left = nd1;
					}
					if ((parent1.left == nd1) && (parent2.right == nd2)) {
						parent1.left = nd2;
						parent2.right = nd1;
					}
					if ((parent1.right == nd1) && (parent2.left == nd2)) {
						parent1.right = nd2;
						parent2.left = nd1;
					}
					if ((parent1.right == nd1) && (parent2.right == nd2)) {
						parent1.right = nd2;
						parent2.right = nd1;
					}
				}
				
			}
		} 
		else 
		{
			// Search for two sine or cosine nodes
			if ((SinCosNodes1.size() > 0) && (SinCosNodes2.size() > 0)&& randomNum < 0.5 && randomNum > 0.35) 
			{
				Node nd1 = SinCosNodes1.get(0 + (int) (Math.random() * ((SinCosNodes1.size() - 1) - 0)));
				Node nd2 = SinCosNodes2.get(0 + (int) (Math.random() * ((SinCosNodes2.size() - 1) - 0)));
				
				if (nd1.parent == null || nd2.parent == null);
				else{
					Node parent1 = nd1.parent;
					Node parent2 = nd2.parent;
					// Change the parent pointer
					nd1.parent = parent2;
					nd2.parent = parent1;

					// Change the Parent's left or right pointer respectively
					if ((parent1.left == nd1) && (parent2.left == nd2)) {
						parent1.left = nd2;
						parent2.left = nd1;
					}
					if ((parent1.left == nd1) && (parent2.right == nd2)) {
						parent1.left = nd2;
						parent2.right = nd1;
					}
					if ((parent1.right == nd1) && (parent2.left == nd2)) {
						parent1.right = nd2;
						parent2.left = nd1;
					}
					if ((parent1.right == nd1) && (parent2.right == nd2)) {
						parent1.right = nd2;
						parent2.right = nd1;
					}
				}
				
			}
			else 
			{
				if((TerminalNodes1.size() > 1) && (TerminalNodes2.size() > 1)){
					// Swap two terminal nodes
					Node nd1 = TerminalNodes1.get(0 + (int) (Math.random() * ((TerminalNodes1.size() - 1) - 0)));
					Node nd2 = TerminalNodes2.get(0 + (int) (Math.random() * ((TerminalNodes2.size() - 1) - 0)));
					
					if (nd1.parent == null || nd2.parent == null);
					else{
						Node parent1 = nd1.parent;
						Node parent2 = nd2.parent;
						// Change the parent pointer
						nd1.parent = parent2;
						nd2.parent = parent1;

						// Change the Parent's left or right pointer respectively
						if ((parent1.left == nd1) && (parent2.left == nd2)) {
							parent1.left = nd2;
							parent2.left = nd1;
						}
						if ((parent1.left == nd1) && (parent2.right == nd2)) {
							parent1.left = nd2;
							parent2.right = nd1;
						}
						if ((parent1.right == nd1) && (parent2.left == nd2)) {
							parent1.right = nd2;
							parent2.left = nd1;
						}
						if ((parent1.right == nd1) && (parent2.right == nd2)) {
							parent1.right = nd2;
							parent2.right = nd1;
						}
					}
			}
				
			}
		}

		Node[] rt = { p1, p2 };
		return rt;

	}

}
