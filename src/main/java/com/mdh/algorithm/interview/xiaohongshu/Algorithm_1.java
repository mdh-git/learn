package com.mdh.algorithm.interview.xiaohongshu;

/**
 * 小红书
 * 2022.3.13
 * 数组中只有 0 和 1 ，一定要翻转一个区间，反转：0 变 1 、 1 变 0
 * 请问翻转后可以使得1的个数最多是多少
 */
public class Algorithm_1 {
    public static void main(String[] args) {
        int[] arr = {1, 1, 0, 0, 1, 0, 0, 1 };
        int cnt = 0;
        for (int i = 0; i < arr.length; i++) {
            arr[i] = arr[i] == 0 ? 1 : -1;
            if(arr[i] == 1){
                cnt++;
            }
        }
        printlnArr(arr);

        System.out.println();
        int cnt1 = ArrayMaxValue1(arr);
        int cnt2 = ArrayMaxValue2(arr);
        System.out.println(cnt + cnt1);
        System.out.println(cnt + cnt2);
    }

    private static void printlnArr(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(" " + arr[i]);
        }
    }


    /**
     * 子序列最大的累加和
     * @param arr
     * @return
     */
    private static int ArrayMaxValue1(int[] arr) {
        if(arr == null || arr.length == 0){
            return 0;
        }

        int length = arr.length;
        int[] dp = new int[length];
        dp[0] = arr[0];
        int max = dp[0];
        for (int i = 1; i < length; i++) {
            dp[i] = Math.max(arr[i], dp[i - 1] + arr[i]);
            max = Math.max(dp[i], max);
        }

        // dp[0 .. n-1] 最大值返回
        return max;
    }

    private static int ArrayMaxValue2(int[] arr) {
        if(arr == null || arr.length == 0){
            return 0;
        }

        // pre -> dp[0]
        int pre = arr[0];
        int max = pre;
        for (int i = 1; i < arr.length; i++) {
            // 滚动数组
//            int p1 = arr[i];
//            int p2 = pre + arr[i];
//            int cur = Math.max(p1, p2);
//            max = Math.max(max, cur);
//            pre = cur;

            pre = Math.max(arr[i], pre + arr[i]);
            max = Math.max(max, pre);
        }
        return max;
    }
}
