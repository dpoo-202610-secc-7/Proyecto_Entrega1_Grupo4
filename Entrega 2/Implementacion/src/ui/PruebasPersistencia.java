package ui;

import modelo.Administrador;
import modelo.Cafe;
import modelo.Cliente;
import modelo.Mesa;
import modelo.PersistenciaSistema;
import modelo.SistemasDulcesDados;
import modelo.Usuario;

public class PruebasPersistencia
{
    public static void main(String[] args)
    {
        System.out.println("=================================");
        System.out.println("   PRUEBA DE PERSISTENCIA");
        System.out.println("=================================");

        // 1. Crear sistema y datos base
        PersistenciaSistema persistencia = new PersistenciaSistema("datos_prueba");
        Cafe cafe = new Cafe(40);
        cafe.agregarMesa(new Mesa(1));
        cafe.agregarMesa(new Mesa(2));

        SistemasDulcesDados sistema = new SistemasDulcesDados(cafe, persistencia);

        Cliente cliente = new Cliente("101", "Daniel", "daniel@mail.com", "daniel", "1234");
        cliente.setPuntosFidelidad(250);

        Administrador admin = new Administrador("900", "Admin", "admin@mail.com", "admin", "admin123");

        sistema.agregarUsuario(cliente);
        sistema.agregarUsuario(admin);

        // 2. Guardar
        sistema.guardarDatos();
        System.out.println("Datos guardados correctamente.");

        // 3. Crear un segundo sistema para verificar carga
        SistemasDulcesDados sistemaCargado = new SistemasDulcesDados(null, persistencia);
        sistemaCargado.inicializarSistema();

        System.out.println("\n--- Verificación de datos cargados ---");
        System.out.println("Usuarios cargados: " + sistemaCargado.getUsuarios().size());
        System.out.println("Mesas cargadas: " + sistemaCargado.getCafe().getMesas().size());
        System.out.println("Capacidad del café: " + sistemaCargado.getCafe().getCapacidadMaximaClientes());

        // 4. Verificar usuarios cargados
        Usuario u1 = sistemaCargado.buscarUsuarioPorLogin("daniel");
        Usuario u2 = sistemaCargado.buscarUsuarioPorLogin("admin");

        System.out.println("Cliente encontrado: " + (u1 != null ? u1.getNombre() : "NO"));
        System.out.println("Admin encontrado: " + (u2 != null ? u2.getNombre() : "NO"));

        // 5. Verificar autenticación
        Usuario autenticado = sistemaCargado.autenticarUsuario("daniel", "1234");
        if (autenticado != null)
        {
            System.out.println("Autenticación correcta para: " + autenticado.getNombre());
        }
        else
        {
            System.out.println("Falló la autenticación");
        }

        System.out.println("\n=================================");
        System.out.println(" FIN PRUEBA DE PERSISTENCIA");
        System.out.println("=================================");
    }
}