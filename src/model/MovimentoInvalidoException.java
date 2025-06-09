package src.model;

//Classe de exceção personalizada para movimentos inválidos no jogo
public class MovimentoInvalidoException extends Exception {
    public MovimentoInvalidoException(String mensagem) {
        super(mensagem);
    }
}
