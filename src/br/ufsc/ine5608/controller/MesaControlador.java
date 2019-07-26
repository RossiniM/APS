package br.ufsc.ine5608.controller;

import br.ufsc.ine5608.model.AtorJogador;
import br.ufsc.ine5608.model.AtorNetGames;
import br.ufsc.ine5608.model.Baralho;

import java.util.*;

public class MesaControlador {
    private static MesaControlador ourInstance = new MesaControlador();

    public static MesaControlador getInstance() {
        return ourInstance;
    }

    private AtorNetGames atorNetGames = new AtorNetGames();
    private HashMap<String, AtorJogador> jogadores;
    private boolean conectado = false;

    private MesaControlador() {
        jogadores = new HashMap<>();
    }

    private List<AtorJogador> atorJogadors = new ArrayList<>();
    private Baralho mainDeck = new Baralho();
    private Baralho visibleDecl = new Baralho();

    private void prepare() {
        //loadSettings
        //loadDeck
        // load atorJogadors
        //define turn
    }

    public void criaJogador(String nome) throws Exception {
        if (Objects.nonNull(nome) && !nome.isEmpty()) {
            jogadores.put(nome, new AtorJogador(nome));
            return;
        }
        throw new Exception("Campo invalido");
    }

    public boolean conectar(String nome) {
        if (jogadores.containsKey(nome))
            return atorNetGames.conectar(jogadores.get(nome));
        return false;
    }

    public void getBarallho() {
    }

    public void iniciarPartida(){
        atorNetGames.iniciarPartida();
    }

    public void carregaConfiguracaoInicial() {
        System.out.println("Conectou");
    }

    public void carregaTelaInicial() {
        System.out.println("Conectou");
    }
}
