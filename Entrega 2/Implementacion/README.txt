DULCES Y DADOS - PROYECTO

Descripción:
Este proyecto implementa el sistema de gestión del café Dulces y Dados.
Permite gestionar usuarios, reservas, préstamos de juegos, ventas, turnos y persistencia de datos.

----------------------------------------
EJECUCIÓN DE LA CONSOLA
----------------------------------------

Clase principal:
ui.ConsolaAplicacion

Pasos:
1. Ejecutar el método main de la clase ConsolaAplicacion.
2. Iniciar sesión con alguno de los siguientes usuarios:

Clientes:
login: sofia      password: 1234
login: mateo      password: 1234

Empleados:
login: vale       password: 1234
login: andres     password: 1234
login: carlos     password: 1234

Administrador:
login: admin      password: admin123

----------------------------------------
PERSISTENCIA
----------------------------------------

Los datos se guardan en archivos dentro de la carpeta configurada en PersistenciaSistema.

El sistema:
- Carga los datos automáticamente al iniciar.
- Guarda los datos al finalizar o cuando se invoque guardarDatos().

----------------------------------------
PRUEBAS
----------------------------------------

Las pruebas se encuentran en el paquete:

pruebas

Cada una se ejecuta de forma independiente.

Pruebas disponibles:

- PruebaPersistencia
  Verifica guardado y carga de datos.

- PruebaReservas
  Verifica creación, activación y cierre de reservas.

- PruebaPrestamos
  Verifica préstamo de juegos y devolución.

- PruebaVentas
  Verifica cálculo de subtotal, impuestos, descuentos y total.

- PruebaTurnos
  Verifica asignación de turnos y solicitudes de cambio.

- PruebaAdministrador
  Verifica gestión de inventario y juegos.

- PruebaAutenticacion
  Verifica inicio de sesión correcto e incorrecto.

Para ejecutar una prueba:
1. Ir a la clase en el paquete pruebas.
2. Ejecutar el método main.

----------------------------------------
ESTRUCTURA DEL PROYECTO
----------------------------------------

modelo     -> lógica del sistema
ui         -> consola y pruebas
datos      -> archivos de persistencia
