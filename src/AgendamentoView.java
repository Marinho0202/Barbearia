import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;             // << ESTE AQUI
import java.util.Date;              // << E ESTE AQUI
import java.util.List;
import com.toedter.calendar.JDateChooser;




public class AgendamentoView extends JFrame {

    private JComboBox<Barbeiro> cbBarbeiros;
    private JComboBox<Servico> cbServicos;
    private JDateChooser dateChooser;
    private JButton btnVerHorarios;
    private JComboBox<LocalTime> cbHorarios;

    private JTextField tfNomeCliente;
    private JTextField tfContatoCliente;

    private JButton btnAgendar;
    private JButton btnOcupar;

    private AgendamentoController controller;
    private Barbearia barbearia;

    public AgendamentoView() {
        super("Agendamento de Serviços");
        this.controller = new AgendamentoController();
        this.barbearia = new Barbearia();

        setLayout(new GridLayout(9, 1, 5, 5));

        // Barbeiros
        cbBarbeiros = new JComboBox<>();
        for (Barbeiro b : barbearia.getBarbeiros()) {
            cbBarbeiros.addItem(b);
        }
        add(new JLabel("Barbeiro:"));
        add(cbBarbeiros);

        // Serviços
        cbServicos = new JComboBox<>();
        for (Servico s : barbearia.getServicos()) {
            cbServicos.addItem(s);
        }
        add(new JLabel("Serviço:"));
        add(cbServicos);

        // Data
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        add(new JLabel("Data:"));
        add(dateChooser);


        // Botão de horários
        btnVerHorarios = new JButton("Ver Horários Disponíveis");
        cbHorarios = new JComboBox<>();
        add(btnVerHorarios);
        add(cbHorarios);

        // Cliente
        tfNomeCliente = new JTextField();
        tfContatoCliente = new JTextField();
        add(new JLabel("Nome do Cliente:"));
        add(tfNomeCliente);
        add(new JLabel("Contato do Cliente:"));
        add(tfContatoCliente);

        // Botões finais
        btnAgendar = new JButton("Agendar");
        btnOcupar = new JButton("Marcar como Ocupado");
        add(btnAgendar);
        add(btnOcupar);

        configurarAcoes();

        setSize(400, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void configurarAcoes() {
        btnVerHorarios.addActionListener(e -> {
            Barbeiro barbeiro = (Barbeiro) cbBarbeiros.getSelectedItem();
            Servico servico = (Servico) cbServicos.getSelectedItem();
            Date dataSelecionada = dateChooser.getDate();
            LocalDate data = dataSelecionada.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();


            cbHorarios.removeAllItems();
            List<LocalTime> horarios = controller.gerarHorariosDisponiveis(barbeiro, data, servico);

            if (horarios.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nenhum horário disponível!");
            } else {
                for (LocalTime hora : horarios) {
                    cbHorarios.addItem(hora);
                }
            }
        });

        btnAgendar.addActionListener(e -> {
            Barbeiro barbeiro = (Barbeiro) cbBarbeiros.getSelectedItem();
            Servico servico = (Servico) cbServicos.getSelectedItem();
            Date dataSelecionada = dateChooser.getDate();
            LocalDate data = dataSelecionada.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            LocalTime hora = (LocalTime) cbHorarios.getSelectedItem();

            if (hora == null) {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um horário.");
                return;
            }


            String nome = tfNomeCliente.getText();
            String contato = tfContatoCliente.getText();

            Cliente cliente = new Cliente(nome, contato);

            boolean ok = controller.agendarServico(barbeiro, servico, data, hora, cliente);

            if (ok) {
                JOptionPane.showMessageDialog(this, "✅ Agendamento realizado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "❌ Falha ao agendar.");
            }
        });

        btnOcupar.addActionListener(e -> {
            Barbeiro barbeiro = (Barbeiro) cbBarbeiros.getSelectedItem();
            Servico servico = (Servico) cbServicos.getSelectedItem();
            Date dataSelecionada = dateChooser.getDate();
            LocalDate data = dataSelecionada.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            LocalTime hora = (LocalTime) cbHorarios.getSelectedItem();

            boolean ok = controller.marcarComoOcupado(barbeiro, servico, data, hora);

            if (ok) {
                JOptionPane.showMessageDialog(this, "⛔ Horário marcado como ocupado!");
            } else {
                JOptionPane.showMessageDialog(this, "❌ Falha ao bloquear horário.");
            }
        });
    }
}
