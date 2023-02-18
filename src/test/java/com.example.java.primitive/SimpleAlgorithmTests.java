package com.example.java.primitive;

import org.junit.jupiter.api.Test;

import java.util.Scanner;

public class SimpleAlgorithmTests {

    /**
     * 计算一个数的天花板 偶数 ...
     * <p>
     * 00001001
     */
    public static void calculateCeilEvenForNumber() {
        Scanner scanner = new Scanner(System.in);
        int value = scanner.nextInt();
        int result;
        if ((value & 0x00000001) == 1) {
            result = (-1 >>> leadingZeros(value - 1)) + 1;
        } else {
            result = value;
        }

        System.out.println(result);
    }

    public static int leadingZeros(int value) {
        int n = 31;
        if (value <= 0) {
            if (value == 0) {
                n = 32;
            } else {
                n = 0;
            }
            return n;
        } else {
            // 是一个正数 ..

            if (value >= 1 << 16) {
                n -= 16;
                value >>>= 16;
            }
            if (value >= 1 << 8) {
                n -= 8;
                value >>>= 8;
            }
            if (value >= 1 << 4) {
                n -= 4;
                value >>>= 4;
            }
            if (value >= 1 << 2) {
                n -= 2;
            }
            return n - (value >>> 1);
        }
    }

    public static void main(String[] args) {
        calculateCeilEvenForNumber();

        System.out.println(-1 >>> 1);
    }


}
