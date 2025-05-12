package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class TabuleiroPilhas {
    private List<Stack<String>> pilhas;
    private String ultimoItemMovido; // Adicionado novamente

    public TabuleiroPilhas() {
        pilhas = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            pilhas.add(new Stack<>());
        }
    }

    public void distribuirPecas() {
        // Criar lista com todas as peças
        List<String> pecas = new ArrayList<>();
        
        // Adicionar 5 peças de cada cor (totalizando 35 peças)
        for (int i = 0; i < 7; i++) {
            pecas.add("R"); // Vermelho
            pecas.add("G"); // Verde
            pecas.add("B"); // Azul
            pecas.add("Y"); // Amarelo
            pecas.add("P"); // Rosa
            pecas.add("L"); // Preto
        }

        // Embaralhar as peças
        Collections.shuffle(pecas);

        // Distribuir as peças entre todas as pilhas
        // Inicialmente, colocar 5 peças em cada uma das 5 primeiras pilhas
        for (int i = 0; i < pecas.size(); i++) {
            int pilhaDestino = i / 7; // Distribui 5 peças por pilha
            pilhas.get(pilhaDestino).push(pecas.get(i));
        }
    }

    public boolean mover(Movimento movimento) {
        int origem = movimento.getOrigem() - 1;
        int destino = movimento.getDestino() - 1;

        // Validação básica dos índices
        if (origem < 0 || origem >= pilhas.size() || destino < 0 || destino >= pilhas.size()) {
            return false;
        }

        Stack<String> pilhaOrigem = pilhas.get(origem);
        Stack<String> pilhaDestino = pilhas.get(destino);

        // Verifica se a pilha de origem está vazia
        if (pilhaOrigem.isEmpty()) {
            return false;
        }

        // Realiza o movimento
        String item = pilhaOrigem.pop();
        pilhaDestino.push(item);
        ultimoItemMovido = item; // Atualiza o último item movido
        return true;
    }

    public List<Stack<String>> getPilhas() {
        return pilhas;
    }

    public void adicionarItem(int numeroPilha, String item) {
        pilhas.get(numeroPilha - 1).push(item);
    }

    public boolean verificarVitoria() {
        // Verifica apenas as pilhas que contêm peças
        for (Stack<String> pilha : pilhas) {
            if (!pilha.isEmpty()) {
                String primeiraCor = pilha.get(0);
                // Verifica se todas as peças na pilha são da mesma cor
                for (String peca : pilha) {
                    if (!peca.equals(primeiraCor)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // Método adicionado novamente para resolver o erro
    public String getUltimoItemMovido() {
        return ultimoItemMovido;
    }
    public Stack<String> getPilha(int numeroPilha) {
        Stack<String> original = pilhas.get(numeroPilha - 1);
        Stack<String> copia = new Stack<>();
        copia.addAll(original);
        return copia;
    }
}