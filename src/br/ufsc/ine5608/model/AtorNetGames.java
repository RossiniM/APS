package br.ufsc.ine5608.model;

import br.ufsc.inf.leobr.cliente.Jogada;
import br.ufsc.inf.leobr.cliente.OuvidorProxy;
import br.ufsc.inf.leobr.cliente.Proxy;
import br.ufsc.inf.leobr.cliente.exception.ArquivoMultiplayerException;
import br.ufsc.inf.leobr.cliente.exception.JahConectadoException;
import br.ufsc.inf.leobr.cliente.exception.NaoConectadoException;
import br.ufsc.inf.leobr.cliente.exception.NaoPossivelConectarException;

public class AtorNetGames implements OuvidorProxy {

    private Proxy proxy;
    private boolean conectado = false;
    private AtorJogador atorJogador;

    private boolean esperandoJogador;

    public AtorNetGames(AtorJogador atorJogador) {
        this.atorJogador = atorJogador;
        proxy = Proxy.getInstance();
        proxy.addOuvinte(this);
    }

    @Override
    public void iniciarNovaPartida(Integer posicao) {
        esperandoJogador = false;
        atorJogador.carregarConfiguracaoInicial(posicao);
    }

    public void iniciarPartida() {
        try {
            proxy.iniciarPartida(2);
        } catch (NaoConectadoException e) {
            e.printStackTrace();
        }
    }

    public boolean conectar(String nomeJogador) {
        try {
            proxy.conectar("localhost", nomeJogador);
            conectado = true;
        } catch (JahConectadoException e) {
            e.printStackTrace();
        } catch (NaoPossivelConectarException e) {
            e.printStackTrace();
        } catch (ArquivoMultiplayerException e) {
            e.printStackTrace();
        }
        return conectado;
    }

    public boolean isConectado(){
        return conectado;
    }

    public void setEsperandoJogador(boolean esperandoJogador) {
        this.esperandoJogador = esperandoJogador;
    }

    @Override
    public void finalizarPartidaComErro(String message) {

    }

    @Override
    public void receberMensagem(String msg) {

    }

    @Override
    public void receberJogada(Jogada jogada) {

    }

    @Override
    public void tratarConexaoPerdida() {

    }

    @Override
    public void tratarPartidaNaoIniciada(String message) {

    }
}
