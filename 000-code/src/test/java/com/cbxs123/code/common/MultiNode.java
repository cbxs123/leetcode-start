package com.cbxs123.code.common;

/**
 * @author cbxs123
 * @title MultiNode.java
 * @description //TODO
 * @date 2022/9/9 21:22
 **/
public class MultiNode {

    public int val;
    public MultiNode prev;
    public MultiNode next;
    public MultiNode child;

    public MultiNode() {
        this(0);
    }

    public MultiNode(int val) {
        this.val = val;
    }

    public MultiNode(int val, MultiNode prev, MultiNode next, MultiNode child) {
        this.val = val;
        this.prev = prev;
        this.next = next;
        this.child = child;
    }

    public MultiNode build(int[] vals) {
        MultiNode head = null, tmp = null, node;
        if (vals.length > 0) {
            for (int i = 0; i < vals.length; i++) {
                node = new MultiNode(vals[i]);
                if (head == null) {
                    head = tmp = node;
                } else {
                    tmp.next = node;
                    tmp = node;
                }
            }
        }
        return head;
    }

}
