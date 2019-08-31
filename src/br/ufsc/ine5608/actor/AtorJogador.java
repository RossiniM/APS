package br.ufsc.ine5608.actor;

import br.ufsc.ine5608.controller.CartasControlador;
import br.ufsc.ine5608.model.Carta;
import br.ufsc.ine5608.model.Jogador;
import br.ufsc.ine5608.shared.OperadoresEnum;
import br.ufsc.ine5608.shared.PosicaoTabuleiro;
import br.ufsc.ine5608.view.TelaPrincipal;

import java.util.HashMap;

import static br.ufsc.ine5608.shared.PosicaoTabuleiro.*;

public class AtorJogador {

    private CartasControlador cartasControlador = CartasControlador.getInstance();

    public HashMap<Long, Carta> cartasSelecionada = new HashMap<>();

    public OperadoresEnum operacao;
    private Jogador jogador;
    private Jogador adversario;

    private TelaPrincipal butterFly;

    private static AtorJogador ourInstance = new AtorJogador();

    public static AtorJogador getInstance() {
        return ourInstance;
    }

    public void inicializa(TelaPrincipal telaPrincipal) {
        butterFly = telaPrincipal;
        butterFly.mostra();
    }

    public Jogador getJogador() {
        return jogador;
    }

    public Jogador getAdversario() {
        return adversario;
    }


    public void distribuiCartas() throws Exception {
        cartasControlador.gerarBaralhoTotal();
        cartasControlador.distribuiCartas(JOGADOR1, 5L);
        cartasControlador.distribuiCartas(JOGADOR2, 5L);
        cartasControlador.distribuiCartas(MESA, 9L);
    }

    private PosicaoTabuleiro getPosicaoOposta(PosicaoTabuleiro posicaoTabuleiro) {
        return (posicaoTabuleiro == JOGADOR1) ? JOGADOR2 : JOGADOR1;
    }
}
