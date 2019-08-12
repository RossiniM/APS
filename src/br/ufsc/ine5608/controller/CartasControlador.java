package br.ufsc.ine5608.controller;

import br.ufsc.ine5608.model.AtorJogador;
import br.ufsc.ine5608.model.Carta;
import br.ufsc.ine5608.shared.ExcecoesMensagens;
import br.ufsc.ine5608.shared.OperacaoEnum;
import br.ufsc.ine5608.shared.PosicaoTabuleiro;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class CartasControlador {
    private static CartasControlador ourInstance = new CartasControlador();

    private Map<Long, Carta> cartas = new HashMap<>();
    private Map<Long, Carta> cartasLivres = new HashMap<>();
    private final int qtdCartasPorCor = 15;
    private final List<Color> cores = Arrays.asList(Color.red, Color.black);

    public static CartasControlador getInstance() {
        return ourInstance;
    }

    private CartasControlador() {
    }

    public Map<Long, Carta> getCartas() {
        return cartas;
    }

    Map<Long, Carta> getCartasLivres() {
        return cartasLivres;
    }

    void setCartas(Map<Long, Carta> cartas) {
        this.cartas = cartas;
    }

    void setCartasLivres(Map<Long, Carta> cartasLivres) {
        this.cartasLivres = cartasLivres;
    }

    boolean validaOperacao(Carta carta1, Carta carta2, Carta cartaMesa, OperacaoEnum operacao) {

        Long valorCarta1 = carta1.getNumero();
        Long valorCarta2 = carta2.getNumero();
        Long valorCartaMesa = cartaMesa.getNumero();

        switch (operacao) {
            case SOMA:
                return valorCartaMesa.equals(valorCarta1 + valorCarta2);

            case SUBTRACAO:
                return valorCartaMesa.equals(valorCarta2 - valorCarta1);

            case MULTIPLICACAO:
                return valorCartaMesa.equals(valorCarta1 * valorCarta2);

            case DIVISAO:
                return valorCartaMesa.equals(valorCarta2 / valorCarta1);
            default:
                return false;
        }
    }

    void gerarBaralhoTotal() {
        cores.forEach(this::adicionaCartas);
        atualizaCartasLivres();
    }

    private void adicionaCartas(Color cor) {
        int qtdCartasBaralho = cartas.size();
        for (long numero = 1; numero <= qtdCartasPorCor; numero++) {
            cartas.put(qtdCartasBaralho + numero, new Carta(qtdCartasBaralho + numero, numero, cor));
        }
    }

    void distribuiCartas(PosicaoTabuleiro posicaoTabuleiro, Long qtdCartasMaxima) throws Exception {
        while (temCartaLivre() && !verificaPosicaoEstaCheia(posicaoTabuleiro, qtdCartasMaxima))
            getCartaAleatoriaLivre().setPosicaoTabuleiro(posicaoTabuleiro);
        atualizaCartasLivres();
    }

    private boolean verificaPosicaoEstaCheia(PosicaoTabuleiro posicaoTabuleiro, Long qtdCartasMaxima) {
        return qtdCartasMaxima.equals(cartas.values()
                .stream()
                .filter(carta -> carta.getPosicaoTabuleiro().equals(posicaoTabuleiro))
                .count());
    }

    private boolean temCartaLivre() {
        return !cartasLivres.isEmpty();
    }

    private Carta getCartaAleatoriaLivre() throws Exception {
        atualizaCartasLivres();
        if (temCartaLivre())
            return cartasLivres.get(getIdAleatorioCartaLivre());
        throw new Exception(ExcecoesMensagens.CARTAS_ESGOTADAS);
    }

    private Long getIdAleatorioCartaLivre() {
        List<Long> idsCartasLivres = new ArrayList<>(cartasLivres.keySet());
        return idsCartasLivres.get(getIndiceAleatorio(idsCartasLivres.size()));
    }

    private int getIndiceAleatorio(int tamanhoArray) {
        return (int) Math.round((tamanhoArray - 1) * Math.random());
    }

    private void atualizaCartasLivres() {
        cartasLivres = cartas.entrySet().stream()
                .filter(cartas -> cartas.getValue().getPosicaoTabuleiro().equals(PosicaoTabuleiro.BARALHO))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    void atualizarCartas(AtorJogador atorJogador, List<Carta> cartasJogada) throws Exception {
        for (Carta carta : cartasJogada) {
            atualizaCartasJogador(atorJogador, carta);
            atualizaCartasMesa(atorJogador, carta);
        }
        adicionaCartaLivrePosicao(PosicaoTabuleiro.MESA);
        adicionaCartaLivrePosicao(atorJogador.getPosicao());
    }

    private void atualizaCartasJogador(AtorJogador atorJogador, Carta cartaJogador) throws Exception {
        if (cartaJogador.getPosicaoTabuleiro().equals(atorJogador.getPosicao())) {
            cartaJogador.setPosicaoTabuleiro(PosicaoTabuleiro.USADA);
            cartas.replace(cartaJogador.getId(), cartaJogador);
        }
    }

    private void atualizaCartasMesa(AtorJogador atorJogador, Carta cartaMesa) throws Exception {
        if (cartaMesa.getPosicaoTabuleiro().equals(PosicaoTabuleiro.MESA)) {
            cartaMesa.setPosicaoTabuleiro(atorJogador.getPosicao());
            cartas.replace(cartaMesa.getId(), cartaMesa);
            atualizaCartasLivres();
        }
    }

    private void adicionaCartaLivrePosicao(PosicaoTabuleiro posicaoTabuleiro) throws Exception {
        if (temCartaLivre()) {
            getCartaAleatoriaLivre().setPosicaoTabuleiro(posicaoTabuleiro);
            atualizaCartasLivres();
        } else
            throw new Exception(ExcecoesMensagens.CARTAS_ESGOTADAS);
    }
}

