package com.example.developmenttaskeyge0001frgesport.Modell;

public class Fraga {

    public String fraga,val1,val2,val3,val4,svar;

    public Fraga(String fraga, String val1, String val2, String val3, String val4, String svar) {
        this.fraga = fraga;
        this.val1 = val1;
        this.val2 = val2;
        this.val3 = val3;
        this.val4 = val4;
        this.svar = svar;
    }

    // här har vi child referensen som finns i Realtime Database
    public Fraga(){}

    //frågorna hämtas
    public String getFraga() {
        return fraga;
    }

    public void setFraga(String fraga) {
        this.fraga = fraga;
    }

    //valen hämtas
    public String getVal1() {
        return val1;
    }

    public void setVal1(String val1) {
        this.val1 = val1;
    }

    public String getVal2() {
        return val2;
    }

    public void setVal2(String val2) {
        this.val2 = val2;
    }

    public String getVal3() {
        return val3;
    }

    public void setVal3(String val3) {
        this.val3 = val3;
    }

    public String getVal4() {
        return val4;
    }

    public void setVal4(String val4) {
        this.val4 = val4;
    }
    // svaren hämtas
    public String getSvar() {
        return svar;
    }

    public void setSvar(String svar) {
        this.svar = svar;
    }
}
