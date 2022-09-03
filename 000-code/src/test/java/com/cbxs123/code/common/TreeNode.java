package com.cbxs123.code.common;

import lombok.Data;

import java.util.*;

/**
 * @author cbxs123
 * @title TreeNode.java
 * @description //TODO
 * @date 2022/7/11 21:19
 **/
public class TreeNode {

    public int val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode() {
        this(0);
    }

    public TreeNode(int val) {
        this.val = val;
    }

    public TreeNode build(Integer[] val) {
        return build(val, this);
    }

    public TreeNode build(Integer[] val, TreeNode root) {
        if (val[0] == null) {
            return root;
        }
        root.val = val[0];
        Queue<TreeNode> queue = new LinkedList<>();
        int cur = 1;
        queue.offer(root);
        while (queue != null) {
            TreeNode tmp = queue.poll();
            if (val[cur] != null) {
                tmp.left = new TreeNode(val[cur]);
                queue.offer(tmp.left);
            }
            if (++cur >= val.length) {
                break;
            }
            if (val[cur] != null) {
                tmp.right = new TreeNode(val[cur]);
                queue.offer(tmp.right);
            }
            if (++cur >= val.length) {
                break;
            }
        }
        return root;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof TreeNode) {
            TreeNode a = this, b = (TreeNode) obj;
            Queue<TreeNode> q1 = new LinkedList<>();
            Queue<TreeNode> q2 = new LinkedList<>();
            q1.offer(a);
            q2.offer(b);
            while (!q1.isEmpty() || !q2.isEmpty()) {
                if ((q1.isEmpty() && !q2.isEmpty()) || (!q1.isEmpty() && q2.isEmpty())) {
                    return false;
                }
                TreeNode c1 = q1.poll();
                TreeNode c2 = q2.poll();
                if (c1.val != c2.val) {
                    return false;
                }
                if (c1.left != null) {
                    q1.offer(c1.left);
                }
                if (c1.right != null) {
                    q1.offer(c1.right);
                }
                if (c2.left != null) {
                    q2.offer(c2.left);
                }
                if (c2.right != null) {
                    q2.offer(c2.right);
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        TreeNode root = this;
        if (root == null) {
            return null;
        }
        List<Integer> result = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        Set<Integer> set = new HashSet<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode cur = queue.poll();
            if (set.contains(cur.val)) {
                continue;
            }
            result.add(cur.val);
            set.add(cur.val);
            if (cur.left != null) {
                queue.offer(cur.left);
            }
            if (cur.right != null) {
                queue.offer(cur.right);
            }
        }
        return result.toString();
    }

}
