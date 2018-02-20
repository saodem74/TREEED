package treeed.utils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The algorithm implemented here is based on "An O(NP) Sequence Comparison Algorithm"
 */
public class Diff<T> {
	private int M;
	private int N;
	private int editDist;

	private ArrayList<T> data1;
	private ArrayList<T> data2;


	public Diff(ArrayList<T> a, ArrayList<T> b) {
		this.data1 = a;
		this.data2 = b;
		this.editDist = 0;
		this.M = data1.size();
		this.N = data2.size();
		if (this.M > this.N) {
			ArrayList<T> tmp = this.data1;
			this.data1 = this.data2;
			this.data2 = tmp;
			this.M = data1.size();
			this.N = data2.size();
		}
	}

	public int getEditdist () {
		return this.editDist;
	}

	public void compose() {
		int p      = -1;
		int size   = this.M + this.N + 3;
		int delta  = this.N - this.M;
		int offset = M + 1;
		int fp[]   = new int[size];
		Arrays.fill(fp, -1);
		do {
			++p;
			for (int k=-p;k<=delta-1;++k) {
				fp[k+offset] = this.snake(k, fp[k-1+offset]+1, fp[k+1+offset]);
			}
			for (int k=delta+p;k>=delta+1;--k) {
				fp[k+offset] = this.snake(k, fp[k-1+offset]+1, fp[k+1+offset]);
			}
			fp[delta+offset] = this.snake(delta, fp[delta-1+offset]+1, fp[delta+1+offset]);
		} while(fp[delta+offset] < this.N);
		this.editDist = delta + 2 * p;
	}

	private int snake (int k, int p, int pp) {
		int y = Math.max(p, pp);
		int x = y - k;
		while (x < M && y < N && this.data1.get(x).equals(data2.get(y))) {
			++x;
			++y;
		}
		return y;
	}

	public static void main(String[] args) {
		ArrayList<Integer> a = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,12,12,14,15));
		ArrayList<Integer> b = new ArrayList<>(Arrays.asList(2,3,4,1,5,6,8,9,7, 10));

		Diff<Integer> diff = new Diff<>(a, b);
		diff.compose();
		System.out.println("editDistance: " + diff.getEditdist());
	}
}
