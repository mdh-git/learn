package com.mdh.datastructure.sparsearray;

/**
 * 稀疏数组
 * @author MDH
 * 2021/5/11 22:08
 */
public class SparseArray {

    public static void main(String[] args) {
        // 创建一个原始的二维数组 11 * 11
        // 0: 表示白棋子   1: 表示黑棋子
        int chessArr1[][] = new int[11][11];
        chessArr1[1][2] = 1;
        chessArr1[2][3] = 2;
        chessArr1[4][5] = 2;
        // 输出原始的二维数组
        System.out.println("原始的二维数组");
        printArr(chessArr1);

        // 将二维数组转化为稀疏数组
        int sparseArr[][] = buildSparseArray(chessArr1);
        System.out.println("输出稀疏数组：");
        printArr(sparseArr);

        
        // 将稀疏数组转为二维数组
        int chessArr2[][] = buildArrayToSparseArray(sparseArr);
        System.out.println("原来的数组：");
        printArr(chessArr2);
    }


    /**
     * 将二维数组转为稀疏数组
     * @param chessArr1  原始二维数组
     * @return  系数数组
     */
    private static int[][] buildSparseArray(int[][] chessArr1) {
        // 1、先遍历二维数组 得到非0的个数
        int sum = 0;
        for (int i = 0; i < chessArr1.length; i++){
            for (int j = 0; j < chessArr1[i].length; j++){
                if(chessArr1[i][j] != 0){
                    sum++;
                }
            }
        }

        System.out.println("非0的个数：" + sum);
        //2. 创建对应的稀疏数组
        int sparseArr[][] = new int[sum + 1][3];
        // 给稀疏数组赋值
        sparseArr[0][0] = 11;
        sparseArr[0][1] = 11;
        sparseArr[0][2] = sum;
        int count = 1;
        for (int i = 0; i < chessArr1.length; i++){
            for (int j = 0; j < chessArr1[i].length; j++){
                if(chessArr1[i][j] != 0){
                    sparseArr[count][0] = i;
                    sparseArr[count][1] = j;
                    sparseArr[count][2] = chessArr1[i][j];
                    count++;
                }
            }
        }

        return sparseArr;
    }

    /**
     * 将稀疏数组转化为二维数组
     * @param sparseArr
     * @return
     */
    private static int[][] buildArrayToSparseArray(int[][] sparseArr) {
        int row = sparseArr[0][0];
        int col = sparseArr[0][1];
        int chessArr[][] = new int[row][col];

        for (int i = 1; i < sparseArr.length; i++){
            int[] rowArr = sparseArr[i];
            chessArr[rowArr[0]][rowArr[1]] = rowArr[2];
        }
        return chessArr;
    }

    /**
     * 打印数组
     * @param arr
     */
    private static void printArr(int[][] arr) {
        for (int[] row : arr){
            for (int data : row){
                System.out.printf("%d\t", data);
            }
            System.out.println("");
        }
    }


}
