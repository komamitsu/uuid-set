package org.komamitsu.uuidset;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;

public class ImmutableUuidSet
    implements Set<String>
{
    private HashSet<UUID> uuids = new HashSet<>();
    private HashSet<String> strings = new HashSet<>();

    @Override
    public int size()
    {
        return uuids.size() + strings.size();
    }

    @Override
    public boolean isEmpty()
    {
        return size() == 0;
    }

    private UUID parseAsUuid(String value)
    {
        try {
            return UUID.fromString(value);
        }
        catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public boolean contains(Object o)
    {
        if (!(o instanceof String)) {
            return false;
        }

        String s = (String) o;

        UUID uuid = parseAsUuid(s);
        if (uuid == null) {
            return strings.contains(s);
        }
        return uuids.contains(uuid);
    }

    @Override
    public Iterator<String> iterator()
    {
        return new UuidSetIterator();
    }

    private class UuidSetIterator
        implements Iterator<String>
    {
        private Iterator<String> uuidIterator = uuids.stream().map(UUID::toString).iterator();
        private Iterator<String> stringIterator = strings.iterator();

        @Override
        public boolean hasNext()
        {
            return uuidIterator.hasNext() || stringIterator.hasNext();
        }

        @Override
        public String next()
        {
            if (uuidIterator.hasNext()) {
                return uuidIterator.next();
            }

            if (stringIterator.hasNext()) {
                return stringIterator.next();
            }

            throw new NoSuchElementException();
        }
    }

    @Override
    public Object[] toArray()
    {
        Object[] objects = new Object[size()];
        Iterator<String> iterator = iterator();
        int i = 0;
        while (iterator.hasNext()) {
            objects[i] = iterator.next();
            i++;
        }
        return objects;
    }

    @Override
    public <T> T[] toArray(T[] a)
    {
        // FIXME
        return null;
    }

    @Override
    public boolean add(String s)
    {
        throw new UnsupportedOperationException("This class is immutable");
    }

    @Override
    public boolean remove(Object o)
    {
        throw new UnsupportedOperationException("This class is immutable");
    }

    @Override
    public boolean containsAll(Collection<?> c)
    {
        return c.stream().allMatch(this::contains);
    }

    @Override
    public boolean addAll(Collection<? extends String> c)
    {
        throw new UnsupportedOperationException("This class is immutable");
    }

    @Override
    public boolean retainAll(Collection<?> c)
    {
        throw new UnsupportedOperationException("This class is immutable");
    }

    @Override
    public boolean removeAll(Collection<?> c)
    {
        throw new UnsupportedOperationException("This class is immutable");
    }

    @Override
    public void clear()
    {
        throw new UnsupportedOperationException("This class is immutable");
    }
}
