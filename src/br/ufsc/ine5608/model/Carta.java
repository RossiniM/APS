package br.ufsc.ine5608.model;

import br.ufsc.ine5608.shared.PosicaoTabuleiro;

import java.awt.*;

public class Carta implements Comparable<Carta> {

    private Long id;
    private Long numero;
    private PosicaoTabuleiro posicaoTabuleiro = PosicaoTabuleiro.BARALHO;
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

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public Color getCorCartaEnum() {
        return cor;
    }

    public void setCorCartaEnum(Color cor) {
        this.cor = cor;
    }

    @Override
    public int compareTo(Carta carta) {
        return (int) (this.getNumero() - carta.getNumero());
    }
}
