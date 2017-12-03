package org.komamitsu.uuidset;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ImmutableUuidHashSetTest
{
    private List<String> strings;
    private ImmutableUuidHashSet uuidHashSet;

    @Before
    public void setUp()
    {
        strings = TestHelper.smallTestData();
        uuidHashSet = new ImmutableUuidHashSet(strings);
    }

    @Test
    public void size()
            throws Exception
    {
        assertThat(uuidHashSet.size(), is(strings.size()));
    }

    @Test
    public void contains()
            throws Exception
    {
        for (String s : strings) {
            assertThat(uuidHashSet.contains(s), is(true));
        }

        assertThat(uuidHashSet.contains(UUID.randomUUID().toString().toLowerCase()), is(false));
        assertThat(uuidHashSet.contains(UUID.randomUUID().toString().toUpperCase()), is(false));
        assertThat(uuidHashSet.contains("ab"), is(false));
    }

    @Test
    public void toBytes()
            throws IOException
    {
        byte[] bytes = uuidHashSet.toBytes();
        ImmutableUuidHashSet restoredSet = new ImmutableUuidHashSet(bytes);

        assertThat(restoredSet.size(), is(strings.size()));
        assertThat(uuidHashSet.contains(strings.get(0)), is(true));
    }
}