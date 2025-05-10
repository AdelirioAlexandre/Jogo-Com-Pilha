import javax.swing.*;
import java.util.*;

public class Principal {
    // Lista com os itens
    static List<String> itens = new ArrayList<>(Arrays.asList(
            "G", "G", "G", "G", "G", "G", "G",
            "R", "R", "R", "R", "R", "R", "R",
            "B", "B", "B", "B", "B", "B", "B",
            "P", "P", "P", "P", "P", "P", "P",
            "Y", "Y", "Y", "Y", "Y", "Y", "Y", // Nova cor: Amarelo
            "L", "L", "L", "L", "L", "L", "L"
    ));

    // Pilhas
    static Stack<String> pilha1 = new Stack<>();
    static Stack<String> pilha2 = new Stack<>();
    static Stack<String> pilha3 = new Stack<>();
    static Stack<String> pilha4 = new Stack<>();
    static Stack<String> pilha5 = new Stack<>();
    static Stack<String> pilha6 = new Stack<>();
    static Stack<String> pilha7 = new Stack<>();

    public static void main(String[] args) {
        Collections.shuffle(itens);
        preenchimento(pilha1);
        preenchimento(pilha2);
        preenchimento(pilha3);
        preenchimento(pilha4);
        preenchimento(pilha5);
        preenchimento(pilha6);
        preenchimento(pilha7);

        //loop de movimento
        while(true) {
            usuario();
            
            if (verificarFimJogo()) {
                JOptionPane.showMessageDialog(null, 
                    "Parabéns! Você venceu!\nTodas as pilhas estão organizadas por cor!", 
                    "Fim de Jogo", 
                    JOptionPane.INFORMATION_MESSAGE);
                break;
            }

            int opcao = JOptionPane.showConfirmDialog(null,
                "Deseja fazer um movimento ?", 
                "Movimento", 
                JOptionPane.YES_NO_OPTION);

            if(opcao != JOptionPane.YES_OPTION) {
                break;
            }

            moverItem();
        }
    }

    public static void usuario() {
        StringBuilder mensagem = new StringBuilder();
        mensagem.append("<html><body style='font-family: Arial; font-size: 11px;'>");

        // Pilha 1
        mensagem.append("<span style='font-size: 11px;'>Pilha 1:</span> ");
        for (String item : pilha1) {
            mensagem.append(colorirItem(item)).append(" ");
        }
        mensagem.append("<br><br>");

        // Pilha 2
        mensagem.append("<span style='font-size: 11px;'>Pilha 2:</span> ");
        for (String item : pilha2) {
            mensagem.append(colorirItem(item)).append(" ");
        }
        mensagem.append("<br><br>");

        // Pilha 3
        mensagem.append("<span style='font-size: 11px;'>Pilha 3:</span> ");
        for (String item : pilha3) {
            mensagem.append(colorirItem(item)).append(" ");
        }
        mensagem.append("<br><br>");

        // Pilha 4
        mensagem.append("<span style='font-size: 11px;'>Pilha 4:</span> ");
        for (String item : pilha4) {
            mensagem.append(colorirItem(item)).append(" ");
        }
        mensagem.append("<br><br>");

        // Pilha 5
        mensagem.append("<span style='font-size: 11px;'>Pilha 5:</span> ");
        for (String item : pilha5) {
            mensagem.append(colorirItem(item)).append(" ");
        }
        mensagem.append("<br><br>");

        // Pilha 6
        mensagem.append("<span style='font-size: 11px;'>Pilha 6:</span> ");
        for (String item : pilha6) {
            mensagem.append(colorirItem(item)).append(" ");
        }
        mensagem.append("<br><br>");

        // Pilha 7
        mensagem.append("<span style='font-size: 11px;'>Pilha 7:</span> ");
        for (String item : pilha7) {
            mensagem.append(colorirItem(item)).append(" ");
        }

        mensagem.append("</body></html>");

        JOptionPane.showMessageDialog(null, mensagem.toString(), "Visualização das Pilhas", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void preenchimento(Stack<String> pilha) {
        pilha.clear();
        for (int i = 0; i <= 7 && !itens.isEmpty(); i++) {
            pilha.push(itens.remove(0));
        }
    }

    private static String colorirItem(String item) {
        String cor = switch (item) {
            case "G" -> "green";
            case "R" -> "red";
            case "B" -> "blue";
            case "P" -> "#FF69B4";
            case "Y" -> "#FFD700"; // Cor amarela
            default -> "black";
        };
        return String.format("<span style='color: %s; font-weight: bold; font-size: 11px;'>%s</span>", cor, item);
    }

    public static void moverItem() {
        String origem = JOptionPane.showInputDialog("Digite a pilha que voce deseja mover !");
        if(origem == null) return;

        String destino = JOptionPane.showInputDialog("Digite a pilha de destino !");
        if(destino == null) return;

        try {
            int pilhaOrigem = Integer.parseInt(origem);
            int pilhaDestino = Integer.parseInt(destino);

            if(pilhaOrigem < 1 || pilhaOrigem > 7 || pilhaDestino < 1 || pilhaDestino > 7) {
                JOptionPane.showMessageDialog(null,"Numero invalido, digite entre 1 e 7");
                return;
            }

            Stack<String> stackOrigem = getPilha(pilhaOrigem);
            Stack<String> stackDestino = getPilha(pilhaDestino);

            if (stackOrigem.isEmpty()) {
                JOptionPane.showMessageDialog(null, "A pilha de origem está vazia!");
                return;
            }

            Stack<String> itensParaMover = new Stack<>();
            String corInicial = stackOrigem.peek();
        
            int quantidadeItens = 0;
        
            while (!stackOrigem.isEmpty() && stackOrigem.peek().equals(corInicial)) {
                quantidadeItens++;
                if (stackDestino.size() + quantidadeItens > 5) {
                    JOptionPane.showMessageDialog(null, "Não há espaço suficiente na pilha de destino!");
                    return;
                }
                itensParaMover.push(stackOrigem.pop());
            }
        
            while (!itensParaMover.isEmpty()) {
                stackDestino.push(itensParaMover.pop());
            }

            usuario();

            if (verificarFimJogo()) {
                JOptionPane.showMessageDialog(null, 
                    "Parabéns! Você venceu!\nTodas as pilhas estão organizadas por cor!", 
                    "Fim de Jogo", 
                    JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por favor, digite apenas números!");
        }
    }

    private static Stack<String> getPilha(int numero) {
        return switch (numero) {
            case 1 -> pilha1;
            case 2 -> pilha2;
            case 3 -> pilha3;
            case 4 -> pilha4;
            case 5 -> pilha5;
            case 6 -> pilha6;
            case 7 -> pilha7;
            default -> throw new IllegalArgumentException("Pilha inválida");
        };
    }
    
    public static boolean verificarFimJogo() {
        return verificarPilhaUnicaCor(pilha1) &&
               verificarPilhaUnicaCor(pilha2) &&
               verificarPilhaUnicaCor(pilha3) &&
               verificarPilhaUnicaCor(pilha4) &&
               verificarPilhaUnicaCor(pilha5) &&
               verificarPilhaUnicaCor(pilha6) &&
               verificarPilhaUnicaCor(pilha7);
    }
    
    private static boolean verificarPilhaUnicaCor(Stack<String> pilha) {
        if(pilha.isEmpty()) {
            return false;
        }
        
        String primeiraCor = pilha.firstElement();
        for (String item : pilha) {
            if (!item.equals(primeiraCor)) {
                return false;
            }
        }
        return true;
    }
}