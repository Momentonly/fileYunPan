package test.basic;

import org.junit.Test;

import java.io.UnsupportedEncodingException;

/**
 * 编码测试
 */
public class UnicodeTest {

    /**
     * unicode
     */
    @Test
    public void test1() throws UnsupportedEncodingException {
        char z = '中';
        System.out.println(z);
        String s = "中";
        char c1 = s.charAt(0);
        System.out.println(c1);

        byte[] b_gbk = "中".getBytes("GBK");
        byte[] bytes = "中".getBytes("utf-8");
        String s_utf8 = new String(b_gbk,"UTF-8");
        System.out.println(s_utf8);  //��  导致乱码
    }

}
