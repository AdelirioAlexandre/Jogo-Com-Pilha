import java.util.Stack;
public class ControladorMovimento {
    private int ultimaPilhaDestino = -1;
    private String ultimaCorMovida = null;
    private boolean primeiroMovimento = true;
    
    public boolean validarMovimento(Movimento movimento, TabuleiroPilhas tabuleiro) throws MovimentoInvalidoException {
        validarNumeroPilha(movimento.getOrigem());
        validarNumeroPilha(movimento.getDestino());
        
        Stack<String> pilhaOrigem = tabuleiro.getPilha(movimento.getOrigem());
        Stack<String> pilhaDestino = tabuleiro.getPilha(movimento.getDestino());
        
        if (pilhaOrigem.isEmpty()) {
            throw new MovimentoInvalidoException("A pilha de origem está vazia!");
        }
        
        if (pilhaDestino.size() > 7) {
            throw new MovimentoInvalidoException("Não há espaço suficiente na pilha de destino!");
        }
        
        if (!primeiroMovimento) {
            validarMovimentoRepetido(movimento, pilhaOrigem);
        }
        
        atualizarUltimoMovimento(movimento, pilhaOrigem.peek());
        return true;
    }
    
    private void validarNumeroPilha(int numeroPilha) throws MovimentoInvalidoException {
        if (numeroPilha < 1 || numeroPilha > 7) {
            throw new MovimentoInvalidoException("Número de pilha inválido: deve ser entre 1 e 7");
        }
    }
    
    private void validarMovimentoRepetido(Movimento movimento, Stack<String> pilhaOrigem) throws MovimentoInvalidoException {
        if (movimento.getDestino() == ultimaPilhaDestino) {
            throw new MovimentoInvalidoException("Você não pode mover para a mesma pilha do último movimento!");
        }
        
        if (movimento.getOrigem() == ultimaPilhaDestino && 
            pilhaOrigem.peek().equals(ultimaCorMovida)) {
            throw new MovimentoInvalidoException("Você não pode mover o item que acabou de mover!");
        }
    }
    
    private void atualizarUltimoMovimento(Movimento movimento, String cor) {
        movimento.getOrigem();
        ultimaPilhaDestino = movimento.getDestino();
        ultimaCorMovida = cor;
        primeiroMovimento = false;
    }
}