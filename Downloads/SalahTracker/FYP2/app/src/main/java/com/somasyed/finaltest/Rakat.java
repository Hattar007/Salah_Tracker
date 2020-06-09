package com.somasyed.finaltest;

public class Rakat
{
    public int rakatno;
    public String namaz;
    public int standing1time;
    public int standing2time;
    public int bowingtime;
    public int prostration1time;
    public int prostration2time;
    public int sitting1time;
    public int sitting2time;


    public Rakat() {
        this.rakatno = 1;
        this.namaz = "";
        this.standing1time = 0;
        this.standing2time = 0;
        this.bowingtime = 0;
        this.prostration1time = 0;
        this.prostration2time = 0;
        this.sitting1time = 0;
        this.sitting2time = 0;
    }

    public Rakat(int rakatno, String namaz, int standing1time, int standing2time, int bowingtime, int prostration1time, int prostration2time, int sitting1time, int sitting2time) {
        this.rakatno = rakatno;
        this.namaz = namaz;
        this.standing1time = standing1time;
        this.standing2time = standing2time;
        this.bowingtime = bowingtime;
        this.prostration1time = prostration1time;
        this.prostration2time = prostration2time;
        this.sitting1time = sitting1time;
        this.sitting2time = sitting2time;
    }
}
