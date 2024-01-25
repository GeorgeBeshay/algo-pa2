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

Considering part 02 of the assignment, we were asked to provide a comprehensive Java implementation of Huffman encoding, which is a fundamental algorithm used in file compression. Huffman encoding efficiently represents data by assigning variable-length codes to input bytes based on their frequencies. These codes, known as Huffman codes (will be referred to hc), enable optimal compression, reducing the overall file size.

The procedure of compressing and decompressing files essentially depends on a set of classes, where each one of them encapsulates a set of methods and functionalities that are related to a specific part of the algorithm, for e.g.: `BytesManipulator`, `Metadata`, `Utilities`, `ProFileReader`, `ProFileWriter`, `Huffman`, `Compressor`, `Decompressor`, `Analysis`, `Main`, `Logger` classes. In the following section a brief illustration for each class and its intended functionalities will be mentioned.

### Program Classes

* The `BytesManipulator` class provides a suite of methods tailored for byte manipulation and conversion, crucial for handling data during file compression and decompression processes. This class encapsulates functionalities like updating frequency maps based on byte representations, converting bytes to hexadecimal strings and vice versa, and transforming bytes to binary strings. Additionally, it offers operations for converting integers and longs to byte arrays and extracting integer and long values from byte arrays. These utilities aid in encoding, decoding, and managing byte-level data essential for efficient file compression using Huffman encoding algorithms.

* The `Utilities` class streamlines file path management and data alignment for compression. It creates new file paths for compressed and extracted files, utilizing the original file path. Additionally, it calculates padding bits to ensure proper byte alignment, vital for data integrity during compression and decompression.

* The `ProFileReader` class serves as a high-performance file reader, designed particularly for handling massive files. It efficiently reads these files in chunks (parts), minimizing memory overhead by utilizing a buffer system. The class dynamically adjusts buffer size based on the specified n value to optimize file processing. It provides methods for reading the file in parts, ensuring smooth and effective handling of large-scale data. This is especially crucial when dealing with extensive datasets or huge files, ensuring a streamlined approach to file reading and manipulation.

* The `ProFileWriter` class manages the writing of large files by handling them in manageable chunks (parts), optimizing the write process and memory usage. It uses a buffered approach, dynamically adjusting the buffer size based on specified parameters for efficient file handling. This enables smooth writing operations, particularly useful when dealing with extensive datasets or massive files, ensuring reliable and optimized file writing while preventing system overload.

* The `Metadata` class manages the metadata generation, writing, and extraction processes essential for the compression and decompression operations. It creates metadata based on frequency and representation maps, enabling accurate file reconstruction post-compression. This class also facilitates reading and extracting metadata, playing a crucial role in the compression and decompression workflow. Additionally, it contains methods for handling and computing metadata, allowing seamless integration into the compression and decompression procedures.

* The `Compressor` class orchestrates the file compression process using Huffman encoding. It encompasses methods to handle file compression, including reading and updating frequency maps, generating metadata, and compressing file parts using Huffman encoding. The compression workflow involves scanning the file, updating frequency maps, applying Huffman encoding, generating metadata, and finally writing the compressed data to a new file. The class efficiently manages the step-by-step compression process, utilizing utility methods and relevant data structures to execute compression operations seamlessly.

* The `Decompressor` class manages the decompression of files (only those ones that were created by utilizing Huffman's algorithm). Its functionality involves reading a compressed file, extracting the metadata (representation map) and additional important attributes (paddingBits, Metadata size, etc..), and then converting the compressed data back to its original form. It handles the process of mapping encoded sequences to their corresponding bytes, effectively restoring the original file from its compressed version. The class orchestrates the step-by-step decompression procedure, ensuring the accurate conversion of encoded data back to its original representation.

* The `Analysis` class assesses how different chunk sizes affect the compression of specified files. It measures the time taken and the compression ratio for various chunk sizes (n). This evaluation helps understand how the chunk size impacts both the compression speed and the resulting file size after compression.

* The `Main` class (which is the entry point specified within the jar) handles file input and output operations, facilitating compression and decompression based on command-line arguments. It validates the input arguments to ensure proper instructions for compression or decompression. For compression, it takes the operation indicator, input file path, and n (number of bytes that to be encoded together) as arguments (c absoluteFilePath n). For decompression, it accepts the operation indicator and the compressed file path (d absoluteCompressedFilePath).

### Compressed File Architecture

```
                            START OF COMPRESSED FILE
                                START OF METADATA
metadataSizeInBytes
mapSizeInEntriesCount
n
paddingBits
                    START OF REPRESENTATION MAP ENTRIES <K, V>
hcSizeInBytes hcPaddingInBits hc rc
..
..
hcSizeInBytes hcPaddingInBits rcSizeInBytes hc rc
                                END OF METADATA
                            START OF FILE ACTUAL DATA
b0 b1 .. b          // notice that b0 contains paddingBits to be neglected.

                            END OF FILE ACTUAL DATA
                            END OF COMPRESSED FILE
```

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