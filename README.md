# CSE 321: Analysis and Design of Algorithms - Programming Assignment 02

`Weighted Activity Selection & Huffman's Algorithm Implementation`

This GitHub repository contains the source code and documentation for the second Programming Assignment related to the academic course CSE 321: Analysis and Design of Algorithms, offered from the department of Computer and Systems Engineering in Fall 2023.

---

This repository contains the Java implementation for two algorithms:

1. **Weighted Activity Selection** - A dynamic programming solution to solve the weighted activity selection problem efficiently.
2. **Huffman's Algorithm** - An implementation that allows compressing and decompressing arbitrary files using Huffman coding.

## Contents

- `algs.part01` - Java package containing the implementation for weighted activity selection.
- `algs.part02` - Java package containing the implementation for Huffman's algorithm.
- `activity_20010435.jar` - Runnable JAR file for weighted activity selection.
- `huffman_20010435.jar` - Runnable JAR file for Huffman's algorithm.

## Weighted Activity Selection

### How to Run

To execute the weighted activity selection algorithm, run the following command:

```bash
java -jar activity_20010435.jar absolute_path_to_input_file
```

The input file for the weighted activity selection algorithm should adhere to this format: 

- The first line indicates the number of activities.
- Subsequent lines for each activity should contain its start time, finish time, and weight, separated by spaces.

### Output

After execution, the algorithm generates a file in the same directory as the input file. This file contains the maximum possible weight achieved by selecting a mutually-compatible set of activities.

## Huffman's Algorithm

### **How to Run**

For compressing a file:

```bash
java -jar huffman_20010435.jar c absolute_path_to_input_file n
```
Replace n with the number of bytes considered together for compression.

For decompressing a file:

```bash
java -jar huffman_20010435.jar d absolute_path_to_input_file
```

---