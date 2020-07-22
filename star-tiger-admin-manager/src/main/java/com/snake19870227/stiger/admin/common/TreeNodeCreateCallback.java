package com.snake19870227.stiger.admin.common;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/07/22
 */
@FunctionalInterface
public interface TreeNodeCreateCallback<T> {

    /**
     * 构建树节点详细信息
     * @param node 树节点
     * @param data 原始数据
     */
    void build(TreeNode<T> node, T data);
}
