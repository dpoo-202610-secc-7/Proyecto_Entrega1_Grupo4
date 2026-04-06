package modelo;

public abstract class ProductoVendible {
	private String nombre;
    private double precio;
    private boolean disponible;

    public ProductoVendible(String nombre, double precio, boolean disponible)
    {
        this.nombre = nombre;
        this.precio = precio;
        this.disponible = disponible;
    }

    public boolean estaDisponible()
    {
        return disponible;
    }

    public double obtenerPrecio()
    {
        return precio;
    }

    public double calcularSubtotal(int cantidad)
    {
        return precio * cantidad;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public double getPrecio()
    {
        return precio;
    }

    public void setPrecio(double precio)
    {
        this.precio = precio;
    }

    public boolean isDisponible()
    {
        return disponible;
    }

    public void setDisponible(boolean disponible)
    {
        this.disponible = disponible;
    }
}
