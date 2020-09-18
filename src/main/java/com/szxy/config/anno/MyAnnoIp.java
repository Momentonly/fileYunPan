package com.szxy.config.anno;

public class MyAnnoIp {

    private String ip;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "MyAnnoIp{" +
                "ip='" + ip + '\'' +
                '}';
    }
}
