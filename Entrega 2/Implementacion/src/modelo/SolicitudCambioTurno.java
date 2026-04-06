package modelo;

public class SolicitudCambioTurno {
	private TipoSolicitud tipoSolicitud;
    private String fechaHora;
    private String estado;
    private Empleado solicitante;
    private Turno turnoOriginal;
    private Turno turnoPropuesto;
    private Empleado empleadoDestino;

    public SolicitudCambioTurno(TipoSolicitud tipoSolicitud, String fechaHora, String estado, Empleado solicitante,
            Turno turnoOriginal, Turno turnoPropuesto, Empleado empleadoDestino)
    {
        this.tipoSolicitud = tipoSolicitud;
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.solicitante = solicitante;
        this.turnoOriginal = turnoOriginal;
        this.turnoPropuesto = turnoPropuesto;
        this.empleadoDestino = empleadoDestino;
    }

    public void aprobar()
    {
        estado = "APROBADA";
    }

    public void rechazar()
    {
        estado = "RECHAZADA";
    }

    public boolean esIntercambio()
    {
        return tipoSolicitud == TipoSolicitud.INTERCAMBIO;
    }

    public boolean esCambioSimple()
    {
        return tipoSolicitud == TipoSolicitud.CAMBIO;
    }

    public boolean estaPendiente()
    {
        return "PENDIENTE".equals(estado);
    }

    public boolean validarMinimosOperacion(Cafe cafe)
    {
        return true;
    }

    public TipoSolicitud getTipoSolicitud()
    {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(TipoSolicitud tipoSolicitud)
    {
        this.tipoSolicitud = tipoSolicitud;
    }

    public String getFechaHora()
    {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora)
    {
        this.fechaHora = fechaHora;
    }

    public String getEstado()
    {
        return estado;
    }

    public void setEstado(String estado)
    {
        this.estado = estado;
    }

    public Empleado getSolicitante()
    {
        return solicitante;
    }

    public void setSolicitante(Empleado solicitante)
    {
        this.solicitante = solicitante;
    }

    public Turno getTurnoOriginal()
    {
        return turnoOriginal;
    }

    public void setTurnoOriginal(Turno turnoOriginal)
    {
        this.turnoOriginal = turnoOriginal;
    }

    public Turno getTurnoPropuesto()
    {
        return turnoPropuesto;
    }

    public void setTurnoPropuesto(Turno turnoPropuesto)
    {
        this.turnoPropuesto = turnoPropuesto;
    }

    public Empleado getEmpleadoDestino()
    {
        return empleadoDestino;
    }

    public void setEmpleadoDestino(Empleado empleadoDestino)
    {
        this.empleadoDestino = empleadoDestino;
    }
}
