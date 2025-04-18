
public class Servico {
    private int id;
    private String nome;
    private int duracaoMinutos;
    private double preco;
    private boolean isCombo;

    public Servico(int id, String nome, int duracaoMinutos, double preco, boolean isCombo) {
        this.id = id;
        this.nome = nome;
        this.duracaoMinutos = duracaoMinutos;
        this.preco = preco;
        this.isCombo = isCombo;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public double getPreco() {
        return preco;
    }

    public boolean isCombo() {
        return isCombo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDuracaoMinutos(int duracaoMinutos) {
        this.duracaoMinutos = duracaoMinutos;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setCombo(boolean isCombo) {
        this.isCombo = isCombo;
    }

    @Override
    public String toString() {
        return nome + " (" + duracaoMinutos + " min - R$" + preco + ")";
    }
}
