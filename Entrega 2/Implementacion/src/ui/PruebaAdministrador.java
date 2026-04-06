package ui;

import modelo.Administrador;
import modelo.Cafe;
import modelo.CategoriaJuego;
import modelo.CopiaJuegoPrestamo;
import modelo.EstadoJuego;
import modelo.JuegoMesa;
import modelo.JuegoVenta;

public class PruebaAdministrador
{
    public static void main(String[] args)
    {
        System.out.println("=================================");
        System.out.println("     PRUEBA DE ADMINISTRADOR");
        System.out.println("=================================");

        Cafe cafe = new Cafe(50);
        Administrador admin = new Administrador("900", "Admin", "admin@mail.com", "admin", "admin123");

        JuegoMesa catanMesa = new JuegoMesa("Catan", 1995, "Kosmos", 3, 4, 10, true, CategoriaJuego.TABLERO);
        CopiaJuegoPrestamo copia1 = new CopiaJuegoPrestamo(EstadoJuego.FALTA_PIEZA, true, catanMesa);
        catanMesa.agregarCopia(copia1);
        cafe.agregarJuegoCatalogo(catanMesa);

        JuegoVenta catanVenta = new JuegoVenta("Catan", 180000, true, 5);
        cafe.agregarJuegoVenta(catanVenta);

        System.out.println("\n--- Inventario inicial ---");
        System.out.println("Stock venta Catan: " + catanVenta.getStockDisponible());
        System.out.println("Estado copia préstamo: " + copia1.getEstado());

        admin.reabastecerJuegoVenta(catanVenta, 3);
        System.out.println("\nDespués de reabastecer:");
        System.out.println("Stock venta Catan: " + catanVenta.getStockDisponible());

        admin.repararJuego(copia1, null);
        System.out.println("\nDespués de reparar:");
        System.out.println("Estado copia préstamo: " + copia1.getEstado());

        admin.marcarJuegoDesaparecido(copia1);
        System.out.println("\nDespués de marcar desaparecido:");
        System.out.println("Estado copia préstamo: " + copia1.getEstado());
        System.out.println("Disponible: " + copia1.estaDisponible());

        // Simular mover una unidad de venta a préstamo
        if (catanVenta.hayStock(1))
        {
            catanVenta.reducirStock(1);
            CopiaJuegoPrestamo nuevaCopia = new CopiaJuegoPrestamo(EstadoJuego.BUENO, true, catanMesa);
            catanMesa.agregarCopia(nuevaCopia);
        }

        System.out.println("\n--- Después de mover venta a préstamo ---");
        System.out.println("Stock venta Catan: " + catanVenta.getStockDisponible());
        System.out.println("Copias en préstamo de Catan: " + catanMesa.getCopias().size());

        System.out.println("\n=================================");
        System.out.println("   FIN PRUEBA DE ADMINISTRADOR");
        System.out.println("=================================");
    }
}