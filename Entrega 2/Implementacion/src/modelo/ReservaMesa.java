package modelo;

public class ReservaMesa {
	private String fechaHora;
    private int numeroPersonas;
    private boolean hayNinosMenores5;
    private boolean hayMenoresEdad;
    private EstadoReserva estadoReserva;
    private Cliente cliente;
    private Mesa mesa;

    public ReservaMesa(String fechaHora, int numeroPersonas, boolean hayNinosMenores5, boolean hayMenoresEdad, Cliente cliente, Mesa mesa)
    {
        this.fechaHora = fechaHora;
        this.numeroPersonas = numeroPersonas;
        this.hayNinosMenores5 = hayNinosMenores5;
        this.hayMenoresEdad = hayMenoresEdad;
        this.estadoReserva = EstadoReserva.ACEPTADA;
        this.cliente = cliente;
        this.mesa = mesa;
    }

    public void aceptar()
    {
        estadoReserva = EstadoReserva.ACEPTADA;
    }

    public void rechazar()
    {
        estadoReserva = EstadoReserva.RECHAZADA;
    }

    public void activar()
    {
        estadoReserva = EstadoReserva.ACTIVA;
    }

    public void cerrar()
    {
        estadoReserva = EstadoReserva.CERRADA;
    }

    public boolean estaActiva()
    {
        return estadoReserva == EstadoReserva.ACTIVA;
    }

    public boolean validarCapacidad(Cafe cafe)
    {
        return cafe != null && cafe.verificarCapacidadDisponible(numeroPersonas);
    }

    public String getFechaHora()
    {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora)
    {
        this.fechaHora = fechaHora;
    }

    public int getNumeroPersonas()
    {
        return numeroPersonas;
    }

    public void setNumeroPersonas(int numeroPersonas)
    {
        this.numeroPersonas = numeroPersonas;
    }

    public boolean isHayNinosMenores5()
    {
        return hayNinosMenores5;
    }

    public void setHayNinosMenores5(boolean hayNinosMenores5)
    {
        this.hayNinosMenores5 = hayNinosMenores5;
    }

    public boolean isHayMenoresEdad()
    {
        return hayMenoresEdad;
    }

    public void setHayMenoresEdad(boolean hayMenoresEdad)
    {
        this.hayMenoresEdad = hayMenoresEdad;
    }

    public EstadoReserva getEstadoReserva()
    {
        return estadoReserva;
    }

    public void setEstadoReserva(EstadoReserva estadoReserva)
    {
        this.estadoReserva = estadoReserva;
    }

    public Cliente getCliente()
    {
        return cliente;
    }

    public void setCliente(Cliente cliente)
    {
        this.cliente = cliente;
    }

    public Mesa getMesa()
    {
        return mesa;
    }

    public void setMesa(Mesa mesa)
    {
        this.mesa = mesa;
    }
}
