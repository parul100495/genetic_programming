import java.util.Random;


public class MakeConstraintTree 
{
	public MakeConstraintTree(){}
	
	boolean CheckForSinCos(String str) 
	{
		if (str.equals("sin") || str.equals("sine") || str.equals("cos")|| str.equals("cosine"))
			return true;
		else
			return false;
	}
	void generateConsTree(Node curNode, int level, int features,int maxLevel) 
	{
		String[] functionSet = { "+", "-", "*", "/", "^", "sin", "cos" };
		int[] varSet = new int[features];
		for (int i = 0; i < features; i++) 
		{
			varSet[i] = i;
		}

		// DecimalFormat form = new DecimalFormat("#0.0000");
		Random randomGenerator = new Random();
		if (maxLevel == 0) 
		{
			return;
		}
		if (CheckForSinCos(curNode.data)) 
		{
			curNode.left = new Node();
			curNode.left.parent = curNode;
		}
		else 
		{
			curNode.left = new Node();
			curNode.left.parent = curNode;
			curNode.right = new Node();
			curNode.right.parent = curNode;
		}
		if (level < maxLevel) 
		{
			// Now randomly get the data into the trees
			// Can store the function set nodes or terminal nodes
			// double randomNum = randomGenerator.nextDouble(1 - 0)+1;
			double randomNum = 0 + (1 - 0) * randomGenerator.nextDouble();
			// nextDouble(100);

			if (randomNum < 0.55) 
			{
				// Both Nodes have data from function set
				curNode.left.data = String.valueOf(functionSet[(int) Math.round(Math.random() * 6)]);

				if (curNode.right != null) 
				{
					curNode.right.data = String.valueOf(functionSet[(int) Math.round(Math.random() * 6)]);
				}
				// Recursively call this operation over both subtrees
				generateConsTree(curNode.left, level + 1, features, maxLevel);
				if (curNode.right != null)
					generateConsTree(curNode.right, level + 1, features,maxLevel);

			} 
			else if ((randomNum < 0.7) && (randomNum > 0.56)) 
			{
				// Left has data from function set and right is a terminal
				curNode.left.data = String.valueOf(functionSet[(int) Math.round(Math.random() * 6)]);

				// Feature Set Data into right node
				if (curNode.right != null) 
				{
					curNode.right.data = "f"+ String.valueOf(varSet[(int) Math.round(Math.random() * (features - 1))]);
				}
				generateConsTree(curNode.left, level + 1, features, maxLevel);
			} 
			else if ((randomNum >= 0.7) && (randomNum < 0.85)) 
			{
				// Right has a data from function set and left is a terminal
				// Feature set data into left node
				curNode.left.data = "f"+ String.valueOf(varSet[(int) Math.round(Math.random()* (features - 1))]);
				if (curNode.right != null) 
				{
					curNode.right.data = String.valueOf(functionSet[(int) Math.round(Math.random() * 6)]);
				}
				if (curNode.right != null)
					generateConsTree(curNode.right, level + 1, features,maxLevel);
			} 
			else 
			{
				// Both are terminals
				// Feature set data into both nodes
				curNode.left.data = "f"+ String.valueOf(varSet[(int) Math.round(Math.random()* (features - 1))]);

				if (curNode.right != null) 
				{
					curNode.right.data = "f"+ String.valueOf(varSet[(int) Math.round(Math.random() * (features - 1))]);
				}
			}
		}
		else 
		{
			// end the given nodes data with terminal nodes
			curNode.left.data = "f"+ String.valueOf(varSet[(int) Math.round(Math.random()* (features - 1))]);

			if (curNode.right != null) 
			{
				curNode.right.data = "f"+ String.valueOf(varSet[(int) Math.round(Math.random()* (features - 1))]);
			}
		}
	}

	void makeConstraintTree(Node root, int maxlevel, int features) 
	{
		Character[] functionSet = { '+', '-', '*', '/', '^' };
		try 
		{
			root.data = String.valueOf(functionSet[(int) Math.round(Math.random() * 4)]);
			root.parent = null;
			// generation of tree
			generateConsTree(root, 1, features, maxlevel);
		} 
		catch (Exception e) 
		{
			// System.out.println(e);
		}
	}
}
