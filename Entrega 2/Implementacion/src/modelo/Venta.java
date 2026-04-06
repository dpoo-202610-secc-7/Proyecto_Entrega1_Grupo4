package modelo;

import java.util.ArrayList;
import java.util.List;

public class Venta {
	private String fechaHora;
    private TipoVenta tipoVenta;
    private double descuentoAplicado;
    private double propina;
    private Usuario comprador;
    private Mesero registradaPor;
    private List<ItemVenta> items;

    public Venta(String fechaHora, TipoVenta tipoVenta, double descuentoAplicado, double propina, Usuario comprador, Mesero registradaPor)
    {
        this.fechaHora = fechaHora;
        this.tipoVenta = tipoVenta;
        this.descuentoAplicado = descuentoAplicado;
        this.propina = propina;
        this.comprador = comprador;
        this.registradaPor = registradaPor;
        this.items = new ArrayList<ItemVenta>();
    }

    public void agregarItem(ItemVenta item)
    {
        if (item != null)
        {
            items.add(item);
        }
    }

    public double calcularSubtotal()
    {
        double total = 0;
        for (ItemVenta item : items)
        {
            total += item.calcularSubtotal();
        }
        return total;
    }

    public double calcularDescuento()
    {
        return descuentoAplicado;
    }

    public double calcularImpuestos()
    {
        if (tipoVenta == TipoVenta.CAFETERIA)
        {
            return calcularSubtotal() * 0.08;
        }
        else
        {
            return calcularSubtotal() * 0.19;
        }
    }

    public double calcularPropina()
    {
        return propina;
    }

    public double calcularTotal()
    {
        return calcularSubtotal() - descuentoAplicado + calcularImpuestos() + propina;
    }

    public void aplicarDescuentoPorcentaje(double porcentaje)
    {
        descuentoAplicado = calcularSubtotal() * porcentaje;
    }

    public void aplicarPuntosFidelidad(int puntos)
    {
        descuentoAplicado += puntos;
    }

    public void registrarPropina(double valor)
    {
        propina = valor;
    }

    public double generarPuntosFidelidad()
    {
        return calcularSubtotal() * 0.01;
    }

    public boolean esVentaCafeteria()
    {
        return tipoVenta == TipoVenta.CAFETERIA;
    }

    public boolean esVentaTienda()
    {
        return tipoVenta == TipoVenta.TIENDA_JUEGOS;
    }

    public String getFechaHora()
    {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora)
    {
        this.fechaHora = fechaHora;
    }

    public TipoVenta getTipoVenta()
    {
        return tipoVenta;
    }

    public void setTipoVenta(TipoVenta tipoVenta)
    {
        this.tipoVenta = tipoVenta;
    }

    public double getDescuentoAplicado()
    {
        return descuentoAplicado;
    }

    public void setDescuentoAplicado(double descuentoAplicado)
    {
        this.descuentoAplicado = descuentoAplicado;
    }

    public double getPropina()
    {
        return propina;
    }

    public void setPropina(double propina)
    {
        this.propina = propina;
    }

    public Usuario getComprador()
    {
        return comprador;
    }

    public void setComprador(Usuario comprador)
    {
        this.comprador = comprador;
    }

    public Mesero getRegistradaPor()
    {
        return registradaPor;
    }

    public void setRegistradaPor(Mesero registradaPor)
    {
        this.registradaPor = registradaPor;
    }

    public List<ItemVenta> getItems()
    {
        return items;
    }

    public void setItems(List<ItemVenta> items)
    {
        this.items = items;
    }
}
