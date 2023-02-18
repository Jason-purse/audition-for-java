package com.example.java.primitive;

import com.example.algorithm.RBTree;
import com.example.algorithm.TreeOperation;

import java.util.Scanner;

public class RBTreeTests {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RBTree<String,String> tree = new RBTree<>();
       while(true) {
           int i = scanner.nextInt();
           if(i == -1) {
               return ;
           }
           String s = String.valueOf(i);
           tree.put(s,s);
           TreeOperation.show(tree.getRoot());
       }

    }
}
