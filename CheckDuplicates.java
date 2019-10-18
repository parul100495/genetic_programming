import java.util.ArrayList;


public class CheckDuplicates 
{
	public CheckDuplicates(){}
	CompareTree ct = new CompareTree();
	ArrayList<Integer> check_duplicates(Node[] x) 
	{
		ArrayList<Integer> ret = new ArrayList<Integer>();
		int len = x.length;
		for (int i = 0; i < len - 1; i++) 
		{
			for (int j = i + 1; j < len; j++) 
			{
				if (ct.compare_tree(x[i], x[j])) 
				{
					ret.add(i);
					ret.add(j);
				}
			}
		}
		return ret;
	}
}
