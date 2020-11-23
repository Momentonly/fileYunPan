package test.basic;

import org.junit.Test;

import java.util.HashMap;

/**
 * 精度类型测试
 *      double
 *      float
 *      BigDecimal
 */
public class PrecisionTest {

    @Test
    public void test1(){
        HashMap<Object, Object> map = new HashMap<>();
        System.out.println((String)map.get("test"));
    }

}
