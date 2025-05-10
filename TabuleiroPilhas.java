import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
public class TabuleiroPilhas {
    private final List<Stack<String>> pilhas;
    private final List<String> cores;
    
    public TabuleiroPilhas() {
        pilhas = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            pilhas.add(new Stack<>());
        }
        
        cores = Arrays.asList(
            "G", "G", "G", "G", "G", "G", "G",
            "R", "R", "R", "R", "R", "R", "R",
            "B", "B", "B", "B", "B", "B", "B",
            "P", "P", "P", "P", "P", "P", "P",
            "Y", "Y", "Y", "Y", "Y", "Y", "Y",
            "B", "B", "B", "B", "B", "B", "B"
        );
    }
    
    public void distribuirPecas() {
        List<String> pecasEmbaralhadas = new ArrayList<>(cores);
        Collections.shuffle(pecasEmbaralhadas);
        
        int pecaPorPilha = pecasEmbaralhadas.size() / 6; // Mudamos de 7 para 6
        int pecaAtual = 0;
        
        // Modificamos o loop para iterar apenas nas 6 primeiras pilhas
        for (int i = 0; i < 6; i++) {
            Stack<String> pilha = pilhas.get(i);
            for (int j = 0; j < pecaPorPilha; j++) {
                pilha.push(pecasEmbaralhadas.get(pecaAtual++));
            }
        }
        // A pilha 7 (índice 6) ficará vazia naturalmente
    }
    
    public Stack<String> getPilha(int numero) {
        return pilhas.get(numero - 1);
    }
    
    public void executarMovimento(Movimento movimento) {
        Stack<String> origem = getPilha(movimento.getOrigem());
        Stack<String> destino = getPilha(movimento.getDestino());
        destino.push(origem.pop());
    }
    
    public boolean verificarVitoria() {
        return pilhas.stream().allMatch(this::verificarPilhaUnicaCor);
    }
    
    private boolean verificarPilhaUnicaCor(Stack<String> pilha) {
        if (pilha.isEmpty()) return false;
        String cor = pilha.firstElement();
        return pilha.stream().allMatch(item -> item.equals(cor));
    }
    
    public List<Stack<String>> getPilhas() {
        return Collections.unmodifiableList(pilhas);
    }
}