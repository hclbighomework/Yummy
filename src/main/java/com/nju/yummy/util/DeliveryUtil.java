package com.nju.yummy.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 计算两地之间距离，判断大致送达时间
 */
public class DeliveryUtil {
    //假设送外卖的平均速度为100m/min
    private static int speed = 100;

    //dist[i][j]=INF<==>i 和 j之间没有边
    private static int INF = Integer.MAX_VALUE;
    private static int[][] dist = new int[10][10];
    //顶点i 到 j的最短路径长度，初值是i到j的边的权重
    private static int[][] path = new int[10][10];
    private static List<Integer> result = new ArrayList<>();

    private static int[][] matrix = {
            {INF,  INF,  1200, 1000, INF,  1000, 2000, 1200, INF,  INF},
            {INF,  INF,  1500, 500,  INF,  2500, INF,  2000, INF,  3000},
            {1200, 1500, INF,  2000, INF,  1500, 1500, 1000, INF,  INF},
            {1000, 500,  2000, INF,  INF,  2500, 2500, 2000, INF,  INF},
            {INF,  INF,  INF,  INF,  INF,  INF,  3000, 3000, 1000, INF},
            {1000, 2500, 1500, 2500, INF,  INF,  3000, 4000, INF,  INF},
            {2000, INF,  1500, 2500, 3000, 3000, INF,  1000, INF,  INF},
            {1200, 2000, 1000, 2000, 3000, 4000, 1000, INF,  5000, INF},
            {INF,  INF,  INF,  INF,  1000, INF,  INF,  5000, INF,  INF},
            {INF,  3000, INF,  INF,  INF,  INF,  INF,  INF,  INF,  INF}
    };


    private static void findCheapestPath(int begin, int end, int[][] matrix) {
        floyd(matrix);
        result.add(begin);
        findPath(begin, end);
        result.add(end);
    }

    private static void findPath(int i, int j) {
        int k = path[i][j];
        if (k == -1) return;
        findPath(i, k);   //递归
        result.add(k);
        findPath(k, j);
    }

    private static void floyd(int[][] matrix) {
        int size = matrix.length;
        //initialize dist and path
        for (int i = 0; i < size; i++) {
            for (int j = 0; j< size; j++) {
                path[i][j] = -1;
                dist[i][j] = matrix[i][j];
            }
        }
        for (int k = 0; k < size; k++) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (dist[i][k] != INF && dist[k][j] != INF && dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        path[i][j] = k;
                    }
                }
            }
        }

    }

    public static int getDeliveryInterval(int start, int end) {
        if (start == end) {
            return 0;
        }
        findCheapestPath(start, end, matrix);
        List<Integer> list = result;
        System.out.println(start + " to " + end + ",the cheapest path is:");
        System.out.println(list.toString());
        System.out.println(dist[start][end]);
        return dist[start][end] / speed;
    }


}