package br.ufsc.ine5608.view;

import br.ufsc.ine5608.controller.CartasControlador;
import br.ufsc.ine5608.controller.MesaControlador;
import br.ufsc.ine5608.model.AtorJogador;
import br.ufsc.ine5608.model.Carta;
import br.ufsc.ine5608.shared.Mensagens;
import br.ufsc.ine5608.shared.OperadoresEnum;
import br.ufsc.ine5608.shared.PosicaoTabuleiro;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

import static br.ufsc.ine5608.shared.PosicaoTabuleiro.*;

public class TelaPrincipal extends JFrame {

    private JMenuBar menuBarra;
    private JMenu menu;
    private JMenu sobre;
    private JMenuItem conectarItem;
    private JMenuItem iniciarPartidaItem;
    private JMenuItem desconectarItem;
    private JMenuItem sobreItem;
    private JPanel board;
    private JPanel boardAtualizado;
    private JLabel labelNome;
    private JLabel nomeJ1;
    private JLabel nomeJ2;
    private JLabel labelpontos;
    private JLabel pontosJ1;
    private JLabel pontosJ2;
    private JButton jogar;
    private JPanel operacoes = new JPanel();
    private GroupLayout gl;
    private Container contentPane;


    JPanel cartasJogador1;
    JPanel cartasJogador2;
    JPanel cartasMesa;

    CartasControlador cartasControlador = CartasControlador.getInstance();
    MesaControlador mesaControlador = MesaControlador.getInstance();


    public TelaPrincipal(String s) throws HeadlessException {
        super(s);
        inicializa();

    }

    public void inicializa() {
        criaGUI();
        carregaLayout();
        adicionaListenners();
    }

    public void criaGUI() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(1024, 780);
    }


    public void carregaLayout() {

        contentPane = getContentPane();
        gl = new GroupLayout(contentPane);
        contentPane.setLayout(gl);

        gl.setAutoCreateContainerGaps(true);
        gl.setAutoCreateGaps(true);

        criaMenu();
        operacoes = criaOperacaoesRadioButton();
        JPanel placar = criaPlacar();

        jogar = new JButton("Jogar");
        jogar.setEnabled(mesaControlador.possoJogar());
        board = criaTabuleiro();
        gl.setHorizontalGroup(
                gl.createParallelGroup()
                        .addGroup(
                                gl.createSequentialGroup()
                                        .addComponent(menuBarra, 50, 80, 100)
                                        .addComponent(placar)
                        )
                        .addGroup(
                                gl.createSequentialGroup()
                                        .addComponent(operacoes)
                                        .addComponent(jogar)
                        )
                        .addComponent(board, 0, 700, 900));
        gl.setVerticalGroup(
                gl.createSequentialGroup()
                        .addGroup(
                                gl.createParallelGroup()
                                        .addComponent(menuBarra, 20, 30, 50)
                                        .addComponent(placar)
                        )
                        .addGroup(
                                gl.createParallelGroup()
                                        .addComponent(operacoes)
                                        .addComponent(jogar)
                        )
                        .addComponent(board, 0, 600, 780));
        pack();
    }

    private JPanel criaPlacar() {
        JPanel mostrador = new JPanel(new GridLayout(0, 3));

        labelNome = new JLabel("Nome:");

        labelpontos = new JLabel("Pontos: ");
        pontosJ1 = new JLabel("10");
        pontosJ2 = new JLabel("10");

        nomeJ1 = new JLabel("");
        nomeJ2 = new JLabel("");

        if(mesaControlador.podeIniciarPartida()){
            nomeJ1 = new JLabel(MesaControlador.getInstance().getJogador(PosicaoTabuleiro.JOGADOR1).getNome());
            nomeJ2 = new JLabel(MesaControlador.getInstance().getJogador(PosicaoTabuleiro.JOGADOR2).getNome());
        }


        mostrador.add(labelNome);
        mostrador.add(nomeJ1);
        mostrador.add(nomeJ2);

        mostrador.add(labelpontos);
        mostrador.add(pontosJ1);
        mostrador.add(pontosJ2);
        mostrador.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLoweredSoftBevelBorder(), "Jogadores"));
        return mostrador;
    }

    public void criaMenu() {

        menuBarra = new JMenuBar();

        menu = new JMenu("Jogo");
        sobre = new JMenu("Sobre");
        conectarItem = new JMenuItem("Conectar");
        iniciarPartidaItem = new JMenuItem("Iniciar Partida");
        desconectarItem = new JMenuItem("Desconectar");
        sobreItem = new JMenuItem("Sobre");
        menu.add(conectarItem);
        menu.add(iniciarPartidaItem);
        menu.add(desconectarItem);
        sobre.add(sobreItem);
        atualizarMenuDesconectado();
        menuBarra.add(menu);
        menuBarra.add(sobre);
    }

    public JPanel criaTabuleiro() {

        JPanel tabuleiro = new JPanel(new BorderLayout());
        cartasJogador1 = new JPanel(new GridLayout(0, 2));
        cartasJogador2 = new JPanel(new GridLayout(0, 2));
        cartasMesa = new JPanel(new GridLayout(0, 3));
        if(mesaControlador.podeIniciarPartida())
            carregaCartasTabuleiro();

        tabuleiro.setBackground(Color.gray);
        tabuleiro.setOpaque(true);
        tabuleiro.add(cartasMesa, BorderLayout.CENTER);
        tabuleiro.add(cartasJogador1, BorderLayout.LINE_START);
        tabuleiro.add(cartasJogador2, BorderLayout.LINE_END);


        return tabuleiro;
    }

    private void carregaCartasTabuleiro() {
        Collection<Carta> cartas = CartasControlador.getInstance().getCartas().values();
        cartas.forEach(carta -> {
            switch (carta.getPosicaoTabuleiro()) {
                case MESA:
                    cartasMesa.add(criaCarta(carta));
                    break;

                case JOGADOR1:
                    cartasJogador1.add(criaCarta(carta));
                    break;

                case JOGADOR2:
                    cartasJogador2.add(criaCarta(carta));
                    break;
            }
        });

        cartasMesa.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Cartas da mesa"));
        cartasJogador1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), mesaControlador.getJogador(JOGADOR1).getNome()));
        cartasJogador2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), mesaControlador.getJogador(JOGADOR2).getNome()));
    }


    private JToggleButton criaCarta(Carta carta) {
        JToggleButton cartaBtn = new JToggleButton(String.valueOf(carta.getNumero()));
        AtorJogador jogador = mesaControlador.getJogador();
        if(carta.getPosicaoTabuleiro() == MESA)
            cartaBtn.setBackground(Color.orange);
        if(carta.getPosicaoTabuleiro() != MESA)
            cartaBtn.setBackground(Color.white);
        cartaBtn.setForeground(carta.getCorCartaEnum());
        cartaBtn.setToolTipText(carta.getId().toString());
        cartaBtn.setSize(10, 200);
        cartaBtn.addActionListener(actionEvent -> registraCartasSelecionadas(carta, cartaBtn));
        return cartaBtn;
    }

    private void registraCartasSelecionadas(Carta carta, JToggleButton cartaBtn) {

        if (cartaBtn.isSelected()) {
            alocaNaPosicao(carta);
        } else
            removeDaPosicao(carta);
    }

    private void removeDaPosicao(Carta carta) {
        if (carta.getPosicaoTabuleiro() != MESA)
            mesaControlador.cartaJogadorSelecionada.remove(carta.getId());
        else
            mesaControlador.cartaMesaSelecionada.remove(carta.getId());
    }

    private void alocaNaPosicao(Carta carta) {
        if (carta.getPosicaoTabuleiro() != MESA)
            mesaControlador.cartaJogadorSelecionada.put(carta.getId(), carta);
        else
            mesaControlador.cartaMesaSelecionada.put(carta.getId(), carta);
    }

    public void adicionaListenners() {
        conectarItem.addActionListener(
                actionEvent -> {
                    new TelaConectar(this, "Conectar", true);
                    atualizarMenuConectado();
                }
        );
        iniciarPartidaItem.addActionListener(
                actionEvent -> {mesaControlador.iniciarPartida();}
        );

        jogar.addActionListener(actionEvent -> {
            try {
                if (mesaControlador.realizarJogada()) {
                    JOptionPane.showMessageDialog(null, Mensagens.OPERACAO_SUCESSO, "", JOptionPane.INFORMATION_MESSAGE);
                    recarregaLayout();
                }
                else
                    JOptionPane.showMessageDialog(null, Mensagens.OPERACAO_ERRADA, "", JOptionPane.WARNING_MESSAGE);
                mesaControlador.enviaJogada();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public void recarregaLayout(){
        contentPane.removeAll();
        contentPane = null;
        carregaLayout();
        adicionaListenners();
    }

    private JPanel criaOperacaoesRadioButton() {
        JPanel operacoes = new JPanel();
        ButtonGroup grupoBotoes = new ButtonGroup();
        for (OperadoresEnum operadoresEnum : OperadoresEnum.values()) {
            JRadioButton operacao = new JRadioButton(operadoresEnum.name(), false);
            operacao.addActionListener(actionEvent -> mesaControlador.operadoresEnum = operadoresEnum);
            grupoBotoes.add(operacao);
            operacoes.add(operacao);
        }
        return operacoes;
    }


    private void atualizarMenuConectado() {
        //todo colocar condicao de conectado
        conectarItem.setEnabled(false);
        iniciarPartidaItem.setEnabled(true);
        desconectarItem.setEnabled(true);
    }


    private void atualizarMenuDesconectado() {
        //todo colocar condicao de desconectado

        conectarItem.setEnabled(true);
        iniciarPartidaItem.setEnabled(false);
        desconectarItem.setEnabled(false);
    }


    public void mostra() {
        setVisible(true);
    }
}
