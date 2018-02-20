package treeed;

public class LineOfCode {
	private int numberOfLine;
	private boolean changed;
	private int numberOfTab;
	private String type;
	private String val;

	public LineOfCode(int numberOfLine, int numberOfTab, String type, String val) {
		this.numberOfLine = numberOfLine;
		this.numberOfTab = numberOfTab;
		this.type = type;
		this.val = val;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	public int getNumberOfLine() {
		return numberOfLine;
	}

	public boolean isChanged() {
		return changed;
	}

	public int getNumberOfTab() {
		return numberOfTab;
	}

	public String getType() {
		return type;
	}

	public String getVal() {
		return val;
	}

	@Override
	public String toString() {
		return Integer.toString(numberOfLine)+ "_" + this.type + "_" + this.val;
	}

	public boolean equals(Object line2) {
		if (this == line2)
			return true;

		if (line2 instanceof LineOfCode) {
			return getType().equals(((LineOfCode) line2).getType()) && getVal().equals(((LineOfCode) line2).getVal());
		}
		return false;
	}
}
