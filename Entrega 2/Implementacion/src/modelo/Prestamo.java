package modelo;

import java.util.ArrayList;
import java.util.List;

public class Prestamo {
	private String fechaInicio;
    private String fechaFin;
    private boolean advertenciaSinMesero;
    private Usuario usuario;
    private Mesa mesa;
    private List<DetallePrestamo> detalles;

    public Prestamo(String fechaInicio, String fechaFin, boolean advertenciaSinMesero, Usuario usuario, Mesa mesa)
    {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.advertenciaSinMesero = advertenciaSinMesero;
        this.usuario = usuario;
        this.mesa = mesa;
        this.detalles = new ArrayList<DetallePrestamo>();
    }

    public void agregarDetalle(DetallePrestamo detalle)
    {
        if (detalle != null && detalles.size() < 2)
        {
            detalles.add(detalle);
        }
    }

    public boolean validarMaximoJuegos()
    {
        return detalles.size() <= 2;
    }

    public boolean validarMesaRequerida()
    {
        if (usuario instanceof Cliente)
        {
            return mesa != null;
        }
        return true;
    }

    public boolean validarRestriccionesMesa(Mesa mesa)
    {
        return mesa != null;
    }

    public boolean validarDisponibilidadCopias()
    {
        for (DetallePrestamo detalle : detalles)
        {
            if (!detalle.getCopiaJuego().estaDisponible())
            {
                return false;
            }
        }
        return true;
    }

    public boolean validarJuegosDificiles(Cafe cafe)
    {
        for (DetallePrestamo detalle : detalles)
        {
            JuegoMesa juego = detalle.getCopiaJuego().getJuegoMesa();
            if (juego.esDificil())
            {
                if (cafe == null || cafe.buscarMeseroCapacitado(juego) == null)
                {
                    return false;
                }
            }
        }
        return true;
    }

    public void registrarAdvertenciaSinMesero()
    {
        advertenciaSinMesero = true;
    }

    public void finalizarPrestamo()
    {
        fechaFin = "DEVUELTO";
        devolverTodasLasCopias();
    }

    public void devolverTodasLasCopias()
    {
        for (DetallePrestamo detalle : detalles)
        {
            detalle.getCopiaJuego().devolver();
            detalle.registrarDevolucion("DEVUELTO");
        }
    }

    public boolean estaActivo()
    {
        return fechaFin == null;
    }

    public String getFechaInicio()
    {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio)
    {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin()
    {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin)
    {
        this.fechaFin = fechaFin;
    }

    public boolean isAdvertenciaSinMesero()
    {
        return advertenciaSinMesero;
    }

    public void setAdvertenciaSinMesero(boolean advertenciaSinMesero)
    {
        this.advertenciaSinMesero = advertenciaSinMesero;
    }

    public Usuario getUsuario()
    {
        return usuario;
    }

    public void setUsuario(Usuario usuario)
    {
        this.usuario = usuario;
    }

    public Mesa getMesa()
    {
        return mesa;
    }

    public void setMesa(Mesa mesa)
    {
        this.mesa = mesa;
    }

    public List<DetallePrestamo> getDetalles()
    {
        return detalles;
    }

    public void setDetalles(List<DetallePrestamo> detalles)
    {
        this.detalles = detalles;
    }
}
