
public class twopointcrossover 
{
	public twopointcrossover(){}
	CloneClassifier cc = new CloneClassifier();
	CloneTree clone = new CloneTree();
	FitnessValue fv = new FitnessValue();
	RandomSubTreeInterchange rsti = new RandomSubTreeInterchange();
	RandomSubTreeInterchange2 rsti2 = new RandomSubTreeInterchange2();
	PrintTree tt = new PrintTree();
	
	Node[] genCrossOver(Node p1, Node p2, Double[][] sample, int classifier, int classes, int attributes, double alpha) 
	{
		Node[] child = new Node[4];
		child[0] = clone.clone_tree(p1);
		child[1] = clone.clone_tree(p2);
		
		double fc1, fc2, fp1, fp2;
		fp1 = fv.fitness_value(p1, sample, classifier, attributes, alpha);
		fp2 = fv.fitness_value(p2, sample, classifier, attributes, alpha);
		fc1 = fp1;
		fc2 = fp2;
		int iter = 0;
		while(! (fc1>fp1 && fc2 > fp2) && (iter != 50))
		 {
			Node[] temp = new Node[2];
			//Node[] temp1 = new Node[2];

			iter++;

			if (fc1 > fp1 && fc1 > fp2) 
			{
				// Need to change only second child
				temp = rsti.random_subtree_interchange(p1, p2);
				//temp = rsti2.random_subtree_interchange2(temp[0], temp[1]);
				child[1] = clone.clone_tree(temp[1]);
				fc2 = fv.fitness_value(child[1], sample, classifier,attributes, alpha);
			}
			if (fc2 > fp1 && fc2 > fp2) {
				// Need to change only first child
				
				temp = rsti.random_subtree_interchange(p1, p2);
				//temp = rsti2.random_subtree_interchange2(temp[0], temp[1]);
				child[0] = clone.clone_tree(temp[0]);
				fc1 = fv.fitness_value(child[0], sample, classifier, attributes,alpha);
			}
			else {
				// Need to change both
				double fl1, fl2;
				child = rsti.random_subtree_interchange(p1, p2);
				//child = rsti2.random_subtree_interchange2(child[0], child[1]);
				fl1 = fv.fitness_value(child[0], sample, classifier, attributes, alpha);
				fl2 = fv.fitness_value(child[1], sample, classifier, attributes, alpha);
				fc1 = fl1;
				fc2 = fl2;
			}
		}
		if (iter == 50) 
		{
			if (fc1 > fp1 && fc1 > fp2)
				child[1] = clone.clone_tree(p2);
			else if (fc2 > fp1 && fc2 > fp2)
				child[0] = clone.clone_tree(p1);
			else {
				child[0] = clone.clone_tree(p1);
				child[1] = clone.clone_tree(p2);
			}
		}

		return child;
	}


	Classifier[] crossOver(Classifier p1, Classifier p2,Double[][] sample, int classes,int attributes, double alpha) 
	{
		Classifier[] temp = new Classifier[2];
		temp[0] = cc.clone_classifier(p1, classes);
		temp[1] = cc.clone_classifier(p2, classes);
		temp[0].cummulative_fitness = 0.0;
		temp[1].cummulative_fitness = 0.0;
		for (int i = 0; i < classes; i++) 
		{
			Node[] temp1 = genCrossOver(temp[0].tree[i], temp[1].tree[i],sample, i, classes, attributes, alpha);
			temp[0].tree[i] = clone.clone_tree(temp1[0]);
			temp[1].tree[i] = clone.clone_tree(temp1[1]);
			temp[0].fitness[i] = fv.fitness_value(temp[0].tree[i], sample, i, attributes, alpha);
			temp[1].fitness[i] = fv.fitness_value(temp[1].tree[i], sample, i, attributes, alpha);
			temp[0].cummulative_fitness += temp[0].fitness[i];
			temp[1].cummulative_fitness += temp[1].fitness[i];
		}
		temp[0].cummulative_fitness /= classes;
		temp[1].cummulative_fitness /= classes;

		if (temp[0].cummulative_fitness > p1.cummulative_fitness && temp[1].cummulative_fitness > p2.cummulative_fitness)
		{
			return temp;
		}
		Classifier[] ret = { p1, p2 };
		return ret;
	}

}