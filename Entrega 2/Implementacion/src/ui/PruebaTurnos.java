package ui;

import modelo.Administrador;
import modelo.Cocinero;
import modelo.DiaSemana;
import modelo.Empleado;
import modelo.Mesero;
import modelo.SolicitudCambioTurno;
import modelo.TipoSolicitud;
import modelo.Turno;

public class PruebaTurnos
{
    public static void main(String[] args)
    {
        System.out.println("=================================");
        System.out.println("        PRUEBA DE TURNOS");
        System.out.println("=================================");

        Administrador admin = new Administrador("900", "Admin", "admin@mail.com", "admin", "admin123");
        Mesero mesero = new Mesero("2001", "Valentina", "vale@mail.com", "vale", "1234", "M001");
        Cocinero cocinero = new Cocinero("3001", "Carlos", "carlos@mail.com", "carlos", "1234", "C001");

        Turno turno1 = new Turno(DiaSemana.LUNES, "08:00", "16:00");
        Turno turno2 = new Turno(DiaSemana.MARTES, "10:00", "18:00");

        admin.asignarTurno(mesero, turno1);
        admin.asignarTurno(cocinero, turno2);

        System.out.println("\n--- Turnos asignados ---");
        mostrarTurnos(mesero);
        mostrarTurnos(cocinero);

        Turno nuevoTurno = new Turno(DiaSemana.MIERCOLES, "12:00", "20:00");
        SolicitudCambioTurno solicitud = mesero.solicitarCambioTurno(turno1, TipoSolicitud.CAMBIO, nuevoTurno, null);
        solicitud.setFechaHora("2026-04-05T21:00");
        solicitud.setEstado("PENDIENTE");

        System.out.println("\n--- Solicitud creada ---");
        System.out.println("Solicitante: " + solicitud.getSolicitante().getNombre());
        System.out.println("Tipo: " + solicitud.getTipoSolicitud());
        System.out.println("Estado: " + solicitud.getEstado());
        System.out.println("Turno original: " + solicitud.getTurnoOriginal().getDia());
        System.out.println("Turno propuesto: " + solicitud.getTurnoPropuesto().getDia());

        admin.aprobarSolicitudCambio(solicitud);
        admin.modificarTurno(mesero, solicitud.getTurnoOriginal(), solicitud.getTurnoPropuesto());

        System.out.println("\n--- Después de aprobar ---");
        System.out.println("Estado solicitud: " + solicitud.getEstado());
        mostrarTurnos(mesero);

        System.out.println("\n=================================");
        System.out.println("      FIN PRUEBA DE TURNOS");
        System.out.println("=================================");
    }

    private static void mostrarTurnos(Empleado empleado)
    {
        System.out.println("Turnos de " + empleado.getNombre() + ":");
        for (Turno turno : empleado.getTurnos())
        {
            System.out.println("- " + turno.getDia() + " " + turno.getHoraInicio() + " - " + turno.getHoraFin());
        }
    }
}