package br.ufsc.ine5608.model;

import br.ufsc.ine5608.shared.Mensagens;
import br.ufsc.ine5608.shared.OperadorMatematico;

import java.util.List;
import java.util.function.BiPredicate;

import static br.ufsc.ine5608.shared.PosicaoTabuleiro.MESA;

class ValidadorDeOperacao {

    private Jogador jogador;
    private final int PRIMEIRA_CARTA_JOGADOR = 0;
    private final int SEGUNDA_CARTA_JOGADOR = 1;
    private final int CARTA_MESA = 2;

    ValidadorDeOperacao(Jogador jogador) {
        this.jogador = jogador;
    }

    boolean jogadaEhValida(List<Carta> cartasJogada, OperadorMatematico operacao) throws Exception {
        if (numeroCartasJogadaEhValida(cartasJogada))
            if (corEhValida(cartasJogada.get(PRIMEIRA_CARTA_JOGADOR), cartasJogada.get(SEGUNDA_CARTA_JOGADOR), cartasJogada.get(CARTA_MESA))) {
                if (operacaoEhValida().test(cartasJogada, operacao)) {
                    atualizaPontuacao(operacao);
                    return true;
                }
                throw new Exception(Mensagens.OPERACAO_ERRADA);
            }

        throw new Exception(Mensagens.CARTAS_SELECAO_NUMERO_ERRADA);
    }

    private void atualizaPontuacao(OperadorMatematico operacao) {

        switch (operacao) {
            case MULTIPLICACAO:
                incrementaPontuacao(2);
                break;
            case DIVISAO:
                incrementaPontuacao(3);
                break;
            default:
                incrementaPontuacao(1);
        }
    }

    private void incrementaPontuacao(int pontuacao) {
        jogador.setPontuacao(jogador.getPontuacao() + pontuacao);
    }

    private boolean numeroCartasJogadaEhValida(List<Carta> cartasJogada) {
        return cartasJogada.stream().filter(carta -> carta.getPosicaoTabuleiro() == jogador.getPosicao()).count() == 2
                &&
                cartasJogada.stream().filter(carta -> carta.getPosicaoTabuleiro() == MESA).count() == 1;
    }

    private boolean corEhValida(Carta mesa, Carta carta1, Carta carta2) throws Exception {
        if (mesa.getCorCartaEnum().equals(carta1.getCorCartaEnum()) && carta1.getCorCartaEnum().equals(carta2.getCorCartaEnum()))
            return true;
        throw new Exception(Mensagens.CARTAS_SELECAO_COR_ERRADA);
    }

    private BiPredicate<List<Carta>, OperadorMatematico> operacaoEhValida() {
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


}
