package com.cbxs123.code;

import com.cbxs123.code.common.ListNode;
import com.cbxs123.code.common.RandomNode;
import com.cbxs123.code.common.TreeNode;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author cbxs123
 * @title Offer_68_220711.java
 * @description https://doocs.github.io/leetcode/#/lcof/README
 * @date 2022/7/11 19:22
 **/
@Slf4j
public class Offer_68_220711 {

    // 03. 数组中重复的数字#1
    @Test
    void code0003() {
        int[] nums = new int[]{2, 3, 1, 0, 2, 5, 3};
        int result = -1;
        for (int i = 0, n = nums.length; i < n; i++) {
            while (nums[i] != i) {
                if (nums[i] == nums[nums[i]]) {
                    result = nums[i];
                    break;
                }
                swap(nums, i, nums[i]);
            }
            if (result != -1) {
                break;
            }
        }
        log.info("result: {}", result);
    }

    private void swap(int[] nums, int i, int j) {
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }

    // 04. 二维数组中的查找#1
    @Test
    void code0004() {
        int[][] matrix = new int[][]{{1, 4, 7, 11, 15}, {2, 5, 8, 12, 19}, {3, 6, 9, 16, 22}, {10, 13, 14, 17, 24},
                {18, 21, 23, 26, 30}};
        int target = 5;
        boolean result = false;
        if (matrix.length > 0 && matrix[0].length > 0) {
            int m = matrix.length, n = matrix[0].length;
            for (int i = m - 1, j = 0; i >= 0 && j < n; ) {
                if (matrix[i][j] == target) {
                    result = true;
                    break;
                } else if (matrix[i][j] > target) {
                    i--;
                } else {
                    j++;
                }
            }
        }
        log.info("result: {}", result);
    }

    // 05. 替换空格#1
    @Test
    void code0005() {
        String s = "We are happy.";
        StringBuilder result = new StringBuilder();
        char[] chars = s.toCharArray();
        for (char c : chars) {
            result.append(c == ' ' ? "%20" : c);
        }
        log.info("result: {}", result.toString());
    }

    // 06. 从尾到头打印链表#2
    @Test
    void code0006() {
        ListNode head = new ListNode().build(new int[]{1, 2, 3});
        log.info("result: {}", code0006_1(head));
        log.info("result: {}", code0006_2(head));
    }

    private int[] code0006_1(ListNode head) {
        if (head == null) {
            return new int[]{};
        }
        int n = 0;
        for (ListNode cur = head; cur != null; ++n, cur = cur.next) ;
        int[] result = new int[n];
        for (ListNode cur = head; cur != null; result[--n] = cur.val, cur = cur.next) ;
        return result;
    }

    private int[] code0006_2(ListNode head) {
        Deque<Integer> deque = new ArrayDeque<>();
        for (ListNode cur = head; cur != null; deque.push(cur.val), cur = cur.next) ;
        int[] result = new int[deque.size()];
        int i = 0;
        while (!deque.isEmpty()) {
            result[i++] = deque.pop();
        }
        return result;
    }

    // 07. 重建二叉树#3
    @Test
    void code0007() {
        int[] preorder = new int[]{3, 9, 20, 15, 7}, inoder = new int[]{9, 3, 15, 20, 7};
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < inoder.length; i++) {
            map.put(inoder[i], i);
        }
        log.info("result: {}", dfs_0007(map, preorder, inoder, 0, 0, inoder.length));
    }

    private TreeNode dfs_0007(Map<Integer, Integer> map, int[] preorder, int[] inorder, int i, int j, int n) {
        if (n <= 0) {
            return null;
        }
        int v = preorder[i];
        int k = map.get(v);
        TreeNode root = new TreeNode(v);
        root.left = dfs_0007(map, preorder, inorder, i + 1, j, k - j);
        root.right = dfs_0007(map, preorder, inorder, i + 1 + k - j, k + 1, n - k + j - 1);
        return root;
    }

    // 09. 用两个栈实现队列#2
    @Test
    void code0009() {
        CQueue cq = new CQueue();
        cq.appendTail(1);
        cq.appendTail(2);
        cq.appendTail(3);
        log.info("result: {}", cq.toString());
        log.info("result: {}", cq.deleteHead());
        log.info("result: {}", cq.toString());
        log.info("result: {}", cq.deleteHead());
        log.info("result: {}", cq.toString());
    }

    @Data
    class CQueue {
        private Deque<Integer> stk1 = new ArrayDeque<>();
        private Deque<Integer> stk2 = new ArrayDeque<>();

        public CQueue() {
        }

        public void appendTail(int value) {
            stk1.push(value);
        }

        public int deleteHead() {
            if (stk2.isEmpty()) {
                while (!stk1.isEmpty()) {
                    stk2.push(stk1.pop());
                }
            }
            return stk2.isEmpty() ? -1 : stk2.pop();
        }
    }

    // 10- I. 斐波那契数列#1
    // 10- II. 青蛙跳台阶问题
    @Test
    void code0010() {
        int n = 5;
        int a = 0, b = 1;
        for (int i = 0; i < n; i++) {
            int c = (a + b) % 1000000007;
            a = b;
            b = c;
        }
        log.info("result: {}", a);
    }

    // 11. 旋转数组的最小数字#1
    @Test
    void code0011() {
        int[] numbers = new int[]{3, 4, 5, 1, 2};
        int l = 0, r = numbers.length - 1;
        while (l < r) {
            int m = (l + r) >>> 1;
            if (numbers[m] > numbers[r]) {
                l = m + 1;
            } else if (numbers[m] < numbers[r]) {
                r = m;
            } else {
                r--;
            }
        }
        log.info("result: {}", numbers[l]);
    }

    // 12. 矩阵中的路径#3
    @Test
    void code0012() {
        char[][] board = new char[][]{{'A', 'B', 'C', 'E'}, {'S', 'F', 'C', 'S'}, {'A', 'D', 'E', 'E'}};
        String word = "ABCCED";
        int m = board.length, n = board[0].length;
        boolean result = false;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (dfs_0012(i, j, 0, m, n, word, board)) {
                    result = true;
                    break;
                }
            }
        }
        log.info("result: {}", result);
    }

    private boolean dfs_0012(int i, int j, int k, int m, int n, String word, char[][] board) {
        if (k == word.length()) {
            return true;
        }
        if (i < 0 || i >= m || j < 0 || j >= n || word.charAt(k) != board[i][j]) {
            return false;
        }
        boolean ans = false;
        board[i][j] = ' ';
        int[] dirs = {-1, 0, 1, 0, -1};
        for (int l = 0; l < 4; l++) {
            ans = ans || dfs_0012(i + dirs[l], j + dirs[l + 1], k + 1, m, n, word, board);
        }
        board[i][j] = word.charAt(k);
        return ans;
    }

    // 13. 机器人的运动范围#2
    @Test
    void code0013() {
        int m = 2, n = 3, k = 1;
        boolean[][] vis = new boolean[m][n];
        log.info("result: {}", dfs_0013(0, 0, m, n, k, vis));
    }

    private int dfs_0013(int i, int j, int m, int n, int k, boolean[][] vis) {
        if (i >= m || j >= n || vis[i][j] || (i % 10 + i / 10 + j % 10 + j / 10) > k) {
            return 0;
        }
        vis[i][j] = true;
        return 1 + dfs_0013(i + 1, j, m, n, k, vis) + dfs_0013(i, j + 1, m, n, k, vis);
    }

    // 14- I. 剪绳子#1
    @Test
    void code0014_1() {
        int n = 10;
        int result = 1;
        if (n < 4) {
            result = n - 1;
        } else {
            while (n > 4) {
                result *= 3;
                n -= 3;
            }
            result *= n;
        }
        log.info("result: {}", result);
    }

    // 14- II. 剪绳子 II#1
    @Test
    void code0014_2() {
        int n = 10;
        int result = 1;
        if (n < 4) {
            result = n - 1;
        } else {
            while (n > 4) {
                result *= 3;
                result %= 1000000007;
                n -= 3;
            }
            result *= n;
            result %= 1000000007;
        }
        log.info("result: {}", result);
    }

    // 15. 二进制中 1 的个数#1
    @Test
    void code0015() {
        int n = 127;
        int result = 0;
        while (n != 0) {
            n &= n - 1;
            result++;
        }
        log.info("result: {}", result);
    }

    // 16. 数值的整数次方#1
    @Test
    void code0016() {
        log.info("result: {}", myPow(2.00, -2));
    }

    private double myPow(double x, int n) {
        if (n == 0) {
            return 1;
        }
        if (n == 1) {
            return x;
        }
        if (n == -1) {
            return 1 / x;
        }
        double half = myPow(x, n / 2);
        return half * half * myPow(x, n % 2);
    }

    // 17. 打印从 1 到最大的 n 位数#1
    @Test
    void code0017() {
        int n = 2;
        n = (int) Math.pow(10, n) - 1;
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            result[i] = i + 1;
        }
        log.info("result: {}", result);
    }

    // 18. 删除链表的节点#1
    @Test
    void code0018() {
        ListNode head = new ListNode().build(new int[]{1, 2, 3});
        int val = 2;
        ListNode result = new ListNode(0, head);
        ListNode pre = result;
        while (pre.next != null && pre.next.val != val) {
            pre = pre.next;
        }
        pre.next = pre.next == null ? null : pre.next.next;
        log.info("result: {}", result.next);
    }

    // 19. 正则表达式匹配#3
    @Test
    void code0019() {
        String s = "mississippi", p = "mis*is*p*.";
        int m = s.length(), n = p.length();
        boolean result = false;
        if (n == 0) {
            result = m == 0;
        } else {
            boolean[][] dp = new boolean[m + 1][n + 1];
            dp[0][0] = true;
            for (int j = 1; j < n + 1; j++) {
                if (p.charAt(j - 1) == '*') {
                    dp[0][j] = dp[0][j - 2];
                }
            }
            for (int i = 1; i < m + 1; i++) {
                for (int j = 1; j < n + 1; j++) {
                    if (s.charAt(i - 1) == p.charAt(j - 1) || p.charAt(j - 1) == '.') {
                        dp[i][j] = dp[i - 1][j - 1];
                    } else if (p.charAt(j - 1) == '*') {
                        if (s.charAt(i - 1) == p.charAt(j - 2) || p.charAt(j - 2) == '.') {
                            dp[i][j] = dp[i][j - 2] || dp[i - 1][j];
                        } else {
                            dp[i][j] = dp[i][j - 2];
                        }
                    }
                }
            }
            result = dp[m][n];
        }
        log.info("result: {}", result);
    }

    // 20. 表示数值的字符串#2
    @Test
    void code0020() {
        String s = "-1E-16";
        boolean findNum = false;
        if (s != null && s.trim().length() == 0) {
            char[] chars = s.trim().toCharArray();
            boolean findE = false, findDot = false;
            for (int i = 0, n = chars.length; i < n; i++) {
                if (chars[i] == '+' || chars[i] == '-') {
                    if (i != 0 && chars[i - 1] == 'e' && chars[i - 1] == 'E') {
                        findNum = false;
                        break;
                    }
                } else if (chars[i] >= '0' && chars[i] <= '9') {
                    findNum = true;
                } else if (chars[i] == '.') {
                    if (findDot || findE) {
                        findNum = false;
                        break;
                    }
                    findDot = true;
                } else if (chars[i] == 'e' || chars[i] == 'E') {
                    if (findE || !findNum) {
                        findNum = false;
                        break;
                    }
                    findE = true;
                    findNum = false;
                } else {
                    findNum = false;
                    break;
                }
            }
        }
        log.info("result: {}", findNum);
    }

    // 21. 调整数组顺序使奇数位于偶数前面#1
    @Test
    void code0021() {
        int[] nums = new int[]{1, 2, 3, 4, 5, 6};
        int p = 0, q = nums.length - 1;
        while (p < q) {
            if ((nums[p] & 1) == 1) {
                p++;
                continue;
            }
            if ((nums[q] & 1) == 0) {
                q--;
                continue;
            }
            swap_021(nums, p, q);
        }
        log.info("result: {}", nums);
    }

    private void swap_021(int[] nums, int p, int q) {
        int t = nums[p];
        nums[p] = nums[q];
        nums[q] = t;
    }

    // 22. 链表中倒数第 k 个节点#1
    @Test
    void code0022() {
        ListNode head = new ListNode().build(new int[]{1, 2, 3, 4, 5});
        int k = 2;
        ListNode slow = head, fast = head;
        while (k-- > 0) {
            fast = fast.next;
        }
        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }
        log.info("result: {}", slow);
    }

    // 24. 反转链表#1
    @Test
    void code0024() {
        ListNode head = new ListNode().build(new int[]{1, 2, 3, 4, 5});
        ListNode pre = null, cur = head, tmp;
        while (cur != null) {
            tmp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = tmp;
        }
        log.info("result: {}", pre);
    }

    // 25. 合并两个排序的链表#1
    @Test
    void code0025() {
        ListNode l1 = new ListNode().build(new int[]{1, 2, 3});
        ListNode l2 = new ListNode().build(new int[]{2, 4, 6});
        ListNode d = new ListNode(0), p = d;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                p.next = l1;
                l1 = l1.next;
            } else {
                p.next = l2;
                l2 = l2.next;
            }
            p = p.next;
        }
        p.next = l1 == null ? l2 : l1;
        log.info("result: {}", d.next);
    }

    // 26. 树的子结构#2
    @Test
    void code0026() {
        TreeNode A = new TreeNode().build(new Integer[]{1, 2, 3, 4, 5});
        TreeNode B = new TreeNode().build(new Integer[]{2, 4, 5});
        boolean result = isSub_0026(A, B);
        log.info("result: {}", result);
    }

    private boolean isSub_0026(TreeNode A, TreeNode B) {
        if (A == null || B == null) {
            return false;
        }
        return dfs_0026(A, B) || isSub_0026(A.left, B) || isSub_0026(A.right, B);
    }

    private boolean dfs_0026(TreeNode A, TreeNode B) {
        if (B == null) {
            return true;
        }
        if (A == null || A.val != B.val) {
            return false;
        }
        return dfs_0026(A.left, B.left) && dfs_0026(A.right, B.right);
    }

    // 27. 二叉树的镜像#1
    @Test
    void code0027() {
        TreeNode root = new TreeNode().build(new Integer[]{1, 2, 3, 4, 5});
        log.info("result: {}", root);
        log.info("result: {}", mirror_0027(root));
    }

    private TreeNode mirror_0027(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode tmp = root.left;
        root.left = root.right;
        root.right = tmp;
        mirror_0027(root.left);
        mirror_0027(root.right);
        return root;
    }

    // 28. 对称的二叉树#1
    @Test
    void code0028() {
        TreeNode root = new TreeNode().build(new Integer[]{1, 2, 2, 4, 5, 5, 4});
        log.info("result: {}", root);
        log.info("result: {}", mirror_0028(root.left, root.right));
    }

    private boolean mirror_0028(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true;
        }
        if (left == null || right == null || left.val != right.val) {
            return false;
        }
        return mirror_0028(left.left, right.right) && mirror_0028(left.right, right.left);
    }

    // 29. 顺时针打印矩阵#2
    @Test
    void code0029() {
        int[][] matrix = new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}};
        int[] result = new int[]{};
        if (matrix != null && matrix[0] != null) {
            int m = matrix.length;
            int n = matrix[0].length;
            result = new int[m * n];
            int index = 0;
            int i1 = 0, i2 = m - 1;
            int j1 = 0, j2 = n - 1;
            while (i1 <= i2 && j1 <= j2) {
                index = add(result, matrix, index, i1++, j1++, i2--, j2--);
            }
        }
        log.info("result: {}", result);
    }

    private int add(int[] result, int[][] matrix, int index, int i1, int j1, int i2, int j2) {
        if (i1 == i2) {
            for (int j = j1; j <= j2; j++) {
                result[index++] = matrix[i1][j];
            }
            return index;
        }
        if (j1 == j2) {
            for (int i = i1; i <= i2; i++) {
                result[index++] = matrix[i][j1];
            }
            return index;
        }
        for (int j = j1; j < j2; j++) {
            result[index++] = matrix[i1][j];
        }
        for (int i = i1; i < i2; i++) {
            result[index++] = matrix[i][j2];
        }
        for (int j = j2; j > j1; j--) {
            result[index++] = matrix[i2][j];
        }
        for (int i = i2; i > i1; i--) {
            result[index++] = matrix[i][j1];
        }
        return index;
    }

    // 30. 包含 min 函数的栈#1
    @Test
    void code0030() {
        MinStack o = new MinStack();
        o.push(3);
        o.push(2);
        o.push(1);
        o.push(4);
        o.push(5);
        System.out.println(o.all);
        o.pop();
        System.out.println(o.all);
        System.out.println(o.min);
        System.out.println(o.top());
        System.out.println(o.min());
    }

    class MinStack {
        private Deque<Integer> all;
        private Deque<Integer> min;

        public MinStack() {
            all = new ArrayDeque<>();
            min = new ArrayDeque<>();
            min.push(Integer.MAX_VALUE);
        }

        public void push(int val) {
            all.push(val);
            min.push(Math.min(min.peek(), val));
        }

        public void pop() {
            all.pop();
            min.pop();
        }

        public int top() {
            return all.peek();
        }

        public int min() {
            return min.peek();
        }
    }

    // 31. 栈的压入、弹出序列#1
    @Test
    void code0031() {
        int[] pushed = new int[]{1, 2, 3, 4, 5};
        int[] popped = new int[]{4, 3, 5, 1, 2};
        Deque<Integer> s = new ArrayDeque<>();
        int q = 0;
        for (int num : pushed) {
            s.push(num);
            while (!s.isEmpty() && s.peek() == popped[q]) {
                s.pop();
                q++;
            }
        }
        log.info("result: {}", s.isEmpty());
    }

    // 32 - I. 从上到下打印二叉树#2
    @Test
    void code0032_1() {
        TreeNode root = new TreeNode().build(new Integer[]{1, 2, 3, 4, 5});
        int[] result = new int[]{};
        if (root != null) {
            Deque<TreeNode> q = new ArrayDeque<>();
            List<Integer> t = new ArrayList<>();
            q.offer(root);
            while (!q.isEmpty()) {
                int size = q.size();
                while (size-- > 0) {
                    TreeNode node = q.poll();
                    t.add(node.val);
                    if (node.left != null) {
                        q.offer(node.left);
                    }
                    if (node.right != null) {
                        q.offer(node.right);
                    }
                }
            }
            // result = t.stream().mapToInt(Integer::intValue).toArray();
            int i = 0;
            result = new int[t.size()];
            for (int v : t) {
                result[i++] = v;
            }
        }
        log.info("result: {}", result);
    }

    // 32 - II. 从上到下打印二叉树 II#2
    @Test
    void code0032_2() {
        TreeNode root = new TreeNode().build(new Integer[]{1, 2, 3, 4, 5});
        List<List<Integer>> result = new ArrayList<>();
        if (root != null) {
            Deque<TreeNode> q = new ArrayDeque<>();
            q.offer(root);
            while (!q.isEmpty()) {
                int size = q.size();
                List<Integer> t = new ArrayList<>();
                while (size-- > 0) {
                    TreeNode node = q.poll();
                    t.add(node.val);
                    if (node.left != null) {
                        q.offer(node.left);
                    }
                    if (node.right != null) {
                        q.offer(node.right);
                    }
                }
                result.add(t);
            }
        }
        log.info("result: {}", result);
    }

    // 32 - III. 从上到下打印二叉树 III#2
    @Test
    void code0032_3() {
        TreeNode root = new TreeNode().build(new Integer[]{1, 2, 3, 4, 5});
        List<List<Integer>> result = new ArrayList<>();
        if (root != null) {
            Deque<TreeNode> q = new ArrayDeque<>();
            q.offer(root);
            while (!q.isEmpty()) {
                int size = q.size();
                List<Integer> t = new ArrayList<>();
                while (size-- > 0) {
                    TreeNode node = q.poll();
                    t.add(node.val);
                    if (node.left != null) {
                        q.offer(node.left);
                    }
                    if (node.right != null) {
                        q.offer(node.right);
                    }
                }
                if ((result.size() & 1) == 1) {
                    Collections.reverse(t);
                }
                result.add(t);
            }
        }
        log.info("result: {}", result);
    }

    // 33. 二叉搜索树的后序遍历序列#3
    @Test
    void code0033() {
        int[] postorder = new int[]{1, 3, 2, 6, 5};
        boolean result = true;
        if (postorder != null && postorder.length > 1) {
            result = dfs_0033(postorder, 0, postorder.length);
        }
        log.info("result: {}", result);
    }

    private boolean dfs_0033(int[] postorder, int i, int n) {
        if (n <= 0) {
            return true;
        }
        int v = postorder[i + n - 1];
        int j = i;
        while (j < i + n && postorder[j] < v) {
            j++;
        }
        for (int k = j; k < i + n; k++) {
            if (postorder[k] < v) {
                return false;
            }
        }
        return dfs_0033(postorder, i, j - i) && dfs_0033(postorder, j, n + i - j - 1);
    }

    // 34. 二叉树中和为某一值的路径#2
    @Test
    void code0034() {
        TreeNode root = new TreeNode().build(new Integer[]{5, 4, 8, 11, null, 13, 4, 7, 2, null, null, 5, 1});
        int target = 22;
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        if (root != null) {
            dfs_0034(result, path, root, target);
        }
        log.info("result: {}", result);
    }

    private void dfs_0034(List<List<Integer>> result, List<Integer> path, TreeNode root, int sum) {
        if (root == null) {
            return;
        }
        path.add(root.val);
        if (root.val == sum && root.left == null && root.right == null) {
            result.add(new ArrayList<>(path));
        }
        dfs_0034(result, path, root.left, sum - root.val);
        dfs_0034(result, path, root.right, sum - root.val);
        path.remove(path.size() - 1);
    }

    // 35. 复杂链表的复制#3
    @Test
    void code0035() {
        Integer[][] val = new Integer[][]{{5, null}, {4, 0}, {3, 1}, {2, 2}, {1, 3}};
        RandomNode head = new RandomNode().build(val);
        RandomNode result = null;
        if (head != null) {
            RandomNode cur = head;
            while (cur != null) {
                RandomNode node = new RandomNode(cur.val);
                node.next = cur.next;
                cur.next = node;
                cur = node.next;
            }

            cur = head;
            while (cur != null) {
                cur.next.random = cur.random == null ? null : cur.random.next;
                cur = cur.next.next;
            }

            result = head.next;
            cur = head;
            while (cur != null) {
                RandomNode next = cur.next;
                cur.next = next.next;
                next.next = next.next == null ? null : next.next.next;
                cur = cur.next;
            }
        }
        log.info("result: {}", result);
    }

    // 36. 二叉搜索树与双向链表#2
    private TreeNode head_0036;
    private TreeNode pre_0036;

    @Test
    void code0036() {
        TreeNode root = new TreeNode().build(new Integer[]{4, 2, 5, 1, 3, null, null});
        if (root != null) {
            dfs_0036(root);
            head_0036.left = pre_0036;
            pre_0036.right = head_0036;
        }
        log.info("result: {}", head_0036);
    }

    void dfs_0036(TreeNode cur) {
        if (cur == null) {
            return;
        }
        dfs_0036(cur.left);
        if (pre_0036 == null) {
            head_0036 = cur;
        } else {
            pre_0036.right = cur;
        }
        cur.left = pre_0036;
        pre_0036 = cur;
        dfs_0036(cur.right);
    }

    // 37. 序列化二叉树#2
    @Test
    void code0037() {
        TreeNode root = new TreeNode().build(new Integer[]{1, 2, 3, 4, null, null, 5});
        TreeNode result = deserialize_0037(serialize_0037(root));
        log.info("root: {}", root);
        log.info("result: {}", result);
    }

    private String serialize_0037(TreeNode root) {
        if (root == null) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder("[");
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node != null) {
                sb.append(node.val);
                queue.offer(node.left);
                queue.offer(node.right);
            } else {
                sb.append("null");
            }
            sb.append(",");
        }
        return sb.deleteCharAt(sb.length() - 1).append("]").toString();
    }

    private TreeNode deserialize_0037(String data) {
        if (data == null || "[]".equals(data)) {
            return null;
        }
        String[] nodes = data.substring(1, data.length() - 1).split(",");
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode root = new TreeNode(Integer.parseInt(nodes[0]));
        queue.offer(root);
        int index = 1;
        while (!queue.isEmpty() && index < nodes.length) {
            TreeNode node = queue.poll();
            if (!"null".equals(nodes[index])) {
                node.left = new TreeNode(Integer.parseInt(nodes[index]));
                queue.offer(node.left);
            }
            index++;
            if (!"null".equals(nodes[index])) {
                node.right = new TreeNode(Integer.parseInt(nodes[index]));
                queue.offer(node.right);
            }
            index++;
        }
        return root;
    }

    // 38. 字符串的排列#2
    @Test
    void code0038() {
        String s = "abc";
        char[] chars = s.toCharArray();
        List<String> result = new ArrayList<>();
        dfs_0038(0, chars, result);
        log.info("result: {}", Arrays.toString(result.toArray(new String[result.size()])));
    }

    private void dfs_0038(int x, char[] chars, List<String> result) {
        if (x == chars.length - 1) {
            result.add(String.valueOf(chars));
            return;
        }
        Set<Character> set = new HashSet<>();
        for (int i = x; i < chars.length; i++) {
            if (set.contains(chars[i])) {
                continue;
            }
            set.add(chars[i]);
            swap_0038(i, x, chars);
            dfs_0038(x + 1, chars, result);
            swap_0038(i, x, chars);
        }
    }

    private void swap_0038(int i, int j, char[] chars) {
        char t = chars[i];
        chars[i] = chars[j];
        chars[j] = t;
    }

    // 39. 数组中出现次数超过一半的数字#1
    @Test
    void code0039() {
        int[] nums = new int[]{1, 2, 3, 2, 2, 2, 5, 4, 2};
        int cnt = 0, m = 0;
        for (int v : nums) {
            if (cnt == 0) {
                m = v;
                cnt = 1;
            } else {
                cnt += (m == v ? 1 : -1);
            }
        }
        log.info("result: {}", m);
    }

    // 40. 最小的k个数#1
    @Test
    void code0040() {
        int[] arr = new int[]{4, 5, 1, 6, 2, 7, 3, 8};
        int k = 4;
        int[] result = new int[]{};
        if (k > 0) {
            PriorityQueue<Integer> queue = new PriorityQueue<>(k, Collections.reverseOrder());
            for (int e : arr) {
                if (queue.size() < k) {
                    queue.offer(e);
                } else if (e < queue.peek()) {
                    queue.poll();
                    queue.offer(e);
                }
            }
            result = new int[k];
            for (int i = 0; i < k; i++) {
                result[i] = queue.poll();
            }
        }
        log.info("result: {}", Arrays.toString(result));
    }

    // 41. 数据流中的中位数#2
    @Test
    void code0041() {
        MedianFinder obj = new MedianFinder();
        obj.addNum(3);
        obj.addNum(1);
        obj.addNum(4);
        obj.addNum(1);
        obj.addNum(5);
        obj.addNum(9);
        obj.addNum(2);
        obj.addNum(6);
        log.info("result: {}", obj.findMedian());
    }

    class MedianFinder {
        Queue<Integer> minHeap;
        Queue<Integer> maxHeap;

        public MedianFinder() {
            this.minHeap = new PriorityQueue<>();
            this.maxHeap = new PriorityQueue<>((a, b) -> b - a);
        }

        public void addNum(int num) {
            if (maxHeap.size() == minHeap.size()) {
                maxHeap.offer(num);
                minHeap.offer(maxHeap.poll());
            } else {
                minHeap.offer(num);
                maxHeap.offer(minHeap.poll());
            }
        }

        public double findMedian() {
            if (((maxHeap.size() + minHeap.size()) & 1) == 0) {
                return (maxHeap.peek() + minHeap.peek()) / 2.0;
            }
            return minHeap.peek();
        }
    }

    // 42. 连续子数组的最大和#1
    @Test
    void code0042() {
        int[] nums = new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4};
        int result = nums[0], dp = nums[0];
        for (int i = 1, n = nums.length; i < n; i++) {
            dp = nums[i] + Math.max(dp, 0);
            result = Math.max(result, dp);
        }
        log.info("result: {}", result);
    }

    // 43. 1~n整数中1出现的次数#1
    @Test
    void code0043() {
        log.info("result: {}", count_0043(13));
    }

    private int count_0043(int n) {
        if (n < 1) {
            return 0;
        }
        String s = String.valueOf(n);
        int high = s.charAt(0) - '0';
        int base = (int) Math.pow(10, s.length() - 1);
        int low = n % base;
        return high == 1 ? count_0043(base - 1) + count_0043(low) + low + 1
                : high * count_0043(base - 1) + count_0043(low) + base;
    }

    // 44. 数字序列中某一位的数字#2
    @Test
    void code0044() {
        int n = 19, result = 0;
        if (n > 9) {
            int pow = 0, count;
            while (true) {
                count = bitNum_0044(pow);
                if (n < count) {
                    break;
                }
                n -= count;
                pow++;
            }
            int num = n / (pow + 1) + (int) Math.pow(10, pow);
            result = String.valueOf(num).charAt(n % (pow + 1)) - '0';
        }
        log.info("result: {}", result);
    }

    private int bitNum_0044(int pow) {
        if (pow == 0) {
            return 10;
        }
        return (int) (9 * Math.pow(10, pow) * (pow + 1));
    }

    // 45. 把数组排成最小的数#1
    @Test
    void code0045() {
        int[] nums = new int[]{3, 30, 34, 5, 9};
        String result = "";
        if (nums != null && nums.length > 0) {
            result = Arrays.stream(nums).mapToObj(String::valueOf).sorted((a, b) -> (a + b).compareTo((b + a)))
                    .reduce((a, b) -> a + b).get();
        }
        log.info("result: {}", result);
    }

    // 46. 把数字翻译成字符串#2
    @Test
    void code0046() {
        log.info("result: {}", cal_0046(String.valueOf(12258)));
    }

    private int cal_0046(String s) {
        int n = s.length();
        if (n < 2) {
            return 1;
        }
        int t = Integer.parseInt(s.substring(0, 2));
        return t < 10 || t > 25 ? cal_0046(s.substring(1)) : cal_0046(s.substring(1)) + cal_0046(s.substring(2));
    }

    // 47. 礼物的最大价值#1
    @Test
    void code0047() {
        int[][] grid = new int[][]{{1, 3, 1}, {1, 5, 1}, {4, 2, 1}};
        int m = grid.length, n = grid[0].length;
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]) + grid[i - 1][j - 1];
            }
        }
        log.info("result: {}", dp[m][n]);
    }

    // 48. 最长不含重复字符的子字符串#2
    @Test
    void code0048() {
        String s = "pwwkew";
        int result = 0;
        Set<Character> set = new HashSet<>();
        for (int i = 0, j = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            while (set.contains(c)) {
                set.remove(s.charAt(j++));
            }
            set.add(c);
            result = Math.max(result, i - j + 1);
        }
        log.info("result: {}", result);
    }

    // 49. 丑数#2
    @Test
    void code0049() {
        int n = 10;
        int[] dp = new int[n];
        dp[0] = 1;
        int p2 = 0, p3 = 0, p5 = 0;
        for (int i = 1; i < n; i++) {
            int next2 = dp[p2] * 2, next3 = dp[p3] * 3, next5 = dp[p5] * 5;
            dp[i] = Math.min(next2, Math.min(next3, next5));
            if (dp[i] == next2) {
                p2++;
            }
            if (dp[i] == next3) {
                p3++;
            }
            if (dp[i] == next5) {
                p5++;
            }
        }
        log.info("result: {}", dp[n - 1]);
    }

    // 50. 第一个只出现一次的字符#1
    @Test
    void code0050() {
        String s = "abaccdeff";
        int n = s.length();
        char result = ' ';
        if (n > 0) {
            int[] count = new int[26];
            for (int i = 0; i < n; i++) {
                int index = s.charAt(i) - 'a';
                count[index]++;
            }
            for (int i = 0; i < n; i++) {
                int index = s.charAt(i) - 'a';
                if (count[index] == 1) {
                    result = s.charAt(i);
                    break;
                }
            }
        }
        log.info("result: {}", result);
    }

    // 51. 数组中的逆序对#3
    @Test
    void code0051() {
        int[] nums = new int[]{7, 5, 6, 4};
        int[] tmp = new int[50010];
        log.info("result: {}", mergeSort_0051(tmp, nums, 0, nums.length - 1));
    }

    private int mergeSort_0051(int[] tmp, int[] nums, int left, int right) {
        if (left >= right) {
            return 0;
        }
        int mid = (left + right) >> 1;
        int res = mergeSort_0051(tmp, nums, left, mid) + mergeSort_0051(tmp, nums, mid + 1, right);
        int i = left, j = mid + 1, k = 0;
        while (i <= mid && j <= right) {
            if (nums[i] <= nums[j]) {
                tmp[k++] = nums[i++];
            } else {
                res += (mid - i + 1);
                tmp[k++] = nums[j++];
            }
        }
        while (i <= mid) {
            tmp[k++] = nums[i++];
        }
        while (j <= right) {
            tmp[k++] = nums[j++];
        }
        for (i = left; i <= right; i++) {
            nums[i] = tmp[i - left];
        }
        return res;
    }

    // 52. 两个链表的第一个公共节点#1
    @Test
    void code0052() {
        ListNode headA = new ListNode().build(new int[]{0, 9, 1});
        ListNode headB = new ListNode().build(new int[]{3});
        ListNode common = new ListNode().build(new int[]{2, 4});
        headA.append(common);
        headB.append(common);

        ListNode cur1 = headA, cur2 = headB;
        while (cur1 != cur2) {
            cur1 = cur1 == null ? headB : cur1.next;
            cur2 = cur2 == null ? headA : cur2.next;
        }
        log.info("result: {}", cur1);
    }

    // 53 - I. 在排序数组中查找数字次数 I#2
    @Test
    void code0053_1() {
        int[] nums = new int[]{5, 7, 7, 8, 8, 10};
        int result = 0, target = 8;
        if (nums.length > 0) {
            // find first position
            int left = 0, right = nums.length - 1;
            while (left < right) {
                int mid = (left + right) >>> 1;
                if (nums[mid] >= target) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }
            if (nums[left] == target) {
                int l = left;
                // find last position
                right = nums.length - 1;
                while (left < right) {
                    int mid = (left + right + 1) >>> 1;
                    if (nums[mid] <= target) {
                        left = mid;
                    } else {
                        right = mid - 1;
                    }
                }
                result = left - l + 1;
            }
        }
        log.info("result: {}", result);
    }

    // 53 - II. 0~n-1 中缺失的数字#2
    @Test
    void code0053_2() {
        int[] nums = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 9};
        int result, left = 0, right = nums.length - 1;
        if (right == 0 || nums[0] == 1) {
            result = nums[0] ^ 1;
        } else if (nums[right] == right) {
            result = right + 1;
        } else {
            while (right - left > 1) {
                int mid = (left + right) >>> 1;
                if (nums[mid] == mid) {
                    left = mid;
                } else {
                    right = mid;
                }
            }
            result = nums[right] - 1;
        }
        log.info("result: {}", result);
    }

    // 54. 二叉搜索树的第 k大节点#2
    @Test
    void code0054() {
        TreeNode root = new TreeNode().build(new Integer[]{5, 3, 6, 2, 4, null, null, 1});
        int k = 3;
        AtomicInteger cur = new AtomicInteger(k);
        AtomicInteger result = new AtomicInteger(0);
        inorder_0054(root, cur, result);
        log.info("result: {}", result);
    }

    private void inorder_0054(TreeNode root, AtomicInteger cur, AtomicInteger result) {
        if (root == null) {
            return;
        }
        inorder_0054(root.right, cur, result);
        if (cur.decrementAndGet() == 0) {
            result.getAndSet(root.val);
            return;
        }
        inorder_0054(root.left, cur, result);
    }

    // 55 - I. 二叉树的深度#1
    @Test
    void code0055_1() {
        TreeNode root = new TreeNode().build(new Integer[]{5, 3, 6, 2, 4, null, null, 1});
        log.info("result: {}", maxDepth_0055(root));
    }

    private int maxDepth_0055(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return 1 + Math.max(maxDepth_0055(root.left), maxDepth_0055(root.right));
    }

    // 55 - II. 平衡二叉树#2
    @Test
    void code0055_2() {
        TreeNode root = new TreeNode().build(new Integer[]{1, 2, 2, 3, 3, null, null, 4, 4});
        log.info("result: {}", isBalanced_0055(root));
    }

    private boolean isBalanced_0055(TreeNode root) {
        if (root == null) {
            return true;
        }
        return Math.abs(depth_0055(root.left) - depth_0055(root.right)) <= 1 && isBalanced_0055(root.left)
                && isBalanced_0055(root.right);
    }

    private int depth_0055(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return 1 + Math.max(depth_0055(root.left), depth_0055(root.right));
    }

    // 56 - I. 数组中数字出现的次数#2
    @Test
    void code0056_1() {
        int[] nums = new int[]{1, 2, 10, 4, 1, 4, 3, 3};
        int eor = 0;
        for (int num : nums) {
            eor ^= num;
        }
        int diff = eor & (~eor + 1);
        int a = 0;
        for (int num : nums) {
            if ((num & diff) == 0) {
                a ^= num;
            }
        }
        int b = eor ^ a;
        log.info("result: {}", new int[]{a, b});
    }

    // 56 - II. 数组中数字出现的次数 II#2
    @Test
    void code0056_2() {
        int[] nums = new int[]{9, 1, 7, 9, 7, 9, 7};
        int result = 0;
        int[] bits = new int[32];
        for (int num : nums) {
            for (int i = 0; i < 32; i++) {
                bits[i] += (num & 1);
                num >>= 1;
            }
        }
        for (int i = 0; i < 32; i++) {
            if (bits[i] % 3 == 1) {
                result += (1 << i);
            }
        }
        log.info("result: {}", result);
    }

    // 57. 和为 s 的两个数字#1
    @Test
    void code0057_1() {
        int[] nums = new int[]{2, 7, 11, 15};
        int target = 9;
        log.info("result: {}", twoSum_0057_1(nums, target));
        log.info("result: {}", twoSum_0057_2(nums, target));
    }

    private int[] twoSum_0057_1(int[] nums, int target) {
        Set<Integer> s = new HashSet<>();
        for (int num : nums) {
            if (s.contains(target - num)) {
                return new int[]{num, target - num};
            }
            s.add(num);
        }
        return null;
    }

    private int[] twoSum_0057_2(int[] nums, int target) {
        for (int p = 0, q = nums.length - 1; p < q; ) {
            int s = nums[p] + nums[q];
            if (s == target) {
                return new int[]{nums[p], nums[q]};
            }
            if (s < target) {
                p++;
            } else {
                q--;
            }
        }
        return null;
    }

    // 57 - II. 和为 s 的连续正数序列#2
    @Test
    void code0057_2() {
        int target = 15;
        List<int[]> list = new ArrayList<>();
        int p = 1, q = 2;
        while (p < q) {
            int s = (p + q) * (q - p + 1) >> 1;
            if (s == target) {
                int[] t = new int[q - p + 1];
                for (int i = 0; i < t.length; i++) {
                    t[i] = p + i;
                }
                list.add(t);
                p++;
            } else if (s < target) {
                q++;
            } else {
                p++;
            }
        }
        int[][] result = new int[list.size()][];
        for (int i = 0; i < result.length; i++) {
            result[i] = list.get(i);
        }
        Arrays.asList(result).stream().map(x -> Arrays.toString(x)).forEach(System.out::println);
    }

    // 58 - I. 翻转单词顺序#1
    @Test
    void code0058_1() {
        String s = "I  am a student. ";
        String result = "";
        if (s != null && s.length() > 0) {
            String[] words = s.split("\\s+");
            StringBuilder sb = new StringBuilder();
            int len = words.length;
            for (int i = len - 1; i >= 0; i--) {
                if (!"".equals(words[i])) {
                    sb.append(words[i]).append(" ");
                }
            }
            s = sb.toString();
            len = s.length();
            result = len > 0 ? s.substring(0, len - 1) : "";
        }
        log.info("result: {}", result);
    }

    // 58 - II. 左旋转字符串#1
    @Test
    void code0058_2() {
        String s = "abcdefg";
        int k = 2;
        String result = s.substring(k) + s.substring(0, k);
        log.info("result: {}", result);
    }

    // 59 - I. 滑动窗口的最大值#2
    @Test
    void code0059_1() {
        int[] nums = new int[]{1, 3, -1, -3, 5, 3, 6, 7};
        int k = 3;
        int[] result = new int[0];
        if (nums != null) {
            int index = 0, n = nums.length;
            if (k != 0 && n != 0) {
                result = new int[n - k + 1];
                LinkedList<Integer> q = new LinkedList<>();
                for (int i = 0; i < n; i++) {
                    while (!q.isEmpty() && nums[q.peekLast()] <= nums[i]) {
                        q.pollLast();
                    }
                    q.addLast(i);
                    if (q.peekFirst() == i - k) {
                        q.pollFirst();
                    }
                    if (i >= k - 1) {
                        result[index++] = nums[q.peekFirst()];
                    }
                }
            }
        }
        log.info("result: {}", result);
    }

    // 59 - II. 队列的最大值#2
    @Test
    void code0059_2() {
        MaxQueue obj = new MaxQueue();
        int p1 = obj.max_value();
        obj.push_back(3);
        int p2 = obj.pop_front();
        obj.push_back(5);
        int p3 = obj.max_value();
        int p4 = obj.pop_front();
        int p5 = obj.max_value();
        log.info("result: {},{},{},{},{}", p1, p2, p3, p4, p5);
    }

    class MaxQueue {

        private Deque<Integer> p;
        private Deque<Integer> q;

        public MaxQueue() {
            p = new ArrayDeque<>();
            q = new ArrayDeque<>();
        }

        public int max_value() {
            return q.isEmpty() ? -1 : q.peekFirst();
        }

        public void push_back(int value) {
            while (!q.isEmpty() && q.peekLast() < value) {
                q.pollLast();
            }
            p.offerLast(value);
            q.offerLast(value);
        }

        public int pop_front() {
            if (p.isEmpty()) {
                return -1;
            }
            int res = p.pollFirst();
            if (q.peek() == res) {
                q.pollFirst();
            }
            return res;
        }
    }

    // 60. n 个骰子的点数#3
    @Test
    void code0060() {
        int n = 2;
        int[][] dp = new int[n + 1][6 * n + 1];
        for (int i = 1; i <= 6; i++) {
            dp[1][i] = 1;
        }
        for (int i = 2; i <= n; i++) {
            for (int j = i; j <= 6 * i; j++) {
                for (int k = 1; k <= 6 && j > k; k++) {
                    dp[i][j] += dp[i - 1][j - k];
                }
            }
        }
        double[] result = new double[5 * n + 1];
        double all = Math.pow(6, n);
        for (int i = 0; i <= 5 * n; i++) {
            result[i] = Double.parseDouble(String.format("%.5f", dp[n][n + i] * 1.0 / all));
        }
        log.info("result: {}", result);
    }

    // 61. 扑克牌中的顺子#1
    @Test
    void code0061() {
        int[] nums = new int[]{0, 0, 2, 3, 6};
        boolean result = true;
        boolean[] t = new boolean[14];
        int maxVal = Integer.MIN_VALUE, minVal = Integer.MAX_VALUE;
        for (int num : nums) {
            if (num == 0) {
                continue;
            }
            if (t[num]) {
                result = false;
                break;
            }
            t[num] = true;
            maxVal = Math.max(maxVal, num);
            minVal = Math.min(minVal, num);
        }
        result = result && maxVal - minVal <= 4;
        log.info("result: {}", result);
    }

    // 62. 圆圈中最后剩下的数字#1
    @Test
    void code0062() {
        int n = 5, m = 3, f = 0;
        for (int i = 2; i <= n; i++) {
            f = (f + m) % i;
        }
        log.info("result: {}", f);
    }

    // 63. 股票的最大利润#2
    @Test
    void code0063() {
        int[] prices = new int[]{7, 1, 5, 3, 6, 4};
        int len = prices.length;
        int result = 0;
        if (len > 0) {
            int min = prices[0];
            for (int i = 1; i < len; i++) {
                result = Math.max(result, prices[i] - min);
                min = Math.min(min, prices[i]);
            }
        }
        log.info("result: {}", result);
    }

    // 64. 求 1+2+…+n#2
    @Test
    void code0064() {
        log.info("result: {}", sum_0064(9));
    }

    private int sum_0064(int n) {
        int s = n;
        boolean t = n > 0 && (s += sum_0064(n - 1)) > 0;
        return s;
    }

    // 65. 不用加减乘除做加法#2
    @Test
    void code0065() {
        int a = 2, b = 3;
        while (b != 0) {
            int s = a ^ b;
            b = (a & b) << 1;
            a = s;
        }
        log.info("result: {}", a);
    }

    // 66. 构建乘积数组#2
    @Test
    void code0066() {
        int[] a = new int[]{1, 2, 3, 4, 5};
        int len = a.length;
        int[] b = new int[len];
        for (int i = 0, left = 1; i < len; i++) {
            b[i] = left;
            left *= a[i];
        }
        for (int i = len - 1, right = 1; i >= 0; i--) {
            b[i] = right;
            right *= a[i];
        }
        log.info("result: {}", b);
    }

    // 67. 把字符串转换成整数#3
    @Test
    void code0067() {
        String s = " -543w21";
        int result = 0;
        if (s != null) {
            int len = s.length();
            if (len > 0) {
                int i = 0;
                while (i < len && s.charAt(i) == ' ') {
                    i++;
                }
                if (i != len) {
                    int sign = s.charAt(i) == '-' ? -1 : 1;
                    if (s.charAt(i) == '-' || s.charAt(i) == '+') {
                        i++;
                    }
                    int flag = Integer.MAX_VALUE / 10;
                    for (; i < len; i++) {
                        if (s.charAt(i) < '0' || s.charAt(i) > '9') {
                            break;
                        }
                        if (result > flag || (result == flag && s.charAt(i) > '7')) {
                            result = sign > 0 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
                            break;
                        }
                        result = result * 10 + (s.charAt(i) - '0');
                    }
                    if (result != Integer.MAX_VALUE || result != Integer.MIN_VALUE) {
                        result *= sign;
                    }
                }
            }
        }
        log.info("result: {}", result);
    }

    // 68 - I. 二叉搜索树的最近公共祖先#2
    @Test
    void code0068_1() {
        TreeNode root = new TreeNode().build(new Integer[]{6, 2, 8, 0, 4, 7, 9, null, null, 3, 5});
        TreeNode p = new TreeNode().build(new Integer[]{2, 0, 4, null, null, 3, 5});
        TreeNode q = new TreeNode().build(new Integer[]{4, 3, 5});
        TreeNode result = null;
        if (p == q) {
            result = q;
        } else {
            while (root != null) {
                if (root.val < p.val && root.val < q.val) {
                    root = root.right;
                } else if (root.val > p.val && root.val > q.val) {
                    root = root.left;
                } else {
                    result = root;
                    break;
                }
            }
        }
        log.info("result: {}", result);
    }

    // 68 - II. 二叉树的最近公共祖先#2
    @Test
    void code0068_2() {
        TreeNode root = new TreeNode().build(new Integer[]{3, 5, 1, 6, 2, 0, 8, null, null, 7, 4});
        TreeNode p = new TreeNode().build(new Integer[]{6, null, null});
        TreeNode q = new TreeNode().build(new Integer[]{4, null, null});
        log.info("result: {}", commonTree_00682(root, p, q));
    }

    private TreeNode commonTree_00682(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root.equals(p) || root.equals(q)) {
            return root;
        }
        TreeNode left = commonTree_00682(root.left, p, q);
        TreeNode right = commonTree_00682(root.right, p, q);
        if (left == null) {
            return right;
        }
        if (right == null) {
            return left;
        }
        return root;
    }

}
