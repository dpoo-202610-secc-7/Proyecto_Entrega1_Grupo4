package modelo;

import java.util.ArrayList;
import java.util.List;

public class Mesero extends Empleado {
	private List<JuegoMesa> juegosConocidos;

    public Mesero(String documentoIdentidad, String nombre, String correoElectronico, String login, String password, String codigoEmpleado)
    {
        super(documentoIdentidad, nombre, correoElectronico, login, password, codigoEmpleado);
        this.juegosConocidos = new ArrayList<JuegoMesa>();
    }

    public void registrarJuegoConocido(JuegoMesa juego)
    {
        if (juego != null && !juegosConocidos.contains(juego))
        {
            juegosConocidos.add(juego);
        }
    }

    public void eliminarJuegoConocido(JuegoMesa juego)
    {
        juegosConocidos.remove(juego);
    }

    public List<JuegoMesa> consultarJuegosConocidos()
    {
        return juegosConocidos;
    }

    public boolean puedeExplicarJuego(JuegoMesa juego)
    {
        return juegosConocidos.contains(juego);
    }

    public void registrarVenta(Venta venta)
    {
        if (venta != null)
        {
            venta.setRegistradaPor(this);
        }
    }

    public List<JuegoMesa> getJuegosConocidos()
    {
        return juegosConocidos;
    }

    public void setJuegosConocidos(List<JuegoMesa> juegosConocidos)
    {
        this.juegosConocidos = juegosConocidos;
    }
}
