package br.ufsc.ine5608.model;

import br.ufsc.ine5608.shared.PosicaoTabuleiro;

public class Jogador {

    private int pontuacao;
    private String nome;
    private PosicaoTabuleiro posicao;

    public Jogador(String nome) {
        this.nome = nome;
    }

    public PosicaoTabuleiro getPosicao() {
        return posicao;
    }

    public void setPosicao(PosicaoTabuleiro posicao) {
        this.posicao = posicao;
    }

    public String getNome() {
        return nome;
    }

}
