package org.example.nomemientan.usecase.juego;

import co.com.sofka.business.generic.BusinessException;
import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.support.RequestCommand;
import org.example.nomemientan.domain.juego.command.CrearJuego;
import org.example.nomemientan.domain.juego.events.JuegoCreado;
import org.example.nomemientan.domain.juego.events.JugadorAdicionado;
import org.example.nomemientan.domain.juego.values.Capital;
import org.example.nomemientan.domain.juego.values.JugadorId;
import org.example.nomemientan.domain.juego.values.Nombre;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Objects;


class CrearJuegoUseCaseTest {

    @Test
    void crearUnJuego() {
        var nombres = Map.of(
                JugadorId.of("xxxxx"), new Nombre("Raul Alzate"),
                JugadorId.of("ffff"), new Nombre("Andres Alzate")
        );
        var capiltales = Map.of(
                JugadorId.of("xxxxx"), new Capital(500),
                JugadorId.of("ffff"), new Capital(500)
        );
        /* Principalmente tenemos los comandos y casos de uso.
        El comando esta compuesto del capital y el nomebre.
        Donde cada nombre es diferente y cada capital debe ser igual. Eso se inyecta o se pasa a crear juego.*/
        var command = new CrearJuego(capiltales, nombres);
        var useCase = new CrearJuegoUseCase();


        /*Se preparan las entradas (creancion de objetos)
        En esta parte se esta ejecutando y tenemos algo llamado Hanlder que es un manejador
        En donde se entrega el comando y el caso de uso dandole gestion para que le entregue los eventos
        que han pasado en esos casos de uso.
         */

        //Con get instance se esta reciclando la instancia
        var events = UseCaseHandler.getInstance(
                //Ejecuación sincrona: Es una respuesta directa. Retorna unos eventos directos.
                .syncExecutor(useCase, new RequestCommand<>(command))
                .orElseThrow()
                .getDomainEvents();

        //Estos eventos se estan consultando por la posicion ya que es una lista.
        var juegoCreado = (JuegoCreado) events.get(0); //Juego creado
        var jugadorAdicionadoParaRaul = (JugadorAdicionado) events.get(2); //Jugador adicionado
        var jugadorAdicionadoParaAndres = (JugadorAdicionado) events.get(1); //Jugador adicionado
        //Son tres eventos que salen cuando se ejecuta el caso de uso con el comando.

        Assertions.assertTrue(Objects.nonNull(juegoCreado.getJuegoId().value()));

        //Verificaciones que se hacen. Validaciones de que pasa o no pasa.
        //Indicando si la prueba falla o si la prueba pasa correctamente.
        Assertions.assertEquals("Raul Alzate", jugadorAdicionadoParaRaul.getNombre().value());
        Assertions.assertEquals(500, jugadorAdicionadoParaRaul.getCapital().value());
        Assertions.assertEquals("xxxxx", jugadorAdicionadoParaRaul.getJugadorId().value());

        Assertions.assertEquals("Andres Alzate", jugadorAdicionadoParaAndres.getNombre().value());
        Assertions.assertEquals(500, jugadorAdicionadoParaAndres.getCapital().value());
        Assertions.assertEquals("ffff", jugadorAdicionadoParaAndres.getJugadorId().value());

    }


    @Test
    void errorAlCrearJuego() {
        var nombres = Map.of(
                JugadorId.of("xxxxx"), new Nombre("Raul Alzate")
        );
        var capiltales = Map.of(
                JugadorId.of("xxxxx"), new Capital(500)
        );
        var command = new CrearJuego(capiltales, nombres);
        var useCase = new CrearJuegoUseCase();


        Assertions.assertThrows(BusinessException.class, () -> {
            UseCaseHandler.getInstance()
                    .syncExecutor(useCase, new RequestCommand<>(command))
                    .orElseThrow();
        }, "No se puede crear el juego por que no tiene la cantidad necesaria de jugadores");

    }

}