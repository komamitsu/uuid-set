package org.komamitsu.uuidset;

import java.util.List;

public class BaseSetBenchmark
{
    protected static int SIZE = 100000;
    protected static final int OPERATIONS_PER_INVOCATION = 1000;
    protected List<String> strings = TestHelper.testData(SIZE, 0.98f);
}
