package br.ufsc.ine5608.view;

import br.ufsc.ine5608.actor.AtorJogador;
import br.ufsc.ine5608.shared.Mensagens;

import javax.swing.*;
import java.awt.*;

import static br.ufsc.ine5608.shared.Mensagens.mostraMensagem;

class TelaConectar extends JDialog {


    private JTextField nomeCampo;
    private JButton conectarBotao;
    private JButton cancelarBotao;
    private AtorJogador atorJogador;

    TelaConectar(Frame frame, String title, boolean modal, AtorJogador atorJogador) {
        super(frame, title, modal);
        this.atorJogador = atorJogador;
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        inicializaForm();
    }

    private void inicializaForm() {
        carregaLayout();
        addListeners();
        setVisible(true);
    }

    private void carregaLayout() {

        Container panel = getContentPane();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);

        JLabel nomeLabel = new JLabel("Nome:");
        nomeCampo = new JTextField();
        conectarBotao = new JButton("Conectar");
        cancelarBotao = new JButton("Cancelar");
        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);
        layout.setHorizontalGroup(
                layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(nomeLabel, 0, 40, 100)
                                .addComponent(nomeCampo, 0, 100, 200))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(conectarBotao, 0, 100, 200)
                                .addComponent(cancelarBotao, 0, 100, 200)));


        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup()
                                .addComponent(nomeLabel, 0, 30, 100)
                                .addComponent(nomeCampo, 0, 30, 200))
                        .addGroup(layout.createParallelGroup()
                                .addComponent(conectarBotao, 0, 30, 100)
                                .addComponent(cancelarBotao, 0, 30, 100)));
        pack();

    }

    private void addListeners() {

        conectarBotao.addActionListener(actionEvent -> {
            try {
                atorJogador.getTabuleiro().criaJogador(nomeCampo.getText());
                   if (!atorJogador.conectar())
                       throw new Exception(Mensagens.ERRO_CONEXAO);
                   this.dispose();
                   mostraMensagem(Mensagens.OPERACAO_SUCESSO,JOptionPane.OK_OPTION);
               } catch (Exception e) {
                mostraMensagem(e.getMessage(),JOptionPane.ERROR_MESSAGE);
            }
        });
        cancelarBotao.addActionListener(actionEvent -> dispose());
    }


}
