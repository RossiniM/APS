package br.ufsc.ine5608.controller;

import br.ufsc.ine5608.model.AtorJogador;
import br.ufsc.ine5608.model.Carta;
import br.ufsc.ine5608.shared.Mensagens;
import br.ufsc.ine5608.shared.OperadoresEnum;
import br.ufsc.ine5608.shared.PosicaoTabuleiro;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiPredicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static br.ufsc.ine5608.shared.Constantes.*;

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

    BiPredicate<List<Carta>, OperadoresEnum> operacaoEhValida() {
        return (cartasJogada, operacao) -> {
            Long valorCarta1 = cartasJogada.get(PRIMEIRA_CARTA_JOGADOR).getNumero();
            Long valorCarta2 = cartasJogada.get(SEGUNDA_CARTA_JOGADOR).getNumero();
            Long valorCartaMesa = cartasJogada.get(CARTA_MESA).getNumero();

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
        };
    }

    void gerarBaralhoTotal() {
        cores.forEach(this::adicionaCartas);
        atualizaCartasLivres();
    }

    private void adicionaCartas(Color cor) {
        long idReferencia = cartas.size();
        cartas.putAll(Stream.generate(criaCarta(idReferencia, new AtomicLong(1), cor))
                .limit(qtdCartasPorCor)
                .collect(Collectors.toMap(Carta::getId, carta -> carta)));
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
        throw new Exception(Mensagens.CARTAS_ESGOTADAS);
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
        }
    }

    private void adicionaCartaLivrePosicao(PosicaoTabuleiro posicaoTabuleiro) throws Exception {
        if (temCartaLivre()) {
            getCartaAleatoriaLivre().setPosicaoTabuleiro(posicaoTabuleiro);
            atualizaCartasLivres();
        } else
            throw new Exception(Mensagens.CARTAS_ESGOTADAS);
    }

    private Supplier<Carta> criaCarta(Long idReferencia, AtomicLong numero, Color cor) {
        return () -> new Carta(numero.get() + idReferencia, numero.getAndIncrement(), cor);
    }
}

