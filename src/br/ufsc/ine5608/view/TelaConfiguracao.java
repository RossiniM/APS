package br.ufsc.ine5608.view;

import br.ufsc.ine5608.actor.AtorJogador;

import javax.swing.*;
import java.awt.*;

public class TelaConfiguracao extends JDialog {

    private JButton confirmarBotao;
    private AtorJogador atorJogador;

    public TelaConfiguracao(Frame frame, String title, boolean modal) {
        super(frame, title, modal);
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

        JLabel nomeLabel = new JLabel("Escolha a pontuação máxima do jogo.");
        confirmarBotao = new JButton("Ok");
        JPanel pontuacao = criarCheckBox();
        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);
        layout.setHorizontalGroup(
                layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(nomeLabel, 0, 280, 400))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(pontuacao, 0, 280,400 ))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(confirmarBotao, 0, 280, 400)));

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(nomeLabel, 0, 50, 100))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(pontuacao, 0, 50, 100))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(confirmarBotao, 0, 20, 100)));
        pack();

    }

    private void addListeners() {

    }

    private void mostraMensagem(String mensagem, int tipoMensagem) {
        JOptionPane.showMessageDialog(null, mensagem, "", tipoMensagem);
    }

    private JPanel criarCheckBox() {

        JPanel operacoes = new JPanel();
        ButtonGroup grupoBotoes = new ButtonGroup();
        for (int i = 10;i<=20;i+=5) {
            JRadioButton operacao = new JRadioButton(Integer.toString(i), false);
//            operacao.addActionListener(actionEvent -> atorJogador.getTabuleiro().setOperador(operadores));
            grupoBotoes.add(operacao);
            operacoes.add(operacao);
        }
        operacoes.setBorder(BorderFactory.createTitledBorder(BorderFactory.createTitledBorder( "")));

        return operacoes;
    }
}