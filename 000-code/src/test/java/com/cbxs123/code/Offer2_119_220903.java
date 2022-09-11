package com.cbxs123.code;

import com.cbxs123.code.common.ListNode;
import com.cbxs123.code.common.MultiNode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

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
            sb.append((char) ((cnt & 1) + '0'));
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
        int[] nums = new int[]{2, 2, 2, 5, 4, 4, 4};
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
        String[] words = new String[]{"abcd", "abcde", "bar", "efgh", "efghd"};
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
        int[] nums = new int[]{1, 2, 4, 6, 8};
        int target = 8;
        int i = 0, j = nums.length - 1;
        int[] result;
        for (; ; ) {
            if (nums[i] + nums[j] < target) {
                i++;
            } else if (nums[i] + nums[j] > target) {
                j--;
            } else {
                result = new int[]{i, j};
                break;
            }
        }
        log.info("result: {}", result);
    }

    // 剑指 Offer II 007. 数组中和为 0 的三个数#3
    @Test
    void code0007() {
        int[] nums = new int[]{-1, 0, 1, 2, -1, -4};
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
        int[] nums = new int[]{2, 3, 1, 2, 4, 3};
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
        int[] nums = new int[]{10, 5, 2, 6};
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
        int[] nums = new int[]{1, 2, 1, 2, 1, 2};
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
        int[] nums = new int[]{0, 1, 0, 1, 1, 0, 0, 1};
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
        int[] nums = new int[]{1, 2, 5, 1, 1, 1};
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
        NumMatrix_0013 numMatrix = new NumMatrix_0013(new int[][]{{3, 0, 1, 4, 2}, {5, 6, 3, 2, 1}, {1, 2, 0, 1, 5}, {4, 1, 0, 1, 7},
                {1, 0, 3, 0, 5}});
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
        ListNode head = new ListNode().build(new int[]{1, 2, 3, 4, 5});
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
        ListNode head = new ListNode().build(new int[]{3, 2, 0, -4});
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
        ListNode headA = new ListNode().build(new int[]{1, 2, 3, 4, 5});
        ListNode headB = new ListNode().build(new int[]{6, 7, 3, 4, 5});
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
        ListNode head = new ListNode().build(new int[]{1, 2, 3, 4, 5});
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
        ListNode l1 = new ListNode().build(new int[]{7, 2, 4, 3});
        ListNode l2 = new ListNode().build(new int[]{5, 6, 4});
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
        ListNode head = new ListNode().build(new int[]{1, 2, 3, 4, 5});
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
        ListNode head = new ListNode().build(new int[]{1, 2, 3, 3, 2, 1});
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
        MultiNode C = new MultiNode().build(new int[]{11, 12});
        MultiNode B2 = new MultiNode().build(new int[]{8, 9, 10});
        B2.child = C;
        MultiNode B1 = new MultiNode().build(new int[]{7});
        B1.next = B2;
        MultiNode A2 = new MultiNode().build(new int[]{3, 4, 5, 6});
        A2.child = B1;
        MultiNode A1 = new MultiNode().build(new int[]{1, 2});
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
        ListNode head = new ListNode().build(new int[]{3, 4, 1});
        head.next.next.next = head;
        int insert = 2;
        ListNode node = new ListNode(2);
        ListNode result;
        if (head == null) {
            node.next = node;
            result = node;
        } else {
            ListNode cur = head;
            for (; ; ) {
                if (cur.val <= insert && insert <= cur.next.val ||
                        cur.val > cur.next.val && (cur.val <= insert || insert <= cur.next.val) ||
                        cur.next == head) {
                    node.next = cur.next;
                    cur.next = node;
                    break;
                }
                cur = cur.next;
            }
            result = head;
        }
        //log.info("result: {}", result);
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

            Node() {
            }

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
        String[] strs = new String[]{"eat", "tea", "tan", "ate", "nat", "bat"};
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

    }

}
