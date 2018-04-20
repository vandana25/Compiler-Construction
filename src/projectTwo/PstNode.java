package projectTwo;

import java.util.ArrayList;
import java.util.List;

public class PstNode {
	private String data;
	private List<PstNode> children;
	private PstNode parent;
	
	public PstNode() {
		data = null;
		children = new ArrayList<PstNode>();
		parent = null;
	}
	
	public PstNode(String val) {
		data = val;
		children = new ArrayList<PstNode>();
		parent = null;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public List<PstNode> getChildren() {
		return children;
	}

	public void setChildren(List<PstNode> children) {
		this.children = children;
	}

	public PstNode getParent() {
		return parent;
	}

	public void setParent(PstNode parent) {
		this.parent = parent;
	}
	
	
}
