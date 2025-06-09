package src.controller;
import src.model.Movimento;
import src.model.MovimentoInvalidoException;
import src.model.TabuleiroPilhas;
public class ControladorMovimento {
    private int ultimaPilhaDestino = -1;
    private String ultimaCorMovida = null;
    private boolean primeiroMovimento = true;

    //Valida o movimento do jogador, verificando se é possível realizar a ação
    public boolean validarMovimento(Movimento movimento, TabuleiroPilhas tabuleiro) throws MovimentoInvalidoException {
        validarNumeroPilha(movimento.getOrigem());
        validarNumeroPilha(movimento.getDestino());

        if (tabuleiro.getPilha(movimento.getOrigem()).isEmpty()) {
            throw new MovimentoInvalidoException("A pilha de origem está vazia!");
        }

        if (!tabuleiro.podeAdicionarItem(movimento.getDestino())) {
            throw new MovimentoInvalidoException("Não há espaço suficiente na pilha de destino!");
        }

        String corAtual = tabuleiro.getPilha(movimento.getOrigem()).peek();

        if (!primeiroMovimento) {
            validarMovimentoRepetido(movimento, corAtual);
        }

        atualizarUltimoMovimento(movimento, corAtual);
        return true;
    }

     // Verifica se os índices das pilhas são válidos
    private void validarNumeroPilha(int numeroPilha) throws MovimentoInvalidoException {
        if (numeroPilha < 1 || numeroPilha > 7) {
            throw new MovimentoInvalidoException("Número de pilha inválido: deve ser entre 1 e 7");
        }
    }

    // Verifica se o movimento é repetido
    private void validarMovimentoRepetido(Movimento movimento, String corAtual) throws MovimentoInvalidoException {
        if (movimento.getOrigem() == ultimaPilhaDestino && corAtual.equals(ultimaCorMovida)) {
            throw new MovimentoInvalidoException("Você não pode mover o item que acabou de mover!");
        }
    }

    //Aatualizar o estado do último movimento realizado
    private void atualizarUltimoMovimento(Movimento movimento, String cor) {
        ultimaPilhaDestino = movimento.getDestino();
        ultimaCorMovida = cor;
        primeiroMovimento = false;
    }
}