package br.ufsc.ine5608;

import br.ufsc.ine5608.controller.MesaControlador;
import br.ufsc.ine5608.view.TelaPrincipal;

public class Main {

    public static void main(String[] args) {
        MesaControlador mesaControlador = MesaControlador.getInstance();
        TelaPrincipal telaPrincipal = new TelaPrincipal("ButterFLy");
        mesaControlador.inicializa(telaPrincipal);
    }
}
