package algs.part02;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.HashMap;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HuffmanTests {

//    public static void main(String[] args) {
//        byte tempPrefix = 0;
//        tempPrefix += (byte) 20;
//        System.out.println(String.format("%02X", tempPrefix));
//        System.out.printf("%1s%n", Integer.toBinaryString(tempPrefix & 0xFF).replace(' ', '0'));
//        System.out.println(String.format("%1s%n", Integer.toBinaryString(tempPrefix & 0xFF)).replace(' ', '0'));
//    }

    @Test
    public void test() {
        HashMap<String, Long> map = new HashMap<>();

        for(long i = 0 ; i < 256 ; i++) {
            map.put("STR " + i, i);
        }

        HashMap<String, String> representationMap = Huffman.huffman(map);
        System.out.println("Finished");
    }

}
