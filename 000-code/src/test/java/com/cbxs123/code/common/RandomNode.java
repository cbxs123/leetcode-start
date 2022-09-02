package com.cbxs123.code.common;

import lombok.Data;

/**
 * @author cbxs123
 * @title RandomNode.java
 * @description //TODO
 * @date 2022/8/10 19:57
 **/
@Data
public class RandomNode {

    public int val;
    public RandomNode next;
    public RandomNode random;

    public RandomNode() {
        this(0);
    }

    public RandomNode(int val) {
        this.val = val;
    }

    public RandomNode(int val, RandomNode next, RandomNode random) {
        this.val = val;
        this.next = next;
        this.random = random;
    }

    public RandomNode get(Integer path) {
        return get(path, this);
    }

    public int size() {
        return size(this);
    }

    public RandomNode build(Integer[][] list) {
        RandomNode head = null, tmp = null, node;
        if (list.length > 0) {
            for (Integer[] l : list) {
                node = new RandomNode(l[0]);
                if (head == null) {
                    head = tmp = node;
                } else {
                    tmp.next = node;
                    tmp = node;
                }
            }
            tmp = head;
            for (Integer[] l : list) {
                node = get(l[1], head);
                tmp.random = node;
                tmp = tmp.next;
            }
        }
        return head;
    }

    public RandomNode get(Integer path, RandomNode head) {
        if (head == null || path == null) {
            return null;
        }
        RandomNode cur = head;
        for (int i = 0; i < path && cur != null; i++) {
            cur = cur.next;
        }
        return cur;
    }

    public int size(RandomNode head) {
        int size = 0;
        if (head == null) {
            return size;
        }
        RandomNode cur = head;
        while (cur != null) {
            size++;
            cur = cur.next;
        }
        return size;
    }

    public Integer path(int size, RandomNode head) {
        if (head == null) {
            return null;
        }
        RandomNode cur = head;
        while (cur != null) {
            size--;
            cur = cur.next;
        }
        return size;
    }

    @Override
    public String toString() {
        RandomNode cur = this;
        if (cur == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        while (cur != null) {
            int size = this.size();
            sb.append(String.format("[%s,%s]->", cur.val, path(size, cur.random)));
            cur = cur.next;
        }
        sb.append("null");
        return sb.toString();
    }

}
