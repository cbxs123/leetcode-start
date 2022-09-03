package com.cbxs123.code;

import com.cbxs123.code.common.ListNode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @author cbxs123
 * @title LeetCode_2000_220620.java
 * @description https://doocs.github.io/leetcode/#/solution/README
 * @date 2022/6/23 22:00
 **/
@Slf4j
public class LeetCode_2000_220620 {

    // 1.两数之和#1
    @Test
    void code0001() {
        int[] nums = new int[]{2, 7, 11, 15};
        int target = 9;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int num = target - nums[i];
            if (map.containsKey(num)) {
                log.info("result: {}", new int[]{map.get(num), i});
            }
            map.put(nums[i], i);
        }
        log.info("null");
    }

    @Test
    void testLinkNode() {
        ListNode l = new ListNode();
        log.info("[add 1] result:{}, head:{}", l.add(2), l.toString());
        l = l.build(new int[]{1, 2, 3, 3, 4, 5});
        log.info("[build 1,2,3,3,4,5] head:{}", l.toString());
        log.info("[add 3,6] result:{}, head:{}", l.add(3, 6), l.toString());
        log.info("[add 7,8] result:{}, head:{}", l.add(7, 8), l.toString());
        log.info("[replace 3,6] result:{}, head:{}", l.replace(3, 6), l.toString());
        log.info("[replace 7,8] result:{}, head:{}", l.replace(7, 8), l.toString());
        log.info("[firstFind 6] result:{}, head:{}", l.firstFind(6), l.toString());
        log.info("[lastFind 6] result:{}, head:{}", l.lastFind(6), l.toString());
        log.info("[firstFind 7] result:{}, head:{}", l.firstFind(7), l.toString());
        log.info("[lastFind 7] result:{}, head:{}", l.lastFind(7), l.toString());
        l = l.reverse();
        log.info("[reverse] result:{}", l.toString());
        l = l.reverse();
        log.info("[reverse] result:{}", l.toString());
        log.info("[delete 6] result:{}, head:{}", l.delete(6), l.toString());
        log.info("[delete 7] result:{}, head:{}", l.delete(7), l.toString());
        log.info("[get 2] result:{}, head:{}", l.get(2).toString(), l.toString());
        log.info("[get 5] result:{}, head:{}", l.get(5), l.toString());
        log.info("[size] result:{}, head:{}", l.size(), l.toString());
    }

    // 2.两数之加#1
    @Test
    void code0002() {
        ListNode l1 = new ListNode().build(new int[]{9, 9, 9, 9, 9, 9, 9});
        ListNode l2 = new ListNode().build(new int[]{9, 9, 9, 9});
        ListNode head = new ListNode(0);
        int cnt = 0;
        ListNode cur = head;
        while (l1 != null || l2 != null || cnt != 0) {
            int sum = (l1 == null ? 0 : l1.val) + (l2 == null ? 0 : l2.val) + cnt;
            cnt = sum / 10;
            cur.next = new ListNode(sum % 10);
            cur = cur.next;
            l1 = l1 == null ? null : l1.next;
            l2 = l2 == null ? null : l2.next;
        }
        log.info("result: {}", head.next.toString());
    }

    // 3.无重复字符的最长子串#2
    @Test
    void code0003() {
        String s = "baidualibaba";
        // 双指针 + 哈希表
        int i = 0, j = 0, ans = 0;
        Set<Character> chars = new HashSet<>();
        for (char c : s.toCharArray()) {
            while (chars.contains(c)) {
                chars.remove(s.charAt(i++));
            }
            chars.add(c);
            ans = Math.max(ans, j - i + 1);
            log.info("sub: {}, head: {}", s.substring(i, j + 1), s);
            j++;
        }
        log.info("result: {}", ans);
    }

    // 4. 寻找两个正序数组的中位数#3
    @Test
    void code0004() {
        findMedianSortedArrays(new int[]{10, 12, 14, 16, 18}, new int[]{11, 13, 15, 17, 19}); // 14.5
        findMedianSortedArrays(new int[]{1}, new int[]{2, 3, 4}); // 2.5
        findMedianSortedArrays(new int[]{1}, new int[]{2, 3, 4, 5}); // 3.0
        findMedianSortedArrays(new int[]{1, 2, 3, 4}, new int[]{5}); // 3.0
    }

    private void findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int left = (m + n + 1) / 2;
        int right = (m + n + 2) / 2;
        double result = (findKth(nums1, 0, nums2, 0, left) + findKth(nums1, 0, nums2, 0, right)) / 2.0;
        log.info("result: {}", result);
    }

    private int findKth(int[] nums1, int i, int[] nums2, int j, int k) {
        if (i >= nums1.length) {
            return nums2[j + k - 1];
        }
        if (j >= nums2.length) {
            return nums1[i + k - 1];
        }
        if (k == 1) {
            return Math.min(nums1[i], nums2[j]);
        }
        int midVal1 = (i + k / 2 - 1 < nums1.length) ? nums1[i + k / 2 - 1] : Integer.MAX_VALUE;
        int midVal2 = (j + k / 2 - 1 < nums2.length) ? nums2[j + k / 2 - 1] : Integer.MAX_VALUE;
        if (midVal1 < midVal2) {
            return findKth(nums1, i + k / 2, nums2, j, k - k / 2);
        }
        return findKth(nums1, i, nums2, j + k / 2, k - k / 2);
    }

    // 5. 最长回文子串#2
    @Test
    void code0005() {
        String s = "abcbcda";
        int n = s.length(), start = 0, mx = 1;
        boolean[][] dp = new boolean[n][n];
        for (int j = 0; j < n; j++) {
            for (int i = 0; i <= j; i++) {
                if (j - i < 2) {
                    dp[i][j] = s.charAt(i) == s.charAt(j);
                } else {
                    dp[i][j] = dp[i + 1][j - 1] && s.charAt(i) == s.charAt(j);
                }
                if (dp[i][j] && mx < j - i + 1) {
                    mx = j - i + 1;
                    start = i;
                }
            }
        }
        String result = s.substring(start, start + mx);
        log.info("result: {}", result);
    }

    // 6. Z 字形变换#2
    @Test
    void code0006() {
        String s = "PAYPALISHIRING";
        int numRows = 4;
        String result = s;
        if (numRows > 1) {
            StringBuilder ans = new StringBuilder();
            int group = 2 * (numRows - 1);
            for (int i = 1; i <= numRows; i++) {
                int interval = (i == 1 || i == numRows) ? group : 2 * (numRows - i);
                int idx = i - 1;
                while (idx < s.length()) {
                    ans.append(s.charAt(idx));
                    idx += interval;
                    interval = group == interval ? group : group - interval;
                }
            }
            result = ans.toString();
        }
        log.info("result: {}", result);
    }

    // 7. 整数反转#1
    @Test
    void code0007() {
        int x = -123, res = 0;
        while (x != 0) {
            res = res * 10 + x % 10;
            x /= 10;
        }
        int result = res < Integer.MIN_VALUE || res > Integer.MAX_VALUE ? 0 : (int) res;
        log.info("result: {}", result);
    }

    // 8. 字符串转换整数#2
    @Test
    void code0008() {
        String s = "  -42 with words";
        int result = 0;
        if (s != null && s.length() > 0) {
            int n = s.length();
            int i = 0;
            while (s.charAt(i) == ' ') {
                if (++i == n) {
                    result = -1;
                    break;
                }
            }
            if (result != -1) {
                int sign = 1;
                if (s.charAt(i) == '-') {
                    sign = -1;
                }
                if (s.charAt(i) == '-' || s.charAt(i) == '+') {
                    ++i;
                }
                int res = 0, flag = Integer.MAX_VALUE / 10;
                for (; i < n; i++) {
                    // 非数字
                    if (s.charAt(i) < '0' || s.charAt(i) > '9') {
                        break;
                    }
                    // 溢出
                    if (res > flag || (res == flag && s.charAt(i) > '7')) {
                        res = Integer.MAX_VALUE;
                        break;
                    }
                    res = res * 10 + (s.charAt(i) - '0');
                }
                result = sign * res;
            }
        }
        log.info("result: {}", result);
    }

    // 9. 整数回文数#1
    @Test
    void code0009() {
        int x = 121;
        boolean result = false;
        if (x >= 0) {
            int y = 0, t = x;
            while (t != 0) {
                y = y * 10 + t % 10;
                t /= 10;
            }
            result = x == y;
        }
        log.info("result: {}", result);
    }

    // 10. 正则表达式匹配#3
    @Test
    void code0010() {
        String s = "abbb", p = "ab*";
        boolean result = false;
        int m = s.length(), n = p.length();
        if (n != 0) {
            boolean[][] dp = new boolean[m + 1][n + 1];
            dp[0][0] = true;
            for (int j = 1; j < n + 1; j++) {
                if (p.charAt(j - 1) == '*') {
                    // 与*前2位一致
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

    // 11. 盛最多水的容器#1
    @Test
    void code0011() {
        int[] height = new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7};
        int i = 0, j = height.length - 1, res = 0;
        while (i < j) {
            int t = (j - i) * Math.min(height[i], height[j]);
            res = Math.max(res, t);
            if (height[i] < height[j]) {
                i++;
            } else {
                j--;
            }
        }
        log.info("result: {}", res);
    }

    // 12. 整数转罗马数字#1
    @Test
    void code0012() {
        int num = 1234;
        int[] nums = new int[]{1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romans = new String[]{"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nums.length; i++) {
            while (nums[i] <= num) {
                num -= nums[i];
                sb.append(romans[i]);
            }
        }
        log.info("result: {}", sb.toString());
    }

    // 13. 罗马数字转整数#1
    @Test
    void code0013() {
        String s = "MCMXCIV"; // 1994
        Map<String, Integer> nums = new HashMap<>();
        nums.put("M", 1000);
        nums.put("CM", 900);
        nums.put("D", 500);
        nums.put("CD", 400);
        nums.put("C", 100);
        nums.put("XC", 90);
        nums.put("L", 50);
        nums.put("XL", 40);
        nums.put("X", 10);
        nums.put("IX", 9);
        nums.put("V", 5);
        nums.put("IV", 4);
        nums.put("I", 1);
        int res = 0;
        for (int i = 0; i < s.length(); ) {
            if (i + 1 < s.length() && nums.get(s.substring(i, i + 2)) != null) {
                res += nums.get(s.substring(i, i + 2));
                i += 2;
            } else {
                res += nums.get(s.substring(i, i + 1));
                i += 1;
            }
        }
        log.info("result: {}", res);
    }

    // 14. 最长公共前缀
    @Test
    void code0014() {
        String[] strs = new String[]{"flower", "flow", "flight"};
        String result = "";
        int n = strs.length;
        if (n != 0) {
            result = strs[0];
            for (int i = 0; i < strs[0].length(); i++) {
                for (int j = 1; j < n; j++) {
                    if (strs[j].length() <= i || strs[j].charAt(i) != strs[0].charAt(i)) {
                        result = strs[0].substring(0, i);
                        i = strs[0].length();
                        break;
                    }
                }
            }
        }
        log.info("result: {}", result);
    }

    // 15. 三数之和为0序列#2
    @Test
    void code0015() {
        int[] nums = new int[]{-1, 0, 1, 2, -1, -4};
        int n = nums.length;
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        if (n >= 3) {
            for (int i = 0; i < n - 2 && nums[i] <= 0; i++) {
                if (i > 0 && nums[i] == nums[i - 1]) {
                    continue;
                }
                int j = i + 1, k = n - 1;
                while (j < k) {
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        res.add(Arrays.asList(nums[i], nums[j], nums[k]));
                        j++;
                        k--;
                        while (j < n && nums[j] == nums[j - 1]) {
                            j++;
                        }
                        while (k > i && nums[k] == nums[k + 1]) {
                            k--;
                        }
                    } else if (nums[i] + nums[j] + nums[k] < 0) {
                        j++;
                    } else {
                        k--;
                    }
                }
            }
        }
        log.info("result: {}", res);
    }

    // 16. 最接近的三数之和#3
    @Test
    void code0016() {

    }

}
