package com.example.cardgame.objects;

public class Card {

    private int card_value;
    private int id;
    private String card_name;

    public Card() {
        card_name = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Card(String card_name, int card_value, int id) {
        this.card_value = card_value;
        this.card_name = card_name;
        this.id = id;
    }

    public int getCard_value() {
        return card_value;
    }

    public void setCard_value(int card_value) {
        this.card_value = card_value;
    }

    public String getCard_name() {
        return card_name;
    }

    public void setCard_name(String card_name) {
        this.card_name = card_name;
    }

    //compare values
    public int compareTo(Card c) {
        if (this.card_value > c.card_value)
            return 1;
        else if (this.card_value < c.card_value)
            return -1;
        else
            return 0;
    }
}
