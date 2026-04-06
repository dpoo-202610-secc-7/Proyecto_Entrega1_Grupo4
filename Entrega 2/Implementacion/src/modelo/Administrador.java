package modelo;

import java.util.ArrayList;
import java.util.List;

public class Administrador extends Usuario {
	public Administrador(String documentoIdentidad, String nombre, String correoElectronico, String login, String password)
    {
        super(documentoIdentidad, nombre, correoElectronico, login, password);
    }

    public List<CopiaJuegoPrestamo> consultarInventarioPrestamo()
    {
        return new ArrayList<CopiaJuegoPrestamo>();
    }

    public List<JuegoVenta> consultarInventarioVenta()
    {
        return new ArrayList<JuegoVenta>();
    }

    public List<Prestamo> consultarHistorialPrestamos()
    {
        return new ArrayList<Prestamo>();
    }

    public void moverJuegoVentaAPrestamo(JuegoVenta juego, int cantidad)
    {
        // No se pudo implementar aún
    }

    public void reabastecerJuegoVenta(JuegoVenta juego, int cantidad)
    {
        if (juego != null)
        {
            juego.aumentarStock(cantidad);
        }
    }

    public void reabastecerCopiaPrestamo(JuegoMesa juego, int cantidad)
    {
    	// No se pudo implementar aún
    }

    public void repararJuego(CopiaJuegoPrestamo copia, JuegoVenta reemplazo)
    {
        if (copia != null)
        {
            copia.cambiarEstado(EstadoJuego.BUENO);
        }
    }

    public void marcarJuegoDesaparecido(CopiaJuegoPrestamo copia)
    {
        if (copia != null)
        {
            copia.marcarDesaparecido();
        }
    }

    public void agregarProductoMenu(ProductoMenu producto)
    {
    	// No se pudo implementar aún
    }

    public void aprobarSugerencia(SugerenciaPlatillo sugerencia)
    {
        if (sugerencia != null)
        {
            sugerencia.aprobar();
        }
    }

    public void rechazarSugerencia(SugerenciaPlatillo sugerencia)
    {
        if (sugerencia != null)
        {
            sugerencia.rechazar();
        }
    }

    public void asignarTurno(Empleado empleado, Turno turno)
    {
        if (empleado != null && turno != null)
        {
            empleado.getTurnos().add(turno);
        }
    }

    public void modificarTurno(Empleado empleado, Turno turnoAnterior, Turno nuevoTurno)
    {
        if (empleado != null && turnoAnterior != null && nuevoTurno != null)
        {
            List<Turno> turnos = empleado.getTurnos();
            int posicion = turnos.indexOf(turnoAnterior);
            if (posicion != -1)
            {
                turnos.set(posicion, nuevoTurno);
            }
        }
    }

    public void aprobarSolicitudCambio(SolicitudCambioTurno solicitud)
    {
        if (solicitud != null)
        {
            solicitud.aprobar();
        }
    }

    public void rechazarSolicitudCambio(SolicitudCambioTurno solicitud)
    {
        if (solicitud != null)
        {
            solicitud.rechazar();
        }
    }

    public List<Venta> generarInformeVentas(String granularidad, TipoVenta tipoVenta)
    {
        return new ArrayList<Venta>();
    }

    public List<Venta> consultarVentasPorRubro(String granularidad)
    {
        return new ArrayList<Venta>();
    }
}
