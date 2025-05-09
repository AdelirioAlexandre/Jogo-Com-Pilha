import javax.swing.*;
import java.util.*;

public class Principal {
    // Lista com os itens
    static List<String> itens = new ArrayList<>(Arrays.asList(
            "G", "G", "G", "G", "G", "G",
            "R", "R", "R", "R", "R", "R",
            "B", "B", "B", "B", "B", "B",
            "P", "P", "P", "P", "P", "P"
    ));

    // Pilhas
    static Stack<String> pilha1 = new Stack<>();
    static Stack<String> pilha2 = new Stack<>();
    static Stack<String> pilha3 = new Stack<>();
    static Stack<String> pilha4 = new Stack<>();
    static Stack<String> pilha5 = new Stack<>();

    public static void main(String[] args) {
        Collections.shuffle(itens);
        preenchimento(pilha1);
        preenchimento(pilha2);
        preenchimento(pilha3);
        preenchimento(pilha4);
        preenchimento(pilha5);
        usuario();
    }

    // Interface do Usuario
    public static void usuario() {
        StringBuilder mensagem = new StringBuilder();
        mensagem.append("<html><body style='font-family: Arial; font-size: 14px;'>");

        // Pilha 1
        mensagem.append("Pilha 1: ");
        for (String item : pilha1) {
            mensagem.append(colorirItem(item)).append(" ");
        }
        mensagem.append("<br><br>");

        // Pilha 2
        mensagem.append("Pilha 2: ");
        for (String item : pilha2) {
            mensagem.append(colorirItem(item)).append(" ");
        }
        mensagem.append("<br><br>");

        // Pilha 3
        mensagem.append("Pilha 3: ");
        for (String item : pilha3) {
            mensagem.append(colorirItem(item)).append(" ");
        }
        mensagem.append("<br><br>");

        // Pilha 4
        mensagem.append("Pilha 4: ");
        for (String item : pilha4) {
            mensagem.append(colorirItem(item)).append(" ");
        }
        mensagem.append("<br><br>");

        // Pilha 5
        mensagem.append("Pilha 5: ");
        for (String item : pilha5) {
            mensagem.append(colorirItem(item)).append(" ");
        }

        mensagem.append("</body></html>");

        JOptionPane.showMessageDialog(null, mensagem.toString(), "Visualização das Pilhas", JOptionPane.INFORMATION_MESSAGE);
    }

    // Sistema de preenchimento da pilha
    public static void preenchimento(Stack<String> pilha) {
        pilha.clear();
        for (int i = 0; i < 6 && !itens.isEmpty(); i++) {
            pilha.push(itens.remove(0));
        }
    }

    // Método para colorir cada item
    private static String colorirItem(String item) {
        String cor = switch (item) {
            case "G" -> "green";
            case "R" -> "red";
            case "B" -> "blue";
            case "P" -> "#FF69B4";
            default -> "black";
        };
        return String.format("<span style='color: %s; font-weight: bold; font-size: 16px;'>%s</span>", cor, item);
    }
}
