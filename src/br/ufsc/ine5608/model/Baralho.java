package br.ufsc.ine5608.model;

import br.ufsc.ine5608.shared.Mensagens;
import br.ufsc.ine5608.shared.PosicaoTabuleiro;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Baralho {

    private Map<Long, Carta> cartas = new LinkedHashMap<>();
    private final int qtdCartasPorCor = 20;

    private final List<Color> cores = Arrays.asList(Color.red, Color.black);

    Baralho() {
        criarBaralho();
    }

    private void criarBaralho() {
        List<Carta> listaCartaTemp = new ArrayList<>();
        cores.forEach(cor -> criarCartas(cor, listaCartaTemp));
        persistirNosAtributos().accept(listaCartaTemp);
    }

    private void criarCartas(Color cor, List<Carta> listaCarta) {
        long idReferencia = listaCarta.size();
        listaCarta.addAll(Stream.generate(criaCarta(idReferencia, new AtomicLong(1), cor))
                .limit(qtdCartasPorCor)
                .collect(Collectors.toList()));
        Collections.shuffle(listaCarta);
    }

    private Supplier<Carta> criaCarta(Long idReferencia, AtomicLong numero, Color cor) {
        return () -> new Carta(numero.get() + idReferencia, numero.getAndIncrement(), cor);
    }

    private Consumer<List<Carta>> persistirNosAtributos() {
        LinkedHashMap<Long, Carta> linkedHashMap = new LinkedHashMap<>();
        return listaCartasTemp -> {
            listaCartasTemp.forEach(carta -> linkedHashMap.put(carta.getId(), carta));
            cartas.putAll(linkedHashMap);
        };
    }

    boolean temCartaLivre() {
        return cartas.entrySet().stream()
                .anyMatch(cartas -> cartas.getValue().getPosicaoTabuleiro().equals(PosicaoTabuleiro.BARALHO));
    }

    public List<Carta> getListaDeCartas() {
        return new ArrayList<>(cartas.values());
    }

    Carta getCartaLivre() {
        return cartas.values()
                .stream()
                .filter(carta -> carta.getPosicaoTabuleiro().equals(PosicaoTabuleiro.BARALHO))
                .findFirst()
                .orElseThrow(() -> new NullPointerException(Mensagens.CARTAS_ESGOTADAS));
    }

    void atualizaCarta(Long idCarta, Carta carta) {
        cartas.replace(idCarta, carta);
    }

    void setCartas(Map<Long, Carta> cartas) {
        this.cartas = cartas;
    }

    Map<Long, Carta> getCartas() {
        return cartas;
    }

}

