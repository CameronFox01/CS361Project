package EntryPoints;

import DataStructures.*;

import java.util.Random;


public class Test {
    public static void main(String[] args) {
        MinHeap<Integer> heap = new MinHeap<>();

        Random random = new Random();
        for (int i = 0; i < 25; i++) {
            heap.add(random.nextInt());
        }


        while (!heap.isEmpty()) {
            System.out.println(heap.poll());
        }
    }
}
