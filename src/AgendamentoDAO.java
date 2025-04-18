import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AgendamentoDAO {

    public boolean salvarAgendamento(Agendamento agendamento) {
        String sql = "INSERT INTO agendamentos (barbeiro_id, barbeiro_nome, data, hora_inicio, servico_nome, duracao_minutos, preco, cliente_nome, cliente_contato, is_ocupado) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, agendamento.getBarbeiro().getId());
            stmt.setString(2, agendamento.getBarbeiro().getNome());
            stmt.setDate(3, Date.valueOf(agendamento.getData()));
            stmt.setTime(4, Time.valueOf(agendamento.getHoraInicio()));
            stmt.setString(5, agendamento.getServico().getNome());
            stmt.setInt(6, agendamento.getServico().getDuracaoMinutos());
            stmt.setDouble(7, agendamento.getServico().getPreco());

            if (agendamento.isOcupado()) {
                stmt.setNull(8, Types.VARCHAR);
                stmt.setNull(9, Types.VARCHAR);
            } else {
                stmt.setString(8, agendamento.getCliente().getNome());
                stmt.setString(9, agendamento.getCliente().getContato());
            }

            stmt.setBoolean(10, agendamento.isOcupado());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Agendamento> listarPorBarbeiroEData(int barbeiroId, LocalDate data) {
        List<Agendamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM agendamentos WHERE barbeiro_id = ? AND data = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, barbeiroId);
            stmt.setDate(2, Date.valueOf(data));

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Barbeiro barbeiro = new Barbeiro(
                        rs.getInt("barbeiro_id"),
                        rs.getString("barbeiro_nome")
                );

                Servico servico = new Servico(
                        0,
                        rs.getString("servico_nome"),
                        rs.getInt("duracao_minutos"),
                        rs.getDouble("preco"),
                        false // se precisar, depois podemos armazenar isCombo no banco
                );

                Cliente cliente = null;
                boolean isOcupado = rs.getBoolean("is_ocupado");

                if (!isOcupado) {
                    cliente = new Cliente(
                            rs.getString("cliente_nome"),
                            rs.getString("cliente_contato")
                    );
                }

                Agendamento agendamento = new Agendamento(
                        rs.getInt("id"),
                        barbeiro,
                        rs.getDate("data").toLocalDate(),
                        rs.getTime("hora_inicio").toLocalTime(),
                        servico,
                        cliente,
                        isOcupado
                );

                lista.add(agendamento);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public boolean removerAgendamento(int id) {
        String sql = "DELETE FROM agendamentos WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
