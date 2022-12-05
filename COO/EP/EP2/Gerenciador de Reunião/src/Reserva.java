import java.time.*;

public class Reserva {
    private String nomeDaSala;
    private Sala sala;
    private LocalDateTime inicio;
    private LocalDateTime fim;

    Reserva () {}

    Reserva (String nomeDaSala, LocalDateTime inicio, LocalDateTime fim) {
        this.nomeDaSala = nomeDaSala;
        this.inicio = inicio;
        this.fim = fim;
    }

    public String getNomeDaSala() {
        return nomeDaSala;
    }

    public Sala getSala() {
        return sala;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public LocalDateTime getFim() {
        return fim;
    }

    public void setNomeDaSala(String nomeDaSala) {
        this.nomeDaSala = nomeDaSala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public void setInicio(LocalDateTime inicio) {
        this.inicio = inicio;
    }

    public void setFim(LocalDateTime fim) {
        this.fim = fim;
    }
}
