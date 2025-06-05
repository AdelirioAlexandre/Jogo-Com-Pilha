package src.model;

import java.util.*;

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

    // ✅ Método adicionado para corrigir erro
    public Stack<String> getPilha(int indice) {
        return pilhas.get(indice - 1); // Convertendo para índice baseado em 0
    }

    public void distribuirPecas() {
        List<String> pecas = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            pecas.addAll(Arrays.asList("R", "G", "B", "Y", "P", "L"));
        }
        Collections.shuffle(pecas);

        boolean distribuido = false;
        while (!distribuido) {
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

        if (!podeAdicionarItem(destino + 1)) {
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
        return pilhas.get(6).isEmpty();
    }
}
