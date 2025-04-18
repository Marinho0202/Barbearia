import java.util.ArrayList;
import java.util.List;

public class Barbearia {

    private List<Barbeiro> barbeiros;
    private List<Servico> servicos;

    public Barbearia() {
        this.barbeiros = new ArrayList<>();
        this.servicos = new ArrayList<>();

        // Barbeiros fixos
        barbeiros.add(new Barbeiro(1, "João"));
        barbeiros.add(new Barbeiro(2, "Carlos"));
        barbeiros.add(new Barbeiro(3, "Lucas"));

        // Serviços fixos
        servicos.add(new Servico(1, "Corte de Cabelo", 50, 35.00, false));
        servicos.add(new Servico(2, "Barba", 40, 30.00, false));
        servicos.add(new Servico(3, "Platinagem", 120, 250.00, false));
        servicos.add(new Servico(4, "Corte de Cabelo e Barba", 80, 60.00, true));
    }

    public List<Barbeiro> getBarbeiros() {
        return barbeiros;
    }

    public List<Servico> getServicos() {
        return servicos;
    }

    public Servico getServicoPorNome(String nome) {
        for (Servico s : servicos) {
            if (s.getNome().equalsIgnoreCase(nome)) {
                return s;
            }
        }
        return null;
    }

    public Barbeiro getBarbeiroPorId(int id) {
        for (Barbeiro b : barbeiros) {
            if (b.getId() == id) {
                return b;
            }
        }
        return null;
    }
}
