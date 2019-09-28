package br.ufsc.ine5608.model;

import br.ufsc.ine5608.actor.AtorNetGames;
import br.ufsc.ine5608.shared.Mensagens;
import br.ufsc.ine5608.shared.OperadorMatematico;
import br.ufsc.ine5608.shared.PosicaoTabuleiro;
import br.ufsc.inf.leobr.cliente.Jogada;

import java.util.*;

import static br.ufsc.ine5608.shared.PosicaoTabuleiro.*;

public class Tabuleiro {

    private Baralho baralho = new Baralho();
    private DistribuidorDeCartas distribuidorDeCartas = new DistribuidorDeCartas(baralho);

    private List<Carta> cartasJogada = new ArrayList<>();
    public Map<Long, Carta> cartasSelecionada = new HashMap<>();

    private OperadorMatematico operador;
    protected Jogador jogador;
    private Jogador adversario;
    private ValidadorDeOperacao validadorOperacaoJogador;

    private boolean configuracaoPronta = false;
    private boolean primeiraRodada = true;

    public void setOperador(OperadorMatematico operador) {
        this.operador = operador;
    }

    private boolean vez = false;

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
            cartasJogada = distribuidorDeCartas.filtrarCartasSelecionadas(cartasSelecionada, jogador.getPosicao());
            if (validadorOperacaoJogador.jogadaEhValida(cartasJogada, operador))
                distribuidorDeCartas.atualizarCartas(jogador, cartasJogada);
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
            adversario.setPosicao(getPosicaoAdversario(jogador));
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
        distribuidorDeCartas.distribuirCartas(jogador.getPosicao(), 5L);
        distribuidorDeCartas.distribuirCartas(adversario.getPosicao(), 5L);
        distribuidorDeCartas.distribuirCartas(MESA, 9L);
    }

    private PosicaoTabuleiro getPosicaoAdversario(Jogador jogador) {
        return (jogador.getPosicao() == JOGADOR1) ? JOGADOR2 : JOGADOR1;
    }
}
