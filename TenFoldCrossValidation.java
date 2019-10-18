
public class TenFoldCrossValidation 
{
	public TenFoldCrossValidation(){}
	public int[] random_shuffle(int n) {
		int[] a = new int[n];
		for (int i = 0; i < n; i++)
			a[i] = i;
		for (int i = 0; i < n; i++) {
			int r = (int) (Math.random() * (i + 1));
			int swap = a[r];
			a[r] = a[i];
			a[i] = swap;
		}
		return a;
	}
	Double[][] ten_fold_cross_validation(Double[][] data, double test_prob) 
	{
		int row = data.length;
		int column = data[0].length;

		// Random shuffle the rows indexes
		int a[] = new int[row];
		a = random_shuffle(row);
/*
		Double[][] test_data = new Double[testing][column];
		Double[][] train_data = new Double[row - testing][column];

		for (int i = 0; i < row - testing; i++) {
			int index = a[i];
			for (int j = 0; j < column; j++) {
				train_data[i][j] = data[index][j];
			}
		}

		for (int i = 0; i < testing; i++) {
			int index = a[row - testing + i];
			for (int j = 0; j < column; j++) {
				test_data[i][j] = data[index][j];
			}
		}
		*/
		Double[][] merge_data = new Double[row][column];
		for (int i = 0; i < row; i++)
		{
			int index = a[i];
			for(int j=0; j< column ;j++)
			{
				merge_data[i][j] = data[index][j];
			}
		}
	/*	int index = row - testing;
		for (int i = 0; i < testing; i++) {
			merge_data[index] = test_data[i];
			index++;
		}
*/
		return merge_data;
	}
}
