
public class CrossOver_onetime 
{
	public CrossOver_onetime(){}
	CloneClassifier cc = new CloneClassifier();
	FitnessValue fv = new FitnessValue();
	RandomSubTreeInterchange rsti = new RandomSubTreeInterchange();

	Classifier[] cross_over_onetime(Classifier p1, Classifier p2,Double[][] sample, int classes, int attributes, double alpha) 
	{
		Classifier[] temp = new Classifier[2];
		temp[0] = cc.clone_classifier(p1, classes);
		temp[1] = cc.clone_classifier(p2, classes);
		temp[0].cummulative_fitness = 0.0;
		temp[1].cummulative_fitness = 0.0;
		for (int i = 0; i < classes; i++) 
		{
			Node[] temp1 = rsti.random_subtree_interchange(temp[0].tree[i],
					temp[1].tree[i]);
			temp[0].tree[i] = temp1[0];
			temp[1].tree[i] = temp1[1];
			temp[0].fitness[i] = fv.fitness_value(temp[0].tree[i], sample, i,
					attributes, alpha);
			temp[1].fitness[i] = fv.fitness_value(temp[1].tree[i], sample, i,
					attributes, alpha);
			temp[0].cummulative_fitness += temp[0].fitness[i];
			temp[1].cummulative_fitness += temp[1].fitness[i];
		}
		temp[0].cummulative_fitness /= classes;
		temp[1].cummulative_fitness /= classes;

		// if(temp[0].cummulative_fitness > p1.cummulative_fitness &&
		// temp[1].cummulative_fitness > p2.cummulative_fitness)
		return temp;
		// Classifier[] ret = {temp[0],temp[1]};
		// return ret;
	}

	
}
