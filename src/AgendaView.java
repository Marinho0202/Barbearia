import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import com.toedter.calendar.JDateChooser;


public class AgendaView extends JFrame {

    private JComboBox<Barbeiro> cbBarbeiros;
    private JDateChooser dateChooser;
    private JButton btnBuscar;
    private JTextArea taResultado;

    private AgendamentoController controller;
    private Barbearia barbearia;

    public AgendaView() {
        super("Visualizar Agenda do Barbeiro");
        this.controller = new AgendamentoController();
        this.barbearia = new Barbearia();

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

        add(new JLabel("Barbeiro:"));
        add(cbBarbeiros);
        add(new JLabel("Data:"));
        add(dateChooser);
        add(btnBuscar);
        add(new JScrollPane(taResultado));

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
                    taResultado.append(ag.toString() + "\n");
                }
            }
        });

        setSize(400, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
