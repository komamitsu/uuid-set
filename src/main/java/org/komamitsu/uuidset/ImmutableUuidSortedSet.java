package org.komamitsu.uuidset;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.msgpack.jackson.dataformat.MessagePackFactory;

import java.io.IOException;
import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;

public class ImmutableUuidSortedSet
    extends AbstractCollection<String>
    implements Set<String>
{
    private static ObjectMapper objectMapper = new ObjectMapper(new MessagePackFactory());
    private HashSet<UUID> uuids = new HashSet<>();
    private HashSet<String> strings = new HashSet<>();

    public static class Stored
    {
        private List<UUID> uuids;
        private List<String> strings;

        public Stored(
                @JsonProperty("uuids") List<UUID> uuids,
                @JsonProperty("strings") List<String> strings)
        {
            this.uuids = uuids;
            this.strings = strings;
        }

        @JsonProperty("uuids")
        public List<UUID> getUuids()
        {
            return uuids;
        }

        @JsonProperty("strings")
        public List<String> getStrings()
        {
            return strings;
        }
    }

    public ImmutableUuidSortedSet(Collection<String> values)
    {
        for (String value : values) {
            UUID uuid = parseAsUuid(value);
            if (uuid == null) {
                strings.add(value);
            }
            else {
                uuids.add(uuid);
            }
        }
    }

    public ImmutableUuidSortedSet(byte[] bytes)
            throws IOException
    {
        Stored stored = objectMapper.readValue(bytes, Stored.class);
        this.uuids.addAll(stored.getUuids());
        this.strings.addAll(stored.getStrings());
    }

    public byte[] toBytes()
            throws JsonProcessingException
    {
        Stored stored = new Stored(Arrays.asList(uuids.toArray(new UUID[uuids.size()])),
                Arrays.asList(strings.toArray(new String[strings.size()])));
        return objectMapper.writeValueAsBytes(stored);
    }

    @Override
    public int size()
    {
        return uuids.size() + strings.size();
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
