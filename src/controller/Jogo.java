package src.controller;

import src.model.Movimento;
import src.model.TabuleiroPilhas;
import src.view.Interface;

public class Jogo {
    private final TabuleiroPilhas tabuleiro;
    private final Interface interfaceJogo;

    public Jogo() {
        this.tabuleiro = TabuleiroPilhas.getInstance();
        this.interfaceJogo = new Interface();
        this.tabuleiro.distribuirPecas();
    }

    public void iniciar() {
        while (!tabuleiro.verificarVitoria()) {
            Movimento movimento = interfaceJogo.pedirMovimento(tabuleiro);

            if (movimento == null) {
                continue;
            }

            if (!tabuleiro.mover(movimento)) {
                interfaceJogo.mostrarErro("Movimento inválido!");
                continue;
            }
        }

        interfaceJogo.mostrarMensagemVitoria();
    }
}