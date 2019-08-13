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

import java.util.*;

import static br.ufsc.ine5608.shared.PosicaoTabuleiro.*;
import static java.util.stream.Collectors.toList;

public class MesaControlador {

    private static MesaControlador ourInstance = new MesaControlador();

    public static MesaControlador getInstance() {
        return ourInstance;
    }

    public HashMap<Long, Carta> cartaJogadorSelecionada = new HashMap<>();
    public HashMap<Long, Carta> cartaMesaSelecionada = new HashMap<>();
    public OperadoresEnum operadoresEnum;
    public CartasControlador cartasControlador = CartasControlador.getInstance();
    private AtorNetGames atorNetGames = new AtorNetGames();
    private AtorJogador jogador;
    private AtorJogador adversario;
    private TelaPrincipal butterFly;

    private boolean conectado = false;
    private boolean primeiraRodada = true;

    private boolean vez = false;

    public void setAdversario(AtorJogador adversario) {
        this.adversario = adversario;
    }

    private MesaControlador() {

    }
    public void inicializa(TelaPrincipal telaPrincipal){
        butterFly = telaPrincipal;
        butterFly.mostra();

    }

    public void criaJogador(String nome) throws Exception {

        if (Objects.nonNull(nome) && !nome.isEmpty()) {
            if (Objects.isNull(jogador)) {
                jogador = new AtorJogador(nome);
                return;
            }
            adversario = new AtorJogador(nome);
        } else
            throw new Exception("Campo invalido");
    }

    public AtorJogador getJogador() {
        return jogador;
    }

    public AtorJogador getAdversario() {
        return adversario;
    }

    public AtorJogador getJogador(PosicaoTabuleiro posicaoTabuleiro) {
        if (jogador.getPosicao() == posicaoTabuleiro)
            return  jogador;
        return adversario;
    }
    public boolean conectar() {
        if (Objects.nonNull(jogador))
            return atorNetGames.conectar(jogador);
        return false;
    }

    public boolean possoJogar(){
        return vez;
    }
    public boolean realizarJogada() throws Exception {
        if(vez) {
            List<Carta> cartaJogador = getCartasSelecionadas();
            List<Carta> cartaMesa = new ArrayList<>(cartaMesaSelecionada.values());

            if (validaJogada(cartaMesa, cartaJogador) && Objects.nonNull(operadoresEnum))
                if (cartasControlador.validaOperacao(cartaJogador.get(0), cartaJogador.get(1), cartaMesa.get(0), operadoresEnum))
                    return atualizaCartas(cartaJogador.get(0), cartaJogador.get(1), cartaMesa.get(0));
            return false;
        }
        return false;
    }

    private List<Carta> getCartasSelecionadas() {
        return cartaJogadorSelecionada.values()
                .stream()
                .filter(carta -> carta.getPosicaoTabuleiro() == jogador.getPosicao())
                .sorted()
                .collect(toList());
    }

    private boolean atualizaCartas(Carta cartaJogador1, Carta cartaJogador2, Carta cartaMesa) throws Exception {
        limpaCartasSelecionadas();
        cartasControlador.atualizarCartas(jogador, Arrays.asList(cartaJogador1,cartaJogador2,cartaMesa));
        return  true;
    }

    private void limpaCartasSelecionadas() {
        cartaMesaSelecionada.clear();
        cartaJogadorSelecionada.clear();
    }

    private boolean validaJogada(List<Carta> cartasMesa, List<Carta> cartaJogador) throws Exception {

        if (cartasMesa.size() == 1 && cartaJogador.size() == 2)
            if (validaCor(cartasMesa.get(0), cartaJogador.get(0), cartaJogador.get(1)))
                return true;
        throw new Exception(Mensagens.CARTAS_SELECAO_ERRADA);
    }

    private boolean validaCor(Carta mesa, Carta carta1, Carta carta2) {
        return mesa.getCorCartaEnum().equals(carta1.getCorCartaEnum()) &&
                carta1.getCorCartaEnum().equals(carta2.getCorCartaEnum());
    }

    public  void receberJogada(Jogada jogada){
        tratarRecebimentoJogada((Operacao) jogada);
    }


    public void enviaJogada(){
        atorNetGames.enviarJogada(new Operacao(cartasControlador.getCartas(),cartasControlador.getCartasLivres()));
        vez = false;
    }
    public boolean podeIniciarPartida(){
        return  conectado;
    }

    public void iniciarPartida() {
        atorNetGames.iniciarPartida();
    }

    public void tratarIniciarPartida(Integer posicao )  {
        try {
            System.out.println("Posicao e"+posicao.toString());
            criaJogador(atorNetGames.informarNomeAdversario(jogador.getNome()));
            jogador.setPosicao(PosicaoTabuleiro.values()[posicao]);
            adversario.setPosicao(getPosicaoOposta(jogador.getPosicao()));
            conectado = true;
            if(jogador.getPosicao().equals(JOGADOR1) && primeiraRodada) {
                gerarConfiguracaoInicial();
                butterFly.recarregaLayout();
                this.enviaJogada();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void tratarRecebimentoJogada(Operacao turno){
        cartasControlador.setCartas(turno.getCartas());
        cartasControlador.setCartasLivres(turno.getCartasLivres());
        vez = true;
        butterFly.recarregaLayout();
    }

    public void gerarConfiguracaoInicial() throws Exception {
        cartasControlador.gerarBaralhoTotal();
        cartasControlador.distribuiCartas(jogador.getPosicao(),5L);
        cartasControlador.distribuiCartas(adversario.getPosicao(),5L);
        cartasControlador.distribuiCartas(MESA,9L);
        primeiraRodada = false;
    }
    public PosicaoTabuleiro getPosicaoOposta(PosicaoTabuleiro posicaoTabuleiro){
        if (posicaoTabuleiro == JOGADOR1) {
            return JOGADOR2;
        }
        return JOGADOR1;
    }
}
