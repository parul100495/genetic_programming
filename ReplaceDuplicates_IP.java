import java.util.ArrayList;


public class ReplaceDuplicates_IP 
{
	public ReplaceDuplicates_IP(){}
	MakeConstraintTree mct = new MakeConstraintTree();
	
	Node[] replace_duplicates_IP(Node[] x, ArrayList<Integer> index,int maxlevel, int features) 
	{
		for (int i = 1; i < index.size(); i += 2) 
		{
			mct.makeConstraintTree(x[index.get(i)], maxlevel, features);
		}
		return x;
	}
}
