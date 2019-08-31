package br.ufsc.ine5608.model;

import br.ufsc.inf.leobr.cliente.Jogada;

import java.util.Map;

public class Operacao implements Jogada {

    private Map<Long, Carta> cartas;
    private Map<Long, Carta> cartasLivres;
    private Long tempoDecorrido;

    public Operacao(Map<Long, Carta> cartas, Map<Long, Carta> cartasLivres,Long tempoDecorrido) {
        this.cartas = cartas;
        this.cartasLivres = cartasLivres;
        this.tempoDecorrido = tempoDecorrido;
    }

    public Long getTempoDecorrido() {
        return tempoDecorrido;
    }

    public Map<Long, Carta> getCartas() {
        return cartas;
    }

    public Map<Long, Carta> getCartasLivres() {
        return cartasLivres;
    }
}
