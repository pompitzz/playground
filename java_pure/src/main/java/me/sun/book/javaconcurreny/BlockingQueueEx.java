package me.sun.book.javaconcurreny;

import lombok.RequiredArgsConstructor;

import java.io.File;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingQueueEx {

    public static void main(String[] args) {
        BlockingQueue<File> fileBlockingQueue = new LinkedBlockingQueue<>(10);
        new Thread(new FileCrawler(fileBlockingQueue, new File("/Users/dongmyeonglee/Projects/java-nol"))).start();
        new Thread(new FilePrinter(fileBlockingQueue)).start();
    }

    @RequiredArgsConstructor
    static class FileCrawler implements Runnable {
        private final BlockingQueue<File> fileBlockingQueue;
        private final File root;

        @Override
        public void run() {
            try {
                crawl(root);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        private void crawl(File file) throws InterruptedException {
            File[] files = Optional.ofNullable(file.listFiles()).orElse(new File[]{});
            for (File eachFile : files) {
                if (eachFile.isDirectory()) {
                    crawl(eachFile);
                } else {
                    fileBlockingQueue.put(eachFile);
                }
            }
        }
    }

    @RequiredArgsConstructor
    static class FilePrinter implements Runnable {
        private final BlockingQueue<File> fileBlockingQueue;

        @Override
        public void run() {
            try {
                while (true) {
                    File file = fileBlockingQueue.take();
                    System.out.println("file: " + file.getName());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
