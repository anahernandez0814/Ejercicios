package org.example.nomemientan.usecase.juego;

import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.business.support.ResponseEvents;
import org.example.nomemientan.domain.juego.Juego;
import org.example.nomemientan.domain.juego.command.InicializarJuego;

//Segundo caso de uso en el paquete Juego
//Se crea un caso de uso para cada necesidad (acciones que se van resolviendo para las condiciones del juego)
//Por cada caso de uso que se crea, se realiza una prueba que se evidencia en el paquete de test
public class InicializarJuegoUseCase extends UseCase<RequestCommand<InicializarJuego>, ResponseEvents> {
    @Override
    public void executeUseCase(RequestCommand<InicializarJuego> input) {
        var command = input.getCommand();

        var juego = Juego.from(command.getJuegoId(), retrieveEvents());

        juego.iniciarJuego();

        emit().onResponse(new ResponseEvents(juego.getUncommittedChanges()));

    }
}
