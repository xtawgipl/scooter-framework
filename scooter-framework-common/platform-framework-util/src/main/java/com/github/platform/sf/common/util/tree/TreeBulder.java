package com.github.platform.sf.common.util.tree;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeBulder {

	private static Logger logger = LoggerFactory.getLogger(TreeBulder.class);

	public static TreeNode build(List<TreeNode> treeNodes) {
		Map<Integer, TreeNode> cache = new HashMap<>(treeNodes.size());
		for(TreeNode node : treeNodes){
			cache.put(node.getNmId(), node);
		}
		List<TreeNode> root = new ArrayList<>();
		for(TreeNode node : treeNodes){
			Integer nmParentid = node.getNmParentid();
			if(nmParentid != null){
				TreeNode parentNode = cache.get(nmParentid);
				if(parentNode != null){
					List<TreeNode> children = parentNode.getChildren();
					if(children == null){
						children = new ArrayList<>();
					}
					children.add(node);
					parentNode.setChildren(children);
				}else{
					root.add(node);
				}
			}else{
				root.add(node);
			}
		}
		if(root.size() > 1){
			logger.warn("根节点超过1个, root : {}", root);
		}
		return root.get(0);
	}

	public static int leafCount(TreeNode treeData){
		return leafCount(treeData, 0);
	}

	private static int leafCount(TreeNode treeData, int count){
		if(treeData == null){
			return count;
		}
		if(treeData.getChildren() == null || treeData.getChildren().isEmpty()){
			count = count + 1;
		}else{
			int temp = 0;
			for(TreeNode child : treeData.getChildren()){
				temp += leafCount(child, count);
			}
			count += temp;
		}
		return count;
	}

}
