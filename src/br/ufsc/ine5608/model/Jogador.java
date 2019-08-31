package br.ufsc.ine5608.model;

import br.ufsc.ine5608.shared.PosicaoTabuleiro;

public class Jogador {

    private int pontuacao;
    private String nome;
    private Long tempoJogadas = 0L;
    private PosicaoTabuleiro posicao;
    private boolean conectado;

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

    public Long getTempoJogadas() {
        return tempoJogadas;
    }

    public void adicionaTempoJogada(Long tempo) {
        this.tempoJogadas += tempo;
    }
}
