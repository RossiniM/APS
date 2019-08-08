package br.ufsc.ine5608.controller;

import br.ufsc.ine5608.model.AtorJogador;
import br.ufsc.ine5608.model.Baralho;
import br.ufsc.ine5608.model.Carta;
import br.ufsc.ine5608.shared.OperacaoEnum;
import br.ufsc.ine5608.shared.PosicaoTabuleiro;

import java.awt.*;
import java.util.*;
import java.util.stream.Collectors;

import static java.awt.Color.*;

public class CartasControlador {
    private static CartasControlador ourInstance = new CartasControlador();

    private HashMap<Long, Carta> cartas = new HashMap<>();

    private final int qtdCartasCor = 12;
    private final Long qtdTotalCartas = 24;
    private final ArrayList<Color> cores = new ArrayList<Color>(Arrays.asList(black, red));

    private Baralho baralho = new Baralho();

    public static CartasControlador getInstance() {
        return ourInstance;
    }

    private CartasControlador() {
    }


    public Baralho getBaralho() {
        return baralho;
    }

    public void gerarBaralhoTotal() {
        long id = 1;
        for (Color cor : cores) {
            for (long number = 1; number <= qtdCartasCor; number++) {
                baralho.adicionaCarta(new Carta(id, number, cor));
                id++;
            }
        }
    }

    public void distribuiCartas(PosicaoTabuleiro posicaoTabuleiro, int qtdCartas) {
        int i = 0;
        while (i < qtdCartas) {
            Carta carta = baralho.getCartas().get(geraIdRandomico());
            if (Objects.equals(carta.getPosicaoTabuleiro(), PosicaoTabuleiro.BARALHO)) {
                carta.setPosicaoTabuleiro(posicaoTabuleiro);
                i++;
            }
        }
    }

    public boolean validaOperacao(Carta carta1, Carta carta2, Carta cartaMesa, OperacaoEnum operacao) {

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


    public boolean atualizaCartasJogador(AtorJogador jogador, long... ids) throws Exception {

        boolean status = false;
        for (long id : ids) {
            status = false;
            Carta carta = baralho.getCartaPorId(id);
            if (Objects.nonNull(carta) && carta.getPosicaoTabuleiro() != PosicaoTabuleiro.MESA) {
                carta.setPosicaoTabuleiro(PosicaoTabuleiro.USADA);
                baralho.getCartas().replace(id, carta);
                status = true;
            }
        }
        if (status)
            adicionaCartaLivrePosicao(jogador.getPosicao());
        return status;
    }


    public boolean atualizaCartasDaMesa(AtorJogador jogador, long id) throws Exception {

        Carta carta = baralho.getCartaPorId(id);
        if (Objects.nonNull(carta) && carta.getPosicaoTabuleiro() == PosicaoTabuleiro.MESA) {
            carta.setPosicaoTabuleiro(jogador.getPosicao());
            baralho.getCartas().replace(id, carta);
            adicionaCartaLivrePosicao(PosicaoTabuleiro.MESA);
            return true;
        }
        return false;
    }

    public boolean adicionaCartaLivrePosicao(PosicaoTabuleiro posicaoTabuleiro) throws Exception {
        if (temCartaLivre()) {
            getCartaLivre().setPosicaoTabuleiro(posicaoTabuleiro);
            return true;
        }
        throw new Exception("Nao ha mais cartas livre. O jogo acabou");
    }

    private Carta getCartaLivre() {
        return baralho.getCartas().values()
                .stream()
                .filter(carta -> carta.getPosicaoTabuleiro() == PosicaoTabuleiro.BARALHO)
                .findFirst().orElse(null);
    }

    private boolean temCartaLivre() {
        if (Objects.nonNull(getCartaLivre()))
            return true;
        return false;
    }


    private long geraIdRandomico() {
        return 1 + Math.round(Math.random() * (qtdTotalCartas - 1));
    }



    private boolean temCartaLivre2() {
       return cartas.values()
               .stream()
               .anyMatch( carta -> carta.getPosicaoTabuleiro() == PosicaoTabuleiro.BARALHO);
    }

    private Carta getPrimeiraCartaLivre() {
        return getCartasLivres().values().stream().findFirst().orElse(null);
    }
    public void gerarBaralhoTotal1() {
        cores.forEach(this::adicionaCartas);
    }

    public void adicionaCartas(Color cor){
        long idCarta = 1;
        for (long number = 1; number <= qtdCartasCor; number++) {
            baralho.adicionaCarta(new Carta(idCarta, number, cor));
            idCarta++;
        }
    }

    public void distribuiCartas1(PosicaoTabuleiro posicaoTabuleiro, int qtdCartas) {
        while (temCartaLivre2()&& !verificaCartasTabuleiroCompleto()) {
            getCartaAleatoriaLivre().setPosicaoTabuleiro(posicaoTabuleiro);
            }
        }


        private boolean verificaCartasTabuleiroCompleto(){
        return verificaQtdCartasPosicao(PosicaoTabuleiro.JOGADOR1,qtdTotalCartas)
                && verificaQtdCartasPosicao(PosicaoTabuleiro.JOGADOR2, qtdTotalCartas)
                && verificaQtdCartasPosicao(PosicaoTabuleiro.MESA,qtdTotalCartas);
        }
        private boolean verificaQtdCartasPosicao(PosicaoTabuleiro posicaoTabuleiro, Long qtd){
            return qtd.equals(cartas.values()
                    .stream()
                    .filter(carta -> carta.getPosicaoTabuleiro() == posicaoTabuleiro)
                    .count());
        }

    private Carta getCartaAleatoriaLivre() {

    }


    private HashMap<Long,Carta> getCartasLivres() {
        return cartas.entrySet().stream()
                .filter(cartaEntry -> cartaEntry.getValue().getPosicaoTabuleiro().equals(PosicaoTabuleiro.BARALHO))
                .collect(Collectors.toMap())
    }
}





}
