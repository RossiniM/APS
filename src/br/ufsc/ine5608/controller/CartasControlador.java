package br.ufsc.ine5608.controller;

import br.ufsc.ine5608.model.Baralho;
import br.ufsc.ine5608.model.Carta;
import br.ufsc.ine5608.shared.OperacaoEnum;
import br.ufsc.ine5608.shared.PosicaoTabuleiro;

import java.awt.*;
import java.util.*;
import java.util.List;

import static java.awt.Color.*;
import static java.util.stream.Collectors.toList;

public class CartasControlador {
    private static CartasControlador ourInstance = new CartasControlador();

    private final int qtdCartasCor = 12;
    private final int qtdTotalCartas = 36;
    private final ArrayList<Color> cores = new ArrayList<Color>(Arrays.asList(black, red, blue));

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
            Carta carta = baralho.getCartas().get(geraNumeroRandomico());
            if (Objects.equals(carta.getPosicaoTabuleiro(), PosicaoTabuleiro.BARALHO)) {
                carta.setPosicaoTabuleiro(posicaoTabuleiro);
                i++;
            }
        }
    }

    public boolean verificaOperacao(Carta cartaJogador1, Carta cartaJogador2, Carta cartaMesa, OperacaoEnum operacao) {

        Long carta1 = cartaJogador1.getNumber();
        Long carta2 = cartaJogador2.getNumber();
        Long carta3 = cartaMesa.getNumber();

        switch (operacao.getOperacao()) {
            case "Soma":
                return carta2 + carta1 == carta3;

            case "Subtracao":
                return carta2 - carta1 == carta3;

            case "Multiplicacao":
                return carta2 * carta1 == carta3;

            case "Divisao":
                return carta2 / carta1 == carta3;
            default:
                return false;
        }
    }

    private long geraNumeroRandomico() {
        return 1 + Math.round(Math.random() * (qtdTotalCartas - 1));
    }

}
