package br.ufsc.ine5608.controller;

import br.ufsc.ine5608.model.AtorJogador;
import br.ufsc.ine5608.model.Baralho;

import java.util.ArrayList;
import java.util.List;

public class MesaControlador {
    private static MesaControlador ourInstance = new MesaControlador();

    public static MesaControlador getInstance() {
        return ourInstance;
    }

    private MesaControlador() {
    }

    private List<AtorJogador> atorJogadors = new ArrayList<>();
    private Baralho mainDeck = new Baralho();
    private Baralho visibleDecl = new Baralho();

    private void prepare(){
        //loadSettings
        //loadDeck
        // load atorJogadors
        //define turn
    }

    public Baralho getBarallho() {
    }

    public void carregaConfiguracaoInicial() {
    }

    public void carregaTelaInicial() {
    }
}
