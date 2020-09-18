package com.szxy.config.cycle;

public class CPerson2 {
    private CPerson1 cPerson1;

    public CPerson1 getcPerson1() {
        return cPerson1;
    }

    public void setcPerson1(CPerson1 cPerson1) {
        this.cPerson1 = cPerson1;
    }
}
