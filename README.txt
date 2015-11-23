# Inacap

Titulo del trabajo: Actividad 20 – Trabajo final: Internacionalización de una aplicación móvil.
Asignatura: Programación de Aplicaciones Móviles Android
Profesor: Andrés Muñoz 
Alumno: Samuel Vasquez

Para ejecutar la aplicacion debe usar las siguientes credenciales:

Usuario: demo
Contraseña: demo

Usuario: test
Contraseña: test
-------------------------------------------------

Se subio proyecto a GitHub, en https://github.com/samuelvasquez/Inacap, version 4.0

La aplicacion opera en dos idiomas: Español e Ingles. Para cambiar de idioma, se debe entrar al menu Configuracion / Setting 

En las pantallas de clientes y entrega de pedidos, se configuraron dos layout distintos. Para pantallas menores a 480 dp de ancho, se muestra el listado de elementos y al hacer clic se cambia a otra pantalla con el detalle del cliente o pedido.

En caso de pantallas con mas de 480 dp de ancho se muestra el listado de elementos en el sidebar, y el detalle en la misma pantalla.

Para probar la diferencias de layout, se propone usar la aplicacion con la pantalla horizontal y vertical. Asi tendra un mayor ancho y podra visualizar el efecto de la pantalla combinada.

Se incorporo boton flotante para la opcion "Agregar" y botones en el Action Bar para Modificar, Eliminar, Guardar y Cancelar.

La Google API KEY se registra en el archivo \app\src\debug\res\values\google_maps_api.xml

Para probar los mapas, debe registrarse como desarrollador en la consola de Google y obtener su propia API KEY

El mapa opera cargando los pedidos del vendedor que se encuentren pendiente. Por lo que para visualizar los clientes en el mapa, primero debe crear al menos un pedido y dejarno pendiente (no entregado)

Si no hay pedidos pendientes se muestra mapa de Santiago y mensaje "No hay clientes con pedidos pendientes"

En caso de encontrar pedidos pendientes muestra mapa de Santiago con la ubicacion de los clientes que tienen pedidos pendientes.

Los casos de test unitario se encuentran en \app\src\androidTest\java\samuelvasquez\inacap\cl\unidad3

----

