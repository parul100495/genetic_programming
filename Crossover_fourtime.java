

public class Crossover_fourtime 
{
	public Crossover_fourtime(){}
	CloneClassifier cc = new CloneClassifier();
	CloneTree clone = new CloneTree();
	FitnessValue fv = new FitnessValue();
	RandomSubTreeInterchange rsti = new RandomSubTreeInterchange();
	
	Node[] genCrossOver_four(Node p1, Node p2, Double[][] sample, int classifier, int classes, int attributes, double alpha) 
	{
		Node[] child = new Node[4];
		child[0] = clone.clone_tree(p1);
		child[1] = clone.clone_tree(p2);
		child[2] = clone.clone_tree(p1);
		child[3] = clone.clone_tree(p2);
		double fc1, fc2, fp1, fp2, fc3, fc4;
		
		fp1 = fv.fitness_value(p1, sample, classifier, attributes, alpha);
		fp2 = fv.fitness_value(p2, sample, classifier, attributes, alpha);
		fc1 = fp1;
		fc2 = fp2;
		fc3 = fp1;
		fc4 = fp2;

		int iter = 0;
		// while(! (fc1>fp1 && fc2 > fp2) && (iter != 20))
		while (!(fc1 > fp1 && fc1 > fp2 && fc2 > fp1 && fc2 > fp2 && fc3 > fp1
				&& fc3 > fp2 && fc4 > fp1 && fc4 > fp2)
				&& (iter != 20)) {
			Node[] temp = new Node[2];
			//Node[] temp1 = new Node[2];

			iter++;

			if (fc1 < fp1 || fc1 < fp2) 
			{
				// Need to change only second child
				temp = rsti.random_subtree_interchange(p1, p2);
				child[0] = clone.clone_tree(temp[0]);
				fc1 = fv.fitness_value(child[0], sample, classifier,attributes, alpha);
			}
			if (fc2 < fp1 || fc2 < fp2) {
				// Need to change only first child
				temp = rsti.random_subtree_interchange(p1, p2);
				child[1] = clone.clone_tree(temp[1]);
				fc2 = fv.fitness_value(child[1], sample, classifier, attributes, alpha);
			}
			if (fc3 < fp1 || fc3 < fp2) {
				// Need to change only second child
				temp = rsti.random_subtree_interchange(p1, p2);
				child[2] = clone.clone_tree(temp[0]);
				fc3 = fv.fitness_value(child[2], sample, classifier, attributes, alpha);
			}
			if (fc4 < fp1 || fc4 < fp2) {
				// Need to change only first child
				temp = rsti.random_subtree_interchange(p1, p2);
				child[3] = clone.clone_tree(temp[1]);
				fc4 = fv.fitness_value(child[3], sample, classifier, attributes, alpha);
			} else {
				// Need to change both
				double fl1, fl2;
				Node[] t = new Node[2];
				t = rsti.random_subtree_interchange(p1, p2);
				child[2] = clone.clone_tree(t[0]);
				child[3] = clone.clone_tree(t[1]);
				fl1 = fv.fitness_value(child[2], sample, classifier, attributes, alpha);
				fl2 = fv.fitness_value(child[3], sample, classifier, attributes, alpha);
				fc3 = fl1;
				fc4 = fl2;
				t = rsti.random_subtree_interchange(p1, p2);
				child[0] = clone.clone_tree(t[0]);
				child[1] = clone.clone_tree(t[1]);
				fl1 = fv.fitness_value(child[0], sample, classifier, attributes, alpha);
				fl2 = fv.fitness_value(child[1], sample, classifier, attributes, alpha);
				fc1 = fl1;
				fc2 = fl2;
			}
		}
		
		double[]fit = {fp1, fp2, fc1, fc2, fc3, fc4};
		int max= 0;
		for(int i=0;i<6;i++){
			if(fit[i]>fit[max]){
				max = i;
			}
		}
		int max2;
		if(max!=0){
			max2 = 0;
		}
		else{
			max2 = 1;
		}
		for(int i=0;i<6;i++){
			if((fit[i]>fit[max2])&&(i!=max)){
				max2 = i;
			}
		}
		Node[]ret = new Node[2];
		if(max==0){
			ret[0] = clone.clone_tree(p1);
		}
		if(max==1){
			ret[0] = clone.clone_tree(p2);
		}
		if(max==2){
			ret[0] = clone.clone_tree(child[0]);
		}
		if(max==3){
			ret[0] = clone.clone_tree(child[1]);
		}
		if(max==4){
			ret[0] = clone.clone_tree(child[2]);
		}
		if(max==5){
			ret[0] = clone.clone_tree(child[3]);
		}
		if(max2==0){
			ret[1] = clone.clone_tree(p1);
		}
		if(max2==1){
			ret[1] = clone.clone_tree(p2);
		}
		if(max2==2){
			ret[1] = clone.clone_tree(child[0]);
		}
		if(max2==3){
			ret[1] = clone.clone_tree(child[1]);
		}
		if(max2==4){
			ret[1] = clone.clone_tree(child[2]);
		}
		if(max2==5){
			ret[1] = clone.clone_tree(child[3]);
		}
		return ret;
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
			Node[] temp1 = genCrossOver_four(temp[0].tree[i], temp[1].tree[i],sample, i, classes, attributes, alpha);
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
