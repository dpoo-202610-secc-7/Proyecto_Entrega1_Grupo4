package modelo;

public class DetallePrestamo {
	private String fechaAsignacion;
    private String fechaDevolucion;
    private CopiaJuegoPrestamo copiaJuego;

    public DetallePrestamo(CopiaJuegoPrestamo copiaJuego)
    {
        this.copiaJuego = copiaJuego;
    }

    public void registrarAsignacion(String fechaHora)
    {
        fechaAsignacion = fechaHora;
    }

    public void registrarDevolucion(String fechaHora)
    {
        fechaDevolucion = fechaHora;
    }

    public boolean estaDevuelto()
    {
        return fechaDevolucion != null;
    }

    public String getFechaAsignacion()
    {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(String fechaAsignacion)
    {
        this.fechaAsignacion = fechaAsignacion;
    }

    public String getFechaDevolucion()
    {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(String fechaDevolucion)
    {
        this.fechaDevolucion = fechaDevolucion;
    }

    public CopiaJuegoPrestamo getCopiaJuego()
    {
        return copiaJuego;
    }

    public void setCopiaJuego(CopiaJuegoPrestamo copiaJuego)
    {
        this.copiaJuego = copiaJuego;
    }
}
