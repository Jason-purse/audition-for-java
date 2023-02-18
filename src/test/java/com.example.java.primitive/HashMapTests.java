package com.example.java.primitive;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class HashMapTests {
    @Test
    public void defaultHashMap() {
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();

        new HashMap<>(8);

        objectObjectHashMap.put("1","23");
    }
}
