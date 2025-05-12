package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class TabuleiroPilhas {
    private static final int NUMERO_PILHAS = 7;
    private static final int PECAS_POR_COR = 7;
    private static final String[] CORES = {"R", "G", "B", "Y", "P", "L"};
    
    private final List<Stack<String>> pilhas;
    private String ultimoItemMovido;

    public TabuleiroPilhas() {
        pilhas = new ArrayList<>(NUMERO_PILHAS);
        for (int i = 0; i < NUMERO_PILHAS; i++) {
            pilhas.add(new Stack<>());
        }
    }

    public void distribuirPecas() {
        List<String> pecas = new ArrayList<>(NUMERO_PILHAS * CORES.length);
        
        // Adicionar peças de cada cor
        for (String cor : CORES) {
            for (int i = 0; i < PECAS_POR_COR; i++) {
                pecas.add(cor);
            }
        }

        Collections.shuffle(pecas);

        // Distribuir as peças
        for (int i = 0; i < pecas.size(); i++) {
            pilhas.get(i % NUMERO_PILHAS).push(pecas.get(i));
        }
    }

    public boolean mover(Movimento movimento) {
        if (movimento == null) {
            return false;
        }

        int origem = movimento.getOrigem() - 1;
        int destino = movimento.getDestino() - 1;

        if (!isIndiceValido(origem) || !isIndiceValido(destino)) {
            return false;
        }

        Stack<String> pilhaOrigem = pilhas.get(origem);
        if (pilhaOrigem.isEmpty()) {
            return false;
        }

        Stack<String> pilhaDestino = pilhas.get(destino);
        String item = pilhaOrigem.pop();
        pilhaDestino.push(item);
        ultimoItemMovido = item;
        return true;
    }

    private boolean isIndiceValido(int indice) {
        return indice >= 0 && indice < NUMERO_PILHAS;
    }

    public List<Stack<String>> getPilhas() {
        List<Stack<String>> copiasPilhas = new ArrayList<>(NUMERO_PILHAS);
        for (Stack<String> pilha : pilhas) {
            Stack<String> copia = new Stack<>();
            copia.addAll(pilha);
            copiasPilhas.add(copia);
        }
        return copiasPilhas;
    }

    public void adicionarItem(int numeroPilha, String item) {
        if (isIndiceValido(numeroPilha - 1) && item != null) {
            pilhas.get(numeroPilha - 1).push(item);
        }
    }

    public boolean verificarVitoria() {
        return pilhas.stream()
                .filter(pilha -> !pilha.isEmpty())
                .allMatch(pilha -> pilha.stream()
                        .distinct()
                        .count() == 1);
    }

    public String getUltimoItemMovido() {
        return ultimoItemMovido;
    }

    public Stack<String> getPilha(int numeroPilha) {
        if (!isIndiceValido(numeroPilha - 1)) {
            return new Stack<>();
        }
        Stack<String> copia = new Stack<>();
        copia.addAll(pilhas.get(numeroPilha - 1));
        return copia;
    }
}