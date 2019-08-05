package br.ufsc.ine5608.model;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Baralho {

    private HashMap<Long, Carta> cartas = new HashMap<>();


    public Baralho() {

    }

    public Baralho(HashMap<Long, Carta> cardDeck) {
        this.cartas = cardDeck;
    }

    public void adicionaCarta(Carta carta) {
        cartas.put(carta.getId(), carta);
    }

    public HashMap<Long, Carta> getCartas() {
        return cartas;
    }

    public List<Carta> retornaCartas() {
        return new ArrayList<>(cartas.values());
    }

    public Carta getCartaPorId(long id) {

        if (Objects.nonNull(id))
            if (cartas.containsKey(id))
                return cartas.get(id);
        return null;
    }

    public Long procuraIDCarta(Color cor, String numero) {
        return cartas.values()
                .stream()
                .filter(carta -> verificaCarta(carta, cor, Long.parseLong(numero)))
                .findFirst().map(Carta::getId).orElse(null);
    }

    private boolean verificaCarta(Carta carta, Color cor, long numero) {
        return carta.getCorCartaEnum() == cor && carta.getNumero().equals(numero);
    }

}
