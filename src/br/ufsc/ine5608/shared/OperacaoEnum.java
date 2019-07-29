package br.ufsc.ine5608.shared;

public enum OperacaoEnum {
    SOMA("Soma"), SUBTRACAO("Subtracao"), MULTIPLICACAO("Multiplicacao"), DIVISAO("Divisao");


    private String operacao;

    OperacaoEnum(String operacao) {
        this.operacao = operacao;
    }

    public String getOperacao() {
        return operacao;
    }


}
