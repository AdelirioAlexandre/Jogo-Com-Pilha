package src.view;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.util.Stack;
import javax.swing.Timer;
import src.model.Movimento;
import src.model.TabuleiroPilhas;

public class Interface {
    private static final int LARGURA_JANELA = 300;
    private static final int ALTURA_JANELA = 400;
    private static final int ESPACAMENTO = 20;
    private static final String FONTE_PADRAO = "Arial";
    private static final int TAMANHO_FONTE = 14;
    private static final int TAMANHO_FONTE_ITEM = 16;

    private JLabel cronometroLabel;
    private long tempoInicial;
    private Timer timer;

    public Interface() {
        inicializarCronometro();
    }

    // Conometro
    private void inicializarCronometro() {
        tempoInicial = System.currentTimeMillis();
        cronometroLabel = new JLabel("Tempo: 00:00");
        cronometroLabel.setFont(new Font(FONTE_PADRAO, Font.BOLD, 16));
        cronometroLabel.setForeground(Color.BLUE);

        timer = new Timer(1000, e -> atualizarCronometro());
        timer.start();
    }

    private void atualizarCronometro() {
        long tempoAtual = System.currentTimeMillis();
        long segundos = (tempoAtual - tempoInicial) / 1000;
        long minutos = segundos / 60;
        segundos %= 60;
        cronometroLabel.setText(String.format("Tempo: %02d:%02d", minutos, segundos));
    }

    // Método para solicitar o movimento do jogador
    public Movimento pedirMovimento(TabuleiroPilhas tabuleiro) {
        JPanel mainPanel = criarPainelPrincipal();

        mainPanel.add(cronometroLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, ESPACAMENTO)));

        JLabel pilhasLabel = new JLabel(criarVisualizacaoPilhas(tabuleiro));
        pilhasLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(pilhasLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, ESPACAMENTO)));

        JPanel inputPanel = criarPainelInput();
        mainPanel.add(inputPanel);

        int result = mostrarDialogo(mainPanel);
        return processarResultado(result, inputPanel, tabuleiro);
    }

    // Métodos auxiliares para criar a interface gráfica
    private JPanel criarPainelPrincipal() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.setPreferredSize(new Dimension(LARGURA_JANELA, ALTURA_JANELA));
        return mainPanel;
    }

    // Cria o painel de entrada para o movimento do jogador
    private JPanel criarPainelInput() {
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.setMaximumSize(new Dimension(300, 80));
        inputPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        inputPanel.add(new JLabel("Pilha Origem:"));
        inputPanel.add(new JTextField(5));
        inputPanel.add(new JLabel("Pilha Destino:"));
        inputPanel.add(new JTextField(5));

        return inputPanel;
    }

    // Exibe o diálogo para o jogador inserir o movimento
    private int mostrarDialogo(JPanel mainPanel) {
        return JOptionPane.showConfirmDialog(
                null,
                mainPanel,
                "Jogo",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );
    }

    // Processa o resultado do diálogo, validando e retornando o movimento
    private Movimento processarResultado(int result, JPanel inputPanel, TabuleiroPilhas tabuleiro) {
        if (result == JOptionPane.OK_OPTION) {
            try {
                JTextField origemField = (JTextField) inputPanel.getComponent(1);
                JTextField destinoField = (JTextField) inputPanel.getComponent(3);
                int origem = Integer.parseInt(origemField.getText().trim());
                int destino = Integer.parseInt(destinoField.getText().trim());

                if (origem < 1 || origem > 7 || destino < 1 || destino > 7) {
                    mostrarErro("As pilhas devem estar entre 1 e 7!");
                    return null;
                }

                if (!tabuleiro.podeAdicionarItem(destino)) {
                    mostrarErro("A pilha de destino já atingiu o limite máximo de 7 itens!");
                    return null;
                }

                return new Movimento(origem, destino);
            } catch (NumberFormatException e) {
                mostrarErro("Movimento invalido!");
                return null;
            }
        } else {
            mostrarMensagemDespedida();
            System.exit(0);
            return null;
        }
    }

    // Cria a visualização das pilhas no formato HTML
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

    // Método para colorir os itens de acordo com suas cores
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

    // Exibe uma mensagem de vitória quando o jogador vence
    public void mostrarMensagemVitoria() {
        timer.stop();
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

    // Exibe uma mensagem de despedida quando o jogador decide sair
    private void mostrarMensagemDespedida() {
        timer.stop();
        JOptionPane.showMessageDialog(null,
                "Até mais! Obrigado por jogar!",
                "Despedida",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
