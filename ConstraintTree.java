//hybrid version
//import java.io.FileWriter;
import java.text.SimpleDateFormat;
//import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
//import java.util.List;
import java.util.Vector;

class Classifier implements Comparable<Classifier> 
{
	Node[] tree;
	Double[] fitness;
	Double cummulative_fitness;

	public Classifier() {}

	public Classifier(int classes) 
	{
		tree = new Node[classes];
		fitness = new Double[classes];
		for (int i = 0; i < classes; i++)
			tree[i] = new Node();
	}

	@Override
	public int compareTo(Classifier o) 
	{
		double d1 = o.cummulative_fitness;
		double d2 = this.cummulative_fitness;
		if (d1 < d2)
			return -1; // Neither val is NaN, thisVal is smaller
		if (d1 > d2)
			return 1; // Neither val is NaN, thisVal is larger
		long thisBits = Double.doubleToLongBits(d1);
		long anotherBits = Double.doubleToLongBits(d2);
		return (thisBits == anotherBits ? 0 : // Values are equal
				(thisBits < anotherBits ? -1 : // (-0.0, 0.0) or (!NaN, NaN)
						1));
	}
}

class Tree implements Comparable<Tree> 
{
	Node root;
	Double fitness_value;

	@Override
	public int compareTo(Tree o) 
	{
		double d1 = o.fitness_value;
		double d2 = this.fitness_value;
		if (d1 < d2)
			return -1; // Neither val is NaN, thisVal is smaller
		if (d1 > d2)
			return 1; // Neither val is NaN, thisVal is larger

		long thisBits = Double.doubleToLongBits(d1);
		long anotherBits = Double.doubleToLongBits(d2);
		return (thisBits == anotherBits ? 0 : // Values are equal
				(thisBits < anotherBits ? -1 : // (-0.0, 0.0) or (!NaN, NaN)
						1));
	}
}

class Node
{
	String data;
	Node left;
	Node right;
	Node parent;

	public Node() {}

	public Node(Node x) 
	{
		this.data = x.data;
		this.left = x.left;
		this.right = x.right;
		this.parent = x.parent;
	}
	
}

public class ConstraintTree 
{
	public Node root;
	
	
	Vector<String> expr_post = new Vector<String>();
	int top = 0;

	//static double alpha = 0.3;
	
	public ConstraintTree() {}
	public static long[][] best_features;
	public static void bubblesort(Classifier[][] cs, int low, int high, Classifier[] tp, int l, int h, int k, int classes, CloneClassifier cc) {
	      boolean swapped = true;
	      //int j = 0;
	      Classifier tmp;
	      while (swapped) {
	            swapped = false;
	            //j++;
	            for (int i = l; i < (h-l)-1; i++) {                                       
	                  if (tp[i].cummulative_fitness < tp[i + 1].cummulative_fitness) {                          
	                        tmp = cc.clone_classifier(tp[i], classes);
	                        tp[i] = cc.clone_classifier(tp[i + 1], classes);
	                        tp[i + 1] = cc.clone_classifier(tmp, classes);
	                        tmp = cc.clone_classifier(cs[k][low+i], classes);
	                        cs[k][low+i] = cc.clone_classifier(cs[k][low+i+1], classes);
	                        cs[k][low+i+1] = cc.clone_classifier(tmp, classes);
	                        swapped = true;
	                  }
	            }                
	      }
	}
	
	
	/*--------------------------------------------------MAIN function-----------------------------------------------*/
	public static void main(String args[]) throws Exception
	{
		
		final int MAXLEVEL = 6;
		final int TOTAL_POPULATION = 100;
		final int GENERATION = 100;
		final double testing_per = 0.2;
		Double[]alphas = {(double) 0,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,(double) 1};
		
		
		Input in = Input.input_extract("./src/p3.txt");
		best_features = new long[2][in.attribute_name.size()+1];
		
		Date dstart = new Date( );
	    SimpleDateFormat ft1 = 
	      new SimpleDateFormat ("E yyyy.MM.dd 'at' hh:mm:ss a zzz");

	    System.out.println("Current Date: " + ft1.format(dstart));
	    
		for(int x=0;x<11;x++)
		{
			double alpha = alphas[x];
			System.out.println(alpha);
			TenFoldCrossValidation tf = new TenFoldCrossValidation();
			MakeConstraintTree mct = new MakeConstraintTree();
			CloneTree clone = new CloneTree();
			FitnessValue fv = new FitnessValue();
			//FitnessEval fev = new FitnessEval();
			CheckDuplicates cd = new CheckDuplicates();
			CompareTree compare = new CompareTree();
			ReplaceDuplicates_IP rd = new ReplaceDuplicates_IP();
			FeatureSelection	fs = new FeatureSelection();
			CloneClassifier cc = new CloneClassifier();
			Mutation mutate = new Mutation();
			//Crossover_fourtime cross = new Crossover_fourtime();
			crossover stdcross = new crossover();
			twopointcrossover stdtwocross = new twopointcrossover();
			crossover_hillclimbing hillcross = new crossover_hillclimbing();
			//RandomSubTreeInterchange rsti = new RandomSubTreeInterchange();
			//CrossOver_onetime crossonetime = new CrossOver_onetime();
			FitnessValueTesting fvt = new FitnessValueTesting();
			PrintTree pt = new PrintTree();
			IntronDetect it = new IntronDetect();
			MutationGoodFeatures goodmutate = new MutationGoodFeatures();
			
			Double[][] sample = tf.ten_fold_cross_validation(in.data, testing_per);
			Double[][] training_data = new Double[(int) Math.ceil((sample.length * (1 - testing_per)))][];
			Double[][] testing_data = new Double[(int) Math.floor((sample.length * testing_per))][];
	
			for (int i = 0; i < (int) Math.ceil((sample.length * (1 - testing_per))); i++)
			{
				training_data[i] = sample[i];
			}
	
			int index = 0;
			for (int i = (int) Math.ceil((sample.length * (1 - testing_per))); i < sample.length; i++) 
			{
				testing_data[index] = sample[i];
				index++;
			}
			
			final double REPRODUCTION_PROB = 0.2, CROSS_OVER_PROB = 0.8;
	
			// TRAINING PHASE--------------------------------------------------------------
			Classifier[][] cs = new Classifier[GENERATION][TOTAL_POPULATION];
	
			// Initializing Total Population
			for (int i = 0; i < GENERATION; i++) 
			{
				for (int j = 0; j < TOTAL_POPULATION; j++) 
				{
					cs[i][j] = new Classifier(2);
					cs[i][j].cummulative_fitness = 0.0;
				}
			}
	
			// Generating Initial Population
			for (int i = 0; i < TOTAL_POPULATION; i++) 
			{
				for (int j = 0; j < 2; j++) 
				{
					mct.makeConstraintTree(cs[0][i].tree[j], MAXLEVEL,in.attribute_name.size());
					
					Node temp1 = new Node();
					temp1 = clone.clone_tree(cs[0][i].tree[j]);
					temp1 = it.intron_detect(temp1);
	
					while (temp1.data.equals("0") || temp1.data.equals("1")) 
					{
						mct.makeConstraintTree(temp1, MAXLEVEL,in.attribute_name.size());
						temp1 = it.intron_detect(temp1);
					}
					
					cs[0][i].tree[j] = null;
					cs[0][i].tree[j] = clone.clone_tree(temp1);
	
				}
			}
	
			// Check Duplicates in Initial Population
			for (int j = 0; j < 2; j++) 
			{
				Node[] TT = new Node[TOTAL_POPULATION];
				Node[] ret = new Node[TOTAL_POPULATION];
				
				for (int i = 0; i < TOTAL_POPULATION; i++) 
				{
					TT[i] = cs[0][i].tree[j];
				}
				// Now check for duplicates in this array
				ArrayList<Integer> dps = new ArrayList<>();
				dps = cd.check_duplicates(TT);
	
				while (dps.size() > 0) 
				{
					ret = rd.replace_duplicates_IP(TT, dps, MAXLEVEL,in.attribute_name.size());
					for (int i = 0; i < TOTAL_POPULATION; i++) 
					{
						TT[i] = ret[i];
					}
					dps = cd.check_duplicates(TT);
				}
				// Replace Initial population if duplicates found
				for (int i = 0; i < TOTAL_POPULATION; i++) 
				{
					cs[0][i].tree[j] = TT[i];
					
				}
			}
	
			for (int i = 0; i < TOTAL_POPULATION; i++) 
			{
				for (int j = 0; j < 2; j++) 
				{
					cs[0][i].fitness[j] = fv.fitness_value(cs[0][i].tree[j],training_data, j, in.attribute_name.size(),alpha);
					cs[0][i].cummulative_fitness += cs[0][i].fitness[j];
				}
				cs[0][i].cummulative_fitness /= (double) 2;
			}
			
			// To sort the initial population on the basis of cumulative fitness
			Arrays.sort(cs[0]);
			
			int last_generation = 0;
			boolean flag = false;
	
			
			for (int i = 1; i < GENERATION; i++) 
			{
				//System.out.println("\nGeneration " + i + " Started!");
				long [][] best_f = new long[2][in.attribute_name.size()]; 
				Classifier[] classy = new Classifier[TOTAL_POPULATION];
				
				for(int j=0; j< TOTAL_POPULATION;j++)
				{
					classy[j]= cs[i-1][j];
				}
				
				if (i < GENERATION/2) 
				{
					best_f = fs.feature_selection(classy, TOTAL_POPULATION);//, best_features);
					//System.out.println();
					for(int k=0;k< 2; k++)
					{
						for(int j=0; j< in.attribute_name.size(); j++)
						{
							best_features[k][j] = best_f[k][j] + best_features[k][j];
						}
					}
				}
				
				//System.out.println("reproduction");
				//Reproduction-------------------------------------------------------------------------------------
				for (int j = 0; j < (int) (TOTAL_POPULATION * REPRODUCTION_PROB); j++) 
				{
					cs[i][j] = cc.clone_classifier(cs[i - 1][j],2);
				}
				
				//System.out.println("crossover");
				//CrossOver-------------------------------------------------------------------------------------
				Classifier[] tp = new Classifier[(int) (TOTAL_POPULATION * CROSS_OVER_PROB)];
				int low = (int) (TOTAL_POPULATION * (REPRODUCTION_PROB));
				int high = (int) (TOTAL_POPULATION * (REPRODUCTION_PROB+0.4) - 1);
				int lj = 0;
				int hj = (int) (TOTAL_POPULATION * (0.4) - 1);;
				while (low < high) 
				{
					// Classifier[] temp = crossonetime.cross_over_onetime(cs[i - 1][low],cs[i - 1][high], training_data, 2,in.attribute_name.size());
					Classifier[] temp = stdtwocross.crossOver(cs[i - 1][low],cs[i - 1][high], training_data, 2,in.attribute_name.size(), alpha);
					tp[lj] = cc.clone_classifier(temp[0], 2);
					lj++;
					tp[hj] = cc.clone_classifier(temp[1],2);
					hj--;
					low++;
					high--;
				}
				
				//Classifier[] tp1 = new Classifier[(int) (TOTAL_POPULATION * 0.3)];
				low = (int) (TOTAL_POPULATION * (REPRODUCTION_PROB+0.4));
				high = (int) (TOTAL_POPULATION - 1);
				lj = (int)(TOTAL_POPULATION * 0.4);
				hj = (int)(TOTAL_POPULATION * 0.8 - 1);
				while (low < high) 
				{
					// Classifier[] temp = crossonetime.cross_over_onetime(cs[i - 1][low],cs[i - 1][high], training_data, 2,in.attribute_name.size());
					Classifier[] temp = stdcross.crossOver(cs[i - 1][low],cs[i - 1][high], training_data, 2,in.attribute_name.size(), alpha);
					tp[lj] = cc.clone_classifier(temp[0], 2);
					lj++;
					tp[hj] = cc.clone_classifier(temp[1], 2);
					hj--;
					low++;
					high--;
				}
				
				//cumm fitness
				lj = (int)(TOTAL_POPULATION * CROSS_OVER_PROB);
				for (int j = 0; j < lj; j++) 
				{
					//tp[j].cummulative_fitness = 0.0;
					for (int k = 0; k < 2; k++) 
					{
						tp[j].fitness[k] = fv.fitness_value(tp[j].tree[k],training_data, k, in.attribute_name.size(), alpha);
						tp[j].cummulative_fitness += tp[j].fitness[k];
					}
					tp[j].cummulative_fitness /= (double)2;
				}
				
				//sort according to children's fitness
				low = (int) (TOTAL_POPULATION * (REPRODUCTION_PROB));
				high = TOTAL_POPULATION;
				
				bubblesort(cs, low, high, tp, 0, lj, i-1, 2, cc);
				
				//0.3 crossover - hill climbing
				Classifier[] tp1 = new Classifier[(int) (TOTAL_POPULATION * 0.3)];
				low = (int) (TOTAL_POPULATION * (REPRODUCTION_PROB));
				high = (int) (TOTAL_POPULATION * (REPRODUCTION_PROB+0.3) - 1);
				lj = 0;
				while (low < high) 
				{
					//Classifier[] temp = crossonetime.cross_over_onetime(cs[i - 1][low],cs[i - 1][high], training_data, 2,in.attribute_name.size());
					Classifier[] temp = hillcross.crossover(cs[i - 1][low],cs[i - 1][high], training_data, 2,in.attribute_name.size(), alpha);
					tp1[lj] = cc.clone_classifier(temp[0], 2);
					lj++;
					tp1[lj] = cc.clone_classifier(temp[1], 2);
					lj++;
					low++;
					high--;
				}
				
				Arrays.sort(tp1, 0, lj);
				
				low = (int) (TOTAL_POPULATION * (REPRODUCTION_PROB));
				high = (int) (TOTAL_POPULATION * (REPRODUCTION_PROB+0.3) - 1);
				int bj = 0;
				while (low < high) 
				{
					cs[i][low] = cc.clone_classifier(tp1[bj], 2);
					bj++;
					low++;
					cs[i][low] = cc.clone_classifier(tp1[bj], 2);
					bj++;
					low++;
				}
				
				//next 0.3 std crossover 
				Classifier[] tp2 = new Classifier[(int) (TOTAL_POPULATION * 0.3)];
				low = (int) (TOTAL_POPULATION * (REPRODUCTION_PROB + 0.3));
				high = (int) (TOTAL_POPULATION * (REPRODUCTION_PROB + 0.6) - 1);
				lj = 0;
				
				while (low < high) 
				{
					//Classifier[] temp = crossonetime.cross_over_onetime(cs[i - 1][low],cs[i - 1][high], training_data, 2,in.attribute_name.size());
					Classifier[] temp = stdcross.crossOver(cs[i - 1][low],cs[i - 1][high], training_data, 2,in.attribute_name.size(), alpha);
					//Classifier[] temp = stdtwocross.crossOver(cs[i - 1][low],cs[i - 1][high], training_data, 2,in.attribute_name.size());
					tp2[lj] = cc.clone_classifier(temp[0], 2);
					lj++;
					tp2[lj] = cc.clone_classifier(temp[1], 2);
					lj++;
					low++;
					high--;
				}
				
				Arrays.sort(tp2, 0, lj);
				
				
				/*for (int j = 0; j < lj; j++) 
				{
					System.out.println("----->"+ tp[j].cummulative_fitness);
				}*/
				// Passing the needed values into next generation
				low = (int) (TOTAL_POPULATION * (REPRODUCTION_PROB+0.3));
				high = (int) (TOTAL_POPULATION * (REPRODUCTION_PROB+0.6) - 1);;
				bj = 0;
				while (low < high) 
				{
					cs[i][low] = cc.clone_classifier(tp2[bj], 2);
					bj++;
					low++;
					cs[i][low] = cc.clone_classifier(tp2[bj], 2);
					bj++;
					low++;
				}
				//----------mutation----------
				
				//System.out.println("mutation");
				//Mutation-----------------------------------------------------------------------------------------
				if( i <GENERATION/2)
				{
					//MUTATE SIMPLY
					for (int j = (int) (TOTAL_POPULATION * 0.8); j < TOTAL_POPULATION; j++) 
					{
						cs[i][j] = mutate.mutation(cs[i - 1][j], 2,training_data, in.attribute_name.size(), alpha);
					}
				}	
				int[][] best_f2 = new int[2][in.attribute_name.size()];
				int[] len= new int[2];
				if(i == GENERATION/2)
				{
					double weight_avg[] = new double[2];
					for (int k = 0; k < 2; k++) 
					{
						weight_avg[k] = 0.0;
						for (int j = 0; j < in.attribute_name.size(); j++) 
						{
							weight_avg[k] += best_features[k][j];
						}
						weight_avg[k] /= in.attribute_name.size();
					}
					
					
					for (int j = 0; j < 2; j++)
					{
						for(int k=0; k<in.attribute_name.size(); k++)
						{
							best_f2[j][k] = -1;
						}
					}
					
					for (int j = 0; j < 2; j++) 
					{
						int l=0;
						for (int k = 0; k < in.attribute_name.size(); k++) 
						{
							if (best_features[j][k] >= weight_avg[j])
							{
								best_f2[j][l]= k;
								l++;
							}
						}
					}
					for(int j=0; j< 2; j++)
					{
						len[j]=0;
					}
					for(int j=0; j<2; j++)
					{
						while(best_f2[j][len[j]]!= -1)
						{
							len[j]++;
						}
					}
				}
				if( i >= GENERATION/2)
				{
					//MUTATE after half gen.
					for (int j = (int) (TOTAL_POPULATION * 0.8); j < TOTAL_POPULATION; j++) 
					{
						cs[i][j] = goodmutate.mutation_with_goodfeatures(cs[i - 1][j],training_data,len, best_f2, 2,in.attribute_name.size(), alpha);
					}
				}	
				
				
				// Check Duplicates in Generations
				for (int j = 0; j < 2; j++) 
				{
					
					Node[] TT = new Node[TOTAL_POPULATION];
					Node[] ret = new Node[TOTAL_POPULATION];
					for (int k = 0; k < TOTAL_POPULATION; k++) 
					{
						TT[k] = cs[i][k].tree[j];
					}
					// Now check for duplicates in this array
					ArrayList<Integer> dps = new ArrayList<>();
					dps = cd.check_duplicates(TT);
					
					int iter=0;
					while (dps.size() > 0 && iter<=500)
					//while(dps.size()>0)
					{
						
						ret = rd.replace_duplicates_IP(TT, dps, MAXLEVEL,in.attribute_name.size());
						for (int k = 0; k < TOTAL_POPULATION; k++) 
						{
							TT[k] = ret[k];
						}
						dps = cd.check_duplicates(TT);
						iter++;
					}
					// Replace the population if duplicates found
					for (int k = 0; k < TOTAL_POPULATION; k++) 
					{
						cs[i][k].tree[j] = TT[k];
					}
				}
				
				// Check for intron detection
				for (int j = 0; j < 2; j++) 
				{
					for (int k = 0; k < TOTAL_POPULATION; k++) 
					{
						Node temp1 = new Node();
						temp1 = clone.clone_tree(cs[i][k].tree[j]);
						temp1 = it.intron_detect(temp1);
						if(temp1 == null){
							mct.makeConstraintTree(temp1, MAXLEVEL,in.attribute_name.size());
						}
						else{
							while (temp1.data.equals("0") || temp1.data.equals("1")) 
							{
								// print_tree(cs[i][k].tree[j]);
								mct.makeConstraintTree(temp1, MAXLEVEL,in.attribute_name.size());
								temp1 = it.intron_detect(temp1);
							}
	
						}
						
						cs[i][k].tree[j] = null;
						if (!compare.compare_tree(cs[i][k].tree[j], temp1)) 
						{
							cs[i][k].tree[j] = clone.clone_tree(temp1);
							cs[i][k].fitness[j] = fv.fitness_value(cs[i][k].tree[j],training_data, j, in.attribute_name.size(), alpha);
							cs[i][k].cummulative_fitness += cs[i][k].fitness[j];
						}
					}
				}
	
				// sorting and updating the cumulative fitness
				for (int k = 0; k < 2; k++) 
				{
					Tree[] t = new Tree[TOTAL_POPULATION];
					for (int j = 0; j < TOTAL_POPULATION; j++)
						t[j] = new Tree();
					for (int j = 0; j < TOTAL_POPULATION; j++) 
					{
						t[j].root = cs[i][j].tree[k];
						t[j].fitness_value = cs[i][j].fitness[k];
					}
					Arrays.sort(t);
					for (int j = 0; j < TOTAL_POPULATION; j++) 
					{
						cs[i][j].tree[k] = t[j].root;
						cs[i][j].fitness[k] = t[j].fitness_value;
					}
				}
				for (int j = 0; j < TOTAL_POPULATION; j++) 
				{
					cs[i][j].cummulative_fitness = 0.0;
					for (int k = 0; k < 2; k++) 
					{
						cs[i][j].cummulative_fitness += cs[i][j].fitness[k];
					}
					cs[i][j].cummulative_fitness /= 2;
				}
			
				//Arrays.sort(cs[i]);
				//System.out.println("Upto Best = " + cs[i][0].cummulative_fitness);
			}		
			if (!flag)
				last_generation = GENERATION - 1;
			System.out.println("\n------------------------------------\nLast Generation = "+ last_generation + "\n");
	
			double trainig_accuracy = 0.0;
			for (int j = 0; j < 2; j++) 
			{
				// print_tree(cs[last_generation][0].tree[i]);
				trainig_accuracy += cs[last_generation][0].fitness[j];
			}
			trainig_accuracy /= 2;
			//String train = "\nTraining_accuracy: \n";
			//FileWriter fw = new FileWriter("wasbs:///Output/output.txt", true);
			//fw.append(train);
			//fw.append(Double.toString(trainig_accuracy));
			
			System.out.println("\nTraining_accuracy: " + trainig_accuracy);
			//System.out.println();
	
			// End of Training Phase -----------------------------
			
			double testing_accuracy = 0.0;
			Double[] fitness_tree = new Double[2];
			/*
			Double[] true_positives = new Double[2];
			Double[] true_negatives = new Double[2];
			Double[] false_positives = new Double[2];
			Double[] false_negatives = new Double[2];
			double specificity = 0.0;
			double sensitivity = 0.0;
		*/
			//String fitness = "\nfitness of tree ";
			//String sens = "\nsensitivity ";
			//String spec = "\nspecificity ";
			for (int l = 0; l < 2; l++) 
			{
				fitness_tree[l] = fvt.fitness_value_testing(cs[last_generation][0].tree[l], testing_data, l, in.attribute_name.size(), alpha);
			//	true_positives[l] = fvt.truepositives(cs[last_generation][0].tree[l], testing_data, l);
			//	true_negatives[l] = fvt.truenegatives(cs[last_generation][0].tree[l], testing_data, l);
			//	false_positives[l] = fvt.falsepositives(cs[last_generation][0].tree[l], testing_data, l);
			//	false_negatives[l] = fvt.falsenegatives(cs[last_generation][0].tree[l], testing_data, l);
			//	sensitivity = (true_positives[l]/(true_positives[l]+false_negatives[l]))*100;
			//	specificity = (true_negatives[l]/(true_negatives[l]+false_positives[l]))*100;
				
				System.out.println("\nthe tree "+(l+1)+": \n");
				pt.print_tree(cs[last_generation][0].tree[l]);
				System.out.println();
				System.out.println("fitness of tree " + (l + 1) + ": "+ fitness_tree[l]);
				//fw.append(fitness);
				//fw.append(Double.toString(l+1));
				//fw.append(Double.toString(fitness_tree[l]));
			//	System.out.println("sensitivity " + (l + 1) + ": "+ sensitivity);
				//fw.append(sens);
				//fw.append(Double.toString(l+1));
				//fw.append(Double.toString(fitness_tree[l]));
			//	System.out.println("specificity " + (l + 1) + ": "+ specificity);
				//fw.append(spec);
				//fw.append(Double.toString(l+1));
				//fw.append(Double.toString(fitness_tree[l]));
				testing_accuracy += fitness_tree[l];
			}
			testing_accuracy /= 2;
			System.out.println("\nTesting accuracy = " + testing_accuracy);
			//String test = "\nTesting accuracy = ";
			//fw.append(test);
			//fw.append(Double.toString(testing_accuracy));
			System.out.println();
			System.out.println("\n-------------------------------------------------------------------------\n");	
			Date dNow = new Date( );
		    SimpleDateFormat ft = 
		      new SimpleDateFormat ("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
	
		    System.out.println("Current Date: " + ft.format(dNow));
		}
	}
}
