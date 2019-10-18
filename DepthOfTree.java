
public class DepthOfTree 
{
	public DepthOfTree(){}
	
	public int depth_of_tree(Node r) 
	{
		if (r != null) {
			int temp_l = depth_of_tree(r.left);
			int temp_r = depth_of_tree(r.right);
			if (temp_l >= temp_r) {
				return temp_l;
			} else {
				return temp_r;
			}
		} else {
			return 0;
		}
	}

}
