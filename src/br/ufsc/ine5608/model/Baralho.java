package br.ufsc.ine5608.model;

import br.ufsc.ine5608.shared.DifficultyEnum;

import java.util.HashMap;

public  class CardDeck {

    private HashMap<Long, Card> cardDeck;


    public CardDeck(){

    }

    public CardDeck(HashMap<Long, Card> cardDeck) {
        this.cardDeck = cardDeck;
    }
}
