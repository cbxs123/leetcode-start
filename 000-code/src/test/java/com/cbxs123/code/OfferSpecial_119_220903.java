package com.cbxs123.code;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cbxs123
 * @title OfferSpecial_119_220903.java
 * @description https://doocs.github.io/leetcode/#/lcof2/README
 * @date 2022/9/3 11:05
 **/
@Slf4j
public class OfferSpecial_119_220903 {

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

    }

}
