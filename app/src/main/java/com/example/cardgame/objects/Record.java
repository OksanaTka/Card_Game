package com.example.cardgame.objects;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Record implements Comparable<Record>, Serializable {
    private int id = 0;
    private String name = "";
    private String date = " 0";
    private int score = 0;
    private double lat;
    private double lng;


    public Record() {
    }

    public Record(int id, String name, int score, double lat, double lng) {
        this.id = id;
        this.name = name;
        this.score = score;
        this.lat = lat;
        this.lng = lng;
        setDate();
    }

    public Record(String name, int score, String date) {
        this.name = name;
        this.date = date;
        this.score = score;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getName() {
        return name;
    }

    public Record setName(String name) {
        this.name = name;
        return this;
    }

    public String getDate() {
        return date;
    }


    public int getScore() {
        return score;
    }

    public Record setScore(int score) {
        this.score = score;
        return this;
    }

    private void setDate() {
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm");
        date = df.format(Calendar.getInstance().getTime());
    }

    // compare by score
    @Override
    public int compareTo(Record record) {
        return this.score > record.score ? -1 : this.score < record.score ? 1 : 0;
    }

    @Override
    public String toString() {
        return name +
                "   Score: " + score +
                "   " + date;

    }
}
