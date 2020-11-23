package com.example.cardgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;


public class Deck {
    private Map<String, Integer> deck;
    private List cardsList;
    private ListIterator<String> it_cardsList;
    public static final int DECK_SIZE = 52;

    public Deck() {
        this.deck = new <String, Integer>HashMap();
        createDeck();
        shuffleCards();
    }

    // add all the cards to deck
    // key- img name in drawable directory
    // value- card value
    public void createDeck() {
        int cardValue = 2;
        String img_name;
        for (int i = 1; i <= DECK_SIZE; i++) {
            img_name = "drawable/" + "poker_" + i;
            addCard(img_name, cardValue);
            //cards with the same number have the same value
            if ((i % 4) == 0)
                cardValue++;
        }
    }


    public void shuffleCards() {
        //init cards_name
        this.cardsList = new ArrayList(deck.keySet());
        //randomly replace elements in cards_name
        Collections.shuffle(this.cardsList);
        // make cards_name iterable
        this.it_cardsList = cardsList.listIterator();
    }



    // add card to deck
    public void addCard(String img_name, int value) {
        this.deck.put(img_name, value);
    }

    // search card value in deck by card name
    public int getCardValue(String key) {
        return deck.get(key);
    }

    //compare values
    public int compareCards(String card1, String card2) {
        if (getCardValue(card1) > getCardValue(card2))
            return 1;
        else if (getCardValue(card1) < getCardValue(card2))
            return -1;
        else
            return 0;
    }

    // get next card name
    public String getNextCard() {
        if (it_cardsList.hasNext())
            return this.it_cardsList.next();
        return null;
    }

}

