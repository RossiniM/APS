package br.ufsc.ine5608.controller;

public class CardController {
    private static CardController ourInstance = new CardController();

    public static CardController getInstance() {
        return ourInstance;
    }

    private CardController() {
    }
}
