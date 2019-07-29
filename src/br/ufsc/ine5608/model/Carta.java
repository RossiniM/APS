package br.ufsc.ine5608.model;

import br.ufsc.ine5608.shared.PosicaoTabuleiro;

import java.awt.*;

public class Carta implements Comparable<Carta> {

    private Long id;
    private Long number;
    private PosicaoTabuleiro posicaoTabuleiro = PosicaoTabuleiro.BARALHO;
    private Color cor;

    public Carta(long id, Long number, Color cor) {

        this.id = id;
        this.number = number;
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

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Color getCorCartaEnum() {
        return cor;
    }

    public void setCorCartaEnum(Color cor) {
        this.cor = cor;
    }

    @Override
    public int compareTo(Carta carta) {
        return (int) (this.getNumber() - carta.getNumber());
    }
}
