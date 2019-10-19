package br.ufsc.ine5608.model;

import br.ufsc.ine5608.shared.PosicaoTabuleiro;

public class Jogador {

    private int pontuacao = 0;
    private String nome;
    private PosicaoTabuleiro posicao;

    public Jogador(String nome) {
        this.nome = nome;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
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
