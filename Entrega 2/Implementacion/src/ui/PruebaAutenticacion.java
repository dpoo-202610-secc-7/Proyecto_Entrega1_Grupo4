package ui;

import modelo.Administrador;
import modelo.Cafe;
import modelo.Cliente;
import modelo.PersistenciaSistema;
import modelo.SistemasDulcesDados;
import modelo.Usuario;

public class PruebaAutenticacion
{
    public static void main(String[] args)
    {
        System.out.println("=================================");
        System.out.println("     PRUEBA DE AUTENTICACIÓN");
        System.out.println("=================================");

        SistemasDulcesDados sistema = new SistemasDulcesDados(new Cafe(50), new PersistenciaSistema("datos_auth"));

        sistema.agregarUsuario(new Cliente("101", "Daniel", "daniel@mail.com", "daniel", "1234"));
        sistema.agregarUsuario(new Administrador("900", "Admin", "admin@mail.com", "admin", "admin123"));

        Usuario u1 = sistema.autenticarUsuario("daniel", "1234");
        System.out.println("Login correcto cliente: " + (u1 != null ? "OK" : "FALLÓ"));

        sistema.cerrarSesion();

        Usuario u2 = sistema.autenticarUsuario("admin", "admin123");
        System.out.println("Login correcto admin: " + (u2 != null ? "OK" : "FALLÓ"));

        sistema.cerrarSesion();

        Usuario u3 = sistema.autenticarUsuario("daniel", "mal");
        System.out.println("Login con contraseña mala: " + (u3 == null ? "OK" : "FALLÓ"));

        Usuario u4 = sistema.autenticarUsuario("noexiste", "1234");
        System.out.println("Login con usuario inexistente: " + (u4 == null ? "OK" : "FALLÓ"));

        System.out.println("\n=================================");
        System.out.println("   FIN PRUEBA DE AUTENTICACIÓN");
        System.out.println("=================================");
    }
}