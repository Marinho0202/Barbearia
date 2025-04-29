import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Mostrar a Splash Screen
        SplashScreen splash = new SplashScreen();
        splash.showSplash(3000); // mostra por 3 segundos

        // Depois abre a tela principal
        SwingUtilities.invokeLater(MainView::new);
    }
}
