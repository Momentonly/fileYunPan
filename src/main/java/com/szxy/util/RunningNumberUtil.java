package com.szxy.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author: cwx
 * @Date: 2020/11/12
 */
public class RunningNumberUtil {

    public static void main(String[] args) {
        new Thread(() -> {
            while (true){
                System.out.println(generateOrderNo());
            }
        }).start();
        new Thread(() -> {
            while (true){
                System.out.println(generateOrderNo());
            }
        }).start();
    }

    public static final String generateOrderNo() {
        return DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")
                .format(LocalDateTime.now());
    }
}
