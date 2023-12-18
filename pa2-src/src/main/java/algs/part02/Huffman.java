package algs.part02;

import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.HashMap;

public class Huffman {

    private static final int numberOfChildren = 255;

    public static HashMap<String, String> huffman(HashMap<String, Long> freqMap) {

        /*
         * Pseudocode:
         *
         * Input: Frequency Map of each  n bytes in hexadecimal to their occurrences count.
         *
         * Create a PQ of HuffmanTreeNode
         * Loop while PQ.size() > 1
         *      pop the least 255 (or smaller if PQ.size() < 255) nodes
         *      Create a new node with those nodes as its children and compute its new frequency.
         *      Insert the new node
         *
         * Convert the Huffman Tree to an equivalent map <String, String>
         *                              (those strings are bytes hexadecimal representation).
         *
         */

        // Initialize the priority queue data structure.
        PriorityQueue<HuffmanTreeNode> pq = new PriorityQueue<>(Comparator.comparingLong(HuffmanTreeNode::getFreq));

        // Copy the freqMap data to the pq, by creating a `HuffmanTreeNode` for each key.
        for (Map.Entry<String, Long> freqMapEntry : freqMap.entrySet()) {
            pq.add(new HuffmanTreeNode(freqMapEntry.getKey(), freqMapEntry.getValue()));
        }

        // Loop while pq.size() > 1: pop() -> create new node -> add()
        while(pq.size() > 1) {

            HuffmanTreeNode newNode = new HuffmanTreeNode();
            HuffmanTreeNode[] tempNodes = new HuffmanTreeNode[Math.min(numberOfChildren, pq.size())];

            for (int i = 0 ; i < tempNodes.length ; i++) {
                tempNodes[i] = pq.poll();
                newNode.updateFreq(tempNodes[i].getFreq());
            }
            newNode.setChildren(tempNodes);

            pq.add(newNode);
        }

        return huffmanTreeNodeToRepresentationMap(pq.peek(), null, null);
    }

    private static HashMap<String, String> huffmanTreeNodeToRepresentationMap(HuffmanTreeNode huffmanTreeRoot,
                                                                              StringBuilder prefix,
                                                                              HashMap<String, String> representationMap) {
        // First call
        if (representationMap == null) {
            representationMap = new HashMap<>();
        }
        if (prefix == null) {
            prefix = new StringBuilder();
        }

        // Reached the leaf nodes (base case)
        if (huffmanTreeRoot.getChildren() == null) {
            representationMap.put(huffmanTreeRoot.getContent(), prefix.toString());
            return representationMap;
        }

        // Iterate over the children
        byte tempPrefix = 0;

        for (HuffmanTreeNode childNode : huffmanTreeRoot.getChildren()) {

            StringBuilder newPrefix = new StringBuilder(prefix.toString());
            if (numberOfChildren == 255)
                newPrefix.append(String.format("%02X", tempPrefix));
            else if (numberOfChildren == 2)
                newPrefix.append(String.format("%1s", Integer.toBinaryString(tempPrefix & 0xFF)).replace(' ', '0'));

            huffmanTreeNodeToRepresentationMap(childNode, newPrefix, representationMap);

            tempPrefix += 1;

        }

        return representationMap;
    }
}

class HuffmanTreeNode {
    private String content;
    private Long freq;
    private HuffmanTreeNode[] children;

    public HuffmanTreeNode() {
        this.content = null;
        this.freq = 0L;
        this.children = null;
    }

    public HuffmanTreeNode(String content, Long freq) {
        this.content = content;
        this.freq = freq;
        this.children = null;
    }

    public HuffmanTreeNode(Long freq) {
        this.content = null;
        this.freq = freq;
        this.children = null;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getFreq() {
        return freq;
    }

    public void setFreq(Long freq) {
        this.freq = freq;
    }

    public HuffmanTreeNode[] getChildren() {
        return children;
    }

    public void setChildren(HuffmanTreeNode[] children) {
        if(children.length > 255)
            throw new RuntimeException("Huffman Tree Node can't have more than 255 children.");

        this.children = new HuffmanTreeNode[children.length];
        System.arraycopy(children, 0, this.children, 0, children.length);
    }

    public void updateFreq(Long addedFreq) {
        this.freq += addedFreq;
    }
}
