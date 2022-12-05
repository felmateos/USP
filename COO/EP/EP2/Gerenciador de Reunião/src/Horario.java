import java.time.LocalDateTime;

public class Horario implements Comparable<Horario>{
    private LocalDateTime horario;
    private String identificador;

    Horario(LocalDateTime horario, String identificador) {
        this.horario = horario;
        this.identificador = identificador;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    public String getIdentificador() {
        return identificador;
    }

    @Override
    public int compareTo(Horario outroHorario) {
        return getHorario().compareTo(outroHorario.getHorario());
    }
}
