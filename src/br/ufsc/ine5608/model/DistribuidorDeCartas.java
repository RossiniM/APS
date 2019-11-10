package br.ufsc.ine5608.model;

import br.ufsc.ine5608.shared.Mensagens;
import br.ufsc.ine5608.shared.PosicaoTabuleiro;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.LongStream;

import static br.ufsc.ine5608.shared.PosicaoTabuleiro.MESA;
import static java.util.stream.Collectors.toList;

class DistribuidorDeCartas {

    private Baralho baralho;

    DistribuidorDeCartas(Baralho baralho) {
        this.baralho = baralho;
    }

    void distribuirCartas(PosicaoTabuleiro posicaoTabuleiro, Long qtdCartasMaxima) {
        LongStream.rangeClosed(1, qtdCartasMaxima).forEach(it -> distribuirCarta().accept(posicaoTabuleiro));
    }

    private Consumer<PosicaoTabuleiro> distribuirCarta() {
        return posicaoTabuleiro -> {
            Carta carta = baralho.getCartaLivre();
            carta.setPosicaoTabuleiro(posicaoTabuleiro);
            baralho.atualizaCarta(carta.getId(), carta);
        };
    }

    List<Carta> filtrarCartasSelecionadas(Map<Long, Carta> cartasSelecionadas, PosicaoTabuleiro posicaoJogador) {
        return cartasSelecionadas.values()
                .stream()
                .filter(carta -> carta.getPosicaoTabuleiro().equals(posicaoJogador) || carta.getPosicaoTabuleiro().equals(MESA))
                .sorted()
                .collect(toList());
    }


    void atualizarCartas(Jogador jogador, List<Carta> cartasJogada) throws Exception {
        for (Carta carta : cartasJogada) {
            atualizaCartasJogador(jogador, carta);
            atualizaCartasMesa(jogador, carta);
        }
        adicionaCartaNovaNaPosicao(PosicaoTabuleiro.MESA);
        adicionaCartaNovaNaPosicao(jogador.getPosicao());
    }

    private void atualizaCartasJogador(Jogador jogador, Carta cartaJogador) {
        if (cartaJogador.getPosicaoTabuleiro().equals(jogador.getPosicao())) {
            cartaJogador.setPosicaoTabuleiro(PosicaoTabuleiro.USADA);
            baralho.atualizaCarta(cartaJogador.getId(), cartaJogador);
        }
    }

    private void atualizaCartasMesa(Jogador jogador, Carta cartaMesa) {
        if (cartaMesa.getPosicaoTabuleiro().equals(PosicaoTabuleiro.MESA)) {
            cartaMesa.setPosicaoTabuleiro(jogador.getPosicao());
            baralho.atualizaCarta(cartaMesa.getId(), cartaMesa);
        }
    }

    private void adicionaCartaNovaNaPosicao(PosicaoTabuleiro posicaoTabuleiro) throws Exception {
        if (baralho.temCartaLivre()) {
            Carta carta = baralho.getCartaLivre();
            carta.setPosicaoTabuleiro(posicaoTabuleiro);
            baralho.atualizaCarta(carta.getId(), carta);
        } else
            throw new Exception(Mensagens.CARTAS_ESGOTADAS);
    }
}
