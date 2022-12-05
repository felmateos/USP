import java.time.*;
import java.util.*;

public class GerenciadorDeSalas {
    private List<Sala> listaDeSalas = new LinkedList<>();
    private Collection<Reserva> listaDeReservas = new LinkedList<>();

    GerenciadorDeSalas() {}

    public void adicionaSalaChamada(String nome, int capacidadeMaxima, String descricao) {
        try {
            if (encontraSala(nome).getNome().equals(nome)) System.out.println("Sala ja existente.");
        } catch (NullPointerException e) {
            adicionaSala(new Sala(nome, capacidadeMaxima, descricao));
        }
    }

    public void removeSalaChamada(String nomeDaSala) {
        try {
            if(encontraSala(nomeDaSala) == null) System.out.println("A sala '" + nomeDaSala + "' nao existe.");
            listaDeSalas().remove(encontraSala(nomeDaSala));
        } catch (NullPointerException e) {
            System.out.println("Sala nao encontrada.");
        }
    }

    public void adicionaSala(Sala novaSala) {
        try {
            if (encontraSala(novaSala.getNome()).getNome().equals(novaSala.getNome())) System.out.println("Sala ja existente.");
        } catch (NullPointerException e) {
            listaDeSalas().add(novaSala);
        }
    }

    public Reserva reservaSalaChamada(String nomeDaSala, LocalDateTime dataInicial, LocalDateTime dataFinal) {
        try {
            if((encontraSala(nomeDaSala) == null) || !(reservaValida(nomeDaSala, dataInicial, dataFinal))) {
                if(encontraSala(nomeDaSala) == null) System.out.println("A sala '" + nomeDaSala + "' nao existe.");
                throw new ReservaException("A reserva para a sala '" + nomeDaSala + "' é invalida");
            }
            Reserva r = new Reserva(nomeDaSala, dataInicial, dataFinal);
            r.setSala(encontraSala(nomeDaSala));
            getListaDeReservas().add(r);
            return r;
        } catch (ReservaException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Sala encontraSala(String nomeDaSala) {
        for(Sala s : listaDeSalas) {
            if (s.getNome().equals(nomeDaSala)) return s;
        }
        return null;
    }

    public boolean reservaValida(String nomeDaSala, LocalDateTime inicio, LocalDateTime fim) {
        if (inicio.isAfter(fim)) {
            System.out.println("horario invalido.");
            return false;
        }
        for(Reserva r : reservasParaSala(nomeDaSala)) {
            if ((inicio.isAfter(r.getInicio()) && inicio.isBefore(r.getFim()))
            || (fim.isAfter(r.getInicio()) && fim.isBefore(fim)) ) {
                System.out.println("horario invalido.");
                return false;
            }
        }
        return true;
    }

    public void cancelaReserva(Reserva cancelada) {
        try {
            if (getListaDeReservas().contains(cancelada)) {
                reservasParaSala(cancelada.getNomeDaSala()  ).remove(cancelada);
                getListaDeReservas().remove(cancelada);
            } else System.out.println("Reserva nao encontrada");
        } catch (NullPointerException e) {
            System.out.println("Reserva nao encontrada.");
        }
    }

    public Collection<Reserva> reservasParaSala(String nomeSala) {
        List<Reserva> reservasDaSala = new LinkedList<>();
        for (Reserva r : getListaDeReservas()) {
            if (r.getNomeDaSala().equals(nomeSala)) reservasDaSala.add(r);
        }
        return reservasDaSala;
    }

    public void imprimeReservasDaSala(String nomeSala) {
        if (reservasParaSala(nomeSala).isEmpty()) {
            System.out.println("nao existem reservas para a sala: " + nomeSala);
            return;
        }
        int i = 1;
        System.out.println("Sala: " + nomeSala);
        for(Reserva r : reservasParaSala(nomeSala)) {
            System.out.println("Reserva " + i + ":  Inicio: " + r.getInicio() + " - Fim: " + r.getFim());
            i++;
        }
    }

    public List<Sala> listaDeSalas() {
        return listaDeSalas;
    }

    public Collection<Reserva> getListaDeReservas() {
        return listaDeReservas;
    }
}