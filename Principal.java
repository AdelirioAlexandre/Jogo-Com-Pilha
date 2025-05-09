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

        //loop de movimento
        while(true){
            usuario();
            int opcao = JOptionPane.showConfirmDialog(null,"Deseja fazer um movimento ?", "Movimento", JOptionPane.YES_NO_OPTION);

            if(opcao != JOptionPane.YES_NO_OPTION){
                break;
            }

            moverItem();
        }
    }

    // Interface do Usuario
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
        return String.format("<span style='color: %s; font-weight: bold; font-size: 11px;'>%s</span>", cor, item);
    }

    //Metodo de Movimento
    public static void moverItem(){
        //Pilha Origem
        String origem = JOptionPane.showInputDialog("Digite a pilha que voce deseja mover !");
        if(origem == null) return; //Caso o usuario cancele

        //Pilha destino
        String destino = JOptionPane.showInputDialog("Digite a pilha de destino !");
        if(destino == null) return;

        try{
            int pilhaOrigem = Integer.parseInt(origem);
            int pilhaDestino = Integer.parseInt(destino);

            //Verificando se a pilha é valida
            if(pilhaOrigem < 1 || pilhaOrigem > 5 || pilhaDestino < 1 || pilhaDestino > 5){
                JOptionPane.showMessageDialog(null,"Numero invalido, digite entre 1 e 5");
            }

            //Obtendo as pilhas
            Stack<String> stackOrigem = getPilha(pilhaOrigem);
            Stack<String> stackDestino = getPilha(pilhaDestino);

            //Verificando se a pilha esta vazia
            if (stackOrigem.isEmpty()) {
                JOptionPane.showMessageDialog(null, "A pilha de origem está vazia!");
                return;
            }

            // Verifica se a pilha de destino não está cheia
            if (stackDestino.size() >= 6) {
                JOptionPane.showMessageDialog(null, "A pilha de destino está cheia!");
                return;
            }

            //Mover itens entre as pilhas
            String item = stackOrigem.pop();
            stackDestino.push(item);

            //Retorna ao menu
            usuario();
        } catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Por favor, digite apenas números!");
        }
    }

    private static Stack<String> getPilha(int numero){
        return switch (numero){
            case 1 -> pilha1;
            case 2 -> pilha2;
            case 3 -> pilha3;
            case 4 -> pilha4;
            case 5 -> pilha5;
            default -> throw new IllegalArgumentException("Pilha inválida");
        };
    }
}
