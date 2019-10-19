package br.ufsc.ine5608.model;

import br.ufsc.inf.leobr.cliente.Jogada;

import java.util.Map;

public class Operacao implements Jogada {

    private Map<Long, Carta> cartas;

    private int pontuacaoAdversario;
    private int pontuacaoMax;

    Operacao(Map<Long, Carta> cartas, int pontuacaoAdversario, int pontuacaoMax) {
        this.cartas = cartas;
        this.pontuacaoAdversario = pontuacaoAdversario;
        this.pontuacaoMax = pontuacaoMax;
    }

    public int getPontuacaoAdversario() {
        return pontuacaoAdversario;
    }

    int getPontuacaoMax() {
        return pontuacaoMax;
    }

    Map<Long, Carta> getCartas() {
        return cartas;
    }
}
