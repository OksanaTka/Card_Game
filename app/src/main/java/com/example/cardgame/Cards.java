package com.example.cardgame;

import android.graphics.drawable.Drawable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Cards {

    private Map<Integer, Integer> cards;
    private boolean[] deck;

    public Cards() {
        this.cards = new <Integer, Integer>HashMap();
        deck = new boolean[53];
        //initialize deck with FALSE
        Arrays.fill(this.deck, false);
    }


    public void addCard(int xml, int value) {
        this.cards.put(xml, value);
    }

    public int getCardValue(int key) {
        return cards.get(key);
    }

    public boolean checkCard(int id) {
        // check if card already used
        if (this.deck[id] == false)
            return false;
        return true;
    }

    public void setDeck(int id) {
        this.deck[id] = true;
    }

    public int compareCards(int card1, int card2) {
        if (getCardValue(card1) > getCardValue(card2))
            return 1;
        else if (getCardValue(card1) < getCardValue(card2))
            return -1;
        else
            return 0;
    }
}

