package treeed;

import treeed.utils.Diff;
import treeed.utils.FileIO;

public class TestTreeEditDistance {
	public static final String inputFolder = "input/";
	public static final String inputFile1 = inputFolder + "GNMT_tranAST.txt";
	public static final String inputFile2 = inputFolder + "GNMT_refAST.txt";
	public static final String outputFile = inputFile1 + "output.txt";
	public static final String outputFileNormalized = inputFile1 + "outputNormalized.txt";


	private static void processingMultipleTree(String file1, String file2) {
		String data1 = FileIO.readStringFromFile(file1);
		String data2 = FileIO.readStringFromFile(file2);
		String[] part1 = data1.split("\n\r*\n");
		String[] part2 = data2.split("\n\r*\n");
		if (part1.length != part2.length) {
			System.out.println(">>>> Number of AST tree in 2 files is not equal! " + Integer.toString(part1.length) + " vs " + Integer.toString(part2.length));
			return;
		}
		System.out.println(Integer.toString(part1.length) + " AST trees read");
		String res = "";
		String resNormalized = "";
		for (int i = 0; i < part1.length; ++i) {
			TreeEDBuilder tree1 = new TreeEDBuilder(part1[i]);
			TreeEDBuilder tree2 = new TreeEDBuilder(part2[i]);
			Diff<LineOfCode> diff = new Diff<>(tree1.getLines(), tree2.getLines());
			diff.compose();

			res += Integer.toString(diff.getEditdist()) + " " + Integer.toString(tree1.getNumberOfNode()) + " " + Integer.toString(tree2.getNumberOfNode());
			resNormalized += Double.toString(1 - (double) diff.getEditdist() / (double) (tree1.getNumberOfNode() + tree2.getNumberOfNode()));

			if (i != part1.length - 1) {
				res += "\n";
				resNormalized += "\n";
			}
		}
		FileIO.writeStringToFile(outputFile, res);
		FileIO.writeStringToFile(outputFileNormalized, resNormalized);
	}

	public static void main(String[] args) {
		System.out.println("Starting...");
		processingMultipleTree(inputFile1, inputFile2);
		System.out.println("DONE!!!");
	}
}
