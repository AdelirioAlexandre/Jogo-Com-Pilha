package src.model;
public class Movimento {
    private final int origem;
    private final int destino;

    // Construtor da classe Movimento
    public Movimento(int origem, int destino) {
        this.origem = origem;
        this.destino = destino;
    }

    // Getters e Setters para os atributos origem e destino
    public int getOrigem() {
        return origem;
    }

    public int getDestino() {
        return destino;
    }
}