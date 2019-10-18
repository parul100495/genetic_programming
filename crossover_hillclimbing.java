
public class crossover_hillclimbing
{
	public crossover_hillclimbing(){}
	
	CloneClassifier cc = new CloneClassifier();
	CloneTree clone = new CloneTree();
	FitnessValue fv = new FitnessValue();
	RandomSubTreeInterchange rsti = new RandomSubTreeInterchange();
	
	Node[] genCrossOverhill(Node p1, Node p2, Double[][] sample, int classifier, int classes, int attributes, double alpha) 
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
		int k = 1;
		do{
			iter++;

			Node[] temp = new Node[2];
			temp = rsti.random_subtree_interchange(child[0], child[1]);
			fc1 = fv.fitness_value(temp[0], sample, classifier, attributes, alpha);
			if(fc1>=fp1 && fc1>=fp2){
				child[0] = temp[0];
			}
			else{
				child[0] = p1;
				fc1 = fp1;
				k=0;
			}
		}while(k == 1 && iter!=50);
		
		k = 1;
		iter = 0;
		do{
			iter++;
			Node[] temp = new Node[2];
			temp = rsti.random_subtree_interchange(child[0], child[1]);
			fc2 = fv.fitness_value(temp[1], sample, classifier, attributes, alpha);
			if(fc2>=fp1 && fc2>=fp2){
				child[1] = temp[1];
			}
			else{
				child[1] = p2;
				fc2 = fp2;
				k=0;
			}
			
			
		}
		while(k == 1 && iter!=50);
		
		return child;
	}


	Classifier[] crossover(Classifier p1, Classifier p2,Double[][] sample, int classes,int attributes, double alpha) 
	{
		Classifier[] temp = new Classifier[2];
		temp[0] = cc.clone_classifier(p1, classes);
		temp[1] = cc.clone_classifier(p2, classes);
		temp[0].cummulative_fitness = 0.0;
		temp[1].cummulative_fitness = 0.0;
		for (int i = 0; i < classes; i++) 
		{
			Node[] temp1 = genCrossOverhill(temp[0].tree[i], temp[1].tree[i],sample, i, classes, attributes, alpha);
			temp[0].tree[i] = temp1[0];
			temp[1].tree[i] = temp1[1];
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
