package view;

import model.Movimento;
import model.TabuleiroPilhas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.util.Stack;

public class Interface {
    private String criarVisualizacaoPilhas(TabuleiroPilhas tabuleiro) {
        StringBuilder mensagem = new StringBuilder();
        mensagem.append("<html><div style='font-family: Arial; font-size: 14px;'>");

        List<Stack<String>> pilhas = tabuleiro.getPilhas();
        for (int i = 0; i < pilhas.size(); i++) {
            mensagem.append(String.format("<p style='margin: 5px 0;'><b>Pilha %d:</b> ", i + 1));
            for (String item : pilhas.get(i)) {
                mensagem.append(colorirItem(item)).append(" ");
            }
            mensagem.append("</p>");
        }

        mensagem.append("</div></html>");
        return mensagem.toString();
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
        return String.format("<span style='color: %s; font-weight: bold; font-size: 16px;'>%s</span>", cor, item);
    }

    public Movimento pedirMovimento(TabuleiroPilhas tabuleiro) {
        // Painel principal usando BoxLayout vertical
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Painel para as pilhas
        JLabel pilhasLabel = new JLabel(criarVisualizacaoPilhas(tabuleiro));
        pilhasLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(pilhasLabel);

        // Adiciona espaço entre as pilhas e os inputs
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Painel para os inputs
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.setMaximumSize(new Dimension(300, 80));
        inputPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel origemLabel = new JLabel("Pilha de origem (1-3):");
        JTextField origemField = new JTextField(5);
        JLabel destinoLabel = new JLabel("Pilha de destino (1-3):");
        JTextField destinoField = new JTextField(5);

        inputPanel.add(origemLabel);
        inputPanel.add(origemField);
        inputPanel.add(destinoLabel);
        inputPanel.add(destinoField);

        mainPanel.add(inputPanel);

        // Define um tamanho preferido para o diálogo
        mainPanel.setPreferredSize(new Dimension(400, 350));

        int result = JOptionPane.showConfirmDialog(
            null,
            mainPanel,
            "Torre de Cores - Fazer Movimento",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                int origem = Integer.parseInt(origemField.getText().trim());
                int destino = Integer.parseInt(destinoField.getText().trim());
                return new Movimento(origem, destino);
            } catch (NumberFormatException e) {
                mostrarErro("Por favor, digite apenas números!");
                return null;
            }
        } else {
            mostrarMensagemDespedida();
            System.exit(0);
            return null;
        }
    }

    private void mostrarMensagemDespedida() {
        JOptionPane.showMessageDialog(null,
                "Até mais! Obrigado por jogar!",
                "Despedida",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarMensagemVitoria() {
        JOptionPane.showMessageDialog(null,
                "Parabéns! Você venceu!\nTodas as pilhas estão organizadas por cor!",
                "Fim de Jogo",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarErro(String mensagem) {
        JOptionPane.showMessageDialog(null,
                mensagem,
                "Erro",
                JOptionPane.ERROR_MESSAGE);
    }
}