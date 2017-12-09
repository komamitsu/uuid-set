package org.komamitsu.uuidset;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class DoubleArraySetTest
{
    private List<String> strings;
    private DoubleArraySet set;

    @Before
    public void setUp()
    {
        strings = TestHelper.smallTestData();
        set = new DoubleArraySet(strings);
    }

    @Test
    public void size()
            throws Exception
    {
        assertThat(set.size(), is(strings.size()));
    }

    @Test
    public void contains()
            throws Exception
    {
        for (String s : strings) {
            assertThat(set.contains(s), is(true));
        }

        assertThat(set.contains(UUID.randomUUID().toString().toLowerCase()), is(false));
        assertThat(set.contains(UUID.randomUUID().toString().toUpperCase()), is(false));
        assertThat(set.contains("ab"), is(false));
    }

    @Test
    public void toBytes()
            throws IOException
    {
        byte[] bytes = set.toBytes();
        DoubleArraySet restoredSet = new DoubleArraySet(bytes);

        assertThat(restoredSet.size(), is(strings.size()));
        assertThat(set.contains(strings.get(0)), is(true));
    }
}