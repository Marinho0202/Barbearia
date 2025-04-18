import javax.swing.*;

public class MainView extends JFrame {

    public MainView() {
        super("Barbearia - Sistema de Agendamentos");

        JTabbedPane tabbedPane = new JTabbedPane();

        // Adiciona as abas
        tabbedPane.addTab("Agendamento", new AgendamentoPanel());
        tabbedPane.addTab("Agenda", new AgendaPanel());

        add(tabbedPane);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 600);
        setLocationRelativeTo(null); // centraliza a janela
        setVisible(true);
    }

    public static void main(String[] args) {
        // Executa a janela principal com as abas
        SwingUtilities.invokeLater(() -> new MainView());
    }
}
