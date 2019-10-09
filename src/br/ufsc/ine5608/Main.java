package br.ufsc.ine5608;

import br.ufsc.ine5608.actor.AtorJogador;
import br.ufsc.ine5608.view.TelaPrincipal;

public class Main {

    public static void main(String[] args) {
       //Tela principal teste
        AtorJogador atorJogador = AtorJogador.getInstance();
        TelaPrincipal telaPrincipal = new TelaPrincipal("ButterFLy");
        atorJogador.inicializa(telaPrincipal);
    }
}
