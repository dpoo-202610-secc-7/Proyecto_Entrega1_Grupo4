package modelo;

import java.util.ArrayList;
import java.util.List;

public class Pasteleria extends ProductoMenu {
	private List<Alergeno> alergenos;

    public Pasteleria(String nombre, double precio, boolean disponible)
    {
        super(nombre, precio, disponible);
        this.alergenos = new ArrayList<Alergeno>();
    }

    public boolean contieneAlergeno(Alergeno alergeno)
    {
        return alergenos.contains(alergeno);
    }

    public List<Alergeno> obtenerAlergenos()
    {
        return alergenos;
    }

    public String generarAdvertenciaAlergenos()
    {
        if (alergenos.isEmpty())
        {
            return "Sin alérgenos";
        }
        return "Contiene: " + alergenos.toString();
    }

    public void agregarAlergeno(Alergeno alergeno)
    {
        if (alergeno != null && !alergenos.contains(alergeno))
        {
            alergenos.add(alergeno);
        }
    }

    public void setAlergenos(List<Alergeno> alergenos)
    {
        this.alergenos = alergenos;
    }
}
