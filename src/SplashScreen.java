import javax.swing.*;
import java.awt.*;

public class SplashScreen extends JWindow {

    public SplashScreen() {
        // Painel principal
        JPanel content = new JPanel();
        content.setBackground(Color.BLACK);
        content.setLayout(new BorderLayout());

        // Carregando a logo
        ImageIcon logo = new ImageIcon(getClass().getResource("/logo.png"));
        Image img = logo.getImage().getScaledInstance(250, 180, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(img));
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Mensagem "Carregando..."
        JLabel loading = new JLabel("Bem-vindo, Brother!", SwingConstants.CENTER);
        loading.setForeground(Color.WHITE);
        loading.setFont(new Font("SansSerif", Font.PLAIN, 16));

        content.add(logoLabel, BorderLayout.CENTER);
        content.add(loading, BorderLayout.SOUTH);

        setContentPane(content);
        setSize(400, 300);
        setLocationRelativeTo(null); // Centraliza a janela
    }

    public void showSplash(int duration) {
        setVisible(true);
        try {
            Thread.sleep(duration); // Espera X milissegundos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setVisible(false);
        dispose();
    }
}
