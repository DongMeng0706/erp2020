package com.dong.system.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 树的构造器
 * @author LJH
 *
 */
public class TreeNodeBuilder {

	/**
	 * 构造json层级树
	 * @param treeNodes
	 * @param i
	 * @return
	 */
	public static List<TreeNode> build(List<TreeNode> treeNodes, int topId) {
		List<TreeNode> nodes=new ArrayList<TreeNode>();
		
		for (TreeNode n1 : treeNodes) {
			if(n1.getPid()<topId) {
				continue;
			}
			if(n1.getPid()==topId) {
				nodes.add(n1);
			}
			//第二层循环添加子节点
			for (TreeNode n2 : treeNodes) {
				if(n2.getPid()==n1.getId()) {
					n1.getChildren().add(n2);
				}
			}
			
		}
		return nodes;
	}

}
