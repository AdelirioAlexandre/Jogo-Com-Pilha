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

        while(true) {
            interfaceJogo.mostrarTabuleiro(tabuleiro);

            if (tabuleiro.verificarVitoria()) {
                interfaceJogo.mostrarMensagemVitoria();
                break;
            }

            if (!interfaceJogo.perguntarSeDesejaContinuar()) {
                break;
            }

            realizarMovimento();
        }
    }

    private void realizarMovimento() {
        Movimento movimento = interfaceJogo.pedirMovimento();
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