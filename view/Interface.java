package view;

import model.Movimento;
import model.TabuleiroPilhas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.util.Stack;

public class Interface {
    private static final int LARGURA_JANELA = 400;
    private static final int ALTURA_JANELA = 350;
    private static final int ESPACAMENTO = 20;
    private static final String FONTE_PADRAO = "Arial";
    private static final int TAMANHO_FONTE = 14;
    private static final int TAMANHO_FONTE_ITEM = 16;

    private String criarVisualizacaoPilhas(TabuleiroPilhas tabuleiro) {
        StringBuilder mensagem = new StringBuilder();
        mensagem.append(String.format("<html><div style='font-family: %s; font-size: %dpx;'>", 
            FONTE_PADRAO, TAMANHO_FONTE));

        List<Stack<String>> pilhas = tabuleiro.getPilhas();
        for (int i = 0; i < pilhas.size(); i++) {
            mensagem.append(String.format("<p style='margin: 5px 0;'><b>Pilha %d:</b> ", i + 1));
            pilhas.get(i).forEach(item -> mensagem.append(colorirItem(item)).append(" "));
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
        return String.format("<span style='color: %s; font-weight: bold; font-size: %dpx;'>%s</span>", 
            cor, TAMANHO_FONTE_ITEM, item);
    }

    public Movimento pedirMovimento(TabuleiroPilhas tabuleiro) {
        JPanel mainPanel = criarPainelPrincipal();
        JLabel pilhasLabel = new JLabel(criarVisualizacaoPilhas(tabuleiro));
        pilhasLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(pilhasLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, ESPACAMENTO)));

        JPanel inputPanel = criarPainelInput();
        mainPanel.add(inputPanel);

        int result = mostrarDialogo(mainPanel);
        return processarResultado(result, inputPanel);
    }

    private JPanel criarPainelPrincipal() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.setPreferredSize(new Dimension(LARGURA_JANELA, ALTURA_JANELA));
        return mainPanel;
    }

    private JPanel criarPainelInput() {
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.setMaximumSize(new Dimension(300, 80));
        inputPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        inputPanel.add(new JLabel("Pilha de origem (1-3):"));
        inputPanel.add(new JTextField(5));
        inputPanel.add(new JLabel("Pilha de destino (1-3):"));
        inputPanel.add(new JTextField(5));

        return inputPanel;
    }

    private int mostrarDialogo(JPanel mainPanel) {
        return JOptionPane.showConfirmDialog(
                null,
                mainPanel,
                "Torre de Cores - Fazer Movimento",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );
    }

    private Movimento processarResultado(int result, JPanel inputPanel) {
        if (result == JOptionPane.OK_OPTION) {
            try {
                JTextField origemField = (JTextField) inputPanel.getComponent(1);
                JTextField destinoField = (JTextField) inputPanel.getComponent(3);
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