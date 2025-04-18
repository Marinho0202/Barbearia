
import java.time.LocalDate;
import java.time.LocalTime;

public class Agendamento {
    private int id;
    private Barbeiro barbeiro;
    private LocalDate data;
    private LocalTime horaInicio;
    private Servico servico;
    private Cliente cliente; // Pode ser null se for "Ocupado"
    private boolean isOcupado;

    public Agendamento(int id, Barbeiro barbeiro, LocalDate data, LocalTime horaInicio,
                       Servico servico, Cliente cliente, boolean isOcupado) {
        this.id = id;
        this.barbeiro = barbeiro;
        this.data = data;
        this.horaInicio = horaInicio;
        this.servico = servico;
        this.cliente = cliente;
        this.isOcupado = isOcupado;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public Barbeiro getBarbeiro() {
        return barbeiro;
    }

    public LocalDate getData() {
        return data;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public Servico getServico() {
        return servico;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public boolean isOcupado() {
        return isOcupado;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBarbeiro(Barbeiro barbeiro) {
        this.barbeiro = barbeiro;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setOcupado(boolean ocupado) {
        isOcupado = ocupado;
    }

    @Override
    public String toString() {
        if (isOcupado) {
            return "[OCUPADO] " + horaInicio + " - " + servico.getNome();
        } else {
            return horaInicio + " - " + servico.getNome() + " | Cliente: " + cliente.getNome();
        }
    }
}
