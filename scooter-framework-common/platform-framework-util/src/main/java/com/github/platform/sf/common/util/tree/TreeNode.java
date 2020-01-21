package com.github.platform.sf.common.util.tree;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TreeNode implements Serializable {
	/**
	 * 节点编号
	 */
	private Integer nmId;
	private String vcName;
	private String vcSimpleName;
	private Integer nmParentid;
	private List<TreeNode> children;


	public TreeNode() {
	}

	public TreeNode(Integer nmId, String vcName, Integer nmParentid) {
		this.nmId = nmId;
		this.vcName = vcName;
		this.nmParentid = nmParentid;
	}

	public TreeNode(Integer nmId, String vcName, String vcSimpleName, Integer nmParentid) {
		this.nmId = nmId;
		this.vcName = vcName;
		this.vcSimpleName = vcSimpleName;
		this.nmParentid = nmParentid;
	}

	@Override
	public String toString() {
		return "TreeNode{" +
				"nmId=" + nmId +
				", vcName='" + vcName + '\'' +
				", vcSimpleName='" + vcSimpleName + '\'' +
				", nmParentid=" + nmParentid +
				", children=" + children +
				'}';
	}
}
