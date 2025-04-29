import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import com.toedter.calendar.JDateChooser;

public class AgendamentoPanel extends JPanel {

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

    public AgendamentoPanel() {
        this.controller = new AgendamentoController();
        setBackground(Color.BLACK); // Fundo preto
        this.barbearia = new Barbearia();

        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();
        formPanel.setBackground(Color.BLACK);
        formPanel.setLayout(new GridLayout(10, 1, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        Font labelFont = new Font("SansSerif", Font.BOLD, 14);
        Color goldColor = new Color(212, 175, 55);

        // Barbeiros
        JLabel lblBarbeiro = new JLabel("Barbeiro:");
        lblBarbeiro.setForeground(goldColor);
        lblBarbeiro.setFont(labelFont);
        cbBarbeiros = new JComboBox<>();
        for (Barbeiro b : barbearia.getBarbeiros()) cbBarbeiros.addItem(b);
        formPanel.add(lblBarbeiro);
        formPanel.add(cbBarbeiros);

        // Serviços
        JLabel lblServico = new JLabel("Serviço:");
        lblServico.setForeground(goldColor);
        lblServico.setFont(labelFont);
        cbServicos = new JComboBox<>();
        for (Servico s : barbearia.getServicos()) cbServicos.addItem(s);
        formPanel.add(lblServico);
        formPanel.add(cbServicos);

        // Data
        JLabel lblData = new JLabel("Data:");
        lblData.setForeground(goldColor);
        lblData.setFont(labelFont);
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        formPanel.add(lblData);
        formPanel.add(dateChooser);

        // Botão Ver Horários
        btnVerHorarios = new JButton("Ver Horários Disponíveis");
        cbHorarios = new JComboBox<>();
        formPanel.add(btnVerHorarios);
        formPanel.add(cbHorarios);

        // Cliente
        JLabel lblNome = new JLabel("Nome do Cliente:");
        lblNome.setForeground(goldColor);
        lblNome.setFont(labelFont);
        tfNomeCliente = new JTextField();
        formPanel.add(lblNome);
        formPanel.add(tfNomeCliente);

        JLabel lblContato = new JLabel("Contato do Cliente:");
        lblContato.setForeground(goldColor);
        lblContato.setFont(labelFont);
        tfContatoCliente = new JTextField();
        formPanel.add(lblContato);
        formPanel.add(tfContatoCliente);

        // Botões finais
        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        btnAgendar = new JButton("Agendar");
        btnOcupar = new JButton("Marcar como Ocupado");

        // Estilo dos botões
        btnAgendar.setBackground(goldColor);
        btnAgendar.setForeground(Color.BLACK);
        btnAgendar.setFont(new Font("SansSerif", Font.BOLD, 12));

        btnOcupar.setBackground(Color.DARK_GRAY);
        btnOcupar.setForeground(Color.WHITE);
        btnOcupar.setFont(new Font("SansSerif", Font.BOLD, 12));

        btnPanel.add(btnAgendar);
        btnPanel.add(btnOcupar);
        formPanel.add(btnPanel);

        add(formPanel, BorderLayout.CENTER);

        configurarAcoes();
    }

    private void configurarAcoes() {
        btnVerHorarios.addActionListener(e -> {
            Barbeiro barbeiro = (Barbeiro) cbBarbeiros.getSelectedItem();
            Servico servico = (Servico) cbServicos.getSelectedItem();
            Date dataSelecionada = dateChooser.getDate();
            if (dataSelecionada == null) {
                JOptionPane.showMessageDialog(this, "Selecione uma data!");
                return;
            }
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
            if (dataSelecionada == null) {
                JOptionPane.showMessageDialog(this, "Selecione uma data!");
                return;
            }

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
            if (dataSelecionada == null) {
                JOptionPane.showMessageDialog(this, "Selecione uma data!");
                return;
            }

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
