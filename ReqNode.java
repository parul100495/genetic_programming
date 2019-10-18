
public class ReqNode 
{
	public ReqNode(){}
	
	Node req_node(Node root, Node val) 
	{
		if (root != null) 
		{
			if (root == val)
				return root;
			else 
			{
				Node foundNode = req_node(root.left, val);
				if (foundNode == null)
					foundNode = req_node(root.right, val);
				return foundNode;
			}
		} 
		else
			return null;
	}

}
