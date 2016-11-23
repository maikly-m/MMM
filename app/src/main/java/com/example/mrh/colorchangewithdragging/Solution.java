package com.example.mrh.colorchangewithdragging;

import java.util.LinkedList;

/**
 * Created by MR.H on 2016/11/23 0023.
 */

public class Solution {
    /**
     * 二叉树节点的定义
     * public class TreeNode {
     *     int value;
     *     TreeNode left;
     *     TreeNode right;
     *     TreeNode(int x) { value = x; }
     * }
     */

    class TreeNode {
        int value;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) {
            value = x;
        }
    }
    //幸运节点总和
    private int sum = 0;

    private int sumOfLuckyNodes(TreeNode root) {
        if (root == null){
            throw new RuntimeException("root 为空");
        }
        getSum(root);
        return sum;
    }
    //递归求和
    private void getSum (TreeNode root) {
        int l = sumOfLeft(root.left);
        int r = sumOfRight(root.right);
        if (l == 0 || r == 0){
            sum += root.value;
        }
    }

    private int sumOfRight (TreeNode right) {
        if (right == null){
            return -1;
        }
        getSum(right);
        return 0;
    }

    private int sumOfLeft (TreeNode left) {
        if (left == null){
            return -1;
        }
        getSum(left);
        return 0;
    }
    //---------------------------------------------------------------
    //测试部分
    public static void main(String[] args){
        int[] test = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Solution solution = new Solution();
        TreeNode tree = solution.createTree(test);
        int sumOfLuckyNodes = solution.sumOfLuckyNodes(tree);
        System.out.println(sumOfLuckyNodes);

    }
    //创建二叉树
    private TreeNode createTree(int[] array){
        LinkedList<TreeNode> list = new LinkedList<>();
        for (int i = 0; i < array.length; i++){
            TreeNode treeNode = new TreeNode(array[i]);
            list.add(treeNode);
        }

        for (int j = 0; j < array.length/2-1; j++){
            list.get(j).left = list.get(j*2 +1);
            list.get(j).right = list.get(j*2 +2);
        }

        int lastParentIndex = array.length / 2 - 1;
        list.get(lastParentIndex).left = list
                .get(lastParentIndex * 2 + 1);
        if (array.length % 2 == 1) {
            list.get(lastParentIndex).right = list.get(lastParentIndex * 2 + 2);
        }
        return list.get(0);
        }
}
