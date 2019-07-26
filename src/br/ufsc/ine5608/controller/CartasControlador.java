package br.ufsc.ine5608.controller;

public class CartasControlador {
    private static CartasControlador ourInstance = new CartasControlador();

    public static CartasControlador getInstance() {
        return ourInstance;
    }

    private CartasControlador() {
    }
}
