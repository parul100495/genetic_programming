
public class RandomShuffle 
{
	public RandomShuffle(){}
	
	int[] random_shuffle(int n) 
	{
		int[] a = new int[n];
		for (int i = 0; i < n; i++)
			a[i] = i;
		for (int i = 0; i < n; i++) 
		{
			int r = (int) (Math.random() * (i + 1));
			int swap = a[r];
			a[r] = a[i];
			a[i] = swap;
		}
		return a;
	}


}
