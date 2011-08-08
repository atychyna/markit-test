package org.ant.sandbox.markit.dictionary;

import org.testng.annotations.Test;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;
import static org.testng.Assert.assertTrue;

@Test
public class DictionariesTest {

    public void testDictionary() throws InterruptedException, InstantiationException, IllegalAccessException {
        int repetitions = 1000;
        int readerThreads = Runtime.getRuntime().availableProcessors() * 2;
        System.out.println(format("Using %d reader threads", readerThreads));
        long fastDictionaryTime = testDictionary(FastDictionary.class, readerThreads, repetitions);
        System.out.println(format("Fast dictionary time %.5f sec.", fastDictionaryTime / 1e9));
        long slowDictionaryTime = testDictionary(SlowDictionary.class, readerThreads, repetitions);
        System.out.println(format("Slow dictionary time %.5f sec.", slowDictionaryTime / 1e9));
        // fingers crossed ;)
        assertTrue(fastDictionaryTime < slowDictionaryTime);
    }

    /**
     * Poor man's microbenchmarking. This method will simulate concurrent access to dictionary by adding new words
     * to it while <code>readerThreads</code> number of threads are simultaneously reading from it.
     */
    private long testDictionary(Class<? extends Dictionary> clazz, int readerThreads, int repetitions) throws InterruptedException, IllegalAccessException, InstantiationException {
        int cycles = 200;
        int warmupCycles = 20;
        long averageTime = 0;
        for (int i = 0; i < cycles + warmupCycles; i++) {
            final Dictionary d = clazz.newInstance();
            ExecutorService executor = Executors.newCachedThreadPool();
            // using barrier to start reading and writing simultaneously
            final CyclicBarrier b = new CyclicBarrier(readerThreads + 1);
            System.gc();
            long start = System.nanoTime();
            executor.submit(new CreateNewTranslationsWorker(d, repetitions, b));
            for (int j = 0; j < readerThreads; j++) {
                executor.submit(new TranslateSpecificWordWorker(d, repetitions, b));
            }
            executor.shutdown();
            executor.awaitTermination(60, TimeUnit.SECONDS);
            long duration = System.nanoTime() - start;
            // allow for a little warmup before we start counting time
            if (i >= warmupCycles) {
                int j = i - warmupCycles;
                // calculation moving averageTime
                averageTime = (duration + j * averageTime) / (j + 1);
            }
        }
        return averageTime;
    }

    private static class TranslateSpecificWordWorker implements Runnable {
        private final Dictionary d;
        private final int repetitionCount;
        private final CyclicBarrier b;

        public TranslateSpecificWordWorker(Dictionary d, int repetitionCount, CyclicBarrier b) {
            this.d = d;
            this.repetitionCount = repetitionCount;
            this.b = b;
        }

        public void run() {
            awaitOnBarrier(b);
            for (int j = 0; j < repetitionCount; j++) {
                try {
                    d.translate("word0");
                } catch (IllegalArgumentException e) {
                    // do nothing
                }
            }
        }
    }

    private static class CreateNewTranslationsWorker implements Runnable {
        private final Dictionary d;
        private final int repetitionCount;
        private final CyclicBarrier b;

        public CreateNewTranslationsWorker(Dictionary d, int repetitionCount, CyclicBarrier b) {
            this.d = d;
            this.repetitionCount = repetitionCount;
            this.b = b;
        }

        public void run() {
            awaitOnBarrier(b);
            for (int j = 0; j < repetitionCount; j++) {
                d.addToDictionary("word" + j, "translation" + j);
            }
        }

    }

    private static void awaitOnBarrier(CyclicBarrier b) {
        try {
            b.await();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
