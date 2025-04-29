import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {

    public MainView() {
        super("Brother Barbearia - Sistema de Agendamentos");

        setLayout(new BorderLayout());

        // Logo no topo (certifique-se de ter o arquivo logo.png na pasta correta)
        // Carrega e redimensiona a logo para 300x100 px (ajuste se quiser!)
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/logo.png"));
        Image resizedImage = originalIcon.getImage().getScaledInstance(250, 180, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        JLabel logoLabel = new JLabel(resizedIcon);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(logoLabel, BorderLayout.NORTH);

        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(logoLabel, BorderLayout.NORTH);

        // Abas
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Agendamento", new AgendamentoPanel());
        tabbedPane.addTab("Agenda", new AgendaPanel());
        add(tabbedPane, BorderLayout.CENTER);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // TELA CHEIA
        setLocationRelativeTo(null); // (isso ajuda no modo janela)
        setVisible(true);

    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new MainView());
    }
}
