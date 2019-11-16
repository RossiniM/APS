package br.ufsc.ine5608.actor;

import br.ufsc.ine5608.model.Operacao;
import br.ufsc.ine5608.model.Tabuleiro;
import br.ufsc.ine5608.shared.Mensagens;
import br.ufsc.ine5608.shared.PosicaoTabuleiro;
import br.ufsc.ine5608.view.TelaPrincipal;
import br.ufsc.inf.leobr.cliente.Jogada;

import javax.swing.*;
import java.util.Objects;

import static br.ufsc.ine5608.shared.Mensagens.SUCESSO_DESCONEXAO;
import static br.ufsc.ine5608.shared.Mensagens.mostraMensagem;

public class AtorJogador {
    private AtorNetGames rede;
    private Tabuleiro tabuleiro;
    private TelaPrincipal telaPrincipal;
    private Boolean conectado = false;

    public AtorJogador() {
        this.rede = new AtorNetGames(this);
        this.tabuleiro = new Tabuleiro();
        this.telaPrincipal = new TelaPrincipal("ButterFly", this);
    }

    public Boolean isConectado() {
        return conectado;
    }

    public void setConectado(Boolean conectado) {
        this.conectado = conectado;
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public boolean conectar() {
        if (Objects.nonNull(tabuleiro.getJogador()))
            if (rede.conectar(tabuleiro.getJogador()))
                conectado = true;
        return conectado;
    }

    public void desconectar() {
        rede.desconectar();
        tabuleiro.desconectar();
        mostraMensagem(SUCESSO_DESCONEXAO, JOptionPane.INFORMATION_MESSAGE);
        conectado = false;
        telaPrincipal.recarregaLayout();
    }

    public void jogar() throws Exception {
        tabuleiro.realizarJogada();
        enviarJogada();
        if (!tabuleiro.partidaEmAndamento()) tratarEncerramentoDePartida();
    }

    void receberJogada(Jogada jogada) {
        tabuleiro.tratarRecebimentoJogada((Operacao) jogada);
        telaPrincipal.recarregaLayout();
        if (!tabuleiro.partidaEmAndamento()) tratarEncerramentoDePartida();
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

    private void tratarEncerramentoDePartida() {
        switch (tabuleiro.getStatus()) {
            case EMPATADA:
                Mensagens.mostraMensagem(Mensagens.PARTIDA_EMPATADA, JOptionPane.INFORMATION_MESSAGE);
                break;
            case TERMINADA:
                Mensagens.mostraMensagem(Mensagens.PARTIDA_ENCERRADA + " O jogador" + tabuleiro.getNomeGanhador() + "venceu.", JOptionPane.INFORMATION_MESSAGE);
                break;
            case TERMINADA_SEM_CARTAS:
                Mensagens.mostraMensagem(Mensagens.PARTIDA_ENCERRADA_SEM_CARTAS + " O jogador" + tabuleiro.getNomeGanhador() + "venceu.", JOptionPane.INFORMATION_MESSAGE);
                break;
        }
        desconectar();
    }

    public boolean ehMinhaVez() {
        return tabuleiro.ehMinhaVez();
    }

    public boolean configuracaoPronta() {
        return tabuleiro.configuracaoPronta();
    }

    public void inicializar() {
        telaPrincipal.mostra();
    }
}
