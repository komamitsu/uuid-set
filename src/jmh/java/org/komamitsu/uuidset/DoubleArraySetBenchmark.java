package org.komamitsu.uuidset;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.OperationsPerInvocation;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class DoubleArraySetBenchmark
    extends BaseSetBenchmark
{
    private DoubleArraySet set = new DoubleArraySet(strings);
    private byte[] bytes;

    @Setup
    public void setUp()
            throws IOException
    {
        bytes = set.toBytes();
    }

    @Benchmark
    @OperationsPerInvocation(OPERATIONS_PER_INVOCATION)
    public void contains() {
        for (int i = 0; i < strings.size(); i += SIZE / OPERATIONS_PER_INVOCATION) {
            set.contains(strings.get(i));
        }
    }

    @Benchmark
    public void toBytes()
            throws IOException
    {
        set.toBytes();
    }

    @Benchmark
    public void restoreFromBytes()
            throws IOException
    {
        new DoubleArraySet(bytes);
    }
}
