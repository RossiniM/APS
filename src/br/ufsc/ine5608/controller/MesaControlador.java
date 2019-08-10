package br.ufsc.ine5608.controller;

import br.ufsc.ine5608.model.AtorJogador;
import br.ufsc.ine5608.model.AtorNetGames;
import br.ufsc.ine5608.model.Carta;
import br.ufsc.ine5608.shared.OperacaoEnum;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class MesaControlador {

    private static MesaControlador ourInstance = new MesaControlador();

    public static MesaControlador getInstance() {
        return ourInstance;
    }

    public HashMap<Long, Carta> cartaJogadorSelecionada = new HashMap<>();
    public HashMap<Long, Carta> cartaMesaSelecionada = new HashMap<>();
    public OperacaoEnum operacaoEnum;
    public CartasControlador cartasControlador = CartasControlador.getInstance();
    private AtorNetGames atorNetGames = new AtorNetGames();
    private AtorJogador jogador;
    private AtorJogador adversario;
    private boolean conectado = false;
    private boolean vez = false;

    public void setAdversario(AtorJogador adversario) {
        this.adversario = adversario;
    }

    private MesaControlador() {
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

    public boolean conectar() {

        if (Objects.nonNull(jogador))
            return atorNetGames.conectar(jogador);
        return false;
    }

    public boolean realizarJogada() throws Exception {

        List<Carta> cartaJogador = cartaJogadorSelecionada.values()
                .stream()
                .filter(carta -> carta.getPosicaoTabuleiro() == jogador.getPosicao()).sorted().collect(toList());

        List<Carta> cartaMesa = new ArrayList<>(cartaMesaSelecionada.values());

        if (validaJogada(cartaMesa, cartaJogador) && Objects.nonNull(operacaoEnum))
            if (cartasControlador.validaOperacao(cartaJogador.get(0), cartaJogador.get(1), cartaMesa.get(0), operacaoEnum))
                return atualizaCartas(cartaJogador.get(0), cartaJogador.get(1), cartaMesa.get(0));
        return false;

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
        throw new Exception("Necessario selecionar duas cartas da mesma cor na sua mao, uma carta da mesa e uma operacao");
    }

    private boolean validaCor(Carta mesa, Carta nro1, Carta nro2) {
        return mesa.getCorCartaEnum() == nro1.getCorCartaEnum() && nro1.getCorCartaEnum() == nro2.getCorCartaEnum();
    }

    public void iniciarPartida() {
        atorNetGames.iniciarPartida();
    }

    public void carregaConfiguracaoInicial() {
        System.out.println("Conectou");
    }

    public void carregaTelaInicial() {
        System.out.println("Conectou");
    }
}
