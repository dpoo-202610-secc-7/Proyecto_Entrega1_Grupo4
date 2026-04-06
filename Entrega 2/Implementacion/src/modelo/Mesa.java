package modelo;

import java.util.ArrayList;
import java.util.List;

public class Mesa {
	private int numeroMesa;
    private List<ReservaMesa> reservas;

    public Mesa(int numeroMesa)
    {
        this.numeroMesa = numeroMesa;
        this.reservas = new ArrayList<ReservaMesa>();
    }

    public void asignarReserva(ReservaMesa reserva)
    {
        if (reserva != null)
        {
            reservas.add(reserva);
        }
    }

    public void cerrarReservaActiva()
    {
        ReservaMesa reservaActiva = getReservaActiva();
        if (reservaActiva != null)
        {
            reservaActiva.cerrar();
        }
    }

    public boolean tieneMenoresEdad()
    {
        ReservaMesa reservaActiva = getReservaActiva();
        return reservaActiva != null && reservaActiva.isHayMenoresEdad();
    }

    public boolean tieneNinosMenores5()
    {
        ReservaMesa reservaActiva = getReservaActiva();
        return reservaActiva != null && reservaActiva.isHayNinosMenores5();
    }

    public boolean admiteJuego(JuegoMesa juego, int numeroPersonas)
    {
        if (juego == null)
        {
            return false;
        }
        return juego.esAptoParaCantidadJugadores(numeroPersonas) && juego.esAptoParaEdad(tieneNinosMenores5(), tieneMenoresEdad());
    }

    public boolean tieneBebidaCalienteActiva()
    {
        return false;
    }

    public boolean tieneJuegoAccionActivo()
    {
        return false;
    }

    public boolean puedeRecibirBebida(Bebida bebida)
    {
        if (bebida == null)
        {
            return false;
        }

        if (bebida.esAlcoholica() && tieneMenoresEdad())
        {
            return false;
        }

        if (bebida.esCaliente() && tieneJuegoAccionActivo())
        {
            return false;
        }

        return true;
    }

    public boolean puedeRecibirJuego(JuegoMesa juego)
    {
        if (juego == null)
        {
            return false;
        }

        if (juego.esCategoriaAccion() && tieneBebidaCalienteActiva())
        {
            return false;
        }

        ReservaMesa reservaActiva = getReservaActiva();
        if (reservaActiva == null)
        {
            return false;
        }

        return admiteJuego(juego, reservaActiva.getNumeroPersonas());
    }

    public ReservaMesa getReservaActiva()
    {
        for (ReservaMesa reserva : reservas)
        {
            if (reserva.estaActiva())
            {
                return reserva;
            }
        }
        return null;
    }

    public int getNumeroMesa()
    {
        return numeroMesa;
    }

    public void setNumeroMesa(int numeroMesa)
    {
        this.numeroMesa = numeroMesa;
    }

    public List<ReservaMesa> getReservas()
    {
        return reservas;
    }

    public void setReservas(List<ReservaMesa> reservas)
    {
        this.reservas = reservas;
    }
}
