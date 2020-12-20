package com.example.cardgame.game;

import android.util.Log;

import com.example.cardgame.objects.Card;
import com.example.cardgame.objects.Deck;
import com.example.cardgame.objects.Player;
import com.example.cardgame.objects.Record;

public class GamaManage {

    private final String PLAYER_1 = "player 1";
    private final String PLAYER_2 = "player 2";

    private Deck deck;
    private Player player1, player2;
    private Card card1, card2;
    private int check;


    public GamaManage() {
        deck = new Deck();
        check = 0;
    }

    public GamaManage(String player_name1, String player_name2) {
        this();
        // check if name is null
        if (player_name1.length() == 0)
            player_name1 = PLAYER_1;
        if (player_name2.length() == 0)
            player_name2 = PLAYER_2;

        //set players name
        player1 = new Player(player_name1, 1);
        player2 = new Player(player_name2, 2);
    }

    public int getCheck() {
        return check;
    }

    public void increaseCheck() {
        this.check++;
    }

    public int getScore1() {
        return player1.getScore();
    }

    public int getScore2() {
        return player2.getScore();
    }

    public Card getCard1() {
        return card1;
    }

    public Card getCard2() {
        return card2;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    //get winner
    public Player getWinner() {
        if (player1.getScore() > player2.getScore()) {
            return player1;

        } else if (player1.getScore() < player2.getScore()) {
            return player2;

        } else {
            //draw
            Player draw = new Player("Draw", 0);
            draw.setScore(player1.getScore());
            return draw;
        }
    }

    // get two cards from deck
    public void getCards() {
        this.card1 = deck.getNextCard();
        this.card2 = deck.getNextCard();
    }

    //compare cards and get the biggest score
    public void getScore() {
        if (card1.compareTo(card2) > 0) {
            player1.increaseScore();
        }
        if (card1.compareTo(card2) < 0) {
            player2.increaseScore();
        }
    }

    public boolean playGame() {
        if (this.check != deck.DECK_SIZE / 2) {
            getCards();
            increaseCheck();
            getScore();
            return true;
        }
        return false;
    }

}
