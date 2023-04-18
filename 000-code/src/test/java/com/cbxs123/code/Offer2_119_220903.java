package com.cbxs123.code;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

import com.cbxs123.code.common.ListNode;
import com.cbxs123.code.common.MultiNode;
import com.cbxs123.code.common.TreeNode;

import lombok.extern.slf4j.Slf4j;

/**
 * @author cbxs123
 * @title Offer2_119_220903.java
 * @description https://doocs.github.io/leetcode/#/lcof2/README
 * @date 2022/9/3 11:05
 **/
@Slf4j
public class Offer2_119_220903 {

    // 剑指 Offer II 001. 整数除法#2
    @Test
    void code0001() {
        int a = 6, b = -2;
        int result = Integer.MAX_VALUE;
        if (a == Integer.MIN_VALUE && (b == 1 || b == -1)) {
            if (b == 1) {
                result = Integer.MIN_VALUE;
            }
        } else if (b == Integer.MIN_VALUE || b == 0) {
            if (b != 0) {
                result = a == Integer.MIN_VALUE ? 1 : 0;
            }
        } else if (a == 0) {
            result = 0;
        } else {
            boolean sign = (a > 0 && b > 0) ? true : false;
            a = a > 0 ? -a : a;
            b = b > 0 ? -b : b;
            List<Integer> list = new ArrayList<>();
            list.add(b);
            int cnt = 0;
            while (list.get(cnt) >= a - list.get(cnt)) {
                list.add(list.get(cnt) + list.get(cnt));
                cnt++;
            }
            int ans = 0;
            for (int i = list.size() - 1; i >= 0; i--) {
                if (list.get(i) >= a) {
                    ans += 1 << i;
                    a -= list.get(i);
                }
            }
            result = sign ? ans : -ans;
        }
        log.info("result: {}", result);
    }

    // 剑指 Offer II 002. 二进制加法#2
    @Test
    void code0002() {
        String a = "101", b = "100";
        int x = a.length() - 1, y = b.length() - 1;
        StringBuilder sb = new StringBuilder();
        int cnt = 0;
        while (x >= 0 || y >= 0) {
            if (x >= 0) {
                if (a.charAt(x) == '1') {
                    cnt++;
                }
                x--;
            }
            if (y >= 0) {
                if (b.charAt(y) == '1') {
                    cnt++;
                }
                y--;
            }
            sb.append((char)((cnt & 1) + '0'));
            cnt >>= 1;
        }
        if (cnt == 1) {
            sb.append('1');
        }
        String result = sb.reverse().toString();
        log.info("result: {}", result);
    }

    // 剑指 Offer II 003. 前 n 个数字二进制中 1 的个数#1
    @Test
    void code0003() {
        int n = 5;
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            dp[i] = dp[i & (i - 1)] + 1;
        }
        log.info("result: {}", dp);
    }

    // 剑指 Offer II 004. 只出现一次的数字#1
    @Test
    void code0004() {
        int[] nums = new int[] {2, 2, 2, 5, 4, 4, 4};
        int result = 0;
        for (int i = 0; i < 32; i++) {
            int cnt = 0;
            for (int num : nums) {
                cnt += num >> i & 1;
            }
            cnt %= 3;
            result |= cnt << i;
        }
        log.info("result: {}", result);
    }

    // 剑指 Offer II 005. 单词长度的最大乘积#2
    @Test
    void code0005() {
        String[] words = new String[] {"abcd", "abcde", "bar", "efgh", "efghd"};
        int n = words.length;
        int[] mark = new int[n];
        for (int i = 0; i < n; i++) {
            for (char ch : words[i].toCharArray()) {
                mark[i] |= 1 << ch - 'a';
            }
        }
        int result = 0;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if ((mark[i] & mark[j]) == 0) {
                    result = Math.max(result, words[i].length() * words[j].length());
                }
            }
        }
        log.info("result: {}", result);
    }

    // 剑指 Offer II 006. 排序数组中两个数字之和#1
    @Test
    void code0006() {
        int[] nums = new int[] {1, 2, 4, 6, 8};
        int target = 8;
        int i = 0, j = nums.length - 1;
        int[] result;
        for (;;) {
            if (nums[i] + nums[j] < target) {
                i++;
            } else if (nums[i] + nums[j] > target) {
                j--;
            } else {
                result = new int[] {i, j};
                break;
            }
        }
        log.info("result: {}", result);
    }

    // 剑指 Offer II 007. 数组中和为 0 的三个数#3
    @Test
    void code0007() {
        int[] nums = new int[] {-1, 0, 1, 2, -1, -4};
        int n = nums.length;
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < n - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int left = i + 1, right = n - 1;
            while (left < right) {
                int cur = nums[i] + nums[left] + nums[right];
                if (cur < 0) {
                    left++;
                } else if (cur > 0) {
                    right--;
                } else {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    left++;
                    right--;
                }
            }
        }
        log.info("result: {}", result);
    }

    // 剑指 Offer II 008. 和大于等于 target 的最短子数组#2
    @Test
    void code0008() {
        int[] nums = new int[] {2, 3, 1, 2, 4, 3};
        int target = 7;
        int n = nums.length, result = Integer.MAX_VALUE, sum = 0;
        int left = 0, right = 0;
        while (right < n) {
            sum += nums[right++];
            while (sum >= target) {
                result = Math.min(result, right - left);
                sum -= nums[left++];
            }
        }
        result = result == Integer.MAX_VALUE ? 0 : result;
        log.info("result: {}", result);
    }

    // 剑指 Offer II 009. 乘积小于 K 的子数组#2
    @Test
    void code0009() {
        int[] nums = new int[] {10, 5, 2, 6};
        int target = 100, n = nums.length;
        int result = 0;
        int sum = 1, left = 0, right = 0;
        while (right < n) {
            sum *= nums[right++];
            while (sum >= target && left < right) {
                sum /= nums[left++];
            }
            result += right - left;
        }
        log.info("result: {}", result);
    }

    // 剑指 Offer II 010. 和为 k 的子数组#3
    @Test
    void code0010() {
        int[] nums = new int[] {1, 2, 1, 2, 1, 2};
        int target = 3;
        Map<Integer, Integer> map = new HashMap<>();
        int result = 0, sum = 0;
        map.put(0, 1);
        for (int num : nums) {
            sum += num;
            result += map.getOrDefault(sum - target, 0);
            map.merge(sum, 1, Integer::sum);
        }
        log.info("result: {}", result);
    }

    // 剑指 Offer II 011. 0 和 1 个数相同的子数组#3
    @Test
    void code0011() {
        int[] nums = new int[] {0, 1, 0, 1, 1, 0, 0, 1};
        int result = 0, sum = 0;
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i] == 1 ? 1 : -1;
            if (map.containsKey(sum)) {
                result = Math.max(result, i - map.get(sum));
            } else {
                map.put(sum, i);
            }
        }
        log.info("result: {}", result);
    }

    // 剑指 Offer II 012. 左右两边子数组的和相等#2
    @Test
    void code0012() {
        int[] nums = new int[] {1, 2, 5, 1, 1, 1};
        int result = -1, n = nums.length;
        int[] sum = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            sum[i] = sum[i - 1] + nums[i - 1];
        }
        for (int i = 0; i < n; i++) {
            if (sum[i] + sum[i + 1] == sum[n]) {
                result = i;
            }
        }
        log.info("result: {}", result);
    }

    // 剑指 Offer II 013. 二维子矩阵的和
    @Test
    void code0013() {
        NumMatrix_0013 numMatrix = new NumMatrix_0013(
            new int[][] {{3, 0, 1, 4, 2}, {5, 6, 3, 2, 1}, {1, 2, 0, 1, 5}, {4, 1, 0, 1, 7}, {1, 0, 3, 0, 5}});
        log.info("result: {}", numMatrix.sumRegion(2, 1, 4, 3));
        log.info("result: {}", numMatrix.sumRegion(1, 1, 2, 2));
        log.info("result: {}", numMatrix.sumRegion(1, 2, 2, 4));
    }

    class NumMatrix_0013 {
        private int[][] pre;

        public NumMatrix_0013(int[][] matrix) {
            int m = matrix.length, n = matrix[0].length;
            pre = new int[m + 1][n + 1];
            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    pre[i][j] = pre[i - 1][j] + pre[i][j - 1] - pre[i - 1][j - 1] + matrix[i - 1][j - 1];
                }
            }
        }

        public int sumRegion(int row1, int col1, int row2, int col2) {
            return pre[row2 + 1][col2 + 1] - pre[row2 + 1][col1] - pre[row1][col2 + 1] + pre[row1][col1];
        }
    }

    // 剑指 Offer II 014. 字符串中的变位词#3
    @Test
    void code0014() {
        String s1 = "dc", s2 = "abcdefg";
        int n1 = s1.length(), n2 = s2.length();
        boolean result = false;
        if (n1 <= n2) {
            int[] window = new int[26];
            for (int i = 0; i < n1; i++) {
                window[s1.charAt(i) - 'a']++;
                window[s2.charAt(i) - 'a']--;
            }
            if (check_0014(window)) {
                result = true;
            } else {
                for (int i = n1; i < n2; i++) {
                    window[s2.charAt(i) - 'a']--;
                    window[s2.charAt(i - n1) - 'a']++;
                    if (check_0014(window)) {
                        result = true;
                        break;
                    }
                }
            }
        }
        log.info("result: {}", result);
    }

    private boolean check_0014(int[] window) {
        return Arrays.stream(window).allMatch(cnt -> cnt == 0);
    }

    // 剑指 Offer II 015. 字符串中的所有变位词#3
    @Test
    void code0015() {
        String s1 = "ab", s2 = "ababa";
        List<Integer> result = new ArrayList<>();
        int n1 = s1.length(), n2 = s2.length();
        if (n1 <= n2) {
            int[] window = new int[26];
            for (int i = 0; i < n1; i++) {
                window[s1.charAt(i) - 'a']++;
                window[s2.charAt(i) - 'a']--;
            }
            if (check_0015(window)) {
                result.add(0);
            }
            for (int i = n1; i < n2; i++) {
                window[s2.charAt(i) - 'a']--;
                window[s2.charAt(i - n1) - 'a']++;
                if (check_0015(window)) {
                    result.add(i - n1 + 1);
                }
            }
        }
        log.info("result: {}", result);
    }

    private boolean check_0015(int[] window) {
        return Arrays.stream(window).allMatch(xnt -> xnt == 0);
    }

    // 剑指 Offer II 016. 不含重复字符的最长子字符串#2
    @Test
    void code0016() {
        String s = "abcabcbb";
        int result = 0;
        Map<Character, Integer> map = new HashMap<>();
        int n = s.length(), left = 0, right = 0;
        while (right < n) {
            char ch = s.charAt(right++);
            map.merge(ch, 1, Integer::sum);
            while (map.get(ch) > 1) {
                map.merge(s.charAt(left++), -1, Integer::sum);
            }
            result = Math.max(result, right - left);
        }
        log.info("result: {}", result);
    }

    // 剑指 Offer II 017. 含有所有字符的最短字符串#3
    @Test
    void code0017() {
        String s = "abdecfgahb", t = "abc";
        String result = "";
        int n1 = s.length(), n2 = t.length();
        if (n1 >= n2) {
            Map<Character, Integer> need = new HashMap<>();
            Map<Character, Integer> window = new HashMap<>();
            for (char ch : t.toCharArray()) {
                need.merge(ch, 1, Integer::sum);
            }
            int start = 0, len = Integer.MAX_VALUE;
            int left = 0, right = 0;
            while (right < n1) {
                window.merge(s.charAt(right++), 1, Integer::sum);
                while (check_0017(need, window)) {
                    if (right - left < len) {
                        len = right - left;
                        start = left;
                    }
                    window.merge(s.charAt(left++), -1, Integer::sum);
                }
            }
            result = len == Integer.MAX_VALUE ? "" : s.substring(start, start + len);
        }
        log.info("result: {}", result);
    }

    private boolean check_0017(Map<Character, Integer> need, Map<Character, Integer> window) {
        for (Map.Entry<Character, Integer> entry : need.entrySet()) {
            if (window.getOrDefault(entry.getKey(), 0) < entry.getValue()) {
                return false;
            }
        }
        return true;
    }

    // 剑指 Offer II 018. 有效的回文#2
    @Test
    void code0018() {
        String s = "race ae car";
        int i = 0, j = s.length() - 1;
        boolean result = true;
        while (i < j) {
            while (i < j && !Character.isLetterOrDigit(s.charAt(i))) {
                i++;
            }
            while (i < j && !Character.isLetterOrDigit(s.charAt(j))) {
                j--;
            }
            if (Character.toLowerCase(s.charAt(i)) != Character.toLowerCase(s.charAt(j))) {
                result = false;
                break;
            }
            i++;
            j--;
        }
        log.info("result: {}", result);
    }

    // 剑指 Offer II 019. 最多删除一个字符得到回文#2
    @Test
    void code0019() {
        String s = "abcda";
        boolean result = true;
        for (int i = 0, j = s.length() - 1; i < j; i++, j--) {
            if (s.charAt(i) != s.charAt(j)) {
                result = check_0019(s, i + 1, j) || check_0019(s, i, j - 1);
            }
        }
        log.info("result: {}", result);
    }

    private boolean check_0019(String s, int i, int j) {
        for (; i < j; i++, j--) {
            if (s.charAt(i) != s.charAt(j)) {
                return false;
            }
        }
        return true;
    }

    // 剑指 Offer II 020. 回文子字符串的个数#3
    @Test
    void code0020() {
        String s = "aaa";
        StringBuilder sb = new StringBuilder("^#");
        for (char ch : s.toCharArray()) {
            sb.append(ch).append('#');
        }
        String t = sb.append('$').toString();
        int n = t.length(), pos = 0, maxRight = 0;
        int[] p = new int[n];
        int result = 0;
        for (int i = 1; i < n - 1; i++) {
            p[i] = maxRight > i ? Math.min(maxRight - i, p[2 * pos - i]) : 1;
            while (t.charAt(i - p[i]) == t.charAt(i + p[i])) {
                p[i]++;
            }
            if (i + p[i] > maxRight) {
                maxRight = i + p[i];
                pos = i;
            }
            result += p[i] / 2;
        }
        log.info("result: {}", result);
    }

    // 剑指 Offer II 021. 删除链表的倒数第 n 个结点#1
    @Test
    void code0021() {
        ListNode head = new ListNode().build(new int[] {1, 2, 3, 4, 5});
        int n = 2;
        ListNode dumy = new ListNode(0, head);
        ListNode fast = dumy, slow = dumy;
        while (n-- > 0) {
            fast = fast.next;
        }
        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;
        log.info("result: {}", dumy.next);
    }

    // 剑指 Offer II 022. 链表中环的入口节点#2
    @Test
    void code0022() {
        ListNode head = new ListNode().build(new int[] {3, 2, 0, -4});
        ListNode slow = head, fast = head;
        ListNode result = null;
        boolean flag = false;
        while (!flag && fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            flag = slow == fast;
        }
        if (flag) {
            ListNode p = head;
            while (p != slow) {
                p = p.next;
                slow = slow.next;
            }
            result = p;
        }
        log.info("result: {}", result);
    }

    // 剑指 Offer II 023. 两个链表的第一个重合节点#1
    @Test
    void code0023() {
        ListNode headA = new ListNode().build(new int[] {1, 2, 3, 4, 5});
        ListNode headB = new ListNode().build(new int[] {6, 7, 3, 4, 5});
        ListNode cur1 = headA, cur2 = headB;
        while (cur1.val != cur2.val) {
            cur1 = cur1 == null ? headB : cur1.next;
            cur2 = cur2 == null ? headA : cur2.next;
        }
        log.info("result: {}", cur1);
    }

    // 剑指 Offer II 024. 反转链表#1
    @Test
    void code0024() {
        ListNode head = new ListNode().build(new int[] {1, 2, 3, 4, 5});
        ListNode pre = null, p = head;
        while (p != null) {
            ListNode q = p.next;
            p.next = pre;
            pre = p;
            p = q;
        }
        log.info("result: {}", pre);
    }

    // 剑指 Offer II 025. 链表中的两数相加#2
    @Test
    void code0025() {
        ListNode l1 = new ListNode().build(new int[] {7, 2, 4, 3});
        ListNode l2 = new ListNode().build(new int[] {5, 6, 4});
        Deque<Integer> s1 = new ArrayDeque<>();
        Deque<Integer> s2 = new ArrayDeque<>();
        while (l1 != null) {
            s1.push(l1.val);
            l1 = l1.next;
        }
        while (l2 != null) {
            s2.push(l2.val);
            l2 = l2.next;
        }
        int carray = 0;
        ListNode result = new ListNode();
        while (!s1.isEmpty() || !s2.isEmpty() || carray != 0) {
            carray += (s1.isEmpty() ? 0 : s1.pop()) + (s2.isEmpty() ? 0 : s2.pop());
            ListNode node = new ListNode(carray % 10, result.next);
            result.next = node;
            carray /= 10;
        }
        log.info("result: {}", result.next);
    }

    // 剑指 Offer II 026. 重排链表#3
    @Test
    void code0026() {
        ListNode head = new ListNode().build(new int[] {1, 2, 3, 4, 5});
        ListNode mid = middleNode_0026(head);
        ListNode tmp = mid.next;
        mid.next = null;
        tmp = reverseList_0026(tmp);
        head = mergeTwoLIsts_0026(head, tmp);
        log.info("result: {}", head);
    }

    private ListNode middleNode_0026(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    private ListNode reverseList_0026(ListNode head) {
        ListNode pre = null, cur = head;
        while (cur != null) {
            ListNode tmp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = tmp;
        }
        return pre;
    }

    private ListNode mergeTwoLIsts_0026(ListNode l1, ListNode l2) {
        ListNode result = new ListNode();
        ListNode cur = result;
        while (l1 != null && l2 != null) {
            cur.next = l1;
            l1 = l1.next;
            cur = cur.next;
            cur.next = l2;
            l2 = l2.next;
            cur = cur.next;
        }
        cur.next = l1 == null ? l2 : l1;
        return result.next;
    }

    // 剑指 Offer II 027. 回文链表#3
    @Test
    void code0027() {
        ListNode head = new ListNode().build(new int[] {1, 2, 3, 3, 2, 1});
        boolean result = true;
        if (head != null && head.next != null) {
            ListNode slow = head, fast = head.next;
            while (fast != null && fast.next != null) {
                slow = slow.next;
                fast = fast.next.next;
            }
            ListNode cur = slow.next;
            slow.next = null;
            ListNode pre = null;
            while (cur != null) {
                ListNode tmp = cur.next;
                cur.next = pre;
                pre = cur;
                cur = tmp;
            }
            while (pre != null) {
                if (pre.val != head.val) {
                    result = false;
                    break;
                }
                pre = pre.next;
                head = head.next;
            }
        }
        log.info("result: {}", result);
    }

    // 剑指 Offer II 028. 展平多级双向链表#3
    @Test
    void code0028() {
        MultiNode C = new MultiNode().build(new int[] {11, 12});
        MultiNode B2 = new MultiNode().build(new int[] {8, 9, 10});
        B2.child = C;
        MultiNode B1 = new MultiNode().build(new int[] {7});
        B1.next = B2;
        MultiNode A2 = new MultiNode().build(new int[] {3, 4, 5, 6});
        A2.child = B1;
        MultiNode A1 = new MultiNode().build(new int[] {1, 2});
        A1.next.next = A2;

        MultiNode head = A1;
        MultiNode result = new MultiNode();
        if (head != null) {
            MultiNode tail = result;
            preOrder_0028(head, tail);
            result.next.prev = null;
        }
        log.info("result: {}", result.next);
    }

    private void preOrder_0028(MultiNode head, MultiNode tail) {
        if (head == null) {
            return;
        }
        MultiNode next = head.next;
        MultiNode child = head.child;
        tail.next = head;
        head.prev = tail;
        tail = tail.next;
        head.child = null;
        preOrder_0028(child, tail);
        preOrder_0028(next, tail);
    }

    // 剑指 Offer II 029. 排序的循环链表#2
    @Test
    void code0029() {
        ListNode head = new ListNode().build(new int[] {3, 4, 1});
        head.next.next.next = head;
        int insert = 2;
        ListNode node = new ListNode(2);
        ListNode result;
        if (head == null) {
            node.next = node;
            result = node;
        } else {
            ListNode cur = head;
            for (;;) {
                if (cur.val <= insert && insert <= cur.next.val
                    || cur.val > cur.next.val && (cur.val <= insert || insert <= cur.next.val) || cur.next == head) {
                    node.next = cur.next;
                    cur.next = node;
                    break;
                }
                cur = cur.next;
            }
            result = head;
        }
        // log.info("result: {}", result);
    }

    // 剑指 Offer II 030. 插入、删除和随机访问都是 O(1) 的容器#2
    @Test
    void code0030() {
        RandomSet obj = new RandomSet();
        log.info("result: {}", obj.insert(1));
        log.info("result: {}", obj.insert(2));
        log.info("result: {}", obj.insert(3));
        log.info("result: {}", obj.insert(3));
        log.info("result: {}", obj.getRandom());
        log.info("result: {}", obj.remove(4));
        log.info("result: {}", obj.remove(3));
        log.info("result: {}", obj.getRandom());
    }

    class RandomSet {
        private final Map<Integer, Integer> map;
        private final List<Integer> list;

        public RandomSet() {
            this.map = new HashMap<>();
            this.list = new ArrayList<>();
        }

        public boolean insert(int val) {
            if (map.containsKey(val)) {
                return false;
            }
            map.put(val, list.size());
            list.add(val);
            return true;
        }

        public boolean remove(int val) {
            if (map.containsKey(val)) {
                int idx = map.get(val);
                int last = list.size() - 1;
                Collections.swap(list, idx, last);
                list.remove(last);
                map.remove(val);
                return true;
            }
            return false;
        }

        public int getRandom() {
            return list.get(ThreadLocalRandom.current().nextInt(list.size()));
        }
    }

    // 剑指 Offer II 031. 最近最少使用缓存#3
    @Test
    void code0031() {
        LRUCache_0031_1 l1 = new LRUCache_0031_1(2);
        l1.put(1, 1);
        l1.put(2, 2);
        log.info("result: {}", l1.get(1));
        l1.put(3, 3);
        log.info("result: {}", l1.get(2));
        l1.put(4, 4);
        log.info("result: {}", l1.get(1));
        log.info("result: {}", l1.get(3));
        log.info("result: {}", l1.get(4));

        LRUCache_0031_2 l2 = new LRUCache_0031_2(2);
        l2.put(1, 1);
        l2.put(2, 2);
        log.info("result: {}", l2.get(1));
        l2.put(3, 3);
        log.info("result: {}", l2.get(2));
        l2.put(4, 4);
        log.info("result: {}", l2.get(1));
        log.info("result: {}", l2.get(3));
        log.info("result: {}", l2.get(4));
    }

    class LRUCache_0031_1 {
        class Node {
            int key;
            int value;
            Node prev;
            Node next;

            Node() {}

            Node(int key, int value) {
                this.key = key;
                this.value = value;
            }
        }

        private Map<Integer, Node> map;
        private Node head;
        private Node tail;
        private int capacity;
        private int size;

        public LRUCache_0031_1(int capacity) {
            map = new HashMap<>();
            head = new Node();
            tail = new Node();
            head.next = tail;
            tail.prev = head;
            this.capacity = capacity;
        }

        public int get(int key) {
            if (!map.containsKey(key)) {
                return -1;
            }
            Node node = map.get(key);
            moveToHead(node);
            return node.value;
        }

        public void put(int key, int value) {
            if (map.containsKey(key)) {
                Node node = map.get(key);
                node.value = value;
                moveToHead(node);
            } else {
                Node node = new Node(key, value);
                map.put(key, node);
                addToHead(node);
                if (++size > capacity) {
                    node = removeTail();
                    map.remove(node.key);
                    size--;
                }
            }
        }

        private void moveToHead(Node node) {
            removeNode(node);
            addToHead(node);
        }

        private void removeNode(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        private void addToHead(Node node) {
            node.next = head.next;
            head.next.prev = node;
            head.next = node;
            node.prev = head;
        }

        private Node removeTail() {
            Node node = tail.prev;
            removeNode(node);
            return node;
        }
    }

    class LRUCache_0031_2 extends LinkedHashMap<Integer, Integer> {

        private final int capacity;

        public LRUCache_0031_2(int capacity) {
            super(capacity, 0.75f, true);
            this.capacity = capacity;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
            return size() > capacity;
        }

        public int get(int key) {
            return super.getOrDefault(key, -1);
        }

        public void put(int key, int value) {
            super.put(key, value);
        }
    }

    // 剑指 Offer II 032. 有效的变位词#2
    @Test
    void code0032() {
        String s = "abc", t = "bca";
        int len = s.length();
        boolean result = true;
        if (t.length() != len || Objects.equals(s, t)) {
            result = false;
        } else {
            int[] chars = new int[26];
            for (int i = 0; i < len; i++) {
                chars[s.charAt(i) - 'a']++;
                chars[t.charAt(i) - 'a']--;
            }
            for (int c : chars) {
                if (c != 0) {
                    result = false;
                    break;
                }
            }
        }
        log.info("result: {}", result);
    }

    // 剑指 Offer II 033. 变位词组#2
    @Test
    void code0033() {
        String[] strs = new String[] {"eat", "tea", "tan", "ate", "nat", "bat"};
        List<List<String>> result;
        Map<String, List<String>> chars = new HashMap<>();
        for (String s : strs) {
            char[] t = s.toCharArray();
            Arrays.sort(t);
            String k = new String(t);
            chars.computeIfAbsent(k, key -> new ArrayList<>()).add(s);
        }
        result = new ArrayList<>(chars.values());
        log.info("result: {}", result);
    }

    // 剑指 Offer II 034. 外星语言是否排序#3
    @Test
    void code0034() {
        String[] words = new String[] {"word", "world", "row"};
        String order = "worldabcefghijkmnpqstuvxyz";
        boolean result = true;
        int[] index = new int[26];
        for (int i = 0; i < order.length(); i++) {
            index[order.charAt(i) - 'a'] = i;
        }
        for (int i = 0; i < words.length - 1; i++) {
            String w1 = words[i];
            String w2 = words[i + 1];
            int l1 = w1.length(), l2 = w2.length();
            for (int j = 0; j < Math.max(l1, l2); j++) {
                int i1 = j >= l1 ? -1 : index[w1.charAt(j) - 'a'];
                int i2 = j >= l2 ? -1 : index[w2.charAt(j) - 'a'];
                if (i1 > i2) {
                    result = false;
                    break;
                }
                if (i1 < i2) {
                    break;
                }
            }
            if (!result) {
                break;
            }
        }
        log.info("result: {}", result);
    }

    // 剑指 Offer II 035. 最小时间差#2
    @Test
    void code0035() {
        List<String> timePoints = Arrays.asList("23:59", "00:00", "00:02");
        int result = 24 * 60;
        if (timePoints.size() > 24 * 60) {
            result = 0;
        } else {
            List<Integer> mins = new ArrayList<>();
            for (String t : timePoints) {
                String[] time = t.split(":");
                mins.add(Integer.parseInt(time[0]) * 60 + Integer.parseInt(time[1]));
            }
            Collections.sort(mins);
            mins.add(mins.get(0) + 24 * 60);
            for (int i = 1; i < mins.size(); i++) {
                result = Math.min(result, mins.get(i) - mins.get(i - 1));
            }
        }
        log.info("result: {}", result);
    }

    // 剑指 Offer II 036. 后缀表达式#2
    @Test
    void code0036() {
        String[] tokens = {"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"};
        Deque<Integer> q = new ArrayDeque<>();
        for (String t : tokens) {
            if (t.length() > 1 || Character.isDigit(t.charAt(0))) {
                q.push(Integer.parseInt(t));
            } else {
                int y = q.pop();
                int x = q.pop();
                switch (t) {
                    case "+":
                        q.push(x + y);
                        break;
                    case "-":
                        q.push(x - y);
                        break;
                    case "*":
                        q.push(x * y);
                        break;
                    default:
                        q.push(x / y);
                        break;
                }
            }
        }
        log.info("result: {}", q.pop());
    }

    // 剑指 Offer II 037. 小行星碰撞#2
    @Test
    void code0037() {
        int[] asteroids = {12, 2, -5, -11, -2};
        Deque<Integer> d = new ArrayDeque<>();
        for (int a : asteroids) {
            if (a > 0) {
                d.offerLast(a);
            } else {
                while (!d.isEmpty() && d.peekLast() > 0 && d.peekLast() <= -a) {
                    d.pollLast();
                }
                if (d.isEmpty() || d.peekLast() < -a) {
                    d.offerLast(a);
                }
            }
        }
        int[] result = d.stream().mapToInt(Integer::valueOf).toArray();
        log.info("result: {}", result);
    }

    // 剑指 Offer II 038. 每日温度#2
    @Test
    void code0038() {
        int[] temperatures = {73, 74, 75, 71, 69, 72, 76, 73};
        int n = temperatures.length;
        int[] result = new int[n];
        Deque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            while (!q.isEmpty() && temperatures[q.peek()] < temperatures[i]) {
                int j = q.pop();
                result[j] = i - j;
            }
            q.push(i);
        }
        log.info("result: {}", result);
    }

    // 剑指 Offer II 039. 直方图最大矩形面积#2
    @Test
    void code0039() {
        int[] heights = {2, 1, 5, 6, 2, 3};
        int o = heights.length, n = o + 2;
        int[] copy = new int[n];
        System.arraycopy(heights, 0, copy, 1, o);
        int result = 0;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && copy[stack.peek()] > copy[i]) {
                int h = copy[stack.peek()];
                stack.pop();
                result = Math.max(result, h * (i - stack.peek() - 1));
            }
            stack.push(i);
        }
        log.info("result: {}", result);
    }

    // 剑指 Offer II 040. 矩阵中最大的矩形#3
    @Test
    void code0040() {
        String[] matrix = {"10100", "10111", "11111", "10010"};
        int result = 0;
        if (matrix != null && matrix.length != 0) {
            int n = matrix[0].length();
            int[] heights = new int[n];
            for (String row : matrix) {
                for (int i = 0; i < n; i++) {
                    if (row.charAt(i) == '1') {
                        heights[i] += 1;
                    } else {
                        heights[i] = 0;
                    }
                }
                result = Math.max(result, largest_0040(heights));
            }
        }
        log.info("result: {}", result);
    }

    private int largest_0040(int[] heights) {
        int res = 0, n = heights.length;
        Deque<Integer> stk = new ArrayDeque<>();
        int[] left = new int[n];
        int[] right = new int[n];
        Arrays.fill(right, n);
        for (int i = 0; i < n; i++) {
            while (!stk.isEmpty() && heights[stk.peek()] >= heights[i]) {
                right[stk.pop()] = i;
            }
            left[i] = stk.isEmpty() ? -1 : stk.peek();
            stk.push(i);
        }
        for (int i = 0; i < n; i++) {
            res = Math.max(res, heights[i] * (right[i] - left[i] - 1));
        }
        return res;
    }

    // 剑指 Offer II 041. 滑动窗口的平均值#2
    @Test
    void code0041() {
        MovingAverage_0041_1 movingAverage1 = new MovingAverage_0041_1(3);
        log.info("result: {}", movingAverage1.next(1)); // 返回 1.0 = 1 / 1
        log.info("result: {}", movingAverage1.next(10)); // 返回 5.5 = (1 + 10) / 2
        log.info("result: {}", movingAverage1.next(3)); // 返回 4.66667 = (1 + 10 + 3) / 3
        log.info("result: {}", movingAverage1.next(5)); // 返回 6.0 = (10 + 3 + 5) / 3

        MovingAverage_0041_2 movingAverage2 = new MovingAverage_0041_2(3);
        log.info("result: {}", movingAverage2.next(1)); // 返回 1.0 = 1 / 1
        log.info("result: {}", movingAverage2.next(10)); // 返回 5.5 = (1 + 10) / 2
        log.info("result: {}", movingAverage2.next(3)); // 返回 4.66667 = (1 + 10 + 3) / 3
        log.info("result: {}", movingAverage2.next(5)); // 返回 6.0 = (10 + 3 + 5) / 3
    }

    class MovingAverage_0041_1 {
        private int[] arr;
        private int sum;
        private int cnt;

        public MovingAverage_0041_1(int size) {
            arr = new int[size];
        }

        public double next(int val) {
            int idx = cnt % arr.length;
            sum += val - arr[idx];
            arr[idx] = val;
            cnt++;
            return sum * 1.0 / Math.min(cnt, arr.length);
        }
    }

    class MovingAverage_0041_2 {
        private Deque<Integer> q = new ArrayDeque<>();
        private int sum;
        private int size;

        public MovingAverage_0041_2(int size) {
            this.size = size;
        }

        public double next(int val) {
            if (q.size() == size) {
                sum -= q.pollFirst();
            }
            sum += val;
            q.offer(val);
            return sum * 1.0 / q.size();
        }
    }

    // 剑指 Offer II 042. 最近请求次数#1
    @Test
    void code0042() {
        RecentCounter_0042 recentCounter = new RecentCounter_0042();
        log.info("result: {}", recentCounter.ping(1)); // requests = [1]，范围是 [-2999,1]，返回 1
        log.info("result: {}", recentCounter.ping(100)); // requests = [1, 100]，范围是 [-2900,100]，返回 2
        log.info("result: {}", recentCounter.ping(3001)); // requests = [1, 100, 3001]，范围是 [1,3001]，返回 3
        log.info("result: {}", recentCounter.ping(3002)); // requests = [1, 100, 3001, 3002]，范围是 [2,3002]，返回 3
    }

    class RecentCounter_0042 {
        private Deque<Integer> q;

        public RecentCounter_0042() {
            q = new LinkedList<>();
        }

        public int ping(int t) {
            q.offerLast(t);
            while (q.peekFirst() < t - 3000) {
                q.pollFirst();
            }
            return q.size();
        }
    }

    // 剑指 Offer II 043. 往完全二叉树添加节点#2
    @Test
    void code0043() {
        TreeNode root = new TreeNode().build(new Integer[] {1, 2, 3, 4, 5, 6, 7});
        CBTInsert_0043 obj = new CBTInsert_0043(root);
        obj.insert(8);
        TreeNode result = obj.getRoot();
        log.info("result: {}", result);
    }

    class CBTInsert_0043 {
        private List<TreeNode> tree;

        public CBTInsert_0043(TreeNode root) {
            tree = new ArrayList<>();
            Deque<TreeNode> q = new ArrayDeque<>();
            q.offer(root);
            while (!q.isEmpty()) {
                TreeNode node = q.pollFirst();
                tree.add(node);
                if (node.left != null) {
                    q.offer(node.left);
                }
                if (node.right != null) {
                    q.offer(node.right);
                }
            }
        }

        public int insert(int v) {
            int pid = (tree.size() - 1) >> 1;
            TreeNode node = new TreeNode(v);
            tree.add(node);
            TreeNode p = tree.get(pid);
            if (p.left == null) {
                p.left = node;
            } else {
                p.right = node;
            }
            return p.val;
        }

        public TreeNode getRoot() {
            return tree.get(0);
        }
    }

    // 剑指 Offer II 044. 二叉树每层的最大值#1
    @Test
    void code0044() {
        TreeNode root = new TreeNode().build(new Integer[] {1, 2, 3, 4, 5, 6, 7});
        List<Integer> result = new ArrayList<>();
        if (root != null) {
            Deque<TreeNode> q = new ArrayDeque<>();
            q.offer(root);
            while (!q.isEmpty()) {
                int t = Integer.MIN_VALUE;
                for (int i = q.size(); i > 0; i--) {
                    TreeNode node = q.poll();
                    t = Math.max(t, node.val);
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

    // 剑指 Offer II 045. 二叉树最底层最左边的值#1
    @Test
    void code0045() {
        TreeNode root = new TreeNode().build(new Integer[] {1, 2, 3, 4, null, 5, 6, null, 7, 8, null});
        int result = -1;
        Queue<TreeNode> q = new ArrayDeque<>();
        q.offer(root);
        while (!q.isEmpty()) {
            int n = q.size();
            for (int i = 0; i < n; i++) {
                TreeNode node = q.poll();
                if (i == 0) {
                    result = node.val;
                }
                if (node.left != null) {
                    q.offer(node.left);
                }
                if (node.right != null) {
                    q.offer(node.right);
                }
            }
        }
        log.info("result: {}", result);
    }

    // 剑指 Offer II 046. 二叉树的右侧视图#1
    @Test
    void code0046() {
        TreeNode root = new TreeNode().build(new Integer[] {1, 2, 3, 4, null, 5, 6, null, 7, null, null});
        List<Integer> result = new ArrayList<>();
        if (root != null) {
            Deque<TreeNode> q = new ArrayDeque<>();
            q.offer(root);
            while (!q.isEmpty()) {
                result.add(q.peekFirst().val);
                for (int i = q.size(); i > 0; i--) {
                    TreeNode node = q.poll();
                    if (node.right != null) {
                        q.offer(node.right);
                    }
                    if (node.left != null) {
                        q.offer(node.left);
                    }
                }
            }
        }
        log.info("result: {}", result);
    }

    // 剑指 Offer II 047. 二叉树剪枝#1
    @Test
    void code0047() {
        TreeNode root = new TreeNode().build(new Integer[] {1, 0, 1, 0, 0, 0, 1});
        TreeNode result = pruneTree_0047(root);
        log.info("result: {}", result);
    }

    private TreeNode pruneTree_0047(TreeNode root) {
        if (root == null) {
            return null;
        }
        root.left = pruneTree_0047(root.left);
        root.right = pruneTree_0047(root.right);
        if (root.val == 0 && root.left == null && root.right == null) {
            return null;
        }
        return root;
    }

    // 剑指 Offer II 048. 序列化与反序列化二叉树#3
    @Test
    void code0048() {
        Codec_0048 ser = new Codec_0048();
        Codec_0048 deser = new Codec_0048();
        TreeNode root = new TreeNode().build(new Integer[] {1, 2, 3, 4, 5, 6, 7});
        TreeNode result = deser.deserialize(ser.serialize(root));
        log.info("result: {}", result);
    }

    class Codec_0048 {
        private static final String NULL = "#";
        private static final String SEP = ",";

        public String serialize(TreeNode root) {
            if (root == null) {
                return "";
            }
            StringBuilder sb = new StringBuilder();
            preOrder(root, sb);
            return sb.toString();
        }

        private void preOrder(TreeNode root, StringBuilder sb) {
            if (root == null) {
                sb.append(NULL + SEP);
                return;
            }
            sb.append(root.val + SEP);
            preOrder(root.left, sb);
            preOrder(root.right, sb);
        }

        public TreeNode deserialize(String data) {
            if (data == null || "".equals(data)) {
                return null;
            }
            List<String> list = new LinkedList<>();
            for (String s : data.split(SEP)) {
                list.add(s);
            }
            return deserialize(list);
        }

        private TreeNode deserialize(List<String> list) {
            String first = list.remove(0);
            if (NULL.equals(first)) {
                return null;
            }
            TreeNode root = new TreeNode(Integer.parseInt(first));
            root.left = deserialize(list);
            root.right = deserialize(list);
            return root;
        }
    }

    // 剑指 Offer II 049. 从根节点到叶节点的路径数字之和#2
    @Test
    void code0049() {
        TreeNode root = new TreeNode().build(new Integer[] {1, 2, 3, 4, 5, 6, 7});
        log.info("result: {}", dfs_0049(root, 0));
    }

    private int dfs_0049(TreeNode root, int preSum) {
        if (root == null) {
            return 0;
        }
        int s = preSum * 10 + root.val;
        if (root.left == null || root.right == null) {
            return s;
        }
        return dfs_0049(root.left, s) + dfs_0049(root.right, s);
    }

    // 剑指 Offer II 050. 向下的路径节点之和#3
    @Test
    void code0050() {
        TreeNode root = new TreeNode().build(new Integer[] {10, 5, -3, 3, 2, null, 11, 3, -2, null, 1});
        int targetSum = 8;
        Map<Integer, Integer> preSum = new HashMap<>();
        preSum.put(0, 1);
        log.info("result: {}", dfs_0050(root, 0, targetSum, preSum));
    }

    private int dfs_0050(TreeNode node, int cur, int targetSum, Map<Integer, Integer> preSum) {
        if (node == null) {
            return 0;
        }
        cur += node.val;
        int res = preSum.getOrDefault(cur - targetSum, 0);

        preSum.merge(cur, 1, Integer::sum);
        res += dfs_0050(node.left, cur, targetSum, preSum);
        res += dfs_0050(node.right, cur, targetSum, preSum);
        preSum.merge(cur, -1, Integer::sum);
        return res;
    }

    // 剑指 Offer II 051. 节点之和最大的路径#2
    @Test
    void code0051() {
        TreeNode root = new TreeNode().build(new Integer[] {-10, 9, 20, null, null, 15, 7});
        AtomicInteger result = new AtomicInteger(0);
        dfs_0051(root, result);
        log.info("result: {}", result.get());
    }

    private int dfs_0051(TreeNode node, AtomicInteger result) {
        if (node == null) {
            return 0;
        }
        int left = Math.max(0, dfs_0051(node.left, result));
        int right = Math.max(0, dfs_0051(node.right, result));
        result.set(Math.max(result.get(), node.val + left + right));
        return node.val + Math.max(left, right);
    }

    // 剑指 Offer II 052. 展平二叉搜索树#3
    @Test
    void code0052() {
        TreeNode root = new TreeNode().build(new Integer[] {1, 2, 3, 4, 5, 6, 7});
        TreeNode head = null, tail = null, cur = root;
        Deque<TreeNode> stack = new ArrayDeque<>();
        while (!stack.isEmpty() || cur != null) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.pop();
            if (head == null) {
                head = cur;
            } else {
                tail.right = cur;
            }
            tail = cur;
            cur.left = null;
            cur = cur.right;
        }
        log.info("result: {}", head);
    }

    // 剑指 Offer II 053. 二叉搜索树中的中序后继#2
    @Test
    void code0053() {
        TreeNode root = new TreeNode().build(new Integer[] {2, 1, 3});
        TreeNode cur = root, result = null, p = new TreeNode(2);
        while (cur != null) {
            if (cur.val <= p.val) {
                cur = cur.right;
            } else {
                result = cur;
                cur = cur.left;
            }
        }
        log.info("result: {}", result);
    }

    // 剑指 Offer II 054. 所有大于等于节点的值之和#2
    @Test
    void code0054() {
        TreeNode root = new TreeNode().build(new Integer[] {3, 2, 4, 1});
        AtomicInteger sum = new AtomicInteger(0);
        dfs_0054(root, sum);
        log.info("result: {}", root);
    }

    private void dfs_0054(TreeNode root, AtomicInteger sum) {
        if (root == null) {
            return;
        }
        dfs_0054(root.right, sum);
        sum.addAndGet(root.val);
        root.val = sum.get();
        dfs_0054(root.left, sum);
    }

    // 剑指 Offer II 055. 二叉搜索树迭代器#2
    @Test
    void code0055() {
        TreeNode root = new TreeNode().build(new Integer[] {7, 3, 15, null, null, 9, 20});
        BSTIterator_0055 bSTIterator = new BSTIterator_0055(root);
        log.info("{}", bSTIterator.next()); // 返回 3
        log.info("{}", bSTIterator.next()); // 返回 7
        log.info("{}", bSTIterator.hasNext()); // 返回 True
        log.info("{}", bSTIterator.next()); // 返回 9
        log.info("{}", bSTIterator.hasNext()); // 返回 True
        log.info("{}", bSTIterator.next()); // 返回 15
        log.info("{}", bSTIterator.hasNext()); // 返回 True
        log.info("{}", bSTIterator.next()); // 返回 20
        log.info("{}", bSTIterator.hasNext()); // 返回 False
    }

    class BSTIterator_0055 {
        private int cur = 0;
        private List<Integer> vals = new ArrayList<>();

        public BSTIterator_0055(TreeNode root) {
            inOrder(root);
        }

        public int next() {
            return vals.get(cur++);
        }

        public boolean hasNext() {
            return cur < vals.size();
        }

        private void inOrder(TreeNode root) {
            if (root != null) {
                inOrder(root.left);
                vals.add(root.val);
                inOrder(root.right);
            }
        }
    }

    // 剑指 Offer II 056. 二叉搜索树中两个节点之和#2
    @Test
    void code0056() {
        Set<Integer> set = new HashSet<>();
        TreeNode root = new TreeNode().build(new Integer[] {8, 6, 10, 5, 7, 9, 11});
        int k = 12;
        log.info("result: {}", find_0056(root, k, set));
    }

    private boolean find_0056(TreeNode root, int k, Set<Integer> set) {
        if (root == null) {
            return false;
        }
        if (set.contains(k - root.val)) {
            return true;
        }
        set.add(root.val);
        return find_0056(root.left, k, set) || find_0056(root.right, k, set);
    }

    // 剑指 Offer II 057. 值和下标之差都在给定的范围内#2
    @Test
    void code0057() {
        int[] nums = {1, 5, 8, 1, 5, 9};
        int t = 3, k = 2;
        TreeSet<Integer> set = new TreeSet<>();
        boolean result = false;
        for (int i = 0; i < nums.length; i++) {
            Integer x = set.ceiling(nums[i] - t);
            if (x != null && x <= nums[i] + t) {
                result = true;
                break;
            }
            set.add(nums[i]);
            if (i >= k) {
                set.remove(nums[i - k]);
            }
        }
        log.info("result: {}", result);
    }

    // 剑指 Offer II 058. 日程表#1
    @Test
    void code0058() {
        Calendar_0058 myCalendar = new Calendar_0058();
        log.info("{}", myCalendar.book(10, 20)); // returns true
        log.info("{}", myCalendar.book(15, 25)); // returns false
        log.info("{}", myCalendar.book(20, 30)); // returns true
    }

    class Calendar_0058 {

        private final TreeMap<Integer, Integer> map = new TreeMap<>();

        public Calendar_0058() {}

        public boolean book(int start, int end) {
            Map.Entry<Integer, Integer> ent = map.floorEntry(start);
            if (ent != null && ent.getValue() > start) {
                return false;
            }
            ent = map.ceilingEntry(start);
            if (ent != null && ent.getKey() < end) {
                return false;
            }
            map.put(start, end);
            return true;
        }
    }

    // 剑指 Offer II 059. 数据流的第 K 大数值#1
    @Test
    void code0059() {
        KthLargest_0059 kthLargest = new KthLargest_0059(3, new int[] {4, 5, 8, 2});
        log.info("{}", kthLargest.add(3)); // return 4
        log.info("{}", kthLargest.add(5)); // return 5
        log.info("{}", kthLargest.add(10)); // return 5
        log.info("{}", kthLargest.add(9)); // return 8
        log.info("{}", kthLargest.add(4)); // return 8
    }

    class KthLargest_0059 {
        private PriorityQueue<Integer> q;
        private int size;

        public KthLargest_0059(int size, int[] nums) {
            q = new PriorityQueue<>(size);
            this.size = size;
            for (int num : nums) {
                add(num);
            }
        }

        public int add(int val) {
            q.offer(val);
            if (q.size() > size) {
                q.poll();
            }
            return q.peek();
        }
    }

    // 剑指 Offer II 060. 出现频率最高的 k 个数字#2
    @Test
    void code0060() {
        int[] nums = new int[] {1, 1, 1, 2, 2, 3};
        int k = 2;
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.merge(num, 1, Integer::sum);
        }
        PriorityQueue<int[]> q = new PriorityQueue<>(Comparator.comparing(x -> x[1]));
        map.forEach((num, freq) -> {
            if (q.size() == k) {
                q.offer(new int[] {num, freq});
                q.poll();
            } else {
                q.offer(new int[] {num, freq});
            }
        });
        int[] result = q.stream().mapToInt(x -> x[0]).toArray();
        log.info("result: {}", result);
    }

    // 剑指 Offer II 061. 和最小的 k 个数对#1
    @Test
    void code0061() {
        int[] nums1 = new int[] {1, 7, 11}, nums2 = new int[] {2, 4, 6};
        int k = 3;
        Queue<List<Integer>> q = new PriorityQueue<>((x, y) -> (y.get(0) + y.get(1)) - (x.get(0) + x.get(1)));
        for (int i = 0; i < nums1.length && i < k; i++) {
            for (int j = 0; j < nums2.length && j < k; j++) {
                q.offer(Arrays.asList(nums1[i], nums2[j]));
                if (q.size() > k) {
                    q.poll();
                }
            }
        }
        log.info("result: {}", new ArrayList<>(q));
    }

    // 剑指 Offer II 062. 实现前缀树#3
    @Test
    void code0062() {
        Trie_0062 trie = new Trie_0062();
        trie.insert("apple");
        log.info("{}", trie.search("apple")); // 返回 True
        log.info("{}", trie.search("app")); // 返回 False
        log.info("{}", trie.startsWith("app")); // 返回 True
        trie.insert("app");
        log.info("{}", trie.search("app")); // 返回 True
    }

    class Trie_0062 {
        private Trie_0062[] children;
        private boolean end;

        public Trie_0062() {
            this.children = new Trie_0062[26];
            this.end = false;
        }

        public void insert(String word) {
            Trie_0062 node = this;
            for (char c : word.toCharArray()) {
                int idx = c - 'a';
                if (node.children[idx] == null) {
                    node.children[idx] = new Trie_0062();
                }
                node = node.children[idx];
            }
            node.end = true;
        }

        private boolean search(String word) {
            Trie_0062 node = searchPrefix(word);
            return node != null && node.end;
        }

        private boolean startsWith(String prefix) {
            Trie_0062 node = searchPrefix(prefix);
            return node != null;
        }

        private Trie_0062 searchPrefix(String s) {
            Trie_0062 node = this;
            for (char c : s.toCharArray()) {
                int idx = c - 'a';
                if (node.children[idx] == null) {
                    return null;
                }
                node = node.children[idx];
            }
            return node;
        }
    }

    // 剑指 Offer II 063. 替换单词#2
    @Test
    void code0063() {
        Set<String> set = new HashSet<>(Arrays.asList("catt", "cat", "bat", "rat"));
        String sentence = "the cattle was rattled by the battery";
        String[] words = sentence.split(" ");
        for (int i = 0; i < words.length; i++) {
            String w = words[i];
            for (int j = 1; j <= w.length(); j++) {
                String t = w.substring(0, j);
                if (set.contains(t)) {
                    words[i] = t;
                    break;
                }
            }
        }
        log.info("result: {}", String.join(" ", words));
    }

    // 剑指 Offer II 064. 神奇的字典#3
    @Test
    void code0064() {
        MagicDictionary_0064 magicDictionary = new MagicDictionary_0064();
        magicDictionary.buildDict(new String[] {"hello", "leetcode"});
        log.info("{}", magicDictionary.search("hello")); // 返回 False
        log.info("{}", magicDictionary.search("hhllo")); // 将第二个 'h' 替换为 'e' 可以匹配 "hello" ，所以返回 True
        log.info("{}", magicDictionary.search("hell")); // 返回 False
        log.info("{}", magicDictionary.search("leetcoded")); // 返回 False
    }

    class MagicDictionary_0064 {
        private Set<String> words;
        private Map<String, Integer> cnt;

        public MagicDictionary_0064() {
            words = new HashSet<>();
            cnt = new HashMap<>();
        }

        public void buildDict(String[] dictionary) {
            for (String word : dictionary) {
                words.add(word);
                for (String p : patterns(word)) {
                    cnt.merge(p, 1, Integer::sum);
                }
            }
        }

        public boolean search(String searchWord) {
            for (String p : patterns(searchWord)) {
                int c = cnt.getOrDefault(p, 0);
                if (c > 1 || (c == 1 && !words.contains(searchWord))) {
                    return true;
                }
            }
            return false;
        }

        private List<String> patterns(String word) {
            List<String> list = new ArrayList<>();
            char[] chars = word.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                char t = chars[i];
                chars[i] = '*';
                list.add(new String(chars));
                chars[i] = t;
            }
            return list;
        }
    }

    // 剑指 Offer II 065. 最短的单词编码#2
    @Test
    void code0065() {
        String[] words = {"time", "me", "bell"};
        Arrays.sort(words, (a, b) -> b.length() - a.length());
        int result = 0;
        Trie_0065 trie = new Trie_0065();
        for (String w : words) {
            result += trie.insert(w);
        }
        log.info("result: {}", result);
    }

    class Trie_0065 {
        Trie_0065[] children = new Trie_0065[26];

        int insert(String w) {
            Trie_0065 node = this;
            boolean pre = true;
            for (int i = w.length() - 1; i >= 0; i--) {
                int idx = w.charAt(i) - 'a';
                if (node.children[idx] == null) {
                    pre = false;
                    node.children[idx] = new Trie_0065();
                }
                node = node.children[idx];
            }
            return pre ? 0 : w.length() + 1;
        }
    }

    // 剑指 Offer II 066. 单词之和#2
    @Test
    void code0066() {
        MapSum_0066 mapSum = new MapSum_0066();
        mapSum.insert("apple", 3);
        log.info("{}", mapSum.sum("ap")); // return 3 (apple = 3)
        mapSum.insert("app", 2);
        log.info("{}", mapSum.sum("ap")); // return 5 (apple + app = 3 + 2 = 5)
    }

    class MapSum_0066 {
        private Map<String, Integer> data;
        private Map<String, Integer> tmp;

        public MapSum_0066() {
            data = new HashMap<>();
            tmp = new HashMap<>();
        }

        public void insert(String key, Integer val) {
            int old = data.getOrDefault(key, 0);
            data.put(key, val);
            for (int i = 1; i < key.length() + 1; i++) {
                String k = key.substring(0, i);
                tmp.put(k, tmp.getOrDefault(k, 0) + (val - old));
            }
        }

        public int sum(String prefix) {
            return tmp.getOrDefault(prefix, 0);
        }
    }

    // 剑指 Offer II 067. 最大的异或#3
    @Test
    void code0067() {
        int[] nums = {3, 10, 5, 25, 2, 8};
        Trie_0067 trie = new Trie_0067();
        int result = 0;
        for (int v : nums) {
            trie.insert(v);
            result = Math.max(result, trie.search(v));
        }
        log.info("result: {}", result);

        result = 0;
        int mask = 0;
        for (int i = 30; i >= 0; i--) {
            int cur = 1 << i;
            // 期望的二进制前缀
            mask ^= cur;
            // 在当前前缀下, 数组内的前缀位数所有情况集合
            Set<Integer> set = new HashSet<>();
            for (int j = 0; j < nums.length; j++) {
                set.add(mask & nums[j]);
            }
            // 期望最终异或值的从右数第i位为1, 再根据异或运算的特性推算假设是否成立
            int flag = result | cur;
            for (Integer prefix : set) {
                if (set.contains(prefix ^ flag)) {
                    result = flag;
                    break;
                }
            }
        }
        log.info("result: {}", result);
    }

    class Trie_0067 {
        Trie_0067[] children = new Trie_0067[2];

        public void insert(int x) {
            Trie_0067 node = this;
            for (int i = 30; i >= 0; i--) {
                int v = (x >> i) & 1;
                if (node.children[v] == null) {
                    node.children[v] = new Trie_0067();
                }
                node = node.children[v];
            }
        }

        public int search(int x) {
            Trie_0067 node = this;
            int result = 0;
            for (int i = 30; i >= 0; i--) {
                int v = (x >> i) & 1;
                if (node.children[v ^ 1] != null) {
                    result = result << 1 | 1;
                    node = node.children[v ^ 1];
                } else {
                    result <<= 1;
                    node = node.children[v];
                }
            }
            return result;
        }
    }

    // 剑指 Offer II 068. 查找插入位置#1
    @Test
    void code0068() {
        int[] nums = {1, 3, 5, 7};
        int target = 6;
        int left = 0, right = nums.length;
        while (left < right) {
            int mid = (left + right) >>> 1;
            if (target <= nums[mid]) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        log.info("result: {}", left);
    }

    // 剑指 Offer II 069. 山峰数组的顶部#1
    @Test
    void code0069() {
        int[] arr = {1, 3, 5, 4, 3, 2, 1};
        int left = 1, right = arr.length - 2;
        while (left < right) {
            int mid = (left + right) >> 1;
            if (arr[mid] > arr[mid + 1]) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        log.info("result: {}", left);
    }

    // 剑指 Offer II 070. 排序数组中只出现一次的数字#2
    @Test
    void code0070() {
        int[] nums = {1, 1, 2, 3, 3, 4, 4, 8, 8};
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = (left + right) >> 1;
            if (nums[mid] != nums[mid ^ 1]) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        log.info("result: {}", nums[left]);
    }

    // 剑指 Offer II 071. 按权重生成随机数#2
    @Test
    void code0071() {
        pickIndex_0071 solution = new pickIndex_0071(new int[] {1, 3});
        log.info("{}", solution.pickIndex()); // 返回下标0概率为 1/4 ,返回下标1概率为 3/4 ,
        log.info("{}", solution.pickIndex());
        log.info("{}", solution.pickIndex());
        log.info("{}", solution.pickIndex());
        log.info("{}", solution.pickIndex());
    }

    class pickIndex_0071 {

        private int[] preSum;

        public pickIndex_0071(int[] w) {
            int n = w.length;
            preSum = new int[n + 1];
            for (int i = 0; i < n; i++) {
                preSum[i + 1] = preSum[i] + w[i];
            }
        }

        public int pickIndex() {
            int n = preSum.length;
            int target = (int)(Math.random() * preSum[n - 1]) + 1;
            int left = 0, right = n - 2;
            while (left < right) {
                int mid = (left + right) >> 1;
                if (target <= preSum[mid + 1]) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }
            return left;
        }
    }

    // 剑指 Offer II 072. 求平方根#2
    @Test
    void code0072() {
        int x = 8;
        int left = 0, right = x;
        while (left < right) {
            int mid = (left + right + 1) >>> 1;
            if (x / mid < mid) {
                right = mid - 1;
            } else {
                left = mid;
            }
        }
        log.info("result: {}", left);
    }

    // 剑指 Offer II 073. 狒狒吃香蕉#2
    @Test
    void code0073() {
        int[] piles = {3, 6, 7, 11};
        int h = 8, max = 0;
        for (int pile : piles) {
            max = Math.max(max, pile);
        }
        int left = 1, right = max;
        while (left < right) {
            int mid = (left + right) >>> 1;
            int sum = 0;
            for (int pile : piles) {
                sum += (pile - 1) / mid + 1;
            }
            if (sum <= h) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        log.info("result: {}", left);
    }

    // 剑指 Offer II 074. 合并区间#2
    @Test
    void code0074() {
        int[][] nums = {{1, 3}, {2, 6}, {8, 10}, {10, 15}, {16, 18}};
        Arrays.sort(nums, Comparator.comparing(x -> x[0]));
        int st = nums[0][0], ed = nums[0][1];
        List<int[]> result = new ArrayList<>();
        for (int i = 1; i < nums.length; i++) {
            int s = nums[i][0], e = nums[i][1];
            if (ed < s) {
                result.add(new int[] {st, ed});
                st = s;
                ed = e;
            } else {
                ed = Math.max(ed, e);
            }
        }
        result.add(new int[] {st, ed});
        String res = result.stream().map(Arrays::toString).reduce((a, b) -> String.join(",", a, b)).get();
        log.info("result: {}", res);
    }

    // 剑指 Offer II 075. 数组相对排序#2
    @Test
    void code0075() {
        int[] arr1 = {2, 3, 1, 3, 2, 4, 6, 7, 9, 2, 19}, arr2 = {2, 1, 4, 3, 9, 6};
        int[] map = new int[1001];
        for (int x : arr1) {
            ++map[x];
        }
        int i = 0;
        for (int x : arr2) {
            while (map[x]-- > 0) {
                arr1[i++] = x;
            }
        }
        for (int j = 0; j < map.length; j++) {
            while (map[j]-- > 0) {
                arr1[i++] = j;
            }
        }
        log.info("result: {}", arr1);
    }

    // 剑指 Offer II 076. 数组中的第 k 大的数字#2
    @Test
    void code0076() {
        int[] nums = {3, 2, 1, 5, 6, 4};
        int k = 2;
        int n = nums.length;
        log.info("result: {}", quickSort_0076(nums, 0, n - 1, n - k));
    }

    private int quickSort_0076(int[] nums, int left, int right, int k) {
        if (left == right) {
            return nums[left];
        }
        int i = left - 1, j = right + 1;
        int mid = nums[(left + right) >>> 1];
        while (i < j) {
            while (nums[++i] < mid);
            while (nums[--j] > mid);
            if (i < j) {
                int t = nums[i];
                nums[i] = nums[j];
                nums[j] = t;
            }
        }
        if (j < k) {
            return quickSort_0076(nums, j + 1, right, k);
        }
        return quickSort_0076(nums, left, j, k);
    }

    // 剑指 Offer II 077. 链表排序#3
    @Test
    void code0077() {
        ListNode head = new ListNode().build(new int[] {-1, 5, 3, 4, 0});
        log.info("result: {}", sortList_0077(head));
    }

    private ListNode sortList_0077(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode slow = head, fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode t = slow.next;
        slow.next = null;
        ListNode l1 = sortList_0077(head);
        ListNode l2 = sortList_0077(t);
        return ListNode.merge(l1, l2);
    }

    // 剑指 Offer II 078. 合并排序链表#2
    @Test
    void code0078() {
        ListNode l1 = new ListNode().build(new int[] {1, 4, 5});
        ListNode l2 = new ListNode().build(new int[] {1, 3, 4});
        ListNode l3 = new ListNode().build(new int[] {2, 6});
        ListNode[] list = new ListNode[] {l1, l2, l3};
        int n = list.length;
        ListNode result = null;
        if (n != 0) {
            for (int i = 1; i < n; i++) {
                list[i] = ListNode.merge(list[i - 1], list[i]);
            }
            result = list[n - 1];
        }
        log.info("result: {}", result);
    }

    // 剑指 Offer II 079. 所有子集#2
    @Test
    void code0079() {
        int[] nums = {1, 2, 3};
        List<List<Integer>> result = new ArrayList<>();
        dfs_0079(0, nums, new ArrayList<>(), result);
        log.info("result: {}", result);
    }

    private void dfs_0079(int i, int[] nums, List<Integer> t, List<List<Integer>> result) {
        result.add(new ArrayList<>(t));
        if (i == nums.length) {
            return;
        }
        for (int j = i; j < nums.length; j++) {
            t.add(nums[j]);
            dfs_0079(j + 1, nums, t, result);
            t.remove(t.size() - 1);
        }
    }

    // 剑指 Offer II 080. 含有 k 个元素的组合#2
    @Test
    void code0080() {
        int n = 4, k = 2;
        List<List<Integer>> result = new ArrayList<>();
        dfs_0080(1, n, k, new ArrayList<>(), result);
        log.info("result: {}", result);
    }

    private void dfs_0080(int i, int n, int k, List<Integer> t, List<List<Integer>> result) {
        if (t.size() == k) {
            result.add(new ArrayList<>(t));
            return;
        }
        for (int j = i; j <= n; j++) {
            t.add(j);
            dfs_0080(j + 1, n, k, t, result);
            t.remove(t.size() - 1);
        }
    }

    // 剑指 Offer II 081. 无重复数组允许重复选择元素的目标组合#3
    @Test
    void code0081() {
        int[] nums = {2, 3, 5};
        int target = 8;
        List<List<Integer>> result = new ArrayList<>();
        dfs_0081(0, 0, new ArrayList<>(), nums, target, result);
        log.info("result: {}", result);
    }

    private void dfs_0081(int sum, int index, List<Integer> tmp, int[] nums, int target, List<List<Integer>> result) {
        if (sum == target) {
            result.add(new ArrayList<>(tmp));
            return;
        }
        if (sum > target) {
            return;
        }
        for (int i = index; i < nums.length; i++) {
            int num = nums[i];
            tmp.add(num);
            dfs_0081(sum + num, i, tmp, nums, target, result);
            tmp.remove(tmp.size() - 1);
        }
    }

    // 剑指 Offer II 082. 有重复元素数组不允许重复选择元素的目标组合#2
    @Test
    void code0082() {
        int[] nums = {10, 1, 2, 7, 6, 1, 5};
        int target = 8;
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        dfs_0082(0, 0, new ArrayList<>(), nums, target, result);
        log.info("result: {}", result);
    }

    private void dfs_0082(int sum, int index, List<Integer> tmp, int[] nums, int target, List<List<Integer>> result) {
        if (sum == target) {
            result.add(new ArrayList<>(tmp));
            return;
        }
        if (sum > target) {
            return;
        }
        for (int i = index; i < nums.length; i++) {
            if (i > index && nums[i] == nums[i - 1]) {
                continue;
            }
            int num = nums[i];
            tmp.add(num);
            dfs_0082(sum + num, i + 1, tmp, nums, target, result);
            tmp.remove(tmp.size() - 1);
        }
    }

    // 剑指 Offer II 083. 没有重复元素集合的不重复全排列#2
    @Test
    void code0083() {
        int[] nums = {1, 2, 3};
        int n = nums.length;
        boolean[] used = new boolean[n];
        List<List<Integer>> result = new ArrayList<>();
        dfs_0083(0, n, nums, used, new ArrayList<>(), result);
        log.info("result: {}", result);
    }

    private void dfs_0083(int cnt, int n, int[] nums, boolean[] used, List<Integer> tmp, List<List<Integer>> result) {
        if (cnt == n) {
            result.add(new ArrayList<>(tmp));
            return;
        }
        for (int i = 0; i < n; i++) {
            if (!used[i]) {
                tmp.add(nums[i]);
                used[i] = true;
                dfs_0083(cnt + 1, n, nums, used, tmp, result);
                used[i] = false;
                tmp.remove(tmp.size() - 1);
            }
        }
    }

    // 剑指 Offer II 084. 含有重复元素集合的不重复全排列#2
    @Test
    void code0084() {
        int[] nums = {1, 1, 3};
        int n = nums.length;
        boolean[] used = new boolean[n];
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        dfs_0084(0, n, nums, used, new ArrayList<>(), result);
        log.info("result: {}", result);
    }

    private void dfs_0084(int cnt, int n, int[] nums, boolean[] used, List<Integer> tmp, List<List<Integer>> result) {
        if (cnt == n) {
            result.add(new ArrayList<>(tmp));
            return;
        }
        for (int i = 0; i < n; i++) {
            if (used[i] || (i > 0 && nums[i] == nums[i - 1] && !used[i - 1])) {
                continue;
            }
            tmp.add(nums[i]);
            used[i] = true;
            dfs_0084(cnt + 1, n, nums, used, tmp, result);
            used[i] = false;
            tmp.remove(tmp.size() - 1);
        }
    }

    // 剑指 Offer II 085. 生成匹配的括号#2
    @Test
    void code0085() {
        int n = 3;
        List<String> result = new ArrayList<>();
        dfs_0085(0, 0, n, "", result);
        log.info("result: {}", result);
    }

    private void dfs_0085(int left, int right, int n, String tmp, List<String> result) {
        if (left == n && right == n) {
            result.add(tmp);
            return;
        }
        if (left < n) {
            dfs_0085(left + 1, right, n, tmp + "(", result);
        }
        if (right < left) {
            dfs_0085(left, right + 1, n, tmp + ")", result);
        }
    }

    // 剑指 Offer II 086. 分割回文子字符串#3
    @Test
    void code0086() {
        String s = "google";
        int n = s.length();
        List<List<String>> result = new ArrayList<>();
        boolean[][] dp = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], true);
        }
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                dp[i][j] = s.charAt(i) == s.charAt(j) && dp[i + 1][j - 1];
            }
        }
        dfs_0086(s, 0, n, new ArrayList<>(), result, dp);
        String[][] res = new String[result.size()][];
        for (int i = 0; i < result.size(); i++) {
            res[i] = result.get(i).toArray(new String[0]);
        }
        log.info("result: {}", result);
    }

    private void dfs_0086(String s, int i, int n, List<String> tmp, List<List<String>> result, boolean[][] dp) {
        if (i == n) {
            result.add(new ArrayList<>(tmp));
            return;
        }
        for (int j = i; j < n; j++) {
            if (dp[i][j]) {
                tmp.add(s.substring(i, j + 1));
                dfs_0086(s, j + 1, n, tmp, result, dp);
                tmp.remove(tmp.size() - 1);
            }
        }
    }

    // 剑指 Offer II 087. 复原 IP#3
    @Test
    void code0087() {
        String s = "10203040";
        List<String> result = new ArrayList<>();
        dfs_0087(s, new ArrayList<>(), result);
        log.info("result: {}", result);
    }

    private void dfs_0087(String s, List<String> tmp, List<String> result) {
        if (tmp.size() == 4) {
            if ("".equals(s)) {
                result.add(String.join(".", tmp));
            }
            return;
        }
        for (int i = 0; i < Math.min(4, s.length() + 1); i++) {
            String c = s.substring(0, i);
            if (check_0087(c)) {
                tmp.add(c);
                dfs_0087(s.substring(i), tmp, result);
                tmp.remove(tmp.size() - 1);
            }
        }
    }

    private boolean check_0087(String s) {
        if ("".equals(s)) {
            return false;
        }
        if (Integer.parseInt(s) > 255) {
            return false;
        }
        if (s.charAt(0) == '0' && s.length() > 1) {
            return false;
        }
        return true;
    }

    // 剑指 Offer II 088. 爬楼梯的最少成本#1
    @Test
    void code0088() {
        int[] cost = {1, 100, 1, 1, 1, 100, 1, 1, 100, 1};
        int a = 0, b = 0, c = 0;
        for (int i = 1; i < cost.length; i++) {
            c = Math.min(a + cost[i - 1], b + cost[i]);
            a = b;
            b = c;
        }
        log.info("result: {}", c);
    }

    // 剑指 Offer II 089. 房屋偷盗#1
    @Test
    void code0089() {
        int[] nums = {2, 7, 9, 3, 1};
        int n = nums.length;
        int[] dp = new int[n];
        dp[0] = nums[0];
        if (n > 1) {
            dp[1] = Math.max(nums[0], nums[1]);
            for (int i = 2; i < n; i++) {
                dp[i] = Math.max(dp[i - 2] + nums[i], dp[i - 1]);
            }
        }
        log.info("result: {}", dp[n - 1]);
    }

    // 剑指 Offer II 090. 环形房屋偷盗#3
    @Test
    void code0090() {
        int[] nums = {2, 3, 4, 1};
        int n = nums.length;
        int result = nums[0];
        if (n > 1) {
            result = Math.max(rob_0090(nums, 0, n - 1), rob_0090(nums, 1, n));
        }
        log.info("result: {}", result);
    }

    private int rob_0090(int[] nums, int start, int end) {
        if (start + 1 == end) {
            return nums[start];
        }
        int n = end - start;
        int[] dp = new int[n];
        dp[0] = nums[start];
        dp[1] = Math.max(nums[start], nums[start + 1]);
        for (int i = 2; i < n; i++) {
            dp[i] = Math.max(dp[i - 2] + nums[start + i], dp[i - 1]);
        }
        return dp[n - 1];
    }

    // 剑指 Offer II 091. 粉刷房子#1
    @Test
    void code0091() {
        int[][] costs = {{17, 2, 17}, {16, 16, 5}, {14, 3, 19}};
        int r = 0, g = 0, b = 0, result = 0;
        for (int[] cost : costs) {
            int _r = r, _g = g, _b = b;
            r = Math.min(_g, _b) + cost[0];
            g = Math.min(_r, _b) + cost[1];
            b = Math.min(_r, _g) + cost[2];
        }
        result = Math.min(r, Math.min(g, b));
        log.info("result: {}", result);
    }

    // 剑指 Offer II 092. 翻转字符#2
    @Test
    void code0092() {
        String s = "010110";
        int n = s.length();
        int[] left = new int[n + 1];
        int[] right = new int[n + 1];
        int result = Integer.MAX_VALUE;
        for (int i = 1; i <= n; i++) {
            left[i] = left[i - 1] + (s.charAt(i - 1) == '1' ? 1 : 0);
        }
        for (int i = n - 1; i >= 0; i--) {
            right[i] = right[i + 1] + (s.charAt(i) == '0' ? 1 : 0);
        }
        for (int i = 0; i <= n; i++) {
            result = Math.min(result, left[i] + right[i]);
        }
        log.info("result: {}", result);
    }

    // 剑指 Offer II 093. 最长斐波那契数列#2
    @Test
    void code0093() {
        int[] arr = {1, 3, 7, 10, 12, 17, 29};
        int n = arr.length;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            map.put(arr[i], i);
        }
        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                dp[j][i] = 2;
            }
        }
        int result = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                int delta = arr[i] - arr[j];
                if (map.containsKey(delta)) {
                    int k = map.get(delta);
                    if (k < j) {
                        dp[j][i] = dp[k][j] + 1;
                        result = Math.max(result, dp[j][i]);
                    }
                }
            }
        }
        log.info("result: {}", result);
    }

    // 剑指 Offer II 094. 最少回文分割#2
    @Test
    void code0094() {
        String s = "aab";
        int n = s.length();
        boolean[][] dp1 = new boolean[n][n];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                dp1[i][j] = s.charAt(i) == s.charAt(j) && (j - i < 3 || dp1[i + 1][j - 1]);
            }
        }
        int[] dp2 = new int[n];
        for (int i = 0; i < n; i++) {
            if (!dp1[0][i]) {
                dp2[i] = i;
                for (int j = 1; j <= i; j++) {
                    if (dp1[j][i]) {
                        dp2[i] = Math.min(dp2[i], dp2[j - 1] + 1);
                    }
                }
            }
        }
        log.info("result: {}", dp2[n - 1]);
    }

    // 剑指 Offer II 095. 最长公共子序列#2
    @Test
    void code0095() {
        String text1 = "abcde", text2 = "ace";
        int m = text1.length(), n = text2.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        log.info("result: {}", dp[m][n]);
    }

    // 剑指 Offer II 096. 字符串交织#2
    @Test
    void code0096() {
        String s1 = "aabcc", s2 = "dbbca", s3 = "aadbbcbcac";
        int m = s1.length(), n = s2.length();
        Map<Integer, Boolean> map = new HashMap<>();
        boolean result = false;
        if (m + n == s3.length()) {
            result = dfs_0096(0, 0, m, n, s1, s2, s3, map);
        }
        log.info("result: {}", result);
    }

    private boolean dfs_0096(int i, int j, int m, int n, String s1, String s2, String s3, Map<Integer, Boolean> map) {
        if (i == m && j == n) {
            return true;
        }
        if (map.containsKey(i * 100 + j)) {
            return map.get(i * 100 + j);
        }
        boolean res = (i < m && s1.charAt(i) == s3.charAt(i + j) && dfs_0096(i + 1, j, m, n, s1, s2, s3, map)
            || (j < n && s2.charAt(j) == s3.charAt(i + j) && dfs_0096(i, j + 1, m, n, s1, s2, s3, map)));
        map.put(i * 100 + j, res);
        return res;
    }

    // 剑指 Offer II 097. 子序列的数目#1
    @Test
    void code0097() {
        String s = "babgbag", t = "bag";
        int m = s.length(), n = t.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++) {
            dp[i][0] = 1;
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                dp[i][j] = dp[i - 1][j];
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    dp[i][j] += dp[i - 1][j - 1];
                }
            }
        }
        log.info("result: {}", dp[m][n]);
    }

    // 剑指 Offer II 098. 路径的数目#1
    @Test
    void code0098() {
        int m = 3, n = 7;
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(dp[i], 1);
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        log.info("result: {}", dp[m - 1][n - 1]);
    }

    // 剑指 Offer II 099. 最小路径之和#2
    @Test
    void code0099() {
        int[][] grid = {{1, 3, 1}, {1, 5, 1}, {4, 2, 1}};
        int m = grid.length, n = grid[0].length;
        int[][] dp = new int[m][n];
        dp[0][0] = grid[0][0];
        for (int i = 1; i < m; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        }
        for (int j = 1; j < n; j++) {
            dp[0][j] = dp[0][j - 1] + grid[0][j];
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }
        log.info("result: {}", dp[m - 1][n - 1]);
    }

    // 剑指 Offer II 100. 三角形中最小路径之和#1
    @Test
    void code0100() {
        int[][] triangle = {{2}, {3, 4}, {6, 5, 7}, {4, 1, 8, 3}};
        int n = triangle.length;
        int[] dp = new int[n + 1];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = 0; j <= i; j++) {
                dp[j] = Math.min(dp[j], dp[j + 1]) + triangle[i][j];
            }
        }
        log.info("result: {}", dp[0]);
    }

    // 剑指 Offer II 101. 分割等和子串#2
    @Test
    void code0101() {
        int[] nums = {1, 5, 11, 5};
        int sum = 0;
        boolean result = false;
        for (int n : nums) {
            sum += n;
        }
        if (sum % 2 == 0) {
            int m = nums.length, n = (sum >> 1) + 1;
            boolean[] dp = new boolean[n];
            dp[0] = true;
            if (nums[0] < n) {
                dp[nums[0]] = true;
            }
            for (int i = 1; i < m; i++) {
                for (int j = n - 1; j >= nums[i]; j--) {
                    dp[j] = dp[j] || dp[j - nums[i]];
                }
            }
            result = dp[n - 1];
        }
        log.info("result: {}", result);
    }

    // 剑指 Offer II 102. 加减的目标值#2
    @Test
    void code0102() {
        int[] nums = {1, 1, 1, 1, 1};
        int target = 3;
        int sum = 0;
        int result = 0;
        for (int n : nums) {
            sum += n;
        }
        if (sum - target >= 0 && (sum - target) % 2 == 0) {
            target = (sum - target) / 2 + 1;
            int[] dp = new int[target];
            dp[0] = 1;
            for (int i = 1; i < nums.length + 1; i++) {
                for (int j = target - 1; j >= nums[i - 1]; j--) {
                    dp[j] += dp[j - nums[i - 1]];
                }
            }
            result = dp[target - 1];
        }
        log.info("result: {}", result);
    }

    // 剑指 Offer II 103. 最少的硬币数目#1
    @Test
    void code0103() {
        int[] coins = {1, 2, 5};
        int amount = 11;
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;
        for (int coin : coins) {
            for (int j = coin; j <= amount; j++) {
                dp[j] = Math.min(dp[j], dp[j - coin] + 1);
            }
        }
        log.info("result: {}", dp[amount] > amount ? -1 : dp[amount]);
    }

    // 剑指 Offer II 104. 排列的数目#1
    @Test
    void code0104() {
        int[] nums = {1, 2, 3};
        int target = 4;
        int[] dp = new int[target + 1];
        dp[0] = 1;
        for (int i = 1; i <= target; i++) {
            for (int num : nums) {
                if (i >= num) {
                    dp[i] += dp[i - num];
                }
            }
        }
        log.info("result: {}", dp[target]);
    }

    // 剑指 Offer II 105. 岛屿的最大面积#2
    @Test
    void code0105() {
        int[][] gird = {{0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
            {0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0},
            {0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0}};
        int m = gird.length, n = gird[0].length;
        int result = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (gird[i][j] == 1) {
                    result = Math.max(result, dfs_0105(i, j, m, n, gird));
                }
            }
        }
        log.info("result: {}", result);
    }

    private int dfs_0105(int i, int j, int m, int n, int[][] gird) {
        gird[i][j] = 0;
        int[] dirs = {-1, 0, 1, 0, -1};
        int result = 1;
        for (int k = 0; k < 4; k++) {
            int x = i + dirs[k];
            int y = j + dirs[k + 1];
            if (x >= 0 && x < m && y >= 0 && y < n && gird[x][y] == 1) {
                result += dfs_0105(x, y, m, n, gird);
            }
        }
        return result;
    }

    // 剑指 Offer II 106. 二分图#3
    @Test
    void code0106() {
        int[][] graph = {{1, 3}, {0, 2}, {1, 3}, {0, 2}};
        int n = graph.length;
        int[] p = new int[n];
        boolean result = true;
        for (int i = 0; i < n; i++) {
            p[i] = i;
        }
        for (int u = 0; u < n; u++) {
            int[] g = graph[u];
            for (int v : g) {
                if (find_0106(u, p) == find_0106(v, p)) {
                    result = false;
                    break;
                }
                p[find_0106(v, p)] = find_0106(g[0], p);
            }
            if (!result) {
                break;
            }
        }
        log.info("result: {}", result);
    }

    private int find_0106(int x, int[] p) {
        if (p[x] != x) {
            p[x] = find_0106(p[x], p);
        }
        return p[x];
    }
}
