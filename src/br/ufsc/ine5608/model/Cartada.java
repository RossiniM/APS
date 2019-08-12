package br.ufsc.ine5608.model;

import br.ufsc.inf.leobr.cliente.Jogada;

import java.util.HashMap;
import java.util.Map;

public class Cartada implements Jogada {

    private Map<Long, Carta> cartas = new HashMap<>();
    private Map<Long, Carta> cartasLivres = new HashMap<>();

    public Cartada(Map<Long, Carta> cartas, Map<Long, Carta> cartasLivres) {
        this.cartas = cartas;
        this.cartasLivres = cartasLivres;
    }

    public Map<Long, Carta> getCartas() {
        return cartas;
    }

    public Map<Long, Carta> getCartasLivres() {
        return cartasLivres;
    }
}
