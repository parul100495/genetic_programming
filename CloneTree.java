
public class CloneTree 
{
	public CloneTree(){}
	
	Node clone_tree(Node x) 
	{
		if (x == null)
			return null;
		Node y = new Node();
		y.data = x.data;
		y.parent = null;
		y.left = clone_tree(x.left);
		y.right = clone_tree(x.right);
		if (y.left != null)
			y.left.parent = y;
		if (y.right != null)
			y.right.parent = y;
		return y;
	}
}
