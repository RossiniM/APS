package br.ufsc.ine5608.controller;

import br.ufsc.ine5608.model.Carta;
import br.ufsc.ine5608.model.Jogador;
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

    public static CartasControlador getInstance() {
        return ourInstance;
    }

    private Map<Long, Carta> cartasTotais = new HashMap<>();
    private Map<Long, Carta> cartasLivres = new HashMap<>();
    private final int qtdCartasPorCor = 15;

    private final List<Color> cores = Arrays.asList(Color.red, Color.black);

    private CartasControlador() {
    }

    public Map<Long, Carta> getCartasTotais() {
        return cartasTotais;
    }

    public Map<Long, Carta> getCartasLivres() {
        return cartasLivres;
    }

    public void setCartasTotais(Map<Long, Carta> cartasTotais) {
        this.cartasTotais = cartasTotais;
    }

    public void setCartasLivres(Map<Long, Carta> cartasLivres) {
        this.cartasLivres = cartasLivres;
    }

    public BiPredicate<List<Carta>, OperadoresEnum> operacaoEhValida() {
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

    public void gerarBaralhoTotal() {
        cores.forEach(this::adicionaCartas);
        atualizaCartasLivres();
    }

    private void adicionaCartas(Color cor) {
        long idReferencia = cartasTotais.size();
        cartasTotais.putAll(Stream.generate(criaCarta(idReferencia, new AtomicLong(1), cor))
                .limit(qtdCartasPorCor)
                .collect(Collectors.toMap(Carta::getId, carta -> carta)));
    }

    public void distribuiCartas(PosicaoTabuleiro posicaoTabuleiro, Long qtdCartasMaxima) throws Exception {
        while (temCartaLivre() && !verificaPosicaoEstaCheia(posicaoTabuleiro, qtdCartasMaxima))
            getCartaAleatoriaLivre().setPosicaoTabuleiro(posicaoTabuleiro);
        atualizaCartasLivres();
    }

    private boolean verificaPosicaoEstaCheia(PosicaoTabuleiro posicaoTabuleiro, Long qtdCartasMaxima) {
        return qtdCartasMaxima.equals(cartasTotais.values()
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
        cartasLivres = cartasTotais.entrySet().stream()
                .filter(cartas -> cartas.getValue().getPosicaoTabuleiro().equals(PosicaoTabuleiro.BARALHO))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void atualizarCartas(Jogador jogador, List<Carta> cartasJogada) throws Exception {
        for (Carta carta : cartasJogada) {
            atualizaCartasJogador(jogador, carta);
            atualizaCartasMesa(jogador, carta);
        }
        adicionaCartaLivrePosicao(PosicaoTabuleiro.MESA);
        adicionaCartaLivrePosicao(jogador.getPosicao());
    }

    private void atualizaCartasJogador(Jogador jogador, Carta cartaJogador) throws Exception {
        if (cartaJogador.getPosicaoTabuleiro().equals(jogador.getPosicao())) {
            cartaJogador.setPosicaoTabuleiro(PosicaoTabuleiro.USADA);
            cartasTotais.replace(cartaJogador.getId(), cartaJogador);
        }
    }

    private void atualizaCartasMesa(Jogador jogador, Carta cartaMesa) throws Exception {
        if (cartaMesa.getPosicaoTabuleiro().equals(PosicaoTabuleiro.MESA)) {
            cartaMesa.setPosicaoTabuleiro(jogador.getPosicao());
            cartasTotais.replace(cartaMesa.getId(), cartaMesa);
        }
    }

    private void adicionaCartaLivrePosicao(PosicaoTabuleiro posicaoTabuleiro) throws Exception {
        if (temCartaLivre()) {
            Carta carta = getCartaAleatoriaLivre();
            carta.setPosicaoTabuleiro(posicaoTabuleiro);
            cartasTotais.replace(carta.getId(),carta);
            atualizaCartasLivres();
        } else
            throw new Exception(Mensagens.CARTAS_ESGOTADAS);
    }

    private Supplier<Carta> criaCarta(Long idReferencia, AtomicLong numero, Color cor) {
        return () -> new Carta(numero.get() + idReferencia, numero.getAndIncrement(), cor);
    }
}

