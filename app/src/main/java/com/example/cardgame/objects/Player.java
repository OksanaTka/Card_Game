package com.example.cardgame.objects;

public class Player  {
    private int id;
    private String name;
    private int score;

    public Player() {
    }

    public Player(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    public void increaseScore() {
        this.score++;
    }
}
