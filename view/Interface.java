package view;

import model.Movimento;
import model.TabuleiroPilhas;

import javax.swing.*;
import java.util.List;
import java.util.Stack;

public class Interface {
    public void mostrarTabuleiro(TabuleiroPilhas tabuleiro) {
        StringBuilder mensagem = new StringBuilder();
        mensagem.append("<html><body style='font-family: Arial; font-size: 11px;'>");

        List<Stack<String>> pilhas = tabuleiro.getPilhas();
        for (int i = 0; i < pilhas.size(); i++) {
            mensagem.append(String.format("Pilha %d: ", i + 1));
            for (String item : pilhas.get(i)) {
                mensagem.append(colorirItem(item)).append(" ");
            }
            mensagem.append("<br><br>");
        }

        mensagem.append("</body></html>");
        JOptionPane.showMessageDialog(null, mensagem.toString(), "Visualização das Pilhas", JOptionPane.INFORMATION_MESSAGE);
    }

    private String colorirItem(String item) {
        String cor = switch (item) {
            case "G" -> "green";
            case "R" -> "red";
            case "B" -> "blue";
            case "P" -> "#FF69B4";
            case "Y" -> "#FFD700";
            default -> "black";
        };
        return String.format("<span style='color: %s; font-weight: bold;'>%s</span>", cor, item);
    }

    public Movimento pedirMovimento() {
        String origem = JOptionPane.showInputDialog("Digite a pilha que você deseja mover:");
        if (origem == null) return null;

        String destino = JOptionPane.showInputDialog("Digite a pilha de destino:");
        if (destino == null) return null;

        try {
            return new Movimento(Integer.parseInt(origem), Integer.parseInt(destino));
        } catch (NumberFormatException e) {
            mostrarErro("Por favor, digite apenas números!");
            return null;
        }
    }

    public boolean perguntarSeDesejaContinuar() {
        int opcao = JOptionPane.showConfirmDialog(null,
                "Deseja fazer um movimento?", "Movimento", JOptionPane.YES_NO_OPTION);
        return opcao == JOptionPane.YES_OPTION;
    }

    public void mostrarMensagemVitoria() {
        JOptionPane.showMessageDialog(null,
                "Parabéns! Você venceu!\nTodas as pilhas estão organizadas por cor!",
                "Fim de Jogo", JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarErro(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }
}