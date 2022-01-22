// Great reference cred:
// https://www.techiedelight.com/huffman-coding/

/**
 * NOTES:
 * - in huffman coding, no code is prefix of any other other code.
 * - total number of bits that required to encode a string: SUM {freq(i) * codelength(i)} i is a letter of the string.
 *
 */

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

// A Tree node
class Node
{
    Character ch;
    Integer freq;
    Node left = null, right = null;

    Node(Character ch, Integer freq)
    {
        this.ch = ch;
        this.freq = freq;
    }

    public Node(Character ch, Integer freq, Node left, Node right)
    {
        this.ch = ch;
        this.freq = freq;
        this.left = left;
        this.right = right;
    }
}

class Huffman {
    // Traverse the Huffman Tree and store Huffman Codes in a map.
    public static void encode(Node root, String str,
                              Map<Character, String> huffmanCode)
    {
        if (root == null) {
            return;
        }

        // Found a leaf node
        if (isLeaf(root)) {
            huffmanCode.put(root.ch, str.length() > 0 ? str : "1");
        }

        encode(root.left, str + '0', huffmanCode);
        encode(root.right, str + '1', huffmanCode);
    }

    // Traverse the Huffman Tree and decode the encoded string
    public static int decode(Node root, int index, StringBuilder sb)
    {
        if (root == null) {
            return index;
        }

        // Found a leaf node
        if (isLeaf(root))
        {
            System.out.print(root.ch);
            return index;
        }

        index++;

        root = (sb.charAt(index) == '0') ? root.left : root.right;

        // recur:
        index = decode(root, index, sb);
        return index;
    }

    // Utility function to check if Huffman Tree contains only a single node
    public static boolean isLeaf(Node root) {
        return root.left == null && root.right == null;
    }


    // string -> char/freq table -> tree
    // Builds Huffman Tree and decodes the given input text
    public static void buildHuffmanTree(String text)
    {
        // Base case: empty string
        if (text == null || text.length() == 0) {
            return;
        }

        // Count the frequency of appearance of each character
        // and store it in a map

        Map<Character, Integer> freq = new HashMap<>();
        for (char c: text.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }

        // create a priority queue to store live nodes of the Huffman tree.
        // Notice that the highest priority item has the lowest frequency

        PriorityQueue<Node> pq;
        pq = new PriorityQueue<>(Comparator.comparingInt(l -> l.freq));

        // create a leaf node for each character and add it
        // to the priority queue.

        for (var entry: freq.entrySet()) {
            pq.add(new Node(entry.getKey(), entry.getValue()));
        }

        // do till there is more than one node in the queue
        while (pq.size() != 1)
        {
            // Remove the two nodes of the highest priority
            // (the lowest frequency) from the queue

            Node left = pq.poll(); // first current minimum
            Node right = pq.poll(); // second current minimum

            // create a new internal node with these two nodes as children
            // and with a frequency equal to the sum of both nodes'
            // frequencies. Add the new node to the priority queue.

            int sum = left.freq + right.freq;
            pq.add(new Node(null, sum, left, right));
        }

        // `root` stores pointer to the root of Huffman Tree
        Node root = pq.peek();

        // Traverse the Huffman tree and store the Huffman codes in a map
        Map<Character, String> huffmanCode = new HashMap<>();
        encode(root, "", huffmanCode);

        // Print the Huffman codes
        System.out.println("Huffman Codes are: " + huffmanCode);
        System.out.println("The original string is: " + text);

        // Print encoded string
        StringBuilder sb = new StringBuilder();
        for (char c: text.toCharArray()) {
            sb.append(huffmanCode.get(c));
        }

        System.out.println("The encoded string is: " + sb);
        System.out.print("The decoded string is: ");

        if (isLeaf(root))
        {
            // Special case: For input like a, aa, aaa, etc.
            while (root.freq-- > 0) {
                System.out.print(root.ch);
            }
        }
        else {
            // Traverse the Huffman Tree again and this time,
            // decode the encoded string
            int index = -1;
            while (index < sb.length() - 1) {
                index = decode(root, index, sb);
            }
        }
    }

    // Huffman coding algorithm implementation in Java
    public static void main(String[] args)
    {
        String text = "Huffman coding is a data compression algorithm.";
        buildHuffmanTree(text);
    }
}

    // todo - Huffman

    /**
     * Data compression - we want to compress a data in such a
     * way that while decoding it, no loss of information occurs.
     * We assign var - length codes to input characters,
     * length of which depends on frequency of characters.
     * We want that the most frequent character get the smallest
     * code length, and the least frequent character gets the
     * longest coding.
     * The variable length codes assigned to input characters are
     * so-called Prefix code.
     */

        // step1: take the 2 chars with the lowest frequencies
        // step2: make a 2 leaf node tree from them. the node value will be the sum of them.
        // step3: remove these 2 chars from the hashmap (the lowest 2)
        // step4: add their mother node to the hashMap, as temp node with the sum of two of the leaves
        // step1: take again the 2 chars with the lowest keys from the hashMap, and connect their nodes by new mother
        //          node with the sum of both
        // step2: delete the 2 chars from the hashMap
        // step3: add their "mom" temp char with the "frequency" of their sum to the HashMap.
        //      ... again!

        // if the root val frequency is higher than 2 keys of the hashMap -
        //          crate a new tree where his leaves are the smallest freqs of the hashMap.
        // that little separate tree will connect later to the main tree.
        // if hashMap.size == 0:
        //      connect both of the subTrees by new temp null node with key of their sum.

        // note: all the new nodes are NULL nodes - containing "frequency" (Key), but not characters (values).





