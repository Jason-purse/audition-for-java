package com.example.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author FLJ
 * @date 2023/2/17
 * @time 12:05
 * @Description
 */
public class RBTreeShow {

    private RBTreeShow() {

    }
    public static  <K extends Comparable<K>,V> void show(RBTree<K,V> tree) {
        RBTree<K, V>.TreeNode root = tree.getRoot();

        List<RBTree<K,V>.TreeNode> values = new ArrayList<>();
        int depth = traverseFirst(root);

        // 算一下有多深 ...
        // 深度就能知道 最底层有多少个节点  2^(n-1)

        int width = 1 << (depth - 1);

       while (root != null) {
           RBTree.Color color = root.getColor();
           V value = root.getValue();
           String showContent;
           showContent = getColorString(color, value);
           prettyPrintf(showContent,width,true);

           RBTree<K, V>.TreeNode left = root.getLeft();
           String leftColorString = getColorString(left == null ? RBTree.Color.BLANK : left.getColor(), (V) "null");
           RBTree<K, V>.TreeNode right = root.getRight();
           String rightColorString = getColorString(right == null ? RBTree.Color.BLANK : right.getColor(), (V) "null");

           prettyPrintf(leftColorString + " " + rightColorString,width - 1,true);

           if(left != null) {
               root = left;
           }
           if(right != null) {
               root = right;
           }

           width -= 1;
       }

    }

    private static <V> String getColorString(RBTree.Color color, V value) {
        String showContent;
        if(color == RBTree.Color.RED) {
            showContent  = getFormatLogString(value == null ? "null" : value.toString(),31,0);
        }
        else {
            showContent = getFormatLogString(value == null ? "null" : value.toString(), 36,0);
        }
        return showContent;
    }

    private static void prettyPrintf(String content,int width,boolean newLine) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < width; i++) {
            builder.append(" ");
        }
        builder.append(content);
        if(newLine) {
            System.out.println(builder.toString());
        }
        else {
            System.out.print(builder.toString());
        }
    }

    private static <K extends Comparable<K>,V> int traverseFirst(RBTree<K,V>.TreeNode tree) {
        int depth = 0;
        // 前序遍历 ..
        if(tree != null) {
            depth =+ 1;
            depth += traverseFirst(tree.getLeft());
            int rightDepth = traverseFirst(tree.getRight());
            if(rightDepth > depth) {
                depth = rightDepth;
            }
        }
        return depth;
    }

    private static String getFormatLogString(String content, int colour, int type) {
        boolean hasType = type != 1 && type != 3 && type != 4;
        if (hasType) {
            return String.format("\033[%dm%s\033[0m", colour, content);
        } else {
            return String.format("\033[%d;%dm%s\033[0m", colour, type, content);
        }
    }
}
