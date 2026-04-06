package modelo;

public class JuegoVenta extends ProductoVendible {
	private int stockDisponible;

    public JuegoVenta(String nombre, double precio, boolean disponible, int stockDisponible)
    {
        super(nombre, precio, disponible);
        this.stockDisponible = stockDisponible;
    }

    public boolean hayStock(int cantidad)
    {
    	if (cantidad <= 0)
        {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0.");
        }
        return stockDisponible >= cantidad;
    }

    public void reducirStock(int cantidad)
    {
    	if (cantidad <= 0)
        {
            throw new IllegalArgumentException("La cantidad a reducir debe ser mayor a 0.");
        }
        if (!hayStock(cantidad))
        {
            throw new IllegalArgumentException("No hay stock suficiente para reducir " + cantidad + " unidades.");
        }
        stockDisponible -= cantidad;
    }

    public void aumentarStock(int cantidad)
    {
    	if (cantidad <= 0)
        {
            throw new IllegalArgumentException("La cantidad a aumentar debe ser mayor a 0.");
        }
        stockDisponible += cantidad;
    }

    public int getStockDisponible()
    {
        return stockDisponible;
    }

    public void setStockDisponible(int stockDisponible)
    {
    	if (stockDisponible < 0)
        {
            throw new IllegalArgumentException("El stock no puede ser negativo.");
        }
        this.stockDisponible = stockDisponible;
    }
}
