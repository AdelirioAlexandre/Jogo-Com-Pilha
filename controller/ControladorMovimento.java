package controller;

import model.Movimento;
import model.MovimentoInvalidoException;
import model.TabuleiroPilhas;

import java.util.Stack;

public class ControladorMovimento {
    private int ultimaPilhaDestino = -1;
    private String ultimaCorMovida = null;
    private boolean primeiroMovimento = true;

    public boolean validarMovimento(Movimento movimento, TabuleiroPilhas tabuleiro) throws MovimentoInvalidoException {
        validarNumeroPilha(movimento.getOrigem());
        validarNumeroPilha(movimento.getDestino());

        // Apenas verificar se a pilha está vazia
        if (tabuleiro.getPilha(movimento.getOrigem()).isEmpty()) {
            throw new MovimentoInvalidoException("A pilha de origem está vazia!");
        }

        // Verificar tamanho da pilha destino
        if (tabuleiro.getPilha(movimento.getDestino()).size() > 7) {
            throw new MovimentoInvalidoException("Não há espaço suficiente na pilha de destino!");
        }

        if (!primeiroMovimento) {
            String corAtual = tabuleiro.getPilha(movimento.getOrigem()).peek();
            validarMovimentoRepetido(movimento, corAtual);
        }

        String corMovimento = tabuleiro.getPilha(movimento.getOrigem()).peek();
        atualizarUltimoMovimento(movimento, corMovimento);
        return true;
    }

    private void validarNumeroPilha(int numeroPilha) throws MovimentoInvalidoException {
        if (numeroPilha < 1 || numeroPilha > 7) {
            throw new MovimentoInvalidoException("Número de pilha inválido: deve ser entre 1 e 7");
        }
    }

    private void validarMovimentoRepetido(Movimento movimento, String corAtual) throws MovimentoInvalidoException {
        if (movimento.getDestino() == ultimaPilhaDestino) {
            throw new MovimentoInvalidoException("Você não pode mover para a mesma pilha do último movimento!");
        }

        if (movimento.getOrigem() == ultimaPilhaDestino && corAtual.equals(ultimaCorMovida)) {
            throw new MovimentoInvalidoException("Você não pode mover o item que acabou de mover!");
        }
    }

    private void atualizarUltimoMovimento(Movimento movimento, String cor) {
        ultimaPilhaDestino = movimento.getDestino();
        ultimaCorMovida = cor;
        primeiroMovimento = false;
    }
}