package modelo;

public class ItemVenta {
	private int cantidad;
    private double precioUnitario;
    private ProductoVendible producto;

    public ItemVenta(int cantidad, double precioUnitario, ProductoVendible producto)
    {
        this.cantidad = cantidad;
        this.precioUnitario = producto.obtenerPrecio();
        this.producto = producto;
    }

    public double calcularSubtotal()
    {
        return cantidad * precioUnitario;
    }

    public boolean correspondeAProductoMenu()
    {
        return producto instanceof ProductoMenu;
    }

    public boolean correspondeAJuegoVenta()
    {
        return producto instanceof JuegoVenta;
    }

    public int getCantidad()
    {
        return cantidad;
    }

    public void setCantidad(int cantidad)
    {
    	if (cantidad <= 0)
        {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0.");
        }
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario()
    {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario)
    {
    	if (precioUnitario < 0)
        {
            throw new IllegalArgumentException("El precio unitario no puede ser negativo.");
        }
        this.precioUnitario = precioUnitario;
    }

    public ProductoVendible getProducto()
    {
        return producto;
    }

    public void setProducto(ProductoVendible producto)
    {
    	if (producto == null)
        {
            throw new IllegalArgumentException("El producto no puede ser null.");
        }
        this.producto = producto;
    }
}
