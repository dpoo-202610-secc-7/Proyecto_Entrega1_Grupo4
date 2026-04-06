package ui;

import java.util.ArrayList;
import java.util.List;

import modelo.Bebida;
import modelo.CategoriaJuego;
import modelo.Cliente;
import modelo.ItemVenta;
import modelo.JuegoVenta;
import modelo.TipoVenta;
import modelo.Venta;

public class PruebaVentas
{
    public static void main(String[] args)
    {
        System.out.println("=================================");
        System.out.println("        PRUEBA DE VENTAS");
        System.out.println("=================================");

        Cliente cliente = new Cliente("101", "Daniel", "daniel@mail.com", "daniel", "1234");

        // Venta cafetería
        Bebida cafe = new Bebida("Café americano", 5000, true, false, true);
        Bebida limonada = new Bebida("Limonada", 6000, true, false, false);

        List<ItemVenta> itemsMenu = new ArrayList<ItemVenta>();
        itemsMenu.add(new ItemVenta(2, cafe.getPrecio(), cafe));
        itemsMenu.add(new ItemVenta(1, limonada.getPrecio(), limonada));

        Venta ventaMenu = cliente.comprarProductos(itemsMenu, 2000);
        ventaMenu.setFechaHora("2026-04-05T20:00");
        ventaMenu.setTipoVenta(TipoVenta.CAFETERIA);

        System.out.println("\n--- Venta de cafetería ---");
        System.out.println("Subtotal: " + ventaMenu.calcularSubtotal());
        System.out.println("Impuestos: " + ventaMenu.calcularImpuestos());
        System.out.println("Propina: " + ventaMenu.calcularPropina());
        System.out.println("Total: " + ventaMenu.calcularTotal());

        int puntosMenu = (int) ventaMenu.generarPuntosFidelidad();
        cliente.setPuntosFidelidad(cliente.getPuntosFidelidad() + puntosMenu);
        System.out.println("Puntos ganados: " + puntosMenu);
        System.out.println("Puntos acumulados cliente: " + cliente.getPuntosFidelidad());

        // Venta tienda
        JuegoVenta uno = new JuegoVenta("Uno", 25000, true, 10);
        JuegoVenta catan = new JuegoVenta("Catan", 180000, true, 5);

        Venta ventaTienda = new Venta("2026-04-05T20:30", TipoVenta.TIENDA_JUEGOS, 0, 0, cliente, null);
        ventaTienda.agregarItem(new ItemVenta(1, uno.getPrecio(), uno));
        ventaTienda.agregarItem(new ItemVenta(1, catan.getPrecio(), catan));
        ventaTienda.aplicarDescuentoPorcentaje(0.10);

        System.out.println("\n--- Venta de tienda ---");
        System.out.println("Subtotal: " + ventaTienda.calcularSubtotal());
        System.out.println("Descuento: " + ventaTienda.calcularDescuento());
        System.out.println("Impuestos: " + ventaTienda.calcularImpuestos());
        System.out.println("Total: " + ventaTienda.calcularTotal());

        int puntosTienda = (int) ventaTienda.generarPuntosFidelidad();
        cliente.setPuntosFidelidad(cliente.getPuntosFidelidad() + puntosTienda);
        System.out.println("Puntos ganados: " + puntosTienda);
        System.out.println("Puntos acumulados cliente: " + cliente.getPuntosFidelidad());

        System.out.println("\n=================================");
        System.out.println("      FIN PRUEBA DE VENTAS");
        System.out.println("=================================");
    }
}