package treeed;

import java.util.ArrayList;

public class TreeEDBuilder {
	private static TreeNode root = new TreeNode(true);
	private static String data;
	private int lineNumber;
	private ArrayList<LineOfCode> lines = new ArrayList<>();

	public TreeEDBuilder(String data) {
		this.data = data;
		this.buildTree();
	}

	private void detachLineFromData() {
		int i = 0;
		lineNumber = 0;
		while (i < data.length()) {
			int numberOfTab = 0;
			while (i < data.length() && data.charAt(i) == '\t') {
				++numberOfTab;
				++i;
			}
			int afterTabIdx = i;
			while (i < data.length() &&  data.charAt(i) != '\n') ++i;

			String currText = data.substring(afterTabIdx, i);
			String type = currText, val = "";

			if (currText.contains("(") && currText.contains(")")) {
				int idx1 = currText.indexOf("(");
				int idx2 = currText.indexOf(")");
				val = currText.substring(idx1 + 1, idx2);
				type = currText.substring(0, idx1);
			}

			LineOfCode currLine = new LineOfCode(lineNumber, numberOfTab, type, val);
			lines.add(currLine);

			i = i + 2; // ignore character '\n'
			++lineNumber;
		}
	}

	private void recursionOnLines(TreeNode curr, int id) {
		if (id >= lines.size()) return;
		LineOfCode thisLine = lines.get(id);
		if (curr.isRoot()) {
			TreeNode newNode = new TreeNode(curr, thisLine);
			curr.addChild(newNode);
			recursionOnLines(newNode, id + 1);
		}
		else {
			if (thisLine.getNumberOfTab() == curr.getLine().getNumberOfTab()) {
				TreeNode newNode = new TreeNode(curr.getParent(), thisLine);
				curr.getParent().addChild(newNode);
				recursionOnLines(newNode, id + 1);
			}
			else {
				if (thisLine.getNumberOfTab() > curr.getLine().getNumberOfTab()) {
					TreeNode newNode = new TreeNode(curr, thisLine);
					curr.addChild(newNode);
					recursionOnLines(newNode, id + 1);
				}
				else { //  mean: thisLine.getNumberOfTab() < curr.getLine().getNumberOfTab()
					TreeNode tmp = curr;
					while ((!tmp.isRoot()) && thisLine.getNumberOfTab() < tmp.getLine().getNumberOfTab()) {
						tmp = tmp.getParent();
					}
					if (tmp.isRoot() || thisLine.getNumberOfTab() > tmp.getLine().getNumberOfTab()) {
						TreeNode newNode = new TreeNode(tmp, thisLine);
						tmp.addChild(newNode);
						recursionOnLines(newNode, id + 1);
					} else { // =
						TreeNode newNode = new TreeNode(tmp.getParent(), thisLine);
						tmp.getParent().addChild(newNode);
						recursionOnLines(newNode, id + 1);
					}
				}
			}
		}

	}
	private void buildTree() {
		this.detachLineFromData();
//		recursionOnLines(root, 0);
	}

	private void goDownAndPrint(TreeNode curr) {
		if (curr.isLeaf()) return;
		ArrayList<TreeNode> children = curr.getChildren();
		for (TreeNode child: children) {
			System.out.println(curr.toString() + " --> " + child.toString());
			goDownAndPrint(child);
		}
		System.out.println();
	}

	public int getNumberOfNode() {
		return lines.size();
	}

	public ArrayList<LineOfCode> getLines() {
		return lines;
	}

	public void printOutEdges() {
		goDownAndPrint(root);
	}
}
