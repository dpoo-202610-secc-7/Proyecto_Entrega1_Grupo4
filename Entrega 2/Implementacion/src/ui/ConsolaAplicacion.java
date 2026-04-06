package ui;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import modelo.Administrador;
import modelo.Alergeno;
import modelo.Bebida;
import modelo.Cafe;
import modelo.CategoriaJuego;
import modelo.CategoriaPropuesta;
import modelo.Cliente;
import modelo.Cocinero;
import modelo.CopiaJuegoPrestamo;
import modelo.DetallePrestamo;
import modelo.DiaSemana;
import modelo.Empleado;
import modelo.EstadoJuego;
import modelo.ItemVenta;
import modelo.JuegoMesa;
import modelo.JuegoVenta;
import modelo.Mesa;
import modelo.Mesero;
import modelo.Pasteleria;
import modelo.PersistenciaSistema;
import modelo.Prestamo;
import modelo.ProductoMenu;
import modelo.ReservaMesa;
import modelo.SistemasDulcesDados;
import modelo.SolicitudCambioTurno;
import modelo.SugerenciaPlatillo;
import modelo.TipoSolicitud;
import modelo.TipoVenta;
import modelo.Turno;
import modelo.Usuario;
import modelo.Venta;

public class ConsolaAplicacion
{
    private SistemasDulcesDados sistema;
    private Scanner scanner;

    // Como el modelo no tiene lista global persistente de sugerencias,
    // las manejamos en memoria durante la ejecución.
    private List<SugerenciaPlatillo> sugerenciasPendientes;

    public ConsolaAplicacion()
    {
    	PersistenciaSistema persistencia = new PersistenciaSistema("src/datos");
        Cafe cafe = new Cafe(50);
        sistema = new SistemasDulcesDados(cafe, persistencia);
        scanner = new Scanner(System.in);
        sugerenciasPendientes = new ArrayList<SugerenciaPlatillo>();

        sistema.inicializarSistema();

        if (sistema.getUsuarios().isEmpty())
        {
            cargarDatosIniciales();
            sistema.guardarDatos();
        }
    }

    public void iniciarAplicacion()
    {
        int opcion = -1;

        while (opcion != 0)
        {
            System.out.println("\n======================================");
            System.out.println("      DULCES & DADOS - CONSOLA");
            System.out.println("======================================");
            System.out.println("1. Iniciar sesión");
            System.out.println("2. Ver información del café");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            opcion = leerEntero();

            switch (opcion)
            {
                case 1:
                    iniciarSesion();
                    break;
                case 2:
                    mostrarInfoCafe();
                    break;
                case 0:
                    sistema.guardarDatos();
                    System.out.println("Datos guardados. Hasta pronto.");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }

        scanner.close();
    }

    private void iniciarSesion()
    {
        System.out.print("Login: ");
        String login = scanner.nextLine().trim();

        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        Usuario usuario = sistema.autenticarUsuario(login, password);

        if (usuario == null)
        {
            System.out.println("Credenciales incorrectas.");
            return;
        }

        System.out.println("Bienvenido/a, " + usuario.getNombre());

        if (usuario instanceof Cliente)
        {
            menuCliente((Cliente) usuario);
        }
        else if (usuario instanceof Mesero)
        {
            menuMesero((Mesero) usuario);
        }
        else if (usuario instanceof Cocinero)
        {
            menuCocinero((Cocinero) usuario);
        }
        else if (usuario instanceof Administrador)
        {
            menuAdministrador((Administrador) usuario);
        }

        sistema.guardarDatos();
        sistema.cerrarSesion();
    }

    // =========================================================
    // MENÚ CLIENTE
    // =========================================================

    private void menuCliente(Cliente cliente)
    {
        int opcion = -1;

        while (opcion != 0)
        {
            System.out.println("\n----------- MENÚ CLIENTE -----------");
            System.out.println("1. Reservar mesa");
            System.out.println("2. Ver catálogo de juegos");
            System.out.println("3. Solicitar préstamo");
            System.out.println("4. Devolver préstamo");
            System.out.println("5. Comprar del menú");
            System.out.println("6. Comprar juego de tienda");
            System.out.println("7. Ver puntos de fidelidad");
            System.out.println("8. Agregar juego favorito");
            System.out.println("9. Ver juegos favoritos");
            System.out.println("0. Cerrar sesión");
            System.out.print("Opción: ");
            opcion = leerEntero();

            switch (opcion)
            {
                case 1:
                    reservarMesa(cliente);
                    break;
                case 2:
                    mostrarCatalogoJuegos();
                    break;
                case 3:
                    solicitarPrestamoCliente(cliente);
                    break;
                case 4:
                    devolverPrestamoCliente(cliente);
                    break;
                case 5:
                    comprarMenuCliente(cliente);
                    break;
                case 6:
                    comprarJuegoCliente(cliente);
                    break;
                case 7:
                    System.out.println("Puntos actuales: " + cliente.getPuntosFidelidad());
                    break;
                case 8:
                    agregarFavoritoCliente(cliente);
                    break;
                case 9:
                    mostrarFavoritos(cliente.consultarJuegosFavoritos());
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private void reservarMesa(Cliente cliente)
    {
        Cafe cafe = sistema.getCafe();
        List<Mesa> disponibles = new ArrayList<Mesa>();

        for (Mesa mesa : cafe.getMesas())
        {
            if (mesa.getReservaActiva() == null)
            {
                disponibles.add(mesa);
            }
        }

        if (disponibles.isEmpty())
        {
            System.out.println("No hay mesas disponibles.");
            return;
        }

        System.out.println("\nMesas disponibles:");
        for (int i = 0; i < disponibles.size(); i++)
        {
            System.out.println((i + 1) + ". Mesa " + disponibles.get(i).getNumeroMesa());
        }

        System.out.print("Seleccione mesa: ");
        int indice = leerEntero() - 1;

        if (indice < 0 || indice >= disponibles.size())
        {
            System.out.println("Selección inválida.");
            return;
        }

        System.out.print("Número de personas: ");
        int numeroPersonas = leerEntero();

        if (!cafe.verificarCapacidadDisponible(numeroPersonas))
        {
            System.out.println("El café no tiene capacidad para ese grupo.");
            return;
        }

        System.out.print("¿Hay niños menores de 5 años? (s/n): ");
        boolean hayNinos = leerSiNo();

        System.out.print("¿Hay menores de edad? (s/n): ");
        boolean hayMenores = leerSiNo();

        Mesa mesa = disponibles.get(indice);
        String fechaHora = LocalDateTime.now().toString();

        ReservaMesa reserva = cliente.crearReserva(mesa, fechaHora, numeroPersonas, hayNinos, hayMenores);
        reserva.activar();
        mesa.asignarReserva(reserva);

        System.out.println("Reserva creada correctamente en mesa " + mesa.getNumeroMesa() + ".");
    }

    private void solicitarPrestamoCliente(Cliente cliente)
    {
        Mesa mesaActiva = buscarMesaActivaDeCliente(cliente);

        if (mesaActiva == null)
        {
            System.out.println("Debes tener una mesa activa para pedir préstamos.");
            return;
        }

        List<Prestamo> prestamosActivos = prestamosActivosDeUsuario(cliente);

        if (prestamosActivos.size() >= 2)
        {
            System.out.println("Ya tienes 2 préstamos activos.");
            return;
        }

        int cupos = 2 - prestamosActivos.size();
        List<JuegoMesa> catalogo = sistema.getCafe().consultarCatalogoJuegos();
        List<CopiaJuegoPrestamo> seleccionadas = new ArrayList<CopiaJuegoPrestamo>();

        mostrarCatalogoJuegos();

        for (int i = 0; i < cupos; i++)
        {
            System.out.print("Seleccione juego " + (i + 1) + " (0 para terminar): ");
            int indice = leerEntero() - 1;

            if (indice == -1)
            {
                break;
            }

            if (indice < 0 || indice >= catalogo.size())
            {
                System.out.println("Selección inválida.");
                continue;
            }

            JuegoMesa juego = catalogo.get(indice);

            if (!mesaActiva.puedeRecibirJuego(juego))
            {
                System.out.println("Ese juego no es apto para tu mesa.");
                continue;
            }

            if (!juego.tieneCopiasDisponibles())
            {
                System.out.println("Ese juego no tiene copias disponibles.");
                continue;
            }

            CopiaJuegoPrestamo copia = juego.obtenerCopiasDisponibles().get(0);
            seleccionadas.add(copia);
            System.out.println("Juego agregado: " + juego.getNombre());
        }

        if (seleccionadas.isEmpty())
        {
            System.out.println("No se seleccionaron juegos.");
            return;
        }

        Prestamo prestamo = new Prestamo(LocalDateTime.now().toString(), null, false, cliente, mesaActiva);

        for (CopiaJuegoPrestamo copia : seleccionadas)
        {
            JuegoMesa juego = copia.getJuegoMesa();

            if (juego.esDificil() && sistema.getCafe().buscarMeseroCapacitado(juego) == null)
            {
                prestamo.registrarAdvertenciaSinMesero();
            }

            copia.prestar();
            DetallePrestamo detalle = new DetallePrestamo(copia);
            detalle.registrarAsignacion(LocalDateTime.now().toString());
            prestamo.agregarDetalle(detalle);
        }

        sistema.getCafe().registrarPrestamo(prestamo);
        System.out.println("Préstamo registrado con " + prestamo.getDetalles().size() + " juego(s).");
    }

    private void devolverPrestamoCliente(Cliente cliente)
    {
        List<Prestamo> activos = prestamosActivosDeUsuario(cliente);

        if (activos.isEmpty())
        {
            System.out.println("No tienes préstamos activos.");
            return;
        }

        System.out.println("\nPréstamos activos:");
        for (int i = 0; i < activos.size(); i++)
        {
            Prestamo p = activos.get(i);
            System.out.print((i + 1) + ". ");
            for (DetallePrestamo d : p.getDetalles())
            {
                System.out.print(d.getCopiaJuego().getJuegoMesa().getNombre() + " ");
            }
            System.out.println();
        }

        System.out.print("Seleccione préstamo a devolver: ");
        int indice = leerEntero() - 1;

        if (indice < 0 || indice >= activos.size())
        {
            System.out.println("Selección inválida.");
            return;
        }

        Prestamo prestamo = activos.get(indice);
        cliente.devolverPrestamo(prestamo);
        System.out.println("Préstamo devuelto correctamente.");
    }

    private void comprarMenuCliente(Cliente cliente)
    {
        List<ProductoMenu> menu = sistema.getCafe().consultarMenu();

        if (menu.isEmpty())
        {
            System.out.println("No hay productos en el menú.");
            return;
        }

        mostrarMenu(menu);

        List<ItemVenta> items = new ArrayList<ItemVenta>();

        while (true)
        {
            System.out.print("Seleccione producto (0 para terminar): ");
            int indice = leerEntero() - 1;

            if (indice == -1)
            {
                break;
            }

            if (indice < 0 || indice >= menu.size())
            {
                System.out.println("Selección inválida.");
                continue;
            }

            ProductoMenu producto = menu.get(indice);

            if (producto instanceof Pasteleria)
            {
                System.out.println(((Pasteleria) producto).generarAdvertenciaAlergenos());
            }

            System.out.print("Cantidad: ");
            int cantidad = leerEntero();

            if (cantidad <= 0)
            {
                System.out.println("Cantidad inválida.");
                continue;
            }

            items.add(new ItemVenta(cantidad, producto.getPrecio(), producto));
        }

        if (items.isEmpty())
        {
            System.out.println("No se seleccionaron productos.");
            return;
        }

        System.out.print("Propina: ");
        double propina = leerDouble();

        Venta venta = cliente.comprarProductos(items, propina);
        venta.setFechaHora(LocalDateTime.now().toString());
        venta.setTipoVenta(TipoVenta.CAFETERIA);

        System.out.print("¿Deseas usar puntos? Tienes " + cliente.getPuntosFidelidad() + " (s/n): ");
        if (leerSiNo())
        {
            int puntos = cliente.getPuntosFidelidad();
            venta.aplicarPuntosFidelidad(puntos);
            cliente.setPuntosFidelidad(0);
        }

        sistema.getCafe().registrarVenta(venta);

        int puntosGanados = (int) venta.generarPuntosFidelidad();
        cliente.setPuntosFidelidad(cliente.getPuntosFidelidad() + puntosGanados);

        imprimirResumenVenta(venta);
        System.out.println("Puntos ganados: " + puntosGanados);
    }

    private void comprarJuegoCliente(Cliente cliente)
    {
        List<JuegoVenta> inventario = sistema.getCafe().consultarInventarioVenta();

        if (inventario.isEmpty())
        {
            System.out.println("No hay juegos de venta.");
            return;
        }

        System.out.println("\nInventario de juegos:");
        for (int i = 0; i < inventario.size(); i++)
        {
            JuegoVenta j = inventario.get(i);
            System.out.println((i + 1) + ". " + j.getNombre() + " - $" + (int) j.getPrecio() + " - Stock: " + j.getStockDisponible());
        }

        List<ItemVenta> items = new ArrayList<ItemVenta>();

        while (true)
        {
            System.out.print("Seleccione juego (0 para terminar): ");
            int indice = leerEntero() - 1;

            if (indice == -1)
            {
                break;
            }

            if (indice < 0 || indice >= inventario.size())
            {
                System.out.println("Selección inválida.");
                continue;
            }

            JuegoVenta juego = inventario.get(indice);

            System.out.print("Cantidad: ");
            int cantidad = leerEntero();

            if (!juego.hayStock(cantidad))
            {
                System.out.println("No hay stock suficiente.");
                continue;
            }

            juego.reducirStock(cantidad);
            items.add(new ItemVenta(cantidad, juego.getPrecio(), juego));
        }

        if (items.isEmpty())
        {
            System.out.println("No se seleccionaron juegos.");
            return;
        }

        Venta venta = new Venta(LocalDateTime.now().toString(), TipoVenta.TIENDA_JUEGOS, 0, 0, cliente, null);

        for (ItemVenta item : items)
        {
            venta.agregarItem(item);
        }

        System.out.print("¿Tienes código de descuento de empleado (10%)? (s/n): ");
        if (leerSiNo())
        {
            venta.aplicarDescuentoPorcentaje(0.10);
        }

        sistema.getCafe().registrarVenta(venta);

        int puntosGanados = (int) venta.generarPuntosFidelidad();
        cliente.setPuntosFidelidad(cliente.getPuntosFidelidad() + puntosGanados);

        imprimirResumenVenta(venta);
        System.out.println("Puntos ganados: " + puntosGanados);
    }

    private void agregarFavoritoCliente(Cliente cliente)
    {
        List<JuegoMesa> catalogo = sistema.getCafe().consultarCatalogoJuegos();
        mostrarCatalogoJuegos();

        System.out.print("Seleccione juego: ");
        int indice = leerEntero() - 1;

        if (indice < 0 || indice >= catalogo.size())
        {
            System.out.println("Selección inválida.");
            return;
        }

        cliente.agregarJuegoFavorito(catalogo.get(indice));
        System.out.println("Juego agregado a favoritos.");
    }

    // =========================================================
    // MENÚ MESERO
    // =========================================================

    private void menuMesero(Mesero mesero)
    {
        int opcion = -1;

        while (opcion != 0)
        {
            System.out.println("\n----------- MENÚ MESERO -----------");
            System.out.println("1. Ver turnos");
            System.out.println("2. Solicitar cambio de turno");
            System.out.println("3. Solicitar préstamo de empleado");
            System.out.println("4. Comprar del menú (20% descuento)");
            System.out.println("5. Sugerir platillo");
            System.out.println("6. Ver juegos que puedo enseñar");
            System.out.println("7. Ver favoritos");
            System.out.println("8. Agregar favorito");
            System.out.println("0. Cerrar sesión");
            System.out.print("Opción: ");
            opcion = leerEntero();

            switch (opcion)
            {
                case 1:
                    mostrarTurnos(mesero);
                    break;
                case 2:
                    solicitarCambioTurno(mesero);
                    break;
                case 3:
                    solicitarPrestamoEmpleado(mesero);
                    break;
                case 4:
                    comprarMenuEmpleado(mesero);
                    break;
                case 5:
                    sugerirPlatillo(mesero);
                    break;
                case 6:
                    mostrarJuegosConocidos(mesero);
                    break;
                case 7:
                    mostrarFavoritos(mesero.consultarJuegosFavoritos());
                    break;
                case 8:
                    agregarFavoritoEmpleado(mesero);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    // =========================================================
    // MENÚ COCINERO
    // =========================================================

    private void menuCocinero(Cocinero cocinero)
    {
        int opcion = -1;

        while (opcion != 0)
        {
            System.out.println("\n----------- MENÚ COCINERO -----------");
            System.out.println("1. Ver turnos");
            System.out.println("2. Solicitar cambio de turno");
            System.out.println("3. Solicitar préstamo de empleado");
            System.out.println("4. Comprar del menú (20% descuento)");
            System.out.println("5. Sugerir platillo");
            System.out.println("6. Ver favoritos");
            System.out.println("7. Agregar favorito");
            System.out.println("0. Cerrar sesión");
            System.out.print("Opción: ");
            opcion = leerEntero();

            switch (opcion)
            {
                case 1:
                    mostrarTurnos(cocinero);
                    break;
                case 2:
                    solicitarCambioTurno(cocinero);
                    break;
                case 3:
                    solicitarPrestamoEmpleado(cocinero);
                    break;
                case 4:
                    comprarMenuEmpleado(cocinero);
                    break;
                case 5:
                    sugerirPlatillo(cocinero);
                    break;
                case 6:
                    mostrarFavoritos(cocinero.consultarJuegosFavoritos());
                    break;
                case 7:
                    agregarFavoritoEmpleado(cocinero);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private void mostrarTurnos(Empleado empleado)
    {
        List<Turno> turnos = empleado.consultarTurnos();

        if (turnos.isEmpty())
        {
            System.out.println("No tienes turnos asignados.");
            return;
        }

        System.out.println("\nTurnos:");
        for (Turno turno : turnos)
        {
            System.out.println("- " + turno.getDia() + " " + turno.getHoraInicio() + " - " + turno.getHoraFin());
        }
    }

    private void solicitarCambioTurno(Empleado empleado)
    {
        List<Turno> turnos = empleado.getTurnos();

        if (turnos.isEmpty())
        {
            System.out.println("No tienes turnos asignados.");
            return;
        }

        System.out.println("\nTus turnos:");
        for (int i = 0; i < turnos.size(); i++)
        {
            Turno t = turnos.get(i);
            System.out.println((i + 1) + ". " + t.getDia() + " " + t.getHoraInicio() + " - " + t.getHoraFin());
        }

        System.out.print("Seleccione turno original: ");
        int indice = leerEntero() - 1;

        if (indice < 0 || indice >= turnos.size())
        {
            System.out.println("Selección inválida.");
            return;
        }

        Turno turnoOriginal = turnos.get(indice);

        System.out.println("Tipo de solicitud:");
        System.out.println("1. Cambio");
        System.out.println("2. Intercambio");
        System.out.print("Opción: ");
        int tipo = leerEntero();

        TipoSolicitud tipoSolicitud = (tipo == 2) ? TipoSolicitud.INTERCAMBIO : TipoSolicitud.CAMBIO;

        System.out.print("Día propuesto (LUNES/MARTES/MIERCOLES/JUEVES/VIERNES/SABADO/DOMINGO): ");
        String diaTexto = scanner.nextLine().trim().toUpperCase();

        DiaSemana dia;
        try
        {
            dia = DiaSemana.valueOf(diaTexto);
        }
        catch (Exception e)
        {
            System.out.println("Día inválido.");
            return;
        }

        System.out.print("Hora inicio propuesta: ");
        String horaInicio = scanner.nextLine().trim();

        System.out.print("Hora fin propuesta: ");
        String horaFin = scanner.nextLine().trim();

        Turno turnoPropuesto = new Turno(dia, horaInicio, horaFin);

        SolicitudCambioTurno solicitud = empleado.solicitarCambioTurno(
            turnoOriginal,
            tipoSolicitud,
            turnoPropuesto,
            null
        );

        solicitud.setFechaHora(LocalDateTime.now().toString());
        solicitud.setEstado("PENDIENTE");

        System.out.println("Solicitud creada correctamente.");
    }

    private void solicitarPrestamoEmpleado(Empleado empleado)
    {
        if (empleado.isEnTurno())
        {
            System.out.println("No puedes pedir préstamos estando en turno.");
            return;
        }

        if (sistema.getCafe().hayClientesPorAtender())
        {
            System.out.println("No puedes pedir préstamos mientras haya clientes por atender.");
            return;
        }

        List<JuegoMesa> catalogo = sistema.getCafe().consultarCatalogoJuegos();
        List<CopiaJuegoPrestamo> seleccionadas = new ArrayList<CopiaJuegoPrestamo>();

        mostrarCatalogoJuegos();

        for (int i = 0; i < 2; i++)
        {
            System.out.print("Seleccione juego " + (i + 1) + " (0 para terminar): ");
            int indice = leerEntero() - 1;

            if (indice == -1)
            {
                break;
            }

            if (indice < 0 || indice >= catalogo.size())
            {
                System.out.println("Selección inválida.");
                continue;
            }

            JuegoMesa juego = catalogo.get(indice);

            if (!juego.tieneCopiasDisponibles())
            {
                System.out.println("Ese juego no tiene copias disponibles.");
                continue;
            }

            CopiaJuegoPrestamo copia = juego.obtenerCopiasDisponibles().get(0);
            seleccionadas.add(copia);
            System.out.println("Juego agregado: " + juego.getNombre());
        }

        if (seleccionadas.isEmpty())
        {
            System.out.println("No se seleccionaron juegos.");
            return;
        }

        Prestamo prestamo = new Prestamo(LocalDateTime.now().toString(), null, false, empleado, null);

        for (CopiaJuegoPrestamo copia : seleccionadas)
        {
            copia.prestar();
            DetallePrestamo detalle = new DetallePrestamo(copia);
            detalle.registrarAsignacion(LocalDateTime.now().toString());
            prestamo.agregarDetalle(detalle);
        }

        sistema.getCafe().registrarPrestamo(prestamo);
        System.out.println("Préstamo registrado correctamente.");
    }

    private void comprarMenuEmpleado(Empleado empleado)
    {
        List<ProductoMenu> menu = sistema.getCafe().consultarMenu();

        if (menu.isEmpty())
        {
            System.out.println("No hay productos en el menú.");
            return;
        }

        mostrarMenu(menu);

        List<ItemVenta> items = new ArrayList<ItemVenta>();

        while (true)
        {
            System.out.print("Seleccione producto (0 para terminar): ");
            int indice = leerEntero() - 1;

            if (indice == -1)
            {
                break;
            }

            if (indice < 0 || indice >= menu.size())
            {
                System.out.println("Selección inválida.");
                continue;
            }

            ProductoMenu producto = menu.get(indice);

            if (producto instanceof Pasteleria)
            {
                System.out.println(((Pasteleria) producto).generarAdvertenciaAlergenos());
            }

            System.out.print("Cantidad: ");
            int cantidad = leerEntero();

            if (cantidad <= 0)
            {
                System.out.println("Cantidad inválida.");
                continue;
            }

            items.add(new ItemVenta(cantidad, producto.getPrecio(), producto));
        }

        if (items.isEmpty())
        {
            System.out.println("No se seleccionaron productos.");
            return;
        }

        Venta venta = empleado.comprarProductos(items, 0);
        venta.setFechaHora(LocalDateTime.now().toString());
        venta.setTipoVenta(TipoVenta.CAFETERIA);
        venta.aplicarDescuentoPorcentaje(empleado.obtenerDescuentoEmpleado());

        sistema.getCafe().registrarVenta(venta);

        imprimirResumenVenta(venta);
    }

    private void sugerirPlatillo(Empleado empleado)
    {
        System.out.print("Nombre del platillo: ");
        String nombre = scanner.nextLine().trim();

        System.out.println("Categoría:");
        System.out.println("1. Bebida");
        System.out.println("2. Pastelería");
        System.out.print("Opción: ");
        int opcion = leerEntero();

        CategoriaPropuesta categoria = (opcion == 2) ? CategoriaPropuesta.PASTELERIA : CategoriaPropuesta.BEBIDA;

        SugerenciaPlatillo sugerencia = empleado.crearSugerenciaPlatillo(nombre, categoria);
        sugerencia.setFechaHora(LocalDateTime.now().toString());
        sugerencia.setEstado(modelo.EstadoSugerencia.PENDIENTE);
        sugerenciasPendientes.add(sugerencia);

        System.out.println("Sugerencia registrada correctamente.");
    }

    private void mostrarJuegosConocidos(Mesero mesero)
    {
        List<JuegoMesa> juegos = mesero.consultarJuegosConocidos();

        if (juegos.isEmpty())
        {
            System.out.println("No tienes juegos registrados.");
            return;
        }

        System.out.println("\nJuegos que puedes enseñar:");
        for (JuegoMesa juego : juegos)
        {
            System.out.println("- " + juego.getNombre());
        }
    }

    private void agregarFavoritoEmpleado(Empleado empleado)
    {
        List<JuegoMesa> catalogo = sistema.getCafe().consultarCatalogoJuegos();
        mostrarCatalogoJuegos();

        System.out.print("Seleccione juego: ");
        int indice = leerEntero() - 1;

        if (indice < 0 || indice >= catalogo.size())
        {
            System.out.println("Selección inválida.");
            return;
        }

        empleado.agregarJuegoFavorito(catalogo.get(indice));
        System.out.println("Juego agregado a favoritos.");
    }

    // =========================================================
    // MENÚ ADMINISTRADOR
    // =========================================================

    private void menuAdministrador(Administrador admin)
    {
        int opcion = -1;

        while (opcion != 0)
        {
            System.out.println("\n-------- MENÚ ADMINISTRADOR --------");
            System.out.println("1. Ver inventario de préstamo");
            System.out.println("2. Ver inventario de venta");
            System.out.println("3. Reabastecer juego de venta");
            System.out.println("4. Mover juego de venta a préstamo");
            System.out.println("5. Reparar copia de préstamo");
            System.out.println("6. Marcar copia como desaparecida");
            System.out.println("7. Ver sugerencias");
            System.out.println("8. Aprobar/Rechazar sugerencia");
            System.out.println("9. Ver turnos de empleados");
            System.out.println("10. Asignar turno");
            System.out.println("11. Ver solicitudes de cambio");
            System.out.println("12. Aprobar/Rechazar solicitud de cambio");
            System.out.println("13. Ver informe de ventas");
            System.out.println("14. Ver historial de préstamos");
            System.out.println("0. Cerrar sesión");
            System.out.print("Opción: ");
            opcion = leerEntero();

            switch (opcion)
            {
                case 1:
                    verInventarioPrestamo();
                    break;
                case 2:
                    verInventarioVenta();
                    break;
                case 3:
                    reabastecerJuegoVenta(admin);
                    break;
                case 4:
                    moverJuegoVentaAPrestamo(admin);
                    break;
                case 5:
                    repararCopiaPrestamo(admin);
                    break;
                case 6:
                    marcarCopiaDesaparecida(admin);
                    break;
                case 7:
                    verSugerencias();
                    break;
                case 8:
                    gestionarSugerencia(admin);
                    break;
                case 9:
                    verTurnosEmpleados();
                    break;
                case 10:
                    asignarTurnoEmpleado(admin);
                    break;
                case 11:
                    verSolicitudesCambioTurno();
                    break;
                case 12:
                    gestionarSolicitudCambio(admin);
                    break;
                case 13:
                    mostrarInformeVentas();
                    break;
                case 14:
                    mostrarHistorialPrestamos();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private void verInventarioPrestamo()
    {
        List<CopiaJuegoPrestamo> copias = sistema.getCafe().consultarInventarioPrestamo();

        if (copias.isEmpty())
        {
            System.out.println("No hay copias de préstamo.");
            return;
        }

        System.out.println("\nInventario de préstamo:");
        for (int i = 0; i < copias.size(); i++)
        {
            CopiaJuegoPrestamo copia = copias.get(i);
            System.out.println((i + 1) + ". " + copia.getJuegoMesa().getNombre()
                    + " | Estado: " + copia.getEstado()
                    + " | Disponible: " + copia.estaDisponible());
        }
    }

    private void verInventarioVenta()
    {
        List<JuegoVenta> juegos = sistema.getCafe().consultarInventarioVenta();

        if (juegos.isEmpty())
        {
            System.out.println("No hay juegos en venta.");
            return;
        }

        System.out.println("\nInventario de venta:");
        for (int i = 0; i < juegos.size(); i++)
        {
            JuegoVenta juego = juegos.get(i);
            System.out.println((i + 1) + ". " + juego.getNombre()
                    + " | Precio: $" + (int) juego.getPrecio()
                    + " | Stock: " + juego.getStockDisponible());
        }
    }

    private void reabastecerJuegoVenta(Administrador admin)
    {
        List<JuegoVenta> juegos = sistema.getCafe().consultarInventarioVenta();
        verInventarioVenta();

        if (juegos.isEmpty())
        {
            return;
        }

        System.out.print("Seleccione juego: ");
        int indice = leerEntero() - 1;

        if (indice < 0 || indice >= juegos.size())
        {
            System.out.println("Selección inválida.");
            return;
        }

        System.out.print("Cantidad a agregar: ");
        int cantidad = leerEntero();

        if (cantidad <= 0)
        {
            System.out.println("Cantidad inválida.");
            return;
        }

        admin.reabastecerJuegoVenta(juegos.get(indice), cantidad);
        System.out.println("Reabastecimiento realizado.");
    }

    private void moverJuegoVentaAPrestamo(Administrador admin)
    {
        List<JuegoVenta> inventarioVenta = sistema.getCafe().consultarInventarioVenta();

        if (inventarioVenta.isEmpty())
        {
            System.out.println("No hay juegos en inventario de venta.");
            return;
        }

        verInventarioVenta();

        System.out.print("Seleccione juego de venta: ");
        int indice = leerEntero() - 1;

        if (indice < 0 || indice >= inventarioVenta.size())
        {
            System.out.println("Selección inválida.");
            return;
        }

        System.out.print("Cantidad a mover: ");
        int cantidad = leerEntero();

        if (cantidad <= 0)
        {
            System.out.println("Cantidad inválida.");
            return;
        }

        JuegoVenta juegoVenta = inventarioVenta.get(indice);

        if (!juegoVenta.hayStock(cantidad))
        {
            System.out.println("No hay stock suficiente.");
            return;
        }

        JuegoMesa juegoMesa = buscarJuegoMesaPorNombre(juegoVenta.getNombre());

        if (juegoMesa == null)
        {
            System.out.println("No existe juego equivalente en catálogo de préstamo.");
            return;
        }

        for (int i = 0; i < cantidad; i++)
        {
            juegoMesa.agregarCopia(new CopiaJuegoPrestamo(EstadoJuego.BUENO, true, juegoMesa));
        }

        juegoVenta.reducirStock(cantidad);
        System.out.println("Movimiento realizado correctamente.");
    }

    private void repararCopiaPrestamo(Administrador admin)
    {
        List<CopiaJuegoPrestamo> copias = sistema.getCafe().consultarInventarioPrestamo();
        verInventarioPrestamo();

        if (copias.isEmpty())
        {
            return;
        }

        System.out.print("Seleccione copia: ");
        int indice = leerEntero() - 1;

        if (indice < 0 || indice >= copias.size())
        {
            System.out.println("Selección inválida.");
            return;
        }

        admin.repararJuego(copias.get(indice), null);
        System.out.println("Copia reparada.");
    }

    private void marcarCopiaDesaparecida(Administrador admin)
    {
        List<CopiaJuegoPrestamo> copias = sistema.getCafe().consultarInventarioPrestamo();
        verInventarioPrestamo();

        if (copias.isEmpty())
        {
            return;
        }

        System.out.print("Seleccione copia: ");
        int indice = leerEntero() - 1;

        if (indice < 0 || indice >= copias.size())
        {
            System.out.println("Selección inválida.");
            return;
        }

        admin.marcarJuegoDesaparecido(copias.get(indice));
        System.out.println("Copia marcada como desaparecida.");
    }

    private void verSugerencias()
    {
        if (sugerenciasPendientes.isEmpty())
        {
            System.out.println("No hay sugerencias pendientes.");
            return;
        }

        System.out.println("\nSugerencias:");
        for (int i = 0; i < sugerenciasPendientes.size(); i++)
        {
            SugerenciaPlatillo s = sugerenciasPendientes.get(i);
            System.out.println((i + 1) + ". " + s.getNombrePropuesto()
                    + " | Categoría: " + s.getCategoriaPropuesta()
                    + " | Estado: " + s.getEstado());
        }
    }

    private void gestionarSugerencia(Administrador admin)
    {
        if (sugerenciasPendientes.isEmpty())
        {
            System.out.println("No hay sugerencias.");
            return;
        }

        verSugerencias();

        System.out.print("Seleccione sugerencia: ");
        int indice = leerEntero() - 1;

        if (indice < 0 || indice >= sugerenciasPendientes.size())
        {
            System.out.println("Selección inválida.");
            return;
        }

        SugerenciaPlatillo sugerencia = sugerenciasPendientes.get(indice);

        System.out.println("1. Aprobar");
        System.out.println("2. Rechazar");
        System.out.print("Opción: ");
        int opcion = leerEntero();

        if (opcion == 1)
        {
            admin.aprobarSugerencia(sugerencia);
            sugerencia.setRevisadaPor(admin);
            System.out.println("Sugerencia aprobada.");
        }
        else if (opcion == 2)
        {
            admin.rechazarSugerencia(sugerencia);
            sugerencia.setRevisadaPor(admin);
            System.out.println("Sugerencia rechazada.");
        }
        else
        {
            System.out.println("Opción inválida.");
        }
    }

    private void verTurnosEmpleados()
    {
        List<Empleado> empleados = obtenerEmpleados();

        if (empleados.isEmpty())
        {
            System.out.println("No hay empleados.");
            return;
        }

        for (int i = 0; i < empleados.size(); i++)
        {
            System.out.println((i + 1) + ". " + empleados.get(i).getNombre());
        }

        System.out.print("Seleccione empleado: ");
        int indice = leerEntero() - 1;

        if (indice < 0 || indice >= empleados.size())
        {
            System.out.println("Selección inválida.");
            return;
        }

        mostrarTurnos(empleados.get(indice));
    }

    private void asignarTurnoEmpleado(Administrador admin)
    {
        List<Empleado> empleados = obtenerEmpleados();

        if (empleados.isEmpty())
        {
            System.out.println("No hay empleados.");
            return;
        }

        for (int i = 0; i < empleados.size(); i++)
        {
            System.out.println((i + 1) + ". " + empleados.get(i).getNombre());
        }

        System.out.print("Seleccione empleado: ");
        int indice = leerEntero() - 1;

        if (indice < 0 || indice >= empleados.size())
        {
            System.out.println("Selección inválida.");
            return;
        }

        Empleado empleado = empleados.get(indice);

        System.out.print("Día (LUNES/MARTES/MIERCOLES/JUEVES/VIERNES/SABADO/DOMINGO): ");
        String diaTexto = scanner.nextLine().trim().toUpperCase();

        DiaSemana dia;
        try
        {
            dia = DiaSemana.valueOf(diaTexto);
        }
        catch (Exception e)
        {
            System.out.println("Día inválido.");
            return;
        }

        System.out.print("Hora inicio: ");
        String horaInicio = scanner.nextLine().trim();

        System.out.print("Hora fin: ");
        String horaFin = scanner.nextLine().trim();

        Turno turno = new Turno(dia, horaInicio, horaFin);
        admin.asignarTurno(empleado, turno);

        System.out.println("Turno asignado correctamente.");
    }

    private void verSolicitudesCambioTurno()
    {
        List<SolicitudCambioTurno> solicitudes = obtenerSolicitudes();

        if (solicitudes.isEmpty())
        {
            System.out.println("No hay solicitudes de cambio.");
            return;
        }

        System.out.println("\nSolicitudes:");
        for (int i = 0; i < solicitudes.size(); i++)
        {
            SolicitudCambioTurno s = solicitudes.get(i);
            System.out.println((i + 1) + ". " + s.getSolicitante().getNombre()
                    + " | Tipo: " + s.getTipoSolicitud()
                    + " | Estado: " + s.getEstado());
        }
    }

    private void gestionarSolicitudCambio(Administrador admin)
    {
        List<SolicitudCambioTurno> solicitudes = obtenerSolicitudes();

        if (solicitudes.isEmpty())
        {
            System.out.println("No hay solicitudes.");
            return;
        }

        verSolicitudesCambioTurno();

        System.out.print("Seleccione solicitud: ");
        int indice = leerEntero() - 1;

        if (indice < 0 || indice >= solicitudes.size())
        {
            System.out.println("Selección inválida.");
            return;
        }

        SolicitudCambioTurno solicitud = solicitudes.get(indice);

        System.out.println("1. Aprobar");
        System.out.println("2. Rechazar");
        System.out.print("Opción: ");
        int opcion = leerEntero();

        if (opcion == 1)
        {
            admin.aprobarSolicitudCambio(solicitud);

            if (solicitud.getSolicitante() != null
                    && solicitud.getTurnoOriginal() != null
                    && solicitud.getTurnoPropuesto() != null)
            {
                admin.modificarTurno(
                    solicitud.getSolicitante(),
                    solicitud.getTurnoOriginal(),
                    solicitud.getTurnoPropuesto()
                );
            }

            System.out.println("Solicitud aprobada.");
        }
        else if (opcion == 2)
        {
            admin.rechazarSolicitudCambio(solicitud);
            System.out.println("Solicitud rechazada.");
        }
        else
        {
            System.out.println("Opción inválida.");
        }
    }

    private void mostrarInformeVentas()
    {
        System.out.println("Tipo de venta:");
        System.out.println("0. Todas");
        System.out.println("1. Cafetería");
        System.out.println("2. Tienda");
        System.out.print("Opción: ");
        int opcion = leerEntero();

        TipoVenta filtro = null;
        if (opcion == 1)
        {
            filtro = TipoVenta.CAFETERIA;
        }
        else if (opcion == 2)
        {
            filtro = TipoVenta.TIENDA_JUEGOS;
        }

        List<Venta> ventas = sistema.getCafe().generarInformeVentas("GENERAL", filtro);

        if (ventas.isEmpty())
        {
            System.out.println("No hay ventas registradas.");
            return;
        }

        double total = 0;

        for (Venta venta : ventas)
        {
            String comprador = (venta.getComprador() != null) ? venta.getComprador().getNombre() : "Sin comprador";
            System.out.println(comprador
                    + " | " + venta.getTipoVenta()
                    + " | Subtotal: $" + (int) venta.calcularSubtotal()
                    + " | Total: $" + (int) venta.calcularTotal());
            total += venta.calcularTotal();
        }

        System.out.println("Total acumulado: $" + (int) total);
    }

    private void mostrarHistorialPrestamos()
    {
        List<Prestamo> prestamos = sistema.getCafe().getPrestamos();

        if (prestamos.isEmpty())
        {
            System.out.println("No hay préstamos registrados.");
            return;
        }

        for (Prestamo prestamo : prestamos)
        {
            String usuario = (prestamo.getUsuario() != null) ? prestamo.getUsuario().getNombre() : "Sin usuario";
            System.out.println("\nUsuario: " + usuario
                    + " | Inicio: " + prestamo.getFechaInicio()
                    + " | Fin: " + prestamo.getFechaFin());

            for (DetallePrestamo detalle : prestamo.getDetalles())
            {
                System.out.println("- " + detalle.getCopiaJuego().getJuegoMesa().getNombre()
                        + " | Devuelto: " + detalle.estaDevuelto());
            }
        }
    }

    // =========================================================
    // APOYO GENERAL
    // =========================================================

    private void mostrarCatalogoJuegos()
    {
        List<JuegoMesa> catalogo = sistema.getCafe().consultarCatalogoJuegos();

        if (catalogo.isEmpty())
        {
            System.out.println("No hay juegos en el catálogo.");
            return;
        }

        System.out.println("\nCatálogo de juegos:");
        for (int i = 0; i < catalogo.size(); i++)
        {
            JuegoMesa juego = catalogo.get(i);
            System.out.println((i + 1) + ". " + juego.getNombre()
                    + " | Categoría: " + juego.getCategoria()
                    + " | Jugadores: " + juego.getMinJugadores() + "-" + juego.getMaxJugadores()
                    + " | Difícil: " + juego.isDificil()
                    + " | Disponible: " + juego.tieneCopiasDisponibles());
        }
    }

    private void mostrarMenu(List<ProductoMenu> menu)
    {
        System.out.println("\nMenú:");
        for (int i = 0; i < menu.size(); i++)
        {
            ProductoMenu producto = menu.get(i);
            String tipo = "Producto";

            if (producto instanceof Bebida)
            {
                tipo = "Bebida";
            }
            else if (producto instanceof Pasteleria)
            {
                tipo = "Pastelería";
            }

            System.out.println((i + 1) + ". " + producto.getNombre()
                    + " | $" + (int) producto.getPrecio()
                    + " | " + tipo);
        }
    }

    private void mostrarFavoritos(List<JuegoMesa> favoritos)
    {
        if (favoritos.isEmpty())
        {
            System.out.println("No hay favoritos.");
            return;
        }

        System.out.println("\nFavoritos:");
        for (JuegoMesa juego : favoritos)
        {
            System.out.println("- " + juego.getNombre());
        }
    }

    private void imprimirResumenVenta(Venta venta)
    {
        System.out.println("\nResumen de venta:");
        System.out.println("Subtotal: $" + (int) venta.calcularSubtotal());
        System.out.println("Descuento: $" + (int) venta.calcularDescuento());
        System.out.println("Impuestos: $" + (int) venta.calcularImpuestos());
        System.out.println("Propina: $" + (int) venta.calcularPropina());
        System.out.println("TOTAL: $" + (int) venta.calcularTotal());
    }

    private Mesa buscarMesaActivaDeCliente(Cliente cliente)
    {
        for (Mesa mesa : sistema.getCafe().getMesas())
        {
            ReservaMesa reserva = mesa.getReservaActiva();
            if (reserva != null && reserva.getCliente() != null
                    && reserva.getCliente().getLogin().equals(cliente.getLogin()))
            {
                return mesa;
            }
        }
        return null;
    }

    private List<Prestamo> prestamosActivosDeUsuario(Usuario usuario)
    {
        List<Prestamo> activos = new ArrayList<Prestamo>();

        for (Prestamo prestamo : sistema.getCafe().getPrestamos())
        {
            if (prestamo.getUsuario() != null
                    && prestamo.getUsuario().getLogin().equals(usuario.getLogin())
                    && prestamo.estaActivo())
            {
                activos.add(prestamo);
            }
        }

        return activos;
    }

    private JuegoMesa buscarJuegoMesaPorNombre(String nombre)
    {
        for (JuegoMesa juego : sistema.getCafe().getCatalogoJuegos())
        {
            if (juego.getNombre().equalsIgnoreCase(nombre))
            {
                return juego;
            }
        }
        return null;
    }

    private List<Empleado> obtenerEmpleados()
    {
        List<Empleado> empleados = new ArrayList<Empleado>();

        for (Usuario usuario : sistema.getUsuarios())
        {
            if (usuario instanceof Empleado)
            {
                empleados.add((Empleado) usuario);
            }
        }

        return empleados;
    }

    private List<SolicitudCambioTurno> obtenerSolicitudes()
    {
        List<SolicitudCambioTurno> solicitudes = new ArrayList<SolicitudCambioTurno>();

        for (Usuario usuario : sistema.getUsuarios())
        {
            if (usuario instanceof Empleado)
            {
                solicitudes.addAll(((Empleado) usuario).getSolicitudesCambioTurno());
            }
        }

        return solicitudes;
    }

    private void mostrarInfoCafe()
    {
        Cafe cafe = sistema.getCafe();

        System.out.println("\nInformación del café:");
        System.out.println("Capacidad máxima: " + cafe.getCapacidadMaximaClientes());
        System.out.println("Mesas registradas: " + cafe.getMesas().size());
        System.out.println("Empleados registrados: " + cafe.getEmpleados().size());
        System.out.println("Juegos en catálogo: " + cafe.getCatalogoJuegos().size());
        System.out.println("Juegos en venta: " + cafe.getInventarioVenta().size());
        System.out.println("Productos en menú: " + cafe.getMenu().size());
    }

    private void cargarDatosIniciales()
    {
        Cafe cafe = sistema.getCafe();

        Mesa mesa1 = new Mesa(1);
        Mesa mesa2 = new Mesa(2);
        Mesa mesa3 = new Mesa(3);

        cafe.agregarMesa(mesa1);
        cafe.agregarMesa(mesa2);
        cafe.agregarMesa(mesa3);

        Cliente cliente1 = new Cliente("1001", "Sofia", "sofia@mail.com", "sofia", "1234");
        Cliente cliente2 = new Cliente("1002", "Mateo", "mateo@mail.com", "mateo", "1234");

        Mesero mesero1 = new Mesero("2001", "Valentina", "vale@mail.com", "vale", "1234", "M001");
        Mesero mesero2 = new Mesero("2002", "Andres", "andres@mail.com", "andres", "1234", "M002");
        Cocinero cocinero1 = new Cocinero("3001", "Carlos", "carlos@mail.com", "carlos", "1234", "C001");
        Administrador admin = new Administrador("9001", "Admin", "admin@mail.com", "admin", "admin123");

        Turno turno1 = new Turno(DiaSemana.LUNES, "08:00", "16:00");
        Turno turno2 = new Turno(DiaSemana.MARTES, "08:00", "16:00");
        Turno turno3 = new Turno(DiaSemana.MIERCOLES, "10:00", "18:00");

        mesero1.getTurnos().add(turno1);
        mesero1.getTurnos().add(turno3);
        mesero2.getTurnos().add(turno2);
        cocinero1.getTurnos().add(turno1);

        JuegoMesa uno = new JuegoMesa("Uno", 1971, "Mattel", 2, 10, 3, false, CategoriaJuego.CARTAS);
        JuegoMesa ajedrez = new JuegoMesa("Ajedrez", 1475, "Universal", 2, 2, 6, true, CategoriaJuego.TABLERO);
        JuegoMesa twister = new JuegoMesa("Twister", 1966, "Hasbro", 2, 6, 5, false, CategoriaJuego.ACCION);
        JuegoMesa catan = new JuegoMesa("Catan", 1995, "Kosmos", 3, 4, 10, true, CategoriaJuego.TABLERO);

        uno.agregarCopia(new CopiaJuegoPrestamo(EstadoJuego.BUENO, true, uno));
        uno.agregarCopia(new CopiaJuegoPrestamo(EstadoJuego.NUEVO, true, uno));
        ajedrez.agregarCopia(new CopiaJuegoPrestamo(EstadoJuego.BUENO, true, ajedrez));
        twister.agregarCopia(new CopiaJuegoPrestamo(EstadoJuego.NUEVO, true, twister));
        catan.agregarCopia(new CopiaJuegoPrestamo(EstadoJuego.BUENO, true, catan));

        cafe.agregarJuegoCatalogo(uno);
        cafe.agregarJuegoCatalogo(ajedrez);
        cafe.agregarJuegoCatalogo(twister);
        cafe.agregarJuegoCatalogo(catan);

        mesero1.registrarJuegoConocido(ajedrez);
        mesero1.registrarJuegoConocido(catan);

        cafe.agregarEmpleado(mesero1);
        cafe.agregarEmpleado(mesero2);
        cafe.agregarEmpleado(cocinero1);
        cafe.setAdministrador(admin);

        cafe.agregarJuegoVenta(new JuegoVenta("Uno", 25000, true, 10));
        cafe.agregarJuegoVenta(new JuegoVenta("Catan", 180000, true, 5));
        cafe.agregarJuegoVenta(new JuegoVenta("Twister", 95000, true, 4));

        Bebida cafeBebida = new Bebida("Cafe americano", 5000, true, false, true);
        Bebida limonada = new Bebida("Limonada", 6000, true, false, false);
        Bebida cerveza = new Bebida("Cerveza", 12000, true, true, false);

        Pasteleria brownie = new Pasteleria("Brownie", 7000, true);
        brownie.agregarAlergeno(Alergeno.GLUTEN);
        brownie.agregarAlergeno(Alergeno.HUEVO);

        Pasteleria croissant = new Pasteleria("Croissant", 8000, true);
        croissant.agregarAlergeno(Alergeno.GLUTEN);

        cafe.agregarProductoMenu(cafeBebida);
        cafe.agregarProductoMenu(limonada);
        cafe.agregarProductoMenu(cerveza);
        cafe.agregarProductoMenu(brownie);
        cafe.agregarProductoMenu(croissant);

        sistema.getUsuarios().add(cliente1);
        sistema.getUsuarios().add(cliente2);
        sistema.getUsuarios().add(mesero1);
        sistema.getUsuarios().add(mesero2);
        sistema.getUsuarios().add(cocinero1);
        sistema.getUsuarios().add(admin);
    }

    private int leerEntero()
    {
        try
        {
            return Integer.parseInt(scanner.nextLine().trim());
        }
        catch (Exception e)
        {
            return -99;
        }
    }

    private double leerDouble()
    {
        try
        {
            return Double.parseDouble(scanner.nextLine().trim());
        }
        catch (Exception e)
        {
            return 0;
        }
    }

    private boolean leerSiNo()
    {
        String texto = scanner.nextLine().trim().toLowerCase();
        return texto.equals("s") || texto.equals("si") || texto.equals("sí");
    }

    public static void main(String[] args)
    {
        ConsolaAplicacion consola = new ConsolaAplicacion();
        consola.iniciarAplicacion();
    }
}