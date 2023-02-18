package com.example.java.primitive;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringTests {

    /**
     * 两个语句都会先去字符串常量池中检查是否已经存在 “xyz”，如果有则直接使用，如果没有则会在常量池中创建 “xyz” 对象。
     *
     * 另外，String s = new String("xyz") 还会通过 new String() 在堆里创建一个内容与 "xyz" 相同的对象实例。
     */
    @Test
    public void constantStringWithStringInstanceEquals() {
        String value = "value";
        String value2 = "value";

        Assertions.assertSame(value,value2);
        String valueObj = new String("value");
        Assertions.assertSame(value, valueObj);
    }
}
