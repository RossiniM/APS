package br.ufsc.ine5608.model;

import br.ufsc.ine5608.actor.AtorJogador;
import br.ufsc.ine5608.actor.AtorNetGames;
import br.ufsc.ine5608.shared.PosicaoTabuleiro;

public class Jogador {

    private int pontuacao;
    private String nome;
    private PosicaoTabuleiro posicao;
    private boolean turno;
    private boolean conectado;
    private AtorNetGames atorNetGames;
    private AtorJogador mesaControlador;

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

    public void setConectado(boolean status) {
        conectado = status;
    }
}
