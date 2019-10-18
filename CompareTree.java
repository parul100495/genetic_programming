
public class CompareTree 
{
	public CompareTree(){}
	
	Boolean compare_tree(Node n1, Node n2) 
	{
		if (n1 == null || n2 == null)
			return false;
		Boolean temp_left = true, temp_right = true, temp_node;
		temp_node = n1.data.equals(n2.data);
		if (n1.left != null && n2.left != null)
			temp_left = compare_tree(n1.left, n2.left);
		if (n1.right != null && n2.right != null)
			temp_right = compare_tree(n1.right, n2.right);
		if (temp_node && temp_left && temp_right)
			return true;
		return false;
	}
}
