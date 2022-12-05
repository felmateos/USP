import java.time.*;

public class Intervalo implements Comparable<Intervalo>{
    private LocalDateTime dataInicial;
    private LocalDateTime dataFinal;

    Intervalo(LocalDateTime dataInicial, LocalDateTime dataFinal) {
       this.dataInicial = dataInicial;
       this.dataFinal = dataFinal;
    }

    public LocalDateTime getDataInicial() {
        return dataInicial;
    }

    public LocalDateTime getDataFinal() {
        return dataFinal;
    }

    public void setDataInicial(LocalDateTime dataInicial) {
        this.dataInicial = dataInicial;
    }

    public void setDataFinal(LocalDateTime dataFinal) {
        this.dataFinal = dataFinal;
    }

    @Override
    public int compareTo(Intervalo d) {
        return dataInicial.compareTo(d.dataInicial);
    }
}
