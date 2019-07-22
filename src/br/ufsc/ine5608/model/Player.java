package br.ufsc.ine5608.model;

public class Player {

    private int score;
    private CardDeck cardDeck;
    private String name;
    private boolean turn;

    public Player(String name, CardDeck cardDeck) {
        this.name = name;
        this.cardDeck = cardDeck;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
