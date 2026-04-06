package modelo;

public class SugerenciaPlatillo {
	private String nombrePropuesto;
    private CategoriaPropuesta categoriaPropuesta;
    private String fechaHora;
    private EstadoSugerencia estado;
    private Empleado empleado;
    private Administrador revisadaPor;

    public SugerenciaPlatillo(String nombrePropuesto, CategoriaPropuesta categoriaPropuesta, String fechaHora,
            EstadoSugerencia estado, Empleado empleado, Administrador revisadaPor)
    {
        this.nombrePropuesto = nombrePropuesto;
        this.categoriaPropuesta = categoriaPropuesta;
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.empleado = empleado;
        this.revisadaPor = revisadaPor;
    }

    public void aprobar()
    {
        estado = EstadoSugerencia.APROBADA;
    }

    public void rechazar()
    {
        estado = EstadoSugerencia.RECHAZADA;
    }

    public boolean estaPendiente()
    {
        return estado == EstadoSugerencia.PENDIENTE;
    }

    public String getNombrePropuesto()
    {
        return nombrePropuesto;
    }

    public void setNombrePropuesto(String nombrePropuesto)
    {
        this.nombrePropuesto = nombrePropuesto;
    }

    public CategoriaPropuesta getCategoriaPropuesta()
    {
        return categoriaPropuesta;
    }

    public void setCategoriaPropuesta(CategoriaPropuesta categoriaPropuesta)
    {
        this.categoriaPropuesta = categoriaPropuesta;
    }

    public String getFechaHora()
    {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora)
    {
        this.fechaHora = fechaHora;
    }

    public EstadoSugerencia getEstado()
    {
        return estado;
    }

    public void setEstado(EstadoSugerencia estado)
    {
        this.estado = estado;
    }

    public Empleado getEmpleado()
    {
        return empleado;
    }

    public void setEmpleado(Empleado empleado)
    {
        this.empleado = empleado;
    }

    public Administrador getRevisadaPor()
    {
        return revisadaPor;
    }

    public void setRevisadaPor(Administrador revisadaPor)
    {
        this.revisadaPor = revisadaPor;
    }
}
