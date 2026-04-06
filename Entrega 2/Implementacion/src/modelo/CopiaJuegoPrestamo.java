package modelo;

public class CopiaJuegoPrestamo {
	private EstadoJuego estado;
    private boolean disponible;
    private JuegoMesa juegoMesa;

    public CopiaJuegoPrestamo(EstadoJuego estado, boolean disponible, JuegoMesa juegoMesa)
    {
        this.estado = estado;
        this.disponible = disponible;
        this.juegoMesa = juegoMesa;
    }

    public boolean estaDisponible()
    {
        return disponible && estado != EstadoJuego.DESAPARECIDO;
    }

    public void prestar()
    {
        disponible = false;
    }

    public void devolver()
    {
        disponible = true;
    }

    public void cambiarEstado(EstadoJuego nuevoEstado)
    {
        estado = nuevoEstado;
    }

    public void marcarDesaparecido()
    {
        estado = EstadoJuego.DESAPARECIDO;
        disponible = false;
    }

    public void marcarFaltaPieza()
    {
        estado = EstadoJuego.FALTA_PIEZA;
    }

    public EstadoJuego getEstado()
    {
        return estado;
    }

    public void setEstado(EstadoJuego estado)
    {
        this.estado = estado;
    }

    public boolean isDisponible()
    {
        return disponible;
    }

    public void setDisponible(boolean disponible)
    {
        this.disponible = disponible;
    }

    public JuegoMesa getJuegoMesa()
    {
        return juegoMesa;
    }

    public void setJuegoMesa(JuegoMesa juegoMesa)
    {
        this.juegoMesa = juegoMesa;
    }
}
