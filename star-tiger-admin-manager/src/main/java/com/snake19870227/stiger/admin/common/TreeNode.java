package com.snake19870227.stiger.admin.common;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/07/22
 */
public class TreeNode<T> {

    private String id;

    private String title;

    private List<TreeNode<T>> children;

    private TreeNode<T> parentNode;

    private boolean spread;

    private boolean checked;

    private boolean disabled;

    private T data;

    public static <T> TreeNode<T> create(T data, TreeNodeCreateCallback<T> createCallback) {
        TreeNode<T> node = new TreeNode<>();
        node.setData(data);
        createCallback.build(node, data);
        return node;
    }

    public TreeNode() {
        this.children = new ArrayList<>();
        this.spread = false;
        this.checked = false;
        this.disabled = false;
    }

    public TreeNode(T data) {
        this();
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<TreeNode<T>> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode<T>> children) {
        this.children = children;
    }

    public TreeNode<T> getParentNode() {
        return parentNode;
    }

    public void setParentNode(TreeNode<T> parentNode) {
        this.parentNode = parentNode;
    }

    public boolean isSpread() {
        return spread;
    }

    public void setSpread(boolean spread) {
        this.spread = spread;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
