package com.szxy.config.cycle;

public class CPerson1 {

    private CPerson2 cPerson2;

    public CPerson2 getcPerson2() {
        return cPerson2;
    }

    public void setcPerson2(CPerson2 cPerson2) {
        this.cPerson2 = cPerson2;
    }
}
