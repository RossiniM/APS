package br.ufsc.ine5608.actor;

import br.ufsc.ine5608.model.Jogador;
import br.ufsc.ine5608.model.Operacao;
import br.ufsc.ine5608.model.Tabuleiro;
import br.ufsc.ine5608.shared.PosicaoTabuleiro;
import br.ufsc.ine5608.view.TelaPrincipal;
import br.ufsc.inf.leobr.cliente.Jogada;

import java.util.Objects;

public class AtorJogador {
    private AtorNetGames rede;
    private Tabuleiro tabuleiro;
    private TelaPrincipal telaPrincipal;

    public AtorJogador() {
        this.rede = new AtorNetGames(this);
        this.tabuleiro = new Tabuleiro();
        this.telaPrincipal = new TelaPrincipal("ButterFly", this);
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public boolean conectar() {
        if (Objects.nonNull(tabuleiro.getJogador()))
            return rede.conectar(tabuleiro.getJogador());
        return false;
    }

    public boolean desconectar() {
        Jogador jogador = tabuleiro.getJogador();
        if (Objects.nonNull(jogador))
            return rede.conectar(jogador);
        return false;
    }

    public void jogar() throws Exception {
        tabuleiro.realizarJogada();
        enviarJogada();
    }

    void receberJogada(Jogada jogada) {
        tabuleiro.tratarRecebimentoJogada((Operacao) jogada);
        telaPrincipal.recarregaLayout();
    }

    public void enviarJogada() {
        rede.enviarJogada(tabuleiro.criarJogada());
        telaPrincipal.recarregaLayout();
    }

    public void iniciarPartida() {
        rede.iniciarPartida();
    }

    void tratarInicioPartida(Integer posicaoJogador) {
        tabuleiro.tratarIniciarPartida(posicaoJogador, rede);
        if (tabuleiro.getJogador().getPosicao().equals(PosicaoTabuleiro.JOGADOR1))
            enviarJogada();
    }

    public boolean ehMinhaVez() {
        return tabuleiro.ehMinhaVez();
    }

    public boolean podeIniciarPartida() {
        return tabuleiro.podeIniciarPartida();
    }

    public void inicializa() {
        telaPrincipal.mostra();
    }
}
