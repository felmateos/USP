public class Sala {
    private String nome;
    private int capacidadeMaxima;
    private String local;
    private String observacoes;

    Sala() {}

    Sala (String nome, int capacidadeMaxima, String observacoes) {
        this.nome = nome;
        this.capacidadeMaxima = capacidadeMaxima;
        this.observacoes = observacoes;
    }

    public String getNome() {
        return nome;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public String getLocal() {
        return local;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCapacidade(int capacidadeMaxima) {
        this.capacidadeMaxima = capacidadeMaxima;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}