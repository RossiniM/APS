package br.ufsc.ine5608.model;

import br.ufsc.ine5608.actor.AtorNetGames;
import br.ufsc.ine5608.shared.Mensagens;
import br.ufsc.ine5608.shared.Operadores;
import br.ufsc.ine5608.shared.PosicaoTabuleiro;
import br.ufsc.inf.leobr.cliente.Jogada;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static br.ufsc.ine5608.shared.PosicaoTabuleiro.*;

public class Tabuleiro {

    private Baralho baralho = new Baralho();
    private DistribuidorDeCartas dealer = new DistribuidorDeCartas(baralho);

    private List<Carta> cartasJogada = new ArrayList<>();
    public HashMap<Long, Carta> cartasSelecionada = new HashMap<>();

    private Operadores operacao;
    protected Jogador jogador;
    private Jogador adversario;
    private ValidadorDeOperacao validadorOperacaoJogador;

    private boolean configuracaoPronta = false;
    private boolean primeiraRodada = true;

    public Operadores getOperacao() {
        return operacao;
    }

    public void setOperacao(Operadores operacao) {
        this.operacao = operacao;
    }

    private boolean vez = false;

    private static Tabuleiro ourInstance = new Tabuleiro();

    public static Tabuleiro getInstance() {
        return ourInstance;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public Jogador getAdversario() {
        return adversario;
    }

    public Baralho getBaralho() {
        return baralho;
    }

    public Jogador getJogadorNaPosicao(PosicaoTabuleiro posicaoTabuleiro) {
        return (jogador.getPosicao() == posicaoTabuleiro) ? jogador : adversario;
    }

    public void realizarJogada() throws Exception {
        if (ehMinhaVez()) {
            cartasJogada = dealer.filtraCartasSelecionadas(cartasSelecionada, jogador.getPosicao());
            if (validadorOperacaoJogador.jogadaEhValida(cartasJogada, operacao))
                dealer.atualizarCartas(jogador, cartasJogada);
        }
    }

    public boolean ehMinhaVez() {
        return vez;
    }


    private void limpaCartasSelecionadas() {
        cartasSelecionada.clear();
        cartasJogada.clear();
    }

    public void tratarRecebimentoJogada(Operacao operacao) {
        baralho.setCartas(operacao.getCartas());
        limpaCartasSelecionadas();
        vez = true;
    }

    public Jogada criarJogada() {
        desabilitaJogador();
        return new Operacao(baralho.getCartas());
    }

    private void desabilitaJogador() {
        vez = false;
    }

    public boolean podeIniciarPartida() {
        return configuracaoPronta;
    }

    public void tratarIniciarPartida(Integer posicaoJogador, AtorNetGames atorNetGames) {
        try {
            criaJogador(atorNetGames.informarNomeAdversario(jogador.getNome()));
            jogador.setPosicao(PosicaoTabuleiro.values()[posicaoJogador]);
            adversario.setPosicao(getPosicaoOposta(jogador.getPosicao()));
            configuracaoPronta = true;
            validadorOperacaoJogador = new ValidadorDeOperacao(jogador);
            if (jogador.getPosicao().equals(JOGADOR1) && primeiraRodada) {
                carregaConfiguracaoInicial();
            }
            primeiraRodada = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void criaJogador(String nome) throws Exception {
        if (Objects.nonNull(nome) && !nome.isEmpty()) {
            if (Objects.isNull(jogador)) jogador = new Jogador(nome);
            adversario = new Jogador(nome);
        } else
            throw new Exception(Mensagens.CAMPO_INVALIDO);
    }

    private void carregaConfiguracaoInicial() throws Exception {
        inicializaCartas();
    }

    private void inicializaCartas() throws Exception {
        dealer.distribuiCartas(jogador.getPosicao(), 5L);
        dealer.distribuiCartas(adversario.getPosicao(), 5L);
        dealer.distribuiCartas(MESA, 9L);
    }

    private PosicaoTabuleiro getPosicaoOposta(PosicaoTabuleiro posicaoTabuleiro) {
        return (posicaoTabuleiro == JOGADOR1) ? JOGADOR2 : JOGADOR1;
    }
}
