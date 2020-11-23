package test.basic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

/**
 * StackOverflowError
 *   栈内存溢出
 *       1.函数递归调用层次过深
 *       2.局部静态变量体积太大
 *   StackOverflowError在程序栈空间耗尽时抛出，通常是深度递归导致。
 */
public class StackOverflowErrorTest {

    public static void main(String[] args) {
        HashMap<Object, Object> map = new HashMap<>();
        map.put("test", map);
        try {
            // 序列化递归造成栈内存溢出
            String s = new ObjectMapper().writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
