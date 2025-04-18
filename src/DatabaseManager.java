import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private static final String URL = "jdbc:mysql://localhost:3306/agendamentos2?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";       // padrão do XAMPP
    private static final String PASSWORD = "";       // sem senha por padrão

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
