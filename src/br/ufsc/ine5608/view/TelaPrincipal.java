package br.ufsc.ine5608.view;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {

    private JMenuBar menuBarra;
    private JMenu menu;
    private JMenu sobre;
    private JMenuItem conecartItem;
    private JMenuItem iniciarPartidaItem;
    private JMenuItem desconectarItem;
    private JMenuItem sobreItem;
    private JLabel board;
    private JLabel labelNome;
    private JLabel nomeJ1;
    private JLabel labelJ2;
    private JLabel nomeJ2;
    private JLabel labelpontos;
    private JLabel pontosJ1;
    private JLabel labelpontosJ2;
    private JLabel pontosJ2;

    private ImageIcon boardImg;


    public TelaPrincipal(String s) throws HeadlessException {
        super(s);
        inicializa();

    }

    public void inicializa() {
        criaGUI();
        carregaLayout();
    }

    public void criaGUI() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(1024, 780);
    }



    public void carregaLayout() {

        Container contentPane = getContentPane();
        GroupLayout gl = new GroupLayout(contentPane);
        contentPane.setLayout(gl);

        gl.setAutoCreateContainerGaps(true);
        gl.setAutoCreateGaps(true);

        criaMenu();
        JPanel placar = criaPlacar();

        boardImg = new ImageIcon((getClass().getResource("../img/board.jpg")));
        board = new JLabel(boardImg);
        gl.setHorizontalGroup(
                gl.createParallelGroup()
                        .addGroup(
                                gl.createSequentialGroup()
                                        .addComponent(menuBarra, 50, 80, 100)
                                        .addComponent(placar)
                                )
                        .addComponent(board, 0, 700, 900));
        gl.setVerticalGroup(
                gl.createSequentialGroup()
                .addGroup(
                        gl.createParallelGroup()
                                .addComponent(menuBarra, 20, 30, 50)
                                .addComponent(placar)
                )
                .addComponent(board, 0, 600, 780));
        pack();
    }

    private JPanel criaPlacar(){
        JPanel mostrador = new JPanel(new GridLayout(0, 3));

        labelNome = new JLabel("Nome:");
        nomeJ1 = new JLabel("Rossini");
        nomeJ2 = new JLabel("Raphael");
        labelpontos = new JLabel("Pontos: ");
        pontosJ1 = new JLabel("10");
        pontosJ2 = new JLabel("10");

        mostrador.add(labelNome);
        mostrador.add(nomeJ1);
        mostrador.add(nomeJ2);

        mostrador.add(labelpontos);
        mostrador.add(pontosJ1);
        mostrador.add(pontosJ2);
        mostrador.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLoweredSoftBevelBorder(),"Jogadores"));
        return mostrador;
    }

    public void criaMenu() {

        menuBarra = new JMenuBar();

        menu = new JMenu("Jogo");
        sobre = new JMenu("Sobre");
        conecartItem = new JMenuItem("Conectar");
        iniciarPartidaItem = new JMenuItem("Iniciar Partida");
        desconectarItem = new JMenuItem("Desconectar");
        sobreItem = new JMenuItem("Sobre");

        menu.add(conecartItem);
        menu.add(iniciarPartidaItem);
        menu.add(desconectarItem);
        sobre.add(sobreItem);
        menuBarra.add(menu);
        menuBarra.add(sobre);

    }

    public void adicionaListenners() {

    }

    public void mostra() {
        setVisible(true);
    }
}
