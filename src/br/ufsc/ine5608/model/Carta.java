package br.ufsc.ine5608.model;

import br.ufsc.ine5608.shared.PosicaoTabuleiro;
import br.ufsc.inf.leobr.cliente.Jogada;

import java.awt.*;

import static br.ufsc.ine5608.shared.PosicaoTabuleiro.BARALHO;
import static br.ufsc.ine5608.shared.PosicaoTabuleiro.MESA;

public class Carta implements Comparable<Carta>, Jogada {

    private Long id;
    private Long numero;
    private PosicaoTabuleiro posicaoTabuleiro = BARALHO;
    private Color cor;

    public Carta(long id, Long numero, Color cor) {
        this.id = id;
        this.numero = numero;
        this.cor = cor;
    }

    public PosicaoTabuleiro getPosicaoTabuleiro() {
        return posicaoTabuleiro;
    }

    public void setPosicaoTabuleiro(PosicaoTabuleiro posicaoTabuleiro) {
        this.posicaoTabuleiro = posicaoTabuleiro;
    }

    public Long getId() {
        return id;
    }

    public Long getNumero() {
        return numero;
    }

    public Color getCorCartaEnum() {
        return cor;
    }

    @Override
    public int compareTo(Carta carta) {
        if (this.getPosicaoTabuleiro() != MESA && carta.getPosicaoTabuleiro() != MESA)
            return (int) (this.getNumero() - carta.getNumero());
        if (this.getPosicaoTabuleiro() == MESA)
            return 1;
        return -1;
    }
}
