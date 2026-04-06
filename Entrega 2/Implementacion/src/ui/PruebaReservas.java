package ui;

import modelo.Cafe;
import modelo.Cliente;
import modelo.Mesa;
import modelo.ReservaMesa;

public class PruebaReservas
{
    public static void main(String[] args)
    {
        System.out.println("=================================");
        System.out.println("      PRUEBA DE RESERVAS");
        System.out.println("=================================");

        Cafe cafe = new Cafe(50);
        Mesa mesa1 = new Mesa(1);
        Mesa mesa2 = new Mesa(2);

        cafe.agregarMesa(mesa1);
        cafe.agregarMesa(mesa2);

        Cliente cliente = new Cliente("101", "Daniel", "daniel@mail.com", "daniel", "1234");

        System.out.println("Mesas en el café: " + cafe.getMesas().size());
        System.out.println("Reserva activa inicial en mesa 1: " + (mesa1.getReservaActiva() != null));

        ReservaMesa reserva = cliente.crearReserva(mesa1, "2026-04-05T18:00", 4, false, true);
        reserva.activar();
        mesa1.asignarReserva(reserva);

        System.out.println("\n--- Datos de la reserva ---");
        System.out.println("Cliente: " + reserva.getCliente().getNombre());
        System.out.println("Mesa: " + reserva.getMesa().getNumeroMesa());
        System.out.println("Fecha y hora: " + reserva.getFechaHora());
        System.out.println("Número de personas: " + reserva.getNumeroPersonas());
        System.out.println("Hay menores de edad: " + reserva.isHayMenoresEdad());
        System.out.println("Estado: " + reserva.getEstadoReserva());

        System.out.println("\n--- Verificación en la mesa ---");
        System.out.println("Reserva activa en mesa 1: " + (mesa1.getReservaActiva() != null));
        System.out.println("Mesa 1 tiene menores de edad: " + mesa1.tieneMenoresEdad());
        System.out.println("Mesa 1 tiene niños menores de 5: " + mesa1.tieneNinosMenores5());

        System.out.println("\n--- Cierre de reserva ---");
        mesa1.cerrarReservaActiva();
        System.out.println("Reserva activa después de cerrar: " + (mesa1.getReservaActiva() != null));

        System.out.println("\n=================================");
        System.out.println("    FIN PRUEBA DE RESERVAS");
        System.out.println("=================================");
    }
}