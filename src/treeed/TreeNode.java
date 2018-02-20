package treeed;

import java.util.ArrayList;

public class TreeNode {
	private TreeNode parent;
	private ArrayList<TreeNode> children = new ArrayList<>();
	private LineOfCode line;
	private boolean isRoot;


	public TreeNode(boolean isRoot) {
		this.isRoot = isRoot;
	}

	public TreeNode(TreeNode parent, LineOfCode line) {
		this.parent = parent;
		this.line = line;
		this.isRoot = false;
	}

	public boolean isRoot() {
		return isRoot;
	}

	public void addChild(TreeNode child) {
		children.add(child);
	}


	public boolean isLeaf() {
		return children.isEmpty();
	}

	public LineOfCode getLine() {
		return line;
	}

	public TreeNode getParent() {
		return parent;
	}

	public ArrayList<TreeNode> getChildren() {
		return children;
	}

	@Override
	public String toString() {
		return isRoot? "ROOT" : getLine().toString();
	}
}
