package br.ufsc.ine5608;

import br.ufsc.ine5608.controller.CartasControlador;
import br.ufsc.ine5608.controller.MesaControlador;
import br.ufsc.ine5608.shared.PosicaoTabuleiro;
import br.ufsc.ine5608.view.TelaPrincipal;

public class Main {

    public static void main(String[] args) throws Exception {
        CartasControlador.getInstance().gerarBaralhoTotal();
        CartasControlador.getInstance().distribuiCartas(PosicaoTabuleiro.JOGADOR1,5);
        CartasControlador.getInstance().distribuiCartas(PosicaoTabuleiro.JOGADOR2,5);
        CartasControlador.getInstance().distribuiCartas(PosicaoTabuleiro.MESA,9);


        MesaControlador.getInstance().criaJogador("Rossini");
        MesaControlador.getInstance().getJogador().setPosicao(PosicaoTabuleiro.JOGADOR1);
        MesaControlador.getInstance().criaJogador("Raphael");
        MesaControlador.getInstance().getAdversario().setPosicao(PosicaoTabuleiro.JOGADOR2);

        TelaPrincipal butterFly = new TelaPrincipal("ButterFly");
        butterFly.mostra();

    }
}
