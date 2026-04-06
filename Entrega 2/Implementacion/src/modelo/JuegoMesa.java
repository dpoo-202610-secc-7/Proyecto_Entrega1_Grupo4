package modelo;

import java.util.ArrayList;
import java.util.List;

public class JuegoMesa {
	private String nombre;
    private int anioPublicacion;
    private String empresaMatriz;
    private int minJugadores;
    private int maxJugadores;
    private int edadMinima;
    private boolean dificil;
    private CategoriaJuego categoria;
    private List<CopiaJuegoPrestamo> copias;

    public JuegoMesa(String nombre, int anioPublicacion, String empresaMatriz, int minJugadores, int maxJugadores,
            int edadMinima, boolean dificil, CategoriaJuego categoria)
    {
        this.nombre = nombre;
        this.anioPublicacion = anioPublicacion;
        this.empresaMatriz = empresaMatriz;
        this.minJugadores = minJugadores;
        this.maxJugadores = maxJugadores;
        this.edadMinima = edadMinima;
        this.dificil = dificil;
        this.categoria = categoria;
        this.copias = new ArrayList<CopiaJuegoPrestamo>();
    }

    public boolean esAptoParaCantidadJugadores(int numeroPersonas)
    {
        return numeroPersonas >= minJugadores && numeroPersonas <= maxJugadores;
    }

    public boolean esAptoParaEdad(boolean hayNinosMenores5, boolean hayMenoresEdad)
    {
        if (edadMinima >= 18 && hayMenoresEdad)
        {
            return false;
        }
        if (edadMinima >= 5 && hayNinosMenores5)
        {
            return false;
        }
        return true;
    }

    public boolean esDificil()
    {
        return dificil;
    }

    public boolean esCategoriaAccion()
    {
        return categoria == CategoriaJuego.ACCION;
    }

    public boolean tieneCopiasDisponibles()
    {
        for (CopiaJuegoPrestamo copia : copias)
        {
            if (copia.estaDisponible())
            {
                return true;
            }
        }
        return false;
    }

    public List<CopiaJuegoPrestamo> obtenerCopiasDisponibles()
    {
        List<CopiaJuegoPrestamo> disponibles = new ArrayList<CopiaJuegoPrestamo>();
        for (CopiaJuegoPrestamo copia : copias)
        {
            if (copia.estaDisponible())
            {
                disponibles.add(copia);
            }
        }
        return disponibles;
    }

    public void agregarCopia(CopiaJuegoPrestamo copia)
    {
        if (copia != null)
        {
            copias.add(copia);
        }
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public int getAnioPublicacion()
    {
        return anioPublicacion;
    }

    public void setAnioPublicacion(int anioPublicacion)
    {
        this.anioPublicacion = anioPublicacion;
    }

    public String getEmpresaMatriz()
    {
        return empresaMatriz;
    }

    public void setEmpresaMatriz(String empresaMatriz)
    {
        this.empresaMatriz = empresaMatriz;
    }

    public int getMinJugadores()
    {
        return minJugadores;
    }

    public void setMinJugadores(int minJugadores)
    {
        this.minJugadores = minJugadores;
    }

    public int getMaxJugadores()
    {
        return maxJugadores;
    }

    public void setMaxJugadores(int maxJugadores)
    {
        this.maxJugadores = maxJugadores;
    }

    public int getEdadMinima()
    {
        return edadMinima;
    }

    public void setEdadMinima(int edadMinima)
    {
        this.edadMinima = edadMinima;
    }

    public boolean isDificil()
    {
        return dificil;
    }

    public void setDificil(boolean dificil)
    {
        this.dificil = dificil;
    }

    public CategoriaJuego getCategoria()
    {
        return categoria;
    }

    public void setCategoria(CategoriaJuego categoria)
    {
        this.categoria = categoria;
    }

    public List<CopiaJuegoPrestamo> getCopias()
    {
        return copias;
    }

    public void setCopias(List<CopiaJuegoPrestamo> copias)
    {
        this.copias = copias;
    }
}
