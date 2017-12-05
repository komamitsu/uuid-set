package org.komamitsu.uuidset;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.OperationsPerInvocation;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class HashSetBenchmark
    extends BaseSetBenchmark
{
    private HashSet<String> hashSet = new HashSet<>(strings);
    private byte[] bytes;

    @Setup
    public void setUp()
            throws JsonProcessingException
    {
        bytes = getBytesFromSet(hashSet);
    }

    private byte[] getBytesFromSet(Set<String> set)
    {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (String aSet : set) {
            if (isFirst) {
                isFirst = false;
            }
            else {
                sb.append(',');
            }
            sb.append(aSet);
        }

        return sb.toString().getBytes();
    }

    @Benchmark
    @OperationsPerInvocation(OPERATIONS_PER_INVOCATION)
    public void contains() {
        for (int i = 0; i < strings.size(); i += SIZE / OPERATIONS_PER_INVOCATION) {
            hashSet.contains(strings.get(i));
        }
    }

    @Benchmark
    public void toBytes()
            throws JsonProcessingException
    {
        getBytesFromSet(hashSet);
    }

    @Benchmark
    public void restoreFromBytes()
            throws IOException
    {
        String s = new String(bytes);
        String[] strings = s.split(",");
        new HashSet<>(Arrays.asList(strings));
    }
}
