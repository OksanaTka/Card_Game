package com.example.cardgame.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;


public class Deck {

    public static final int DECK_SIZE = 52;
    private final List<Card> card_deck;
    private ListIterator<Card> it_deck;

    public Deck() {
        card_deck = new ArrayList<Card>();
        createDeck();
        shuffleCards();
        makeIterable();
    }


    public void createDeck() {
        int cardValue = 2;
        String img_name;
        for (int i = 1; i <= DECK_SIZE; i++) {
            img_name = "drawable/" + "img_card_" + i;
            addCard(img_name, cardValue, i);
            //cards with the same number have the same value
            if ((i % 4) == 0)
                cardValue++;
        }
    }

    public void shuffleCards() {
        //randomly replace elements in cards_name
        Collections.shuffle(this.card_deck);
    }

    private void makeIterable() {
        // make cards_name iterable
        this.it_deck = card_deck.listIterator();
    }


    // add card to deck
    public void addCard(String img_name, int value, int id) {
        this.card_deck.add(new Card(img_name, value, id));
    }


    // get next card
    public Card getNextCard() {
        if (it_deck.hasNext())
            return this.it_deck.next();
        return null;
    }

}

