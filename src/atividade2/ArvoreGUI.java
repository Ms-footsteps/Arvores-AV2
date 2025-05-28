package atividade2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ArvoreGUI extends JFrame {

    private ArvoreBinaria binaria = new ArvoreBinaria();
    private ArvoreAVL avl = new ArvoreAVL();
    private ArvoreRubroNegra rubroNegra = new ArvoreRubroNegra();

    private JTextField inputValor = new JTextField(10);
    private JTextArea outputArea = new JTextArea(15, 50);
    private JLabel statusLabel = new JLabel(" ");

    public ArvoreGUI() {
        super("Visualizador de Árvores - Binária, AVL e Rubro-Negra");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Painel de controle
        JPanel painelControles = new JPanel();
        painelControles.add(new JLabel("Valor:"));
        painelControles.add(inputValor);

        // Botões para inserir
        painelControles.add(botao("Inserir Binária", e -> {
            int val = getInput();
            binaria.inserir(val);
            setStatus("Inserido em Binária.");
        }));

        painelControles.add(botao("Inserir AVL", e -> {
            int val = getInput();
            avl.inserir(val);
            setStatus("Inserido em AVL.");
        }));

        painelControles.add(botao("Inserir Rubro-Negra", e -> {
            int val = getInput();
            rubroNegra.inserir(val);
            setStatus("Inserido em Rubro-Negra.");
        }));

        // Botões para remover
        painelControles.add(botao("Remover Binária", e -> {
            int val = getInput();
            binaria.remover(val);
            setStatus("Removido da Binária.");
        }));

        painelControles.add(botao("Remover AVL", e -> {
            int val = getInput();
            avl.remover(val);
            setStatus("Removido da AVL.");
        }));

        painelControles.add(botao("Remover Rubro-Negra", e -> {
            int val = getInput();
            rubroNegra.remover(val);
            setStatus("Removido da Rubro-Negra.");
        }));

        // Botões para busca
        painelControles.add(botao("Buscar em Binária", e -> {
            int val = getInput();
            setStatus(binaria.buscar(val) == true ? "Encontrado na Binária." : "Não encontrado.");
        }));

        painelControles.add(botao("Buscar em AVL", e -> {
            int val = getInput();
            setStatus(avl.buscar(val) == true ? "Encontrado na AVL." : "Não encontrado.");
        }));

        painelControles.add(botao("Buscar em Rubro-Negra", e -> {
            int val = getInput();
            setStatus(rubroNegra.buscar(val) == true ? "Encontrado na Rubro-Negra." : "Não encontrado.");
        }));

        // Botões para imprimir
        painelControles.add(botao("Imprimir Todas", e -> {
            StringBuilder sb = new StringBuilder();
            sb.append("Binária: ").append(binaria.imprimir()).append("\n");
            sb.append("AVL: ").append(avl.imprimir()).append("\n");
            sb.append("Rubro-Negra: ").append(rubroNegra.imprimir()).append("\n");
            outputArea.setText(sb.toString());
        }));

        // Área de resultado
        JPanel painelSaida = new JPanel(new BorderLayout());
        painelSaida.add(new JScrollPane(outputArea), BorderLayout.CENTER);
        painelSaida.add(statusLabel, BorderLayout.SOUTH);
        outputArea.setEditable(false);

        add(painelControles, BorderLayout.NORTH);
        add(painelSaida, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JButton botao(String texto, ActionListener action) {
        JButton btn = new JButton(texto);
        btn.addActionListener(action);
        return btn;
    }

    private int getInput() {
        try {
            return Integer.parseInt(inputValor.getText().trim());
        } catch (NumberFormatException e) {
            setStatus("Insira um número válido.");
            throw e;
        }
    }

    private void setStatus(String msg) {
        statusLabel.setText(msg);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ArvoreGUI::new);
    }
}
