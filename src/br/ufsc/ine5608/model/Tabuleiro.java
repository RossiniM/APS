package br.ufsc.ine5608.model;

import br.ufsc.ine5608.actor.AtorNetGames;
import br.ufsc.ine5608.shared.Mensagens;
import br.ufsc.ine5608.shared.OperadorMatematico;
import br.ufsc.ine5608.shared.PartidaStatus;
import br.ufsc.ine5608.shared.PosicaoTabuleiro;
import br.ufsc.ine5608.view.TelaConfiguracao;
import br.ufsc.inf.leobr.cliente.Jogada;

import java.util.*;

import static br.ufsc.ine5608.shared.PartidaStatus.*;
import static br.ufsc.ine5608.shared.PosicaoTabuleiro.*;

public class Tabuleiro {

    private Baralho baralho = new Baralho();
    private DistribuidorDeCartas distribuidorDeCartas = new DistribuidorDeCartas(baralho);

    private List<Carta> cartasJogada = new ArrayList<>();
    public Map<Long, Carta> cartasSelecionada = new HashMap<>();

    private OperadorMatematico operador;
    protected Jogador jogador;
    private Jogador adversario;
    private ValidadorDeOperacao validadorOperacao;
    private int pontuacaoMaxima;
    private PartidaStatus status = NAO_INICIADA;

    public void setOperador(OperadorMatematico operador) {
        this.operador = operador;
    }

    public boolean partidaEmAndamento() {
        return status == ANDAMENTO;
    }

    public PartidaStatus getStatus() {
        return status;
    }

    public void setStatus(PartidaStatus status) {
        this.status = status;
    }

    private boolean vez = false;

    public Jogador getJogador() {
        return jogador;
    }

    public Baralho getBaralho() {
        return baralho;
    }

    public Jogador getJogadorNaPosicao(PosicaoTabuleiro posicaoTabuleiro) {
        return (jogador.getPosicao() == posicaoTabuleiro) ? jogador : adversario;
    }

    public int getPontuacaoMaxima() {
        return pontuacaoMaxima;
    }

    public void setPontuacaoMaxima(int pontuacaoMaxima) {
        this.pontuacaoMaxima = pontuacaoMaxima;
    }


    public void realizarJogada() throws Exception {
        cartasJogada = distribuidorDeCartas.filtrarCartasSelecionadas(cartasSelecionada, jogador.getPosicao());
        if (validadorOperacao.jogadaEhValida(cartasJogada, operador)) {
            try {
                distribuidorDeCartas.atualizarCartas(jogador, cartasJogada);
                status = (jogadorGanhou(jogador)) ? TERMINADA : ANDAMENTO;
            } catch (Exception e) {
                status = (temGanhador()) ? TERMINADA_SEM_CARTAS : EMPATADA;
            }
        }
    }

    public boolean ehMinhaVez() {
        return vez;
    }

    private void limpaCartasSelecionadas() {
        cartasSelecionada.clear();
        cartasJogada.clear();
    }

    private boolean jogadorGanhou(Jogador jogador) {
        return !(jogador.getPontuacao() < this.pontuacaoMaxima);
    }

    private boolean temGanhador() {
        return jogador.getPontuacao() != adversario.getPontuacao();
    }

    public String getNomeGanhador() {
        return (jogador.getPontuacao() > adversario.getPontuacao()) ? jogador.getNome() : adversario.getNome();
    }

    public void tratarRecebimentoJogada(Operacao operacao) {
        baralho.setCartas(operacao.getCartas());
        pontuacaoMaxima = operacao.getPontuacaoMax();
        adversario.setPontuacao(operacao.getPontuacaoAdversario());
        status = operacao.getPartidaStatus();
        limpaCartasSelecionadas();
        if (partidaEmAndamento()) vez = true;
    }

    public Jogada criarJogada() {
        desabilitaJogador();
        return new Operacao(baralho.getCartas(), jogador.getPontuacao(), pontuacaoMaxima, status);
    }

    private void desabilitaJogador() {
        vez = false;
    }

    public boolean configuracaoPronta() {
        return status != NAO_INICIADA;
    }

    public void tratarIniciarPartida(Integer posicaoJogador, AtorNetGames atorNetGames) {
        try {
            criaJogador(atorNetGames.informarNomeAdversario(jogador.getNome()));
            jogador.setPosicao(PosicaoTabuleiro.values()[posicaoJogador]);
            adversario.setPosicao(getPosicaoAdversario(jogador));
            validadorOperacao = new ValidadorDeOperacao(jogador);
            if (jogador.getPosicao().equals(JOGADOR1) && status.equals(NAO_INICIADA)) {
                carregaConfiguracaoInicial();
                new TelaConfiguracao(null, "Configuracao Partida", true, this);
            }
            status = ANDAMENTO;
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

    public void desconectar() {
        limpaCartasSelecionadas();
        baralho = new Baralho();
        distribuidorDeCartas = new DistribuidorDeCartas(baralho);
        jogador = null;
        adversario = null;
        status = NAO_INICIADA;
    }
}
