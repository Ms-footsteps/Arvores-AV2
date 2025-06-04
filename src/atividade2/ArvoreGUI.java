package atividade2;

import javax.swing.*;
import java.awt.*;

public class ArvoreGUI extends JFrame {
    private final ArvoreAVL arvore = new ArvoreAVL();
    private final JTextField input = new JTextField(10);
    private final JTextArea output = new JTextArea(25, 70);

    public ArvoreGUI() {
        super("Árvore AVL - Inserção, Remoção e Impressão");

        // Botão Inserir
        JButton btnInserir = new JButton("Inserir");
        btnInserir.addActionListener(e -> {
            try {
                int val = Integer.parseInt(input.getText());
                if (arvore.contem(val)) {
                    output.setText("Valor " + val + " já existe na árvore.");
                } else {
                    arvore.inserir(val);
                    // Mostra no JTextArea a árvore vertical com arestas
                    String desenho = arvore.imprimirVerticalComArestasEmString();
                    output.setText("Inserido: " + val + "\n\n" + desenho);

                    // Também imprime no console
                    System.out.println("\n--- Árvore no terminal após inserir " + val + " ---");
                    System.out.println(desenho);
                }
            } catch (NumberFormatException ex) {
                output.setText("Erro: insira um número válido.");
            }
        });

        // Botão Remover
        JButton btnRemover = new JButton("Remover");
        btnRemover.addActionListener(e -> {
            try {
                int val = Integer.parseInt(input.getText());
                boolean removido = arvore.remover(val);
                if (!removido) {
                    output.setText("Valor " + val + " não encontrado.");
                } else {
                    // Depois de remover, exibe no JTextArea a árvore atualizada
                    String desenho = arvore.imprimirVerticalComArestasEmString();
                    output.setText("Removido: " + val + "\n\n" + desenho);

                    // Também imprime no console
                    System.out.println("\n--- Árvore no terminal após remover " + val + " ---");
                    System.out.println(desenho);
                }
            } catch (NumberFormatException ex) {
                output.setText("Erro: insira um número válido.");
            }
        });

        // Percursos tradicionais
        JButton btnEmOrdem = new JButton("Em Ordem");
        btnEmOrdem.addActionListener(e ->
            output.setText("Em Ordem:\n" + arvore.imprimirEmOrdem())
        );

        JButton btnPreOrdem = new JButton("Pré Ordem");
        btnPreOrdem.addActionListener(e ->
            output.setText("Pré Ordem:\n" + arvore.imprimirPreOrdem())
        );

        JButton btnPosOrdem = new JButton("Pós Ordem");
        btnPosOrdem.addActionListener(e ->
            output.setText("Pós Ordem:\n" + arvore.imprimirPosOrdem())
        );

        // Impressão sideways (filho direito cima e filho esquerdo embaixo)
        JButton btnImprimirHierarquia = new JButton("Imprimir Hierarquia");
        btnImprimirHierarquia.addActionListener(e -> {
            String s = arvore.imprimirHierarquicoEmString();
            if (s.trim().isEmpty()) {
                output.setText("Árvore vazia.");
            } else {
                output.setText("Hierarquia (sideways):\n\n" + s);
            }
            System.out.println("\n--- Impressão hierárquica (sideways) solicitada ---");
            arvore.imprimirHierarquicoNoConsole();
        });

        // Impressão vertical com arestas (usando o novo método)
        JButton btnImprimirVerticalArestas = new JButton("Imprimir Vertical c/ Arestas");
        btnImprimirVerticalArestas.addActionListener(e -> {
            String s = arvore.imprimirVerticalComArestasEmString();
            if (s.trim().isEmpty()) {
                output.setText("Árvore vazia.");
            } else {
                output.setText("Árvore (vertical com arestas):\n\n" + s);
            }
            System.out.println("\n--- Impressão vertical com arestas solicitada ---");
            System.out.println(s);
        });

        // Montagem do painel de botões + campo de entrada
        JPanel painelTopo = new JPanel();
        painelTopo.add(new JLabel("Valor:"));
        painelTopo.add(input);
        painelTopo.add(btnInserir);
        painelTopo.add(btnRemover);
        painelTopo.add(btnEmOrdem);
        painelTopo.add(btnPreOrdem);
        painelTopo.add(btnPosOrdem);
        painelTopo.add(btnImprimirHierarquia);
        painelTopo.add(btnImprimirVerticalArestas);

        // Usa fonte monoespaçada para alinhar precisamente
        output.setFont(new Font("Monospaced", Font.PLAIN, 12));
        output.setEditable(false);
        JScrollPane scroll = new JScrollPane(output);

        add(painelTopo, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        setSize(900, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ArvoreGUI::new);
    }
}
