package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class TabuleiroPilhas {
    private static final int MAX_ITENS_POR_PILHA = 7;
    private static final int NUMERO_DE_PILHAS = 7;
    private static TabuleiroPilhas instance;
    private final List<Stack<String>> pilhas;
    private String ultimoItemMovido;

    public TabuleiroPilhas() {
        pilhas = new ArrayList<>();
        for (int i = 0; i < NUMERO_DE_PILHAS; i++) {
            pilhas.add(new Stack<>());
        }
        ultimoItemMovido = "";
    }

    public static TabuleiroPilhas getInstance() {
        if (instance == null) {
            instance = new TabuleiroPilhas();
        }
        return instance;
    }

    public void distribuirPecas() {
        // Lista com todas as peças do jogo
        List<String> pecas = new ArrayList<>();

        // Adiciona 7 peças de cada cor
        for (int i = 0; i < 7; i++) {
            pecas.addAll(Arrays.asList("R", "G", "B", "Y", "P", "L"));
        }

        // Embaralha as peças
        Collections.shuffle(pecas);

        // Tentativa de distribuir garantindo cores diferentes no topo das pilhas 1-6
        boolean distribuido = false;
        while (!distribuido) {
            // Limpar pilhas antes de tentar distribuir
            for (int i = 0; i < 6; i++) {
                pilhas.get(i).clear();
            }

            List<String> copiaPecas = new ArrayList<>(pecas);
            int pecaAtual = 0;
            for (int i = 0; i < 6; i++) {
                Stack<String> pilha = pilhas.get(i);
                for (int j = 0; j < MAX_ITENS_POR_PILHA; j++) {
                    if (pecaAtual < copiaPecas.size()) {
                        pilha.push(copiaPecas.get(pecaAtual));
                        pecaAtual++;
                    }
                }
            }

            // Verificar se os topos são diferentes
            Set<String> coresTopo = new HashSet<>();
            boolean todosDiferentes = true;
            for (int i = 0; i < 6; i++) {
                Stack<String> pilha = pilhas.get(i);
                if (!pilha.isEmpty()) {
                    String topo = pilha.peek();
                    if (coresTopo.contains(topo)) {
                        todosDiferentes = false;
                        break;
                    }
                    coresTopo.add(topo);
                }
            }

            if (todosDiferentes) {
                distribuido = true;
            } else {
                Collections.shuffle(pecas);
            }
        }
    }

    public boolean mover(Movimento movimento) {
        int origem = movimento.getOrigem() - 1;
        int destino = movimento.getDestino() - 1;

        if (origem < 0 || origem >= NUMERO_DE_PILHAS ||
                destino < 0 || destino >= NUMERO_DE_PILHAS) {
            return false;
        }

        if (!podeAdicionarItem(movimento.getDestino())) {
            return false;
        }

        Stack<String> pilhaOrigem = pilhas.get(origem);
        Stack<String> pilhaDestino = pilhas.get(destino);

        if (pilhaOrigem.isEmpty()) {
            return false;
        }

        ultimoItemMovido = pilhaOrigem.peek();
        pilhaDestino.push(pilhaOrigem.pop());
        
        return true;
    }

    public String getUltimoItemMovido() {
        return ultimoItemMovido;
    }

    public List<Stack<String>> getPilhas() {
        return pilhas;
    }

    public boolean podeAdicionarItem(int numeroPilha) {
        Stack<String> pilha = pilhas.get(numeroPilha - 1);
        return pilha.size() < MAX_ITENS_POR_PILHA;
    }

    public boolean verificarVitoria() {
        // Verifica se as pilhas 1-6 estão organizadas por cor
        for (int i = 0; i < 6; i++) {
            Stack<String> pilha = pilhas.get(i);
            if (pilha.isEmpty()) {
                continue;
            }

            String cor = pilha.peek();
            for (String item : pilha) {
                if (!item.equals(cor)) {
                    return false;
                }
            }
        }

        // Verifica se a pilha 7 está vazia
        return pilhas.get(6).isEmpty();
    }
}