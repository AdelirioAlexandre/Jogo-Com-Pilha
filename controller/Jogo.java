package controller;

import model.Movimento;
import model.TabuleiroPilhas;
import view.Interface;

public class Jogo {
    private TabuleiroPilhas tabuleiro;
    private Interface interfaceJogo;

    public Jogo() {
        tabuleiro = new TabuleiroPilhas();
        interfaceJogo = new Interface();
    }

    public void iniciar() {
        tabuleiro.distribuirPecas();

        while (!tabuleiro.verificarVitoria()) {
            Movimento movimento = interfaceJogo.pedirMovimento(tabuleiro);
            
            if (movimento == null) {
                continue; // Volta ao início do loop se o movimento for nulo
            }

            realizarMovimento(movimento);
        }

        interfaceJogo.mostrarMensagemVitoria();
    }

    private void realizarMovimento(Movimento movimento) {
        if (!tabuleiro.mover(movimento)) { // Aqui mudamos de executarMovimento para mover
            if (movimento.getOrigem() - 1 >= 0 
                && movimento.getOrigem() - 1 < tabuleiro.getPilhas().size() 
                && !tabuleiro.getPilhas().get(movimento.getOrigem() - 1).isEmpty() 
                && tabuleiro.getPilhas().get(movimento.getOrigem() - 1).peek().equals(tabuleiro.getUltimoItemMovido())) {
                interfaceJogo.mostrarErro("Você não pode mover o mesmo item duas vezes seguidas!");
            } else {
                interfaceJogo.mostrarErro("Movimento inválido!");
            }
        }
    }
}