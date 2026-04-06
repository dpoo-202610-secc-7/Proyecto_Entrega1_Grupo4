package ui;

import modelo.Cafe;
import modelo.CategoriaJuego;
import modelo.Cliente;
import modelo.CopiaJuegoPrestamo;
import modelo.DetallePrestamo;
import modelo.EstadoJuego;
import modelo.JuegoMesa;
import modelo.Mesa;
import modelo.Prestamo;
import modelo.ReservaMesa;

public class PruebaPrestamos
{
    public static void main(String[] args)
    {
        System.out.println("=================================");
        System.out.println("      PRUEBA DE PRÉSTAMOS");
        System.out.println("=================================");

        Cafe cafe = new Cafe(50);
        Mesa mesa1 = new Mesa(1);
        cafe.agregarMesa(mesa1);

        Cliente cliente = new Cliente("101", "Daniel", "daniel@mail.com", "daniel", "1234");

        ReservaMesa reserva = cliente.crearReserva(mesa1, "2026-04-05T19:00", 4, false, false);
        reserva.activar();
        mesa1.asignarReserva(reserva);

        JuegoMesa uno = new JuegoMesa("Uno", 1971, "Mattel", 2, 10, 3, false, CategoriaJuego.CARTAS);
        JuegoMesa catan = new JuegoMesa("Catan", 1995, "Kosmos", 3, 4, 10, true, CategoriaJuego.TABLERO);

        CopiaJuegoPrestamo copiaUno = new CopiaJuegoPrestamo(EstadoJuego.BUENO, true, uno);
        CopiaJuegoPrestamo copiaCatan = new CopiaJuegoPrestamo(EstadoJuego.NUEVO, true, catan);

        uno.agregarCopia(copiaUno);
        catan.agregarCopia(copiaCatan);

        cafe.agregarJuegoCatalogo(uno);
        cafe.agregarJuegoCatalogo(catan);

        Prestamo prestamo = new Prestamo("2026-04-05T19:10", null, false, cliente, mesa1);

        copiaUno.prestar();
        DetallePrestamo detalle1 = new DetallePrestamo(copiaUno);
        detalle1.registrarAsignacion("2026-04-05T19:10");
        prestamo.agregarDetalle(detalle1);

        copiaCatan.prestar();
        DetallePrestamo detalle2 = new DetallePrestamo(copiaCatan);
        detalle2.registrarAsignacion("2026-04-05T19:11");
        prestamo.agregarDetalle(detalle2);

        cafe.registrarPrestamo(prestamo);

        System.out.println("\n--- Estado del préstamo ---");
        System.out.println("Usuario: " + prestamo.getUsuario().getNombre());
        System.out.println("Mesa asociada: " + prestamo.getMesa().getNumeroMesa());
        System.out.println("Cantidad de juegos: " + prestamo.getDetalles().size());
        System.out.println("Préstamo activo: " + prestamo.estaActivo());
        System.out.println("Copia Uno disponible: " + copiaUno.estaDisponible());
        System.out.println("Copia Catan disponible: " + copiaCatan.estaDisponible());

        System.out.println("\n--- Devolución ---");
        cliente.devolverPrestamo(prestamo);

        System.out.println("Préstamo activo después de devolver: " + prestamo.estaActivo());
        System.out.println("Fecha fin: " + prestamo.getFechaFin());
        System.out.println("Copia Uno disponible: " + copiaUno.estaDisponible());
        System.out.println("Copia Catan disponible: " + copiaCatan.estaDisponible());
        System.out.println("Detalle 1 devuelto: " + detalle1.estaDevuelto());
        System.out.println("Detalle 2 devuelto: " + detalle2.estaDevuelto());

        System.out.println("\n=================================");
        System.out.println("    FIN PRUEBA DE PRÉSTAMOS");
        System.out.println("=================================");
    }
}