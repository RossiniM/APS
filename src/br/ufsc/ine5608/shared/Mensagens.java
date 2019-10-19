package br.ufsc.ine5608.shared;

import javax.swing.*;

public class Mensagens {

    public static final String CARTAS_ESGOTADAS = "Não há mais cartas livres disponíveis";
    public static final String CARTAS_SELECAO_NUMERO_ERRADA = "Necessario selecionar duas cartas da sua mao, uma carta da mesa e uma operacao";
    public static final String CARTAS_SELECAO_COR_ERRADA = "As cartas devem possuir a mesma cor";


    public static final String SUCESSO_CONEXAO = "Conexão bem sucedida!";
    public static final String ERRO_CONEXAO = "Não foi possível conectar";
    public static final String INFO_CONEXAO_PRONTA = "Você já está conectado";

    public static final String SUCESSO_DESCONEXAO = "Desconexão bem sucedida";
    public static final String INFO_DESCONEXAO_PRONTA = "Você já está desconectado";

    public static final String INFO_CONEXAO_NECESSARIA = "A partida já está em andamento, ou você não está conectado";
    public static final String INFO_CONEXAO_PERDIDA = "A conexão com o outro jogador foi perdida. Você  será desconectado da partida";


    public static final String OPERACAO_SUCESSO = "Operação bem sucedida";
    public static final String OPERACAO_ERRADA = "Operação incorreta";

    public static final String CAMPO_INVALIDO = "Campos inválido";

    public static void mostraMensagem(String mensagem,int tipoMensagem){
        JOptionPane.showMessageDialog(null, mensagem, "", tipoMensagem);
    }

}
