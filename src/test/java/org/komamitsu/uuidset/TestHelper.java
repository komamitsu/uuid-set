package org.komamitsu.uuidset;

import java.util.ArrayList;
import java.util.List;
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
}
