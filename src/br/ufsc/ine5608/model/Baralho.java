package br.ufsc.ine5608.model;

import java.util.HashMap;

public  class Baralho {

    private HashMap<Long, Carta> cardDeck;


    public Baralho(){

    }

    public Baralho(HashMap<Long, Carta> cardDeck) {
        this.cardDeck = cardDeck;
    }
}
