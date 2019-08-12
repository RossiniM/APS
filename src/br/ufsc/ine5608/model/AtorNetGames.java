package br.ufsc.ine5608.model;

import br.ufsc.ine5608.controller.MesaControlador;
import br.ufsc.inf.leobr.cliente.Jogada;
import br.ufsc.inf.leobr.cliente.OuvidorProxy;
import br.ufsc.inf.leobr.cliente.Proxy;
import br.ufsc.inf.leobr.cliente.exception.*;

public class AtorNetGames implements OuvidorProxy {

    private Proxy proxy;
    private boolean conectado = false;
    private AtorJogador atorJogador;

    private boolean esperandoJogador;

    public AtorNetGames() {
        proxy = Proxy.getInstance();
        proxy.addOuvinte(this);
    }

    @Override
    public void iniciarNovaPartida(Integer posicao) {
            MesaControlador.getInstance().tratarIniciarPartida(posicao);
    }

    public void iniciarPartida() {
        try {
            proxy.iniciarPartida(2);
        } catch (NaoConectadoException e) {
            e.printStackTrace();
        }
    }

    public boolean conectar(AtorJogador jogador) {
        try {
            proxy.conectar("localhost", jogador.getNome());
            conectado = true;
        } catch (JahConectadoException | NaoPossivelConectarException | ArquivoMultiplayerException e) {
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
        System.out.println(msg);

    }

    @Override
    public void receberJogada(Jogada jogada) {
        MesaControlador.getInstance().receberJogada(jogada);
    }

    public void enviarJogada(Jogada jogada){
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
        if (aux1.equals(nomeUsuario)){
            return aux2;
        } else {
            return aux1;
        }
    }

    @Override
    public void tratarPartidaNaoIniciada(String message) {
        System.out.println(message);

    }
}
