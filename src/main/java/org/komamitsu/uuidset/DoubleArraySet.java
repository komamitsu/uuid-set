package org.komamitsu.uuidset;

import org.trie4j.TermIdNode;
import org.trie4j.doublearray.DoubleArray;
import org.trie4j.patricia.PatriciaTrie;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class DoubleArraySet
    extends AbstractCollection<String>
    implements Set<String>
{
    private final DoubleArray doubleArray;

    public DoubleArraySet(Collection<String> strings)
    {
        PatriciaTrie trie = new PatriciaTrie();
        for (String string : strings) {
            trie.insert(string);
        }
        this.doubleArray = new DoubleArray(trie, strings.size());
    }

    public DoubleArraySet(byte[] bytes)
            throws IOException
    {
        this.doubleArray = new DoubleArray();
        this.doubleArray.load(new ByteArrayInputStream(bytes));
    }

   @Override
    public Iterator iterator()
    {
        // FIXME
        return null;
    }

    @Override
    public int size()
    {
        return doubleArray.size();
    }

    @Override
    public boolean contains(Object o)
    {
        if (!(o instanceof String)) {
            return false;
        }

        String s = (String) o;

        return doubleArray.contains(s);
    }

    public byte[] toBytes()
            throws IOException
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        doubleArray.save(outputStream);
        return outputStream.toByteArray();
    }
}
