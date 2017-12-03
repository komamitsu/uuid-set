package org.komamitsu.uuidset;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.OperationsPerInvocation;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class ImmutableUuidHashSetBenchmark
{
    private static int SIZE = 100000;
    private static final int OPERATIONS_PER_INVOCATION = 1000;
    private List<String> strings = TestHelper.testData(SIZE, 0.98f);
    private ImmutableUuidHashSet hashSet = new ImmutableUuidHashSet(strings);
    private byte[] bytes;

    @Setup
    public void setUp()
            throws JsonProcessingException
    {
        bytes = hashSet.toBytes();
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
        hashSet.toBytes();
    }

    @Benchmark
    public void restoreFromBytes()
            throws IOException
    {
        new ImmutableUuidHashSet(bytes);
    }
}
