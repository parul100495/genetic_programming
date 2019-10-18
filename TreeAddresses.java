import java.util.ArrayList;


public class TreeAddresses 
{
	public TreeAddresses(){}
	
	ArrayList<Node> tree_addresses(Node root, ArrayList<Node> TNA) 
	{
		if (root == null)
			return TNA;

		else 
		{
			// str = root;
			TNA.add(root);
			// Recursively call over left and right subtrees -- PRE ORDER
			tree_addresses(root.left, TNA);
			tree_addresses(root.right, TNA);
			return TNA;
		}
	}

}
