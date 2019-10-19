package br.ufsc.ine5608.view;

import br.ufsc.ine5608.actor.AtorJogador;
import br.ufsc.ine5608.model.Carta;
import br.ufsc.ine5608.model.Jogador;
import br.ufsc.ine5608.model.Tabuleiro;
import br.ufsc.ine5608.shared.Mensagens;
import br.ufsc.ine5608.shared.OperadorMatematico;
import br.ufsc.ine5608.shared.PosicaoTabuleiro;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

import static br.ufsc.ine5608.shared.PosicaoTabuleiro.*;

public class TelaPrincipal extends JFrame {

    private JMenuBar menuBarra;
    private JMenuItem conectarItem;
    private JMenuItem iniciarPartidaItem;
    private JMenuItem desconectarItem;
    private JButton jogarBotao;
    private Container contentPane;

    private JPanel cartasJogador1;
    private JPanel cartasJogador2;
    private JPanel cartasMesa;

    private AtorJogador atorJogador;


    public TelaPrincipal(String s, AtorJogador atorJogador) throws HeadlessException {
        super(s);
        this.atorJogador = atorJogador;
        inicializa();
    }

    private void inicializa() {
        criaGUI();
        carregaLayout();
        adicionaListeners();
    }

    private void criaGUI() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(1024, 780);
    }


    private void carregaLayout() {

        contentPane = getContentPane();
        GroupLayout gl = new GroupLayout(contentPane);
        contentPane.setLayout(gl);

        gl.setAutoCreateContainerGaps(true);
        gl.setAutoCreateGaps(true);

        criaMenu();
        JPanel operacoes = criaOperacaoesRadioButton();
        JPanel placar = criaPlacar();

        jogarBotao = new JButton("Jogar");
        jogarBotao.setEnabled(atorJogador.ehMinhaVez());
        JPanel board = criaTabuleiro();
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
                                        .addComponent(jogarBotao)
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
                                        .addComponent(jogarBotao)
                        )
                        .addComponent(board, 0, 600, 780));
        pack();
    }

    private JPanel criaPlacar() {
        JPanel mostrador = new JPanel(new GridLayout(0, 3));

        JLabel labelNome = new JLabel("Nome:");

        JLabel labelpontos = new JLabel("Pontos: ");
        JLabel pontosJ1 = new JLabel("0");
        JLabel pontosJ2 = new JLabel("0");

        JLabel nomeJ1 = new JLabel("");
        JLabel nomeJ2 = new JLabel("");

        JLabel labelPontMaxima = new JLabel("Pontuacao Maxima");
        JLabel pontMaxima = new JLabel("");

        if (atorJogador.podeIniciarPartida()) {
            nomeJ1 = new JLabel(atorJogador.getTabuleiro().getJogadorNaPosicao(PosicaoTabuleiro.JOGADOR1).getNome());
            nomeJ2 = new JLabel(atorJogador.getTabuleiro().getJogadorNaPosicao(PosicaoTabuleiro.JOGADOR2).getNome());
            pontMaxima = new JLabel(Integer.toString(atorJogador.getTabuleiro().getPontuacaoMaxima()));
            pontosJ1 = new JLabel(Integer.toString(atorJogador.getTabuleiro().getJogadorNaPosicao(PosicaoTabuleiro.JOGADOR1).getPontuacao()));
            pontosJ2 = new JLabel(Integer.toString(atorJogador.getTabuleiro().getJogadorNaPosicao(PosicaoTabuleiro.JOGADOR2).getPontuacao()));
        }

        mostrador.add(labelNome);
        mostrador.add(nomeJ1);
        mostrador.add(nomeJ2);

        mostrador.add(labelpontos);
        mostrador.add(pontosJ1);
        mostrador.add(pontosJ2);
        mostrador.add(labelPontMaxima);
        mostrador.add(pontMaxima);

        mostrador.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLoweredSoftBevelBorder(), "Jogadores"));
        return mostrador;
    }

    private void criaMenu() {

        menuBarra = new JMenuBar();

        JMenu menu = new JMenu("Jogo");
        JMenu sobre = new JMenu("Sobre");
        conectarItem = new JMenuItem("Conectar");
        iniciarPartidaItem = new JMenuItem("Iniciar Partida");
        desconectarItem = new JMenuItem("Desconectar");
        JMenuItem sobreItem = new JMenuItem("Sobre");
        menu.add(conectarItem);
        menu.add(iniciarPartidaItem);
        menu.add(desconectarItem);
        sobre.add(sobreItem);
        atualizarMenuDesconectado();
        menuBarra.add(menu);
        menuBarra.add(sobre);
    }

    private JPanel criaTabuleiro() {

        JPanel tabuleiro = new JPanel(new BorderLayout());
        cartasJogador1 = new JPanel(new GridLayout(0, 2));
        cartasJogador2 = new JPanel(new GridLayout(0, 2));
        cartasMesa = new JPanel(new GridLayout(0, 3));
        if (atorJogador.podeIniciarPartida())
            carregaCartasTabuleiro();

        tabuleiro.setBackground(Color.gray);
        tabuleiro.setOpaque(true);
        tabuleiro.add(cartasMesa, BorderLayout.CENTER);
        tabuleiro.add(cartasJogador1, BorderLayout.LINE_START);
        tabuleiro.add(cartasJogador2, BorderLayout.LINE_END);


        return tabuleiro;
    }

    private void carregaCartasTabuleiro() {
        Tabuleiro tabuleiro = atorJogador.getTabuleiro();
        Jogador jogadorPosicao1 = atorJogador.getTabuleiro().getJogadorNaPosicao(JOGADOR1);
        Jogador jogadorPosicao2 = atorJogador.getTabuleiro().getJogadorNaPosicao(JOGADOR2);
        Collection<Carta> cartas = tabuleiro.getBaralho().getListaDeCartas();
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
        cartasJogador1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), jogadorPosicao1.getNome()));
        cartasJogador2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), jogadorPosicao2.getNome()));
    }


    private JToggleButton criaCarta(Carta carta) {
        JToggleButton cartaBtn = new JToggleButton(String.valueOf(carta.getNumero()));
        if (carta.getPosicaoTabuleiro() == MESA)
            cartaBtn.setBackground(Color.orange);
        if (carta.getPosicaoTabuleiro() != MESA)
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
        } else {
            removeDaPosicao(carta);
        }
    }

    private void alocaNaPosicao(Carta carta) {
        atorJogador.getTabuleiro().cartasSelecionada.put(carta.getId(), carta);
    }

    private void removeDaPosicao(Carta carta) {
        atorJogador.getTabuleiro().cartasSelecionada.remove(carta.getId());
    }

    private void adicionaListeners() {
        conectarItem.addActionListener(
                actionEvent -> {
                    new TelaConectar(this, "Conectar", true, atorJogador);
                    atualizarMenuConectado();
                }
        );
        iniciarPartidaItem.addActionListener(
                actionEvent -> atorJogador.iniciarPartida()
        );

        jogarBotao.addActionListener(actionEvent -> {
            try {
                atorJogador.jogar();
                JOptionPane.showMessageDialog(null, Mensagens.OPERACAO_SUCESSO, "", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "", JOptionPane.WARNING_MESSAGE);
                atorJogador.enviarJogada();
            }
        });
    }

    public void recarregaLayout() {
        contentPane.removeAll();
        contentPane = null;
        carregaLayout();
        adicionaListeners();
    }

    private JPanel criaOperacaoesRadioButton() {
        JPanel operacoes = new JPanel();
        ButtonGroup grupoBotoes = new ButtonGroup();
        for (OperadorMatematico operadores : OperadorMatematico.values()) {
            JRadioButton operacao = new JRadioButton(operadores.name(), false);
            operacao.addActionListener(actionEvent -> atorJogador.getTabuleiro().setOperador(operadores));
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
