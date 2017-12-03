package org.komamitsu.uuidset;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class TestHelper
{
    public static List<String> smallTestData()
    {
        List<String> strings = new ArrayList<>();
        strings.add(UUID.randomUUID().toString().toLowerCase());
        strings.add(UUID.randomUUID().toString().toUpperCase());
        strings.add("a");
        strings.add("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
        strings.add(UUID.randomUUID().toString().toUpperCase());
        strings.add(UUID.randomUUID().toString().toLowerCase());
        return strings;
    }

    public static List<String> testData(int size, float uuidRate)
    {
        return testData(size, uuidRate,
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
    }

    public static List<String> testData(int size, float uuidRate, String stringPrefix)
    {
        Random random = new Random();
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (random.nextFloat() > uuidRate) {
                strings.add(String.format("%s-%d", stringPrefix, i));
            }
            else {
                strings.add(UUID.randomUUID().toString());
            }
        }
        return strings;
    }
}
