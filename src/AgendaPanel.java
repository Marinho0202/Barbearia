import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import com.toedter.calendar.JDateChooser;

public class AgendaPanel extends JPanel {

    private JComboBox<Barbeiro> cbBarbeiros;
    private JDateChooser dateChooser;
    private JButton btnBuscar;
    private JTextArea taResultado;

    private AgendamentoController controller;
    private Barbearia barbearia;

    public AgendaPanel() {
        this.controller = new AgendamentoController();
        this.barbearia = new Barbearia();

        setBackground(Color.BLACK);


        setLayout(new GridLayout(6, 1, 5, 5));

        cbBarbeiros = new JComboBox<>();
        for (Barbeiro b : barbearia.getBarbeiros()) {
            cbBarbeiros.addItem(b);
        }

        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");

        btnBuscar = new JButton("Ver Agenda");
        taResultado = new JTextArea();
        taResultado.setEditable(false);

        taResultado.setBackground(Color.DARK_GRAY);
        taResultado.setForeground(Color.WHITE);


        add(new JLabel("Barbeiro:"));
        add(cbBarbeiros);
        add(new JLabel("Data:"));
        add(dateChooser);
        add(btnBuscar);
        add(new JScrollPane(taResultado));

        configurarAcoes();
    }

    private void configurarAcoes() {
        btnBuscar.addActionListener(e -> {
            Barbeiro barbeiro = (Barbeiro) cbBarbeiros.getSelectedItem();
            Date dataSelecionada = dateChooser.getDate();

            if (dataSelecionada == null) {
                JOptionPane.showMessageDialog(this, "Selecione uma data!");
                return;
            }

            LocalDate data = dataSelecionada.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            List<Agendamento> lista = controller.listarAgendamentos(barbeiro, data);

            taResultado.setText("");
            if (lista.isEmpty()) {
                taResultado.append("Nenhum agendamento para essa data.");
            } else {
                for (Agendamento ag : lista) {
                    taResultado.append(ag.toString() + "\\n");
                }
            }
        });
    }

}
