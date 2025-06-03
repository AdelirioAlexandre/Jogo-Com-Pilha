package src.model;

public class Movimento {
    private final int origem;
    private final int destino;

    public Movimento(int origem, int destino) {
        this.origem = origem;
        this.destino = destino;
    }

    public int getOrigem() {
        return origem;
    }

    public int getDestino() {
        return destino;
    }
}