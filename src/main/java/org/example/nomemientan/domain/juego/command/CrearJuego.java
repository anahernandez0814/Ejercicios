package org.example.nomemientan.domain.juego.command;

import co.com.sofka.domain.generic.Command;
import org.example.nomemientan.domain.juego.values.Capital;
import org.example.nomemientan.domain.juego.values.JugadorId;
import org.example.nomemientan.domain.juego.values.Nombre;

import java.util.Map;

/* Un dominio es lo que se hace dentro de las necesidades (son las soluciones) para un
problema planteado. Dentro de este dominio, como podemos observar a continuación, tenemos un DTO.

Un DTO, son aquellos objetos de transporte, los cuales se representan dentro de este código como un
comando (Command). Los comandos basicamente son ordenes (Estás ordenes tienen unas propiedades,
ellos son inmutables(no cambian en el tiempo) y no tienen comportamiento).

Un ejemplo un poco más claro, es lo que se presenta en este código.Tenemos una orden a la que
le estamos diciendo que cree un juego "Crear Juego" esto es igual a una orden directa para la creación
del juego)
 */

/* Inicialmente tenemos un objeto que es un comando, el cual es Crear Juego. Lo identificamos ya que se
    esta implementando a traves de la palabra Command. Linea de código 24 */
public class CrearJuego implements Command {
    /*Este comando tiene dos argumentos que son valores finales (estos son las capitales y nombres)
    Se pueden observar en la linea 27 y 28*/
    private final Map<JugadorId, Capital> capitales;
    private final Map<JugadorId, Nombre> nombres;

    /* El comando, en este caso "Crear juego" es pasado por un constructor que es mostrado
    en la linea 33 y las lineas de código 27 y 28, lo que se esta haciendo es que se
    inicializan para despues poderlos utilizar*/
    public CrearJuego(Map<JugadorId, Capital> capitales, Map<JugadorId, Nombre> nombres) {
        this.capitales = capitales;
        this.nombres = nombres;
    }
    /*Como se menciona anteriormente los argumentos no van a cambiar en el tiempo,
    pero con la instrucción get podremos obtener las propiedades de ellos dos (getCapitales
    y getNombre).
    */
    public Map<JugadorId, Capital> getCapitales() {
        return capitales;
    }

    public Map<JugadorId, Nombre> getNombres() {
        return nombres;
    }
}
