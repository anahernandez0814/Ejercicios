package org.example.nomemientan.usecase.juego;

import co.com.sofka.business.generic.BusinessException;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.business.support.ResponseEvents;
import org.example.nomemientan.domain.juego.Juego;
import org.example.nomemientan.domain.juego.command.CrearJuego;
import org.example.nomemientan.domain.juego.factory.JugadorFactory;
import org.example.nomemientan.domain.juego.values.JuegoId;

/*Primer caso de uso en el paquete Juego.
Se crea un caso de uso para cada necesidad (acciones que se van resolviendo para las condiciones del juego)
Por cada caso de uso que se crea, se realiza una prueba que se evidencia en el paquete de test.

En este primer caso de uso, estamos diciendo que vamos a crear un juego.

En la linea de código , podemos observar que tenemos una extensión. "CrearJuegoUseCase" que se extiende
de UseCase<RequestCommand<CrearJuego>, ResponseEvents>.
La primera parte que corresponde <RequestCommand<CrearJuego>, es lo que le estamos ingresando,
el ResponseEvents> es lo que esta saliendo (una respuesta a los eventos) */

public class CrearJuegoUseCase extends UseCase<RequestCommand<CrearJuego>, ResponseEvents> {
    @Override

    public void executeUseCase(RequestCommand<CrearJuego> input) {
        var command = input.getCommand(); //Se obtiene el comando que es extraido del input presente en la linea de código 27
        var juegoId = new JuegoId(); // Se esta creando un nuevo ID del juego

        /*Desde la linea de código 34 hasta la 41, logramos observar que estamos utilizando una "factory",
          es como una fabrica que nos permite crear jugadores.
          Para cada identifiacor o ID del jugador el sistema va a entregar un nombre. Ya con factory lo
          que vamos a obtener es la creación de un nuevo jugador. */
        var factory = JugadorFactory.builder();
        command.getNombres()
                /*Se pasa el valor del ID, nombre y con command.getCapitales.get(jugadorId)
                 El saca el valor del capital desde un mapa (un mapa es una llave de valor)
                jugadorId, nombre, command.getCapitales().get(jugadorId)*/

               .forEach((jugadorId, nombre) ->
                       factory.nuevoJugador());

        if (factory.jugadores().size() < 2) {
            throw new BusinessException(juegoId.value(), "No se puede crear el juego por que no tiene la cantidad necesaria de jugadores");
        }

        var juego = new Juego(juegoId, factory);

        emit().onResponse(new ResponseEvents(juego.getUncommittedChanges()));
    }
}
