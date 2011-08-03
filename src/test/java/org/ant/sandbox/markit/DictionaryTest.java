package org.ant.sandbox.markit;

import junit.framework.TestCase;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DictionaryTest extends TestCase {

    public void testDictionary() throws InterruptedException, InstantiationException, IllegalAccessException {
        int repetitions = 10000;
        int readerThreads = Runtime.getRuntime().availableProcessors() * 2;
        System.out.println("Using " + readerThreads + " reader threads");
        long average = testDictionary(FastDictionary.class, readerThreads, repetitions);
        System.out.println("Fast dictionary average " + average / 1e9 + " sec");
        average = testDictionary(SlowDictionary.class, readerThreads, repetitions);
        System.out.println("Slow dictionary average " + average / 1e9 + " sec");
    }

    private long testDictionary(Class<? extends Dictionary> clazz, int readerThreads, int repetitions) throws InterruptedException, IllegalAccessException, InstantiationException {
        long average = 0;
        for (int i = 0; i < 220; i++) {
            final Dictionary d = clazz.newInstance();
            ExecutorService executor = Executors.newCachedThreadPool();
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
            if (i >= 20) {
                int j = i - 20;
                average = (duration + j * average) / (j + 1);
            }
        }
        return average;
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
