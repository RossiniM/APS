package br.ufsc.ine5608.controller;

import br.ufsc.ine5608.model.Card;
import br.ufsc.ine5608.model.CardDeck;
import br.ufsc.ine5608.model.Player;

import java.util.ArrayList;
import java.util.List;

public class TableController {
    private static TableController ourInstance = new TableController();

    public static TableController getInstance() {
        return ourInstance;
    }

    private TableController() {
    }

    private List<Player> players = new ArrayList<>();
    private CardDeck mainDeck = new CardDeck();
    private CardDeck visibleDecl = new CardDeck();

    private void prepare(){
        //loadSettings
        //loadDeck
        // load players
        //define turn
    }

}
