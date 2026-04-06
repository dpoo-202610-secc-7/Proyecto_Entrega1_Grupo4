package modelo;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Usuario {
	private int puntosFidelidad;
    private List<JuegoMesa> juegosFavoritos;

    public Cliente(String documentoIdentidad, String nombre, String correoElectronico, String login, String password)
    {
        super(documentoIdentidad, nombre, correoElectronico, login, password);
        this.puntosFidelidad = 0;
        this.juegosFavoritos = new ArrayList<JuegoMesa>();
    }

    public ReservaMesa crearReserva(Mesa mesa, String fechaHora, int numeroPersonas, boolean hayNinosMenores5, boolean hayMenoresEdad)
    {
        return new ReservaMesa(fechaHora, numeroPersonas, hayNinosMenores5, hayMenoresEdad, this, mesa);
    }

    public Prestamo solicitarPrestamo(Mesa mesa, List<CopiaJuegoPrestamo> copias)
    {
        return new Prestamo(null, null, false, this, mesa);
    }

    public void devolverPrestamo(Prestamo prestamo)
    {
        if (prestamo != null)
        {
            prestamo.finalizarPrestamo();
        }
    }

    public Venta comprarProductos(List<ItemVenta> items, double propina)
    {
        Venta venta = new Venta(null, TipoVenta.CAFETERIA, 0, propina, this, null);
        if (items != null)
        {
            for (ItemVenta item : items)
            {
                venta.agregarItem(item);
            }
        }
        return venta;
    }

    public void aplicarCodigoDescuento(String codigo)
    {
    	// No se pudo implementar aún
    }

    public void usarPuntosFidelidad(double valor)
    {
        puntosFidelidad -= (int) valor;
    }

    public void acumularPuntos(double valorCompra)
    {
        puntosFidelidad += (int) valorCompra;
    }

    public int consultarPuntosFidelidad()
    {
        return puntosFidelidad;
    }

    public List<Venta> consultarHistorialCompras()
    {
        return new ArrayList<Venta>();
    }

    public void agregarJuegoFavorito(JuegoMesa juego)
    {
        if (juego != null && !juegosFavoritos.contains(juego))
        {
            juegosFavoritos.add(juego);
        }
    }

    public void eliminarJuegoFavorito(JuegoMesa juego)
    {
        juegosFavoritos.remove(juego);
    }

    public List<JuegoMesa> consultarJuegosFavoritos()
    {
        return juegosFavoritos;
    }

    public int getPuntosFidelidad()
    {
        return puntosFidelidad;
    }

    public void setPuntosFidelidad(int puntosFidelidad)
    {
        this.puntosFidelidad = puntosFidelidad;
    }
    
}
