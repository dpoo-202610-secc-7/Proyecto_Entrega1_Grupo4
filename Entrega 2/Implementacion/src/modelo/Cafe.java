package modelo;

import java.util.ArrayList;
import java.util.List;

public class Cafe {
	private int capacidadMaximaClientes;
    private List<Mesa> mesas;
    private List<Empleado> empleados;
    private Administrador administrador;
    private List<JuegoMesa> catalogoJuegos;
    private List<JuegoVenta> inventarioVenta;
    private List<ProductoMenu> menu;
    private List<Venta> ventas;
    private List<Prestamo> prestamos;

    public Cafe(int capacidadMaximaClientes)
    {
        this.capacidadMaximaClientes = capacidadMaximaClientes;
        this.mesas = new ArrayList<Mesa>();
        this.empleados = new ArrayList<Empleado>();
        this.catalogoJuegos = new ArrayList<JuegoMesa>();
        this.inventarioVenta = new ArrayList<JuegoVenta>();
        this.menu = new ArrayList<ProductoMenu>();
        this.ventas = new ArrayList<Venta>();
        this.prestamos = new ArrayList<Prestamo>();
    }

    public boolean verificarCapacidadDisponible(int numeroPersonas)
    {
        return numeroPersonas <= capacidadMaximaClientes;
    }

    public Mesa buscarMesaDisponible(int numeroPersonas, String fechaHora)
    {
        for (Mesa mesa : mesas)
        {
            if (mesa.getReservaActiva() == null)
            {
                return mesa;
            }
        }
        return null;
    }

    public void agregarMesa(Mesa mesa)
    {
        if (mesa != null)
        {
            mesas.add(mesa);
        }
    }

    public void agregarEmpleado(Empleado empleado)
    {
        if (empleado != null)
        {
            empleados.add(empleado);
        }
    }

    public void agregarJuegoCatalogo(JuegoMesa juego)
    {
        if (juego != null)
        {
            catalogoJuegos.add(juego);
        }
    }

    public void agregarJuegoVenta(JuegoVenta juego)
    {
        if (juego != null)
        {
            inventarioVenta.add(juego);
        }
    }

    public void agregarProductoMenu(ProductoMenu producto)
    {
        if (producto != null)
        {
            menu.add(producto);
        }
    }

    public List<JuegoMesa> consultarCatalogoJuegos()
    {
        return catalogoJuegos;
    }

    public List<ProductoMenu> consultarMenu()
    {
        return menu;
    }

    public List<CopiaJuegoPrestamo> consultarInventarioPrestamo()
    {
        List<CopiaJuegoPrestamo> copias = new ArrayList<CopiaJuegoPrestamo>();
        for (JuegoMesa juego : catalogoJuegos)
        {
            copias.addAll(juego.getCopias());
        }
        return copias;
    }

    public List<JuegoVenta> consultarInventarioVenta()
    {
        return inventarioVenta;
    }

    public void registrarVenta(Venta venta)
    {
        if (venta != null)
        {
            ventas.add(venta);
        }
    }

    public void registrarPrestamo(Prestamo prestamo)
    {
        if (prestamo != null)
        {
            prestamos.add(prestamo);
        }
    }

    public boolean hayClientesPorAtender()
    {
        for (Mesa mesa : mesas)
        {
            if (mesa.getReservaActiva() != null)
            {
                return true;
            }
        }
        return false;
    }

    public Mesero buscarMeseroCapacitado(JuegoMesa juego)
    {
        for (Empleado empleado : empleados)
        {
            if (empleado instanceof Mesero)
            {
                Mesero mesero = (Mesero) empleado;
                if (mesero.puedeExplicarJuego(juego))
                {
                    return mesero;
                }
            }
        }
        return null;
    }

    public List<Venta> generarInformeVentas(String granularidad, TipoVenta tipoVenta)
    {
        List<Venta> resultado = new ArrayList<Venta>();
        for (Venta venta : ventas)
        {
            if (tipoVenta == null || venta.getTipoVenta() == tipoVenta)
            {
                resultado.add(venta);
            }
        }
        return resultado;
    }

    public int getCapacidadMaximaClientes()
    {
        return capacidadMaximaClientes;
    }

    public void setCapacidadMaximaClientes(int capacidadMaximaClientes)
    {
        this.capacidadMaximaClientes = capacidadMaximaClientes;
    }

    public List<Mesa> getMesas()
    {
        return mesas;
    }

    public void setMesas(List<Mesa> mesas)
    {
        this.mesas = mesas;
    }

    public List<Empleado> getEmpleados()
    {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados)
    {
        this.empleados = empleados;
    }

    public Administrador getAdministrador()
    {
        return administrador;
    }

    public void setAdministrador(Administrador administrador)
    {
        this.administrador = administrador;
    }

    public List<JuegoMesa> getCatalogoJuegos()
    {
        return catalogoJuegos;
    }

    public void setCatalogoJuegos(List<JuegoMesa> catalogoJuegos)
    {
        this.catalogoJuegos = catalogoJuegos;
    }

    public List<JuegoVenta> getInventarioVenta()
    {
        return inventarioVenta;
    }

    public void setInventarioVenta(List<JuegoVenta> inventarioVenta)
    {
        this.inventarioVenta = inventarioVenta;
    }

    public List<ProductoMenu> getMenu()
    {
        return menu;
    }

    public void setMenu(List<ProductoMenu> menu)
    {
        this.menu = menu;
    }

    public List<Venta> getVentas()
    {
        return ventas;
    }

    public void setVentas(List<Venta> ventas)
    {
        this.ventas = ventas;
    }

    public List<Prestamo> getPrestamos()
    {
        return prestamos;
    }

    public void setPrestamos(List<Prestamo> prestamos)
    {
        this.prestamos = prestamos;
    }
}
