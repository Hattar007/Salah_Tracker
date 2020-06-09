package com.somasyed.finaltest;

public class UserData {

    private String Namaz;
    private String Position;
    private int Time;
    private int Rakat;

    public String getUserid() {
        return Userid;
    }

    public void setUserid(String userid) {
        Userid = userid;
    }

    private String Userid;


    public UserData(String namaz) {
        Namaz = namaz;
    }

    public String getNamaz() {
        return Namaz;
    }

    public void setNamaz(String namaz) {
        Namaz = namaz;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public int getTime() {
        return Time;
    }

    public void setTime(int time) {
        Time = time;
    }

    public int getRakat() {
        return Rakat;
    }

    public void setRakat(int rakat) {
        Rakat = rakat;
    }



    public UserData() {
    }

    public UserData(String namaz, String position, int time, int rakat, String userid) {
        Namaz = namaz;
        Position = position;
        Time = time;
        Rakat = rakat;
        Userid=userid;
    }

}
