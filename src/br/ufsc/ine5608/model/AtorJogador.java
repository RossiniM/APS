package br.ufsc.ine5608.model;

import br.ufsc.ine5608.controller.MesaControlador;
import br.ufsc.ine5608.shared.PosicaoTabuleiro;

public class AtorJogador {

    private int pontuacao;
    private String nome;
    private PosicaoTabuleiro posicao;
    private boolean turno;
    private boolean conectado;
    private AtorNetGames atorNetGames;
    private MesaControlador mesaControlador;

    public AtorJogador(String nome) {
        this.nome = nome;

    }

    public PosicaoTabuleiro getPosicao() {
        return posicao;
    }

    public void setPosicao(PosicaoTabuleiro posicao) {
        this.posicao = posicao;
    }


    public boolean iniciarPartida() {
        if (!atorNetGames.isConectado()) return false;
        atorNetGames.iniciarPartida();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    public String getNome() {
        return nome;
    }

    public void setConectado(boolean status) {
        conectado = status;
    }
}
