package projectTwo;

import java.util.List;

public class Pst {
	
	PstNode root;
	static int id = 0;
	
	public void addChildren(PstNode root, List<PstNode> children) {
		root.setChildren(children);
	}
	
	public void serializeTree(PstNode root) {
		if(root == null) {
			return;
		}
		System.out.println();
		System.out.print("(");
		if(root.getChildren() != null) {//internal node
			System.out.println();
			System.out.print("(");
			//System.out.print(root.getClass().getName());
			System.out.print(id++);
			System.out.print(" data: "+root.getData());
//			System.out.print(" children: "+root.getClass().getName()+", id: "+id);
			System.out.print(", id: "+id);
			for(PstNode node : root.getChildren()) {
				//System.out.print(id++);
				System.out.print("data: "+root.getData());
				if(root.getParent() != null) {
//					System.out.print("parent: " +root.getParent().getData()+", id: "+id);
					System.out.print("parent: " +root.getParent().getData()+", id: "+id);
				}
				
			}
//			System.out.print("parent:" +root.getParent()+", id: "+id);
			System.out.print(")");
		} else {//leaf node
			System.out.print(root.getClass().getName());
			System.out.print(id++);
			System.out.print(" data: "+root.getData());
//			System.out.print(" children: "+root.getClass().getName()+", id: "+id);
			System.out.print(" id: "+id);
			System.out.print(" parent: " +root.getParent()+", id: "+id);
		}
		for(PstNode node : root.getChildren()) {
			serializeTree(node);
		}
		
	}
}
