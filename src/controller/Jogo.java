package src.controller;
import src.model.Movimento;
import src.model.MovimentoInvalidoException;
import src.model.TabuleiroPilhas;
import src.view.Interface;

public class Jogo {
    private final TabuleiroPilhas tabuleiro;
    private final Interface interfaceJogo;
    private final ControladorMovimento controladorMovimento;

    public Jogo() {
        this.tabuleiro = TabuleiroPilhas.getInstance();
        this.interfaceJogo = new Interface();
        this.controladorMovimento = new ControladorMovimento();
        this.tabuleiro.distribuirPecas();
    }

    // Loop principal do jogo, continua até que o jogador vença
    public void iniciar() {
        while (!tabuleiro.verificarVitoria()) {
            Movimento movimento = interfaceJogo.pedirMovimento(tabuleiro);

            if (movimento == null) {
                continue;
            }
            try {
                // Valida e realiza o movimento
                if (controladorMovimento.validarMovimento(movimento, tabuleiro)) {
                    if (!tabuleiro.mover(movimento)) {
                        interfaceJogo.mostrarErro("Movimento inválido!");
                    }
                }
            } catch (MovimentoInvalidoException e) {
                interfaceJogo.mostrarErro(e.getMessage());
            }
        }

        interfaceJogo.mostrarMensagemVitoria();
    }
}
