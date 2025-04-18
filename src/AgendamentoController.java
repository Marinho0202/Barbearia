import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AgendamentoController {

    private static final LocalTime HORA_ABERTURA = LocalTime.of(9, 0);
    private static final LocalTime HORA_FECHAMENTO = LocalTime.of(20, 0); // ajuste se necessário

    private AgendamentoDAO dao;

    public AgendamentoController() {
        this.dao = new AgendamentoDAO();
    }

    public List<LocalTime> gerarHorariosDisponiveis(Barbeiro barbeiro, LocalDate data, Servico servico) {
        List<Agendamento> agendamentos = dao.listarPorBarbeiroEData(barbeiro.getId(), data);
        List<LocalTime> horariosDisponiveis = new ArrayList<>();

        int duracao = servico.getDuracaoMinutos();
        LocalTime horaAtual = HORA_ABERTURA;

        while (!horaAtual.plusMinutes(duracao).isAfter(HORA_FECHAMENTO)) {
            boolean conflita = false;

            for (Agendamento ag : agendamentos) {
                LocalTime inicio = ag.getHoraInicio();
                LocalTime fim = inicio.plusMinutes(ag.getServico().getDuracaoMinutos());

                LocalTime tentativaFim = horaAtual.plusMinutes(duracao);

                if (!(tentativaFim.isBefore(inicio) || horaAtual.isAfter(fim.minusMinutes(1)))) {
                    conflita = true;
                    break;
                }
            }

            if (!conflita) {
                horariosDisponiveis.add(horaAtual);
            }

            horaAtual = horaAtual.plusMinutes(10); // avança em blocos de 10 min
        }

        return horariosDisponiveis;
    }

    public boolean agendarServico(Barbeiro barbeiro, Servico servico, LocalDate data, LocalTime hora, Cliente cliente) {
        Agendamento ag = new Agendamento(
                0,
                barbeiro,
                data,
                hora,
                servico,
                cliente,
                false // não é bloqueio
        );
        return dao.salvarAgendamento(ag);
    }

    public boolean marcarComoOcupado(Barbeiro barbeiro, Servico servico, LocalDate data, LocalTime hora) {
        Agendamento bloqueio = new Agendamento(
                0,
                barbeiro,
                data,
                hora,
                servico,
                null,
                true // é um bloqueio
        );
        return dao.salvarAgendamento(bloqueio);
    }

    public List<Agendamento> listarAgendamentos(Barbeiro barbeiro, LocalDate data) {
        return dao.listarPorBarbeiroEData(barbeiro.getId(), data);
    }
}
