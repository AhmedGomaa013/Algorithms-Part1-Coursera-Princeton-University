import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int k = -1;
        if (args.length == 1) {
            k = Integer.parseInt(args[0]);
        }
        if (k == 0) k = -1;
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        int count = 0;
        while (!StdIn.isEmpty()) {
            if (k != -1 && count == k) break;
            queue.enqueue(StdIn.readString());
            count++;
        }

        for (String s : queue) {
            StdOut.println(s);
        }
    }
}
