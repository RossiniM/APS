package br.ufsc.ine5608.actor;

import br.ufsc.ine5608.model.Jogador;
import br.ufsc.inf.leobr.cliente.Jogada;
import br.ufsc.inf.leobr.cliente.OuvidorProxy;
import br.ufsc.inf.leobr.cliente.Proxy;
import br.ufsc.inf.leobr.cliente.exception.*;

public class AtorNetGames implements OuvidorProxy {

    private Proxy proxy;
    private AtorJogador atorJogador;
    private boolean conectado = false;

    AtorNetGames(AtorJogador atorJogador) {
        this.atorJogador = atorJogador;
        proxy = Proxy.getInstance();
        proxy.addOuvinte(this);
    }

    @Override
    public void iniciarNovaPartida(Integer posicao) {
        atorJogador.tratarInicioPartida(posicao);
    }

    public void iniciarPartida() {
        try {
            proxy.iniciarPartida(2);
        } catch (NaoConectadoException e) {
            e.printStackTrace();
        }
    }

    public boolean conectar(Jogador jogador) {
        try {
            proxy.conectar("localhost", jogador.getNome());
            conectado = true;
        } catch (JahConectadoException | NaoPossivelConectarException | ArquivoMultiplayerException e) {
            e.printStackTrace();
        }
        return conectado;
    }

    @Override
    public void finalizarPartidaComErro(String message) {

    }

    @Override
    public void receberMensagem(String msg) {
        System.out.println(msg);

    }

    @Override
    public void receberJogada(Jogada jogada) {
        atorJogador.receberJogada(jogada);
    }

    void enviarJogada(Jogada jogada) {
        try {
            proxy.enviaJogada(jogada);
        } catch (NaoJogandoException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void tratarConexaoPerdida() {

    }

    public String informarNomeAdversario(String nomeUsuario) {
        String aux1 = proxy.obterNomeAdversario(1);
        String aux2 = proxy.obterNomeAdversario(2);
        if (aux1.equals(nomeUsuario)) {
            return aux2;
        }
        return aux1;
    }

    @Override
    public void tratarPartidaNaoIniciada(String message) {
        System.out.println(message);

    }
}
