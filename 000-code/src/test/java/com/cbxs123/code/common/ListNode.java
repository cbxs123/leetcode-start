package com.cbxs123.code.common;

import lombok.Data;

/**
 * @author cbxs123
 * @title ListNode.java
 * @description //TODO
 * @date 2022/7/11 20:48
 **/
@Data
public class ListNode {

    public int val;
    public ListNode next;

    public ListNode() {
        this(0);
    }

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    public ListNode build(int[] vals) {
        ListNode head = null, tmp = null, node;
        if (vals.length > 0) {
            for (int i = 0; i < vals.length; i++) {
                node = new ListNode(vals[i]);
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

    public boolean add(int target) {
        return add(target, this);
    }

    public boolean add(int after, int target) {
        return add(after, target, this);
    }

    public boolean replace(int origin, int target) {
        return replace(origin, target, this);
    }

    public boolean delete(int target) {
        return delete(target, this);
    }

    public ListNode get(int target) {
        return get(target, this);
    }

    public int firstFind(int target) {
        return firstFind(target, this);
    }

    public int lastFind(int target) {
        return lastFind(target, this);
    }

    public int size() {
        return size(this);
    }

    public ListNode reverse() {
        return reverse(this);
    }

    public boolean add(int target, ListNode head) {
        ListNode cur = head, tmp;
        ListNode node = new ListNode(target);
        if (head == null) {
            head = node;
            return true;
        }
        tmp = cur.next;
        cur.next = node;
        node.next = tmp;
        return true;
    }

    public boolean add(int after, int target, ListNode head) {
        boolean flag = false;
        if (head == null) {
            head = new ListNode(target);
            return true;
        }
        ListNode cur = head, node, tmp;
        while (cur != null) {
            if (cur.val == after) {
                node = new ListNode(target);
                tmp = cur.next;
                cur.next = node;
                node.next = tmp;
                flag = true;
            }
            cur = cur.next;
        }
        return flag;
    }

    public boolean append(ListNode head) {
        boolean flag = false;
        ListNode cur = this;
        if (head == null) {
            return true;
        }
        if (cur == null) {
            cur = head;
            return true;
        }
        while (cur.next != null) {
            cur = cur.next;
        }
        cur.next = head;
        return flag;
    }

    public boolean replace(int origin, int target, ListNode head) {
        boolean flag = false;
        if (head == null) {
            head = new ListNode(target);
            return true;
        }
        if (head.val == origin) {
            head.val = target;
            return true;
        }
        ListNode cur = head, node, tmp;
        while (cur.next != null) {
            if (cur.next.val == origin) {
                node = new ListNode(target);
                tmp = cur.next.next;
                cur.next = node;
                node.next = tmp;
                flag = true;
            }
            cur = cur.next;
        }
        return flag;
    }

    public boolean delete(int target, ListNode head) {
        boolean flag = false;
        if (head == null) {
            return true;
        }
        if (head.val == target) {
            head = head.next;
            return true;
        }
        ListNode cur = head.next, prev = head;
        while (cur != null) {
            if (cur.val == target) {
                prev.next = cur.next;
                cur = cur.next;
                flag = true;
                continue;
            }
            cur = cur.next;
            prev = prev.next;
        }
        return flag;
    }

    public ListNode get(int path, ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode cur = head;
        for (int i = 0; i < path && cur != null; i++) {
            cur = cur.next;
        }
        return cur;
    }

    public int firstFind(int target, ListNode head) {
        int path = -1;
        if (head == null) {
            return path;
        }
        ListNode cur = head;
        while (cur != null) {
            path++;
            if (cur.val == target) {
                return path;
            }
            cur = cur.next;
        }
        return -1;
    }

    public int lastFind(int target, ListNode head) {
        int path = -1, total = -1;
        if (head == null) {
            return path;
        }
        ListNode cur = head;
        while (cur != null) {
            total++;
            if (cur.val == target) {
                path = total;
            }
            cur = cur.next;
        }
        return path;
    }

    public int size(ListNode head) {
        int size = 0;
        if (head == null) {
            return size;
        }
        ListNode cur = head;
        while (cur != null) {
            size++;
            cur = cur.next;
        }
        return size;
    }

    public ListNode reverse(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode cur = head, prev = null, tmp;
        while (cur != null) {
            tmp = cur.next;
            cur.next = prev;
            prev = cur;
            cur = tmp;
        }
        return prev;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        if (this == null)
            return 0;
        int result = 1;
        ListNode cur = this;
        while (cur != null) {
            result = 31 * result + cur.val;
            cur = cur.next;
        }
        return result;
    }

    @Override
    public String toString() {
        ListNode cur = this;
        if (cur == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        while (cur != null) {
            sb.append(String.format("%s->", cur.val));
            cur = cur.next;
        }
        sb.append("null");
        return sb.toString();
    }

}
