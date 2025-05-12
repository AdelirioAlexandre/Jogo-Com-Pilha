package controller;

import model.Movimento;
import model.TabuleiroPilhas;
import view.Interface;

public class Jogo {
    private final TabuleiroPilhas tabuleiro;
    private final Interface interfaceJogo;

    public Jogo() {
        tabuleiro = new TabuleiroPilhas();
        interfaceJogo = new Interface();
    }

    public void iniciar() {
        tabuleiro.distribuirPecas();

        while (!tabuleiro.verificarVitoria()) {
            Movimento movimento = interfaceJogo.pedirMovimento(tabuleiro);
            
            if (movimento == null) {
                continue;
            }

            if (!realizarMovimento(movimento)) {
                verificarEExibirErro(movimento);
            }
        }

        interfaceJogo.mostrarMensagemVitoria();
    }

    private boolean realizarMovimento(Movimento movimento) {
        return tabuleiro.mover(movimento);
    }

    private void verificarEExibirErro(Movimento movimento) {
        if (isMovimentoRepetido(movimento)) {
            interfaceJogo.mostrarErro("Você não pode mover o mesmo item duas vezes seguidas!");
        } else {
            interfaceJogo.mostrarErro("Movimento inválido!");
        }
    }

    private boolean isMovimentoRepetido(Movimento movimento) {
        int origem = movimento.getOrigem() - 1;
        return origem >= 0 
            && origem < tabuleiro.getPilhas().size() 
            && !tabuleiro.getPilhas().get(origem).isEmpty() 
            && tabuleiro.getPilhas().get(origem).peek().equals(tabuleiro.getUltimoItemMovido());
    }
}