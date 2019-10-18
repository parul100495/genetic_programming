
public class PrintTree 
{
	public PrintTree(){}
	
	void print_tree(Node n) 
	{
		if (n.left != null)
			print_tree(n.left);
		System.out.print(n.data + " ");
		if (n.right != null)
			print_tree(n.right);
	}
}
