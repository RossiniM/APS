package br.ufsc.ine5608.controller;

import br.ufsc.ine5608.model.AtorJogador;
import br.ufsc.ine5608.model.AtorNetGames;
import br.ufsc.ine5608.model.Carta;
import br.ufsc.ine5608.model.Operacao;
import br.ufsc.ine5608.shared.Mensagens;
import br.ufsc.ine5608.shared.OperadoresEnum;
import br.ufsc.ine5608.shared.PosicaoTabuleiro;
import br.ufsc.ine5608.view.TelaPrincipal;
import br.ufsc.inf.leobr.cliente.Jogada;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;

import static br.ufsc.ine5608.shared.Constantes.*;
import static br.ufsc.ine5608.shared.PosicaoTabuleiro.*;
import static java.util.stream.Collectors.toList;

public class MesaControlador {

    private static MesaControlador ourInstance = new MesaControlador();

    public static MesaControlador getInstance() {
        return ourInstance;
    }

    public HashMap<Long, Carta> cartaJogadorSelecionada = new HashMap<>();
    public HashMap<Long, Carta> cartaMesaSelecionada = new HashMap<>();
    public OperadoresEnum operacao;
    private CartasControlador cartasControlador = CartasControlador.getInstance();
    private AtorNetGames atorNetGames = new AtorNetGames();

    private AtorJogador jogador;
    private AtorJogador adversario;

    private TelaPrincipal butterFly;

    private boolean conectado = false;
    private boolean primeiraRodada = true;
    private boolean vez = false;

    private MesaControlador() {
    }

    public void inicializa(TelaPrincipal telaPrincipal) {
        butterFly = telaPrincipal;
        butterFly.mostra();
    }

    public AtorJogador getJogador() {
        return jogador;
    }

    public AtorJogador getAdversario() {
        return adversario;
    }

    public AtorJogador getJogadorNaPosicao(PosicaoTabuleiro posicaoTabuleiro) {
        return (jogador.getPosicao() == posicaoTabuleiro) ? jogador : adversario;
    }

    public boolean conectar() {
        if (Objects.nonNull(jogador))
            return atorNetGames.conectar(jogador);
        return false;
    }

    public boolean realizarJogada() throws Exception {
        if (ehMinhaVez()) {
            List<Carta> cartasDaJogada = getCartasDaJogada();
            if (jogadaEhValida().and(cartasControlador.operacaoEhValida()).test(cartasDaJogada, operacao))
                return atualizaCartas(cartasDaJogada);
            return false;
        }
        return false;
    }

    public boolean ehMinhaVez() {
        return vez;
    }

    private List<Carta> getCartasDaJogada() {
        return cartaJogadorSelecionada.values()
                .stream()
                .filter(carta ->
                        carta.getPosicaoTabuleiro().equals(jogador.getPosicao()) ||
                                carta.getPosicaoTabuleiro().equals(MESA))
                .sorted()
                .collect(toList());
    }

    private BiPredicate<List<Carta>, OperadoresEnum> jogadaEhValida() {
        return this::jogadaEhValida;
    }

    private boolean jogadaEhValida(List<Carta> cartasJogada, OperadoresEnum operacao) {
        if (numeroCartasJogadaEhValida(cartasJogada))
            return corEhValida(cartasJogada.get(PRIMEIRA_CARTA_JOGADOR), cartasJogada.get(SEGUNDA_CARTA_JOGADOR), cartasJogada.get(CARTA_MESA));
        return false;
    }

    private boolean numeroCartasJogadaEhValida(List<Carta> cartasJogada) {
        return cartasJogada.stream()
                .filter(carta -> carta.getPosicaoTabuleiro() == jogador.getPosicao()).count() == 2
                &&
                cartasJogada.stream().
                        filter(carta -> carta.getPosicaoTabuleiro() == MESA).count() == 1;
    }

    private boolean corEhValida(Carta mesa, Carta carta1, Carta carta2) {
        return mesa.getCorCartaEnum().equals(carta1.getCorCartaEnum()) &&
                carta1.getCorCartaEnum().equals(carta2.getCorCartaEnum());
    }

    private boolean atualizaCartas(List<Carta> cartasDaJogada) throws Exception {
        limpaCartasSelecionadas();
        cartasControlador.atualizarCartas(jogador, cartasDaJogada);
        return true;
    }

    private void limpaCartasSelecionadas() {
        cartaMesaSelecionada.clear();
        cartaJogadorSelecionada.clear();
    }

    public void receberJogada(Jogada jogada) {
        tratarRecebimentoJogada((Operacao) jogada);
    }

    private void tratarRecebimentoJogada(Operacao operacao) {
        cartasControlador.setCartas(operacao.getCartas());
        cartasControlador.setCartasLivres(operacao.getCartasLivres());
        vez = true;
        butterFly.recarregaLayout();
    }

    public void enviaJogada() {
        atorNetGames.enviarJogada(new Operacao(cartasControlador.getCartas(), cartasControlador.getCartasLivres()));
        vez = false;
    }

    public boolean podeIniciarPartida() {
        return conectado;
    }

    public void iniciarPartida() {
        atorNetGames.iniciarPartida();
    }

    public void tratarIniciarPartida(Integer posicaoJogador) {
        try {
            criaJogador(atorNetGames.informarNomeAdversario(jogador.getNome()));
            jogador.setPosicao(PosicaoTabuleiro.values()[posicaoJogador]);
            adversario.setPosicao(getPosicaoOposta(jogador.getPosicao()));
            conectado = true;
            if (jogador.getPosicao().equals(JOGADOR1) && primeiraRodada) {
                carregaConfiguracaoInicial();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void criaJogador(String nome) throws Exception {
        if (Objects.nonNull(nome) && !nome.isEmpty()) {
            if (Objects.isNull(jogador)) jogador = new AtorJogador(nome);
            adversario = new AtorJogador(nome);
        } else
            throw new Exception(Mensagens.CAMPO_INVALIDO);
    }

    private void carregaConfiguracaoInicial() throws Exception {
        distribuiCartas();
        butterFly.recarregaLayout();
        this.enviaJogada();
    }

    private void distribuiCartas() throws Exception {
        cartasControlador.gerarBaralhoTotal();
        cartasControlador.distribuiCartas(jogador.getPosicao(), 5L);
        cartasControlador.distribuiCartas(adversario.getPosicao(), 5L);
        cartasControlador.distribuiCartas(MESA, 9L);
        primeiraRodada = false;
    }

    private PosicaoTabuleiro getPosicaoOposta(PosicaoTabuleiro posicaoTabuleiro) {
        return (posicaoTabuleiro == JOGADOR1) ? JOGADOR2 : JOGADOR1;
    }
}
