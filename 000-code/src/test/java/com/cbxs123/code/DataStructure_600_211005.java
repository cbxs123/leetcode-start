package com.cbxs123.code;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 微信公众号"数据结构和算法",回复"pdf"
 */
@Slf4j
class DataStructure_600_211005 {

    /**
     * 动态规划：运算结果等于target的不同表达式的数目 O(length*capacity) O(length*capacity)
     */
    @Test
    void code494() {
        int[] nums = new int[]{1, 2, 1, 3, 1};
        int target = 6;
        int length = nums.length;
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if (sum < target || ((sum - target) & 1) != 0) {
            log.info("result:{}", 0);
            System.exit(0);
        }
        int capacity = (sum - target) >> 1;
        int dp[][] = new int[length + 1][capacity + 1];
        dp[0][0] = 1;
        for (int i = 1; i <= length; i++) {
            for (int j = 0; j <= capacity; j++) {
                if (j >= nums[i - 1]) {
                    dp[i][j] = dp[i - 1][j] + dp[i - 1][j - nums[i - 1]];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
                log.info("step1:{},num:{}", dp[i][j], nums[i - 1]);
            }
            log.info("step2");
        }
        log.info("result:{}", dp[length][capacity]);
    }

    /**
     * 动态规划：运算结果等于target的不同表达式的数目 O(length*capacity) O(capacity)
     */
    @Test
    void code494N1() {
        int[] nums = new int[]{1, 2, 1, 3, 1};
        int target = 6;
        int length = nums.length;
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if (sum < target || ((sum - target) & 1) != 0) {
            log.info("result:{}", 0);
            System.exit(0);
        }
        int capacity = (sum - target) >> 1;
        int dp[] = new int[capacity + 1];
        dp[0] = 1;
        for (int i = 1; i <= length; i++) {
            for (int j = capacity; j >= 0; j--) {
                if (j >= nums[i - 1]) {
                    dp[j] += dp[j - nums[i - 1]];
                }
                log.info("step1:{},num:{}", dp[j], nums[i - 1]);
            }
            log.info("step2");
        }
        log.info("result:{}", dp[capacity]);
    }

    /**
     * 动态规划解分割等和子集
     */
    @Test
    void code588() {
        int[] nums = new int[]{1, 5, 11, 5};
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if ((sum & 1) != 0) {
            log.info("false");
            System.exit(0);
        }
        int target = sum >> 1, length = nums.length;
    /*        int[][] dp = new int[length + 1][target + 1];
    for (int i = 1; i <= length; i++) {
      for (int j = 1; j <= target; j++) {
        if (j >= nums[i - 1]) {
          dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - nums[i - 1]] + nums[i - 1]);
        } else {
          dp[i][j] = dp[i - 1][j];
        }
      }
    }
    log.info("result:{}", dp[length][target] == target);*/
    /*    boolean[][] dp = new boolean[length + 1][target + 1];
    dp[0][0] = true;
    for (int i = 1; i <= length; i++) {
      for (int j = 1; j <= target; j++) {
        if (j >= nums[i - 1]) {
          dp[i][j] = (dp[i - 1][j] || dp[i - 1][j - nums[i - 1]]);
        } else {
          dp[i][j] = dp[i - 1][j];
        }
      }
    }
    log.info("result:{}", dp[length][target]);*/
        boolean[] dp = new boolean[target + 1];
        dp[0] = true;
        for (int i = 1; i <= length; i++) {
            for (int j = target; j >= 1; j--) {
                if (j >= nums[i - 1]) {
                    dp[j] = (dp[j] || dp[j - nums[i - 1]]);
                }
            }
        }
        log.info("result:{}", dp[target]);
    }

    /**
     * DFS解分割等和子集
     */
    @Test
    void code588N1() {
        int[] nums = new int[]{1, 5, 11, 5};
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if ((sum & 1) != 0) {
            log.info("false");
            System.exit(0);
        }
        int target = sum >> 1;
        Boolean[][] map = new Boolean[nums.length][target + 1];
        log.info("result:{}", dfs588(nums, 0, target, map));
    }

    private boolean dfs588(int[] nums, int index, int target, Boolean[][] map) {
        if (target == 0) return true;
        if (index == nums.length || target < 0) return false;
        if (map[index][target] != null) return map[index][target];
        boolean select = dfs588(nums, index + 1, target - nums[index], map);
        boolean unselect = dfs588(nums, index + 1, target, map);
        if (select || unselect) {
            map[index][target] = true;
            return true;
        }
        map[index][target] = false;
        return false;
    }

    /**
     * 位运算解分割等和子集
     */
    @Test
    void code588N2() {
        int[] nums = new int[]{1, 5, 11, 5};
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        boolean result = false;
        if ((sum & 1) == 0) {
            int len = sum >> 1;
            byte[] bits = new byte[len + 1];
            bits[0] = 1;
            for (int i = 0; i < nums.length; i++) {
                int num = nums[i];
                int size = len - num;
                for (int j = size; j >= 0; j--) {
                    bits[j + num] |= bits[j];
                }
                if ((bits[len] & 1) != 0) {
                    result = true;
                    break;
                }
            }
        }
        log.info("result:{}", result);
    }

    /**
     * 最大的以1为边界的正方形
     */
    @Test
    void code1139() {
        int[][] gird = new int[][]{{1, 1, 1}, {1, 0, 1}, {1, 1, 1}};
        int m = gird.length, n = gird[0].length, maxSide = 0;
        int[][][] dp = new int[m + 1][n + 1][2];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (gird[i - 1][j - 1] == 0) continue;
                dp[i][j][0] = dp[i][j - 1][0] + 1;
                dp[i][j][1] = dp[i - 1][j][1] + 1;
            }
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                int curSide = Math.min(dp[i][j][0], dp[i][j][1]);
                if (curSide <= maxSide) continue;
                for (; curSide > maxSide; curSide--) {
                    if (dp[i][j - curSide + 1][1] >= curSide && dp[i - curSide + 1][j][0] >= curSide) {
                        maxSide = curSide;
                        break;
                    }
                }
            }
        }
        log.info("result:{}", maxSide * maxSide);
    }

    /**
     * 动态规划解最长公共子串 O(m*n) O(n)
     */
    @Test
    void code127() {
        String str1 = "1AB2345CD", str2 = "12345EF";
        int maxLength = 0, maxLastIndex = 0;
        int[] dp = new int[str2.length() + 1];
        for (int i = 0; i < str1.length(); i++) {
            for (int j = str2.length() - 1; j >= 0; j--) {
                if (str1.charAt(i) == str2.charAt(j)) {
                    dp[j + 1] = dp[j] + 1;
                    if (dp[j + 1] > maxLength) {
                        maxLength = dp[j + 1];
                        maxLastIndex = i;
                    }
                } else {
                    dp[j + 1] = 0;
                }
            }
        }
        log.info("result:{}", str1.substring(maxLastIndex - maxLength + 1, maxLastIndex + 1));
    }

    @Test
    void code001() {
        int[] a1 = new int[]{1, 2, 3};
        Integer[] a2 = new Integer[]{4, 5, 6};
        // List<Integer> l1 = Arrays.asList(a1);//编译错误
        // List<int> l1 = Arrays.asList(a1);//编译错误
        // List<int> l2 = Arrays.asList(a2);//编译错误
    /*    List<Integer> l2 = Arrays.asList(a2);
    l2.add(7);
    log.info(l2.toString());  运行错误 java.lang.UnsupportedOperationException*/
        List<Integer> l2 = new ArrayList<>(Arrays.asList(a2));
        l2.add(7);
        log.info(l2.toString());
        List<Integer> l3 = new ArrayList<>();
        Collections.addAll(l3, a2);
        l3.add(7);
        log.info(l2.toString());
        List<Integer> l4 = new ArrayList<>(Arrays.asList(ArrayUtils.toObject(a1)));
        l4.add(7);
        log.info(l4.toString());
    }

    /**
     * 动态规划解单词拆分
     */
    @Test
    void code573() {
        String s = "catsandog";
        List<String> dict = Arrays.asList(new String[]{"cats", "dog", "sand", "and", "og"});
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                dp[i] = dp[j] && dict.contains(s.substring(j, i));
                if (dp[i]) break;
            }
        }
        log.info("result:{}", dp[s.length()]);
    }

    /**
     * 动态规划解分割回文串 III
     */
    @Test
    void code572() {
        String s = "aabdbbc";
        int k = 3;
        int length = s.length();
        int[][] dp = new int[length + 1][k + 1];
        for (int i = 0; i < dp.length; i++) {
            Arrays.fill(dp[i], length);
        }
        for (int i = 1; i <= length; i++) {
            int len = Math.min(i, k);
            for (int j = 1; j <= len; j++) {
                if (j == 1) {
                    dp[i][j] = change572(s, j - 1, i - 1);
                } else {
                    for (int m = j - 1; m < i; m++) {
                        dp[i][j] = Math.min(dp[i][j], dp[m][j - 1] + change572(s, m, i - 1));
                    }
                }
            }
            log.info("result:{}", dp[length][k]);
        }
    }

    private int change572(String s, int left, int right) {
        int count = 0;
        while (left < right) {
            if (s.charAt(left++) != s.charAt(right--)) count++;
        }
        return count;
    }

    @Test
    void code572N1() {
        String s = "aabdbbc";
        int k = 3;
        int length = s.length();
        int[][] pa = new int[length][length];
        for (int i = length - 2; i >= 0; i--) {
            for (int j = i + 1; j < length; j++) {
                pa[i][j] = pa[i + 1][j - 1] + (s.charAt(i) == s.charAt(j) ? 0 : 1);
            }
        }
        int[][] dp = new int[length + 1][k + 1];
        for (int i = 1; i < dp.length; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }
        for (int i = 1; i <= length; i++) {
            int len = Math.min(i, k);
            for (int j = 1; j <= len; j++) {
                if (j == 1) {
                    dp[i][j] = pa[j - 1][i - 1];
                } else {
                    for (int m = j - 1; m < i; m++) {
                        dp[i][j] = Math.min(dp[i][j], dp[m][j - 1] + pa[m][i - 1]);
                    }
                }
            }
            log.info("result:{}", dp[length][k]);
        }
    }

    /**
     * 动态规划解回文串分割 IV
     */
    @Test
    void code570() {
        String s = "aabbbc";
        int length = s.length();
        boolean result = false;
        boolean[][] dp = new boolean[length][length];
        for (int i = length - 1; i >= 0; i--) {
            for (int j = i; j < length; j++) {
                if (s.charAt(i) != s.charAt(j)) continue;
                dp[i][j] = j - i <= 2 || dp[i + 1][j - 1];
            }
        }
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length - 1; j++) {
                if (dp[0][i] && dp[i + 1][j] && dp[j + 1][length - 1]) {
                    result = true;
                    break;
                }
            }
        }
        log.info("result:{}", result);
    }

    /**
     * 动态规划解最后一块石头的重量 II
     */
    @Test
    void code568() {
        int[] stones = new int[]{2, 7, 4, 1, 8, 1};
        int length = stones.length, sum = 0;
        for (int num : stones) {
            sum += num;
        }
        int capacity = sum >> 1;
        int dp[][] = new int[length + 1][capacity + 1];
        for (int i = 1; i <= length; i++) {
            for (int j = 1; j <= capacity; j++) {
                if (j >= stones[i - 1]) {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - stones[i - 1]] + stones[i - 1]);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        log.info("result:{}", (sum - dp[length][capacity]) - dp[length][capacity]);
    }

    /**
     * 动态规划解最后一块石头的重量 II
     */
    @Test
    void code568N1() {
        int[] stones = new int[]{2, 7, 4, 1, 8, 1};
        int length = stones.length, sum = 0;
        for (int num : stones) {
            sum += num;
        }
        int capacity = sum >> 1;
        int dp[] = new int[capacity + 1];
        for (int i = 1; i <= length; i++) {
            for (int j = capacity; j >= 1; j--) {
                if (j >= stones[i - 1]) {
                    dp[j] = Math.max(dp[j], dp[j - stones[i - 1]] + stones[i - 1]);
                }
            }
        }
        log.info("result:{}", (sum - dp[capacity]) - dp[capacity]);
    }
}
