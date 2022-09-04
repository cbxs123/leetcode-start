package com.cbxs123.code;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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

    }

}
