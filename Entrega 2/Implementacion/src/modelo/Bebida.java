package modelo;

public class Bebida extends ProductoMenu {
	private boolean esAlcoholica;
    private boolean esCaliente;

    public Bebida(String nombre, double precio, boolean disponible, boolean esAlcoholica, boolean esCaliente)
    {
        super(nombre, precio, disponible);
        this.esAlcoholica = esAlcoholica;
        this.esCaliente = esCaliente;
    }

    public boolean esAlcoholica()
    {
        return esAlcoholica;
    }

    public boolean esCaliente()
    {
        return esCaliente;
    }

    public boolean puedeDespacharseAMesa(Mesa mesa)
    {
        if (mesa == null)
        {
            return false;
        }

        if (esAlcoholica && mesa.tieneMenoresEdad())
        {
            return false;
        }

        if (esCaliente && mesa.tieneJuegoAccionActivo())
        {
            return false;
        }

        return true;
    }

    public void setEsAlcoholica(boolean esAlcoholica)
    {
        this.esAlcoholica = esAlcoholica;
    }

    public void setEsCaliente(boolean esCaliente)
    {
        this.esCaliente = esCaliente;
    }
}
