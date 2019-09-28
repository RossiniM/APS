package br.ufsc.ine5608.model;

import br.ufsc.inf.leobr.cliente.Jogada;

import java.util.Map;

public class Operacao implements Jogada {

    private Map<Long, Carta> cartas;


    Operacao(Map<Long, Carta> cartas) {
        this.cartas = cartas;
    }

    Map<Long, Carta> getCartas() {
        return cartas;
    }
}
