
public class CloneClassifier 
{
	public CloneClassifier(){}
	CloneTree clone = new CloneTree();
	
	Classifier clone_classifier(Classifier x, int classes) 
	{
		
		Classifier y = new Classifier(classes);
		for (int i = 0; i < classes; i++) {
			y.tree[i] = clone.clone_tree(x.tree[i]);
			y.fitness[i] = x.fitness[i];
		}
		y.cummulative_fitness = x.cummulative_fitness;
		return y;
	}
}
