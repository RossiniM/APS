package br.ufsc.ine5608.model;

import br.ufsc.ine5608.controller.MesaControlador;
import br.ufsc.inf.leobr.cliente.Jogada;

public class AtorJogador {

    private int pontuacao;
    private Baralho baralho;
    private String nome;
    private boolean turno;
    private AtorNetGames atorNetGames;
    private MesaControlador mesaControlador;

    public AtorJogador(String nome, Baralho baralho) {
        mesaControlador = MesaControlador.getInstance();
        atorNetGames = new AtorNetGames(this);
        mesaControlador.carregaTelaInicial();
    }

    public void carregarConfiguracaoInicial(Integer posicao) {
        mesaControlador.carregaConfiguracaoInicial();
//        this.baralho = mesaControlador.getBarallho();
    }

    public boolean realizarConexao(String nome){
        this.nome = nome;
        return atorNetGames.conectar(nome);
    }
    public boolean iniciarPartida(){
        if(!atorNetGames.isConectado())return  false;
        atorNetGames.iniciarPartida();
        atorNetGames.setEsperandoJogador(true);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void receberJogada(Jogada jogada){

    }

    public void enviarJogada(Jogada jogada){

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
