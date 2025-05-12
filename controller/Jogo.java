package controller;

import model.Movimento;
import model.MovimentoInvalidoException;
import model.TabuleiroPilhas;
import view.Interface;

public class Jogo {
    private final TabuleiroPilhas tabuleiro;
    private final Interface interfaceJogo;
    private final ControladorMovimento controladorMovimento;

    public Jogo() {
        this.tabuleiro = new TabuleiroPilhas();
        this.interfaceJogo = new Interface();
        this.controladorMovimento = new ControladorMovimento();
    }

    public void iniciar() {
        tabuleiro.distribuirPecas();

        while (true) {
            if (tabuleiro.verificarVitoria()) {
                interfaceJogo.mostrarMensagemVitoria();
                break;
            }

            realizarMovimento();
        }
    }

    private void realizarMovimento() {
        Movimento movimento = interfaceJogo.pedirMovimento(tabuleiro);
        if (movimento == null) return;

        try {
            if (controladorMovimento.validarMovimento(movimento, tabuleiro)) {
                tabuleiro.executarMovimento(movimento);
            }
        } catch (MovimentoInvalidoException e) {
            interfaceJogo.mostrarErro(e.getMessage());
        }
    }
}