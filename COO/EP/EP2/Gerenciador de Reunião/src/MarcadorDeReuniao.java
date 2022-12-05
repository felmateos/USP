import java.time.*;
import java.util.*;

public class MarcadorDeReuniao {
    private LocalDate dataInicial;
    private LocalDate dataFinal;
    private Collection<String> listaDeParticipante;
    private Map<String, List<Intervalo>> disponibilidades = new HashMap<>();

    MarcadorDeReuniao() {}

    public void marcarReuniaoEntre(LocalDate dataInicial, LocalDate dataFinal, Collection<String> listaDeParticipantes) {
        if (!validaDatas(dataInicial, dataFinal)) {
            System.out.println("Data invalida.");
            return;
        }
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
        this.listaDeParticipante = listaDeParticipantes;
        inicializaDisponibilidades();
    }

    public void inicializaDisponibilidades() {
        for(String p : getListaDeParticipante()) {
            List<Intervalo> intervalos = new ArrayList<>();
            getdisponibilidades().put(p, intervalos);
        }
    }

    public void indicaDisponibilidadeDe(String participante, LocalDateTime inicio, LocalDateTime fim) {
        if (!verificaDatas(inicio, fim) || !validaDatas(inicio, fim)
                || !getdisponibilidades().containsKey(participante)) {
            System.out.println("Data e/ou participante invalido(s)");
            return;
        }
        getdisponibilidades().get(participante).add(new Intervalo(inicio, fim));
        Collections.sort(getdisponibilidades().get(participante));
    }

    public void mostraSobreposicao() {
        List<Intervalo> sobreposicoes = encontraSobreposicao();
        if (!sobreposicoes.isEmpty()) {
            System.out.println("\nSobreposicao de disponibilidades: ");
            for(Intervalo intervalo : sobreposicoes) {
                System.out.println("   Inicial: " + intervalo.getDataInicial() + " - Final: " + intervalo.getDataFinal());
            }
        } else System.out.println("\nNao existe um intervalo de tempo onde todos os participantes possam comparecer a reuniao.");
        System.out.println();
    }

    public List<Intervalo> encontraSobreposicao() {
        List<Intervalo> sobreposicoes = new LinkedList<>();
        LinkedList<Horario> horarios = addHorarios();
        LocalDateTime inicio = horarios.getFirst().getHorario();
        boolean valido = false;
        int cont = getListaDeParticipante().size();
        System.out.println("\nHorarios de todos os participantes (processo para encontrar a sobreposicao):");
        for(Horario h : horarios) {
            System.out.print("   " + h.getHorario() + ", tipo: " + h.getIdentificador());
            if (h.getIdentificador().equals("Inicial") && inicio.isBefore(h.getHorario())) {
                System.out.print(" - (Candidato a inicio da sobreposicao " + (sobreposicoes.size()+1) + ")");
                inicio = h.getHorario();
                valido = true;
            } else if (h.getIdentificador().equals("Final") && valido && cont == 0) {
                System.out.print(" - (Fim da sobreposicao " + (sobreposicoes.size()+1) + ")");
                sobreposicoes.add(new Intervalo(inicio, h.getHorario()));
                valido = false;
            }
            if (h.getIdentificador().equals("Inicial") && cont > 0) cont--;
            else cont++;
            System.out.println();
        }
        return sobreposicoes;
    }

    public LinkedList<Horario> addHorarios() {
        LinkedList<Horario> horarios = new LinkedList<>();
        for(String s : getListaDeParticipante()) {
            for(Intervalo i : getdisponibilidades().get(s)) {
                horarios.add(new Horario(i.getDataInicial(), "Inicial"));
                horarios.add(new Horario(i.getDataFinal(), "Final"));
            }
        }
        Collections.sort(horarios);
        return horarios;
    }

    public void exibeDisponibilidades() {
        for (String participante : getListaDeParticipante()) {
            System.out.println("Disponibilidades de " + participante + ":");
            if (!getdisponibilidades().get(participante).isEmpty()) {
                for (Intervalo intervalo : getdisponibilidades().get(participante))
                    System.out.println("   Inicial: " + intervalo.getDataInicial() + " - Final: " + intervalo.getDataFinal());
            } else System.out.println("   sem disponibilidades");
        }
    }

    public boolean validaDatas(Object inicio, Object fim) {
        LocalDateTime dataInicio;
        LocalDateTime dataFim;
        try {
            dataInicio = (LocalDateTime) inicio;
            dataFim = (LocalDateTime) fim;
        } catch (Exception e) {
            dataInicio = LocalDateTime.parse(inicio.toString()+"T00:00");
            dataFim = LocalDateTime.parse(fim.toString()+"T23:59");
        }
        return (dataInicio.isBefore(dataFim));
    }

    public boolean verificaDatas(LocalDateTime inicio, LocalDateTime fim) {
        LocalDateTime inicioReuniao = getDataInicial().atStartOfDay();
        LocalDateTime fimReuniao = LocalDateTime.parse(getDataFinal().toString()+"T23:59");
        return (inicio.isAfter(inicioReuniao) && fim.isBefore(fimReuniao));
    }

    public LocalDate getDataFinal() {
        return dataFinal;
    }

    public LocalDate getDataInicial() {
        return dataInicial;
    }

    public Collection<String> getListaDeParticipante() {
        return listaDeParticipante;
    }

    public Map<String, List<Intervalo>> getdisponibilidades() {
        return disponibilidades;
    }
}