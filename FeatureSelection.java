import java.util.Stack;


public class FeatureSelection 
{
	public FeatureSelection(){}
	CloneTree clone = new CloneTree();
	
	long[][] feature_selection(Classifier[] cs, int TOTAL_POPULATION) 
	{
		long[][] best_features = new long[2][22];
		for(int i=0;i< 2; i++)
		{
			for(int j=0; j< 22; j++)
			{
				best_features[i][j]=0;
			}
		}
		
		// Average of all class
		double avg_classes[] = new double[2];
		for (int k = 0; k < 2; k++) 
		{
			avg_classes[k] = 0.0;
			for (int j = 0; j < TOTAL_POPULATION; j++) 
			{
				avg_classes[k] += cs[j].fitness[k];
			}
			avg_classes[k] /= TOTAL_POPULATION;
		}
		
		// Feature Selection
		for (int i = 0; i < 2; i++) 
		{
			for (int j = 0; j < TOTAL_POPULATION; j++) 
			{
				if (cs[j].fitness[i] > avg_classes[i]) 
				{
					// Inorder Traversal
					Stack<Node> stack = new Stack<Node>();
					// define a pointer to track nodes
					Node p = clone.clone_tree(cs[j].tree[i]);

					while (!stack.empty() || p != null) 
					{
						if (p != null) 
						{
							stack.push(p);
							p = p.left;

						} 
						else 
						{
							Node t = stack.pop();
							if (t.data.charAt(0) == 'f') 
							{
								String parts[] = t.data.split("f");
								//System.out.println(parts[1]);
								/*if(Integer.parseInt(parts[1]) == 18)
								{
									System.out.println("this is 18");
								}*/
								best_features[i][Integer.parseInt(parts[1])]++;
							}
							p = t.right;
						}
					}
					// End of Inorder Traversal
				} 
				else
					break;
			}
		}/*
		double weight_avg[] = new double[2];
		for (int i = 0; i < 2; i++) {
			weight_avg[i] = 0.0;
			for (int j = 0; j < 22; j++) {
				weight_avg[i] += best_features[i][j];
			}
			weight_avg[i] /= 22;
		}
		ArrayList<Integer>[] best_f = new ArrayList[2];
		for (int i = 0; i < 2; i++)
			best_f[i] = new ArrayList<Integer>();
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 22; j++) {
				if (best_features[i][j] >= weight_avg[i])
					best_f[i].add(j);
			}
		}

		// End of Feature Selection

		return best_f;*/
		return best_features;
	}

}
