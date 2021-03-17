package org.example.nomemientan.usecase.ronda;

import co.com.sofka.business.generic.BusinessException;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.ResponseEvents;
import co.com.sofka.business.support.TriggeredEvent;
import org.example.nomemientan.domain.juego.events.JuegoInicializado;
import org.example.nomemientan.domain.juego.values.JuegoId;
import org.example.nomemientan.domain.ronda.values.RondaId;
import org.example.nomemientan.domain.ronda.Ronda;

//Segundo caso de uso en paquete ronda
//Se crea un caso de uso para cada necesidad (acciones que se van resolviendo para las condiciones del juego)
//Por cada caso de uso que se crea, se realiza una prueba que se evidencia en el paquete de test
public class CrearRondaInicalUseCase extends UseCase<TriggeredEvent<JuegoInicializado>, ResponseEvents> {
    @Override

    public void executeUseCase(TriggeredEvent<JuegoInicializado> input) {
        var event = input.getDomainEvent();
        var rondaId = new RondaId();
        if (event.getJugadoresIds().size() < 2) {
            throw new BusinessException(rondaId.value(), "No se puede crear la ronda por falta de jugadores");
        }
        var juegoId = JuegoId.of(event.aggregateRootId());
        var ronda = new Ronda(rondaId, juegoId, event.getJugadoresIds());
        ronda.inicializarRonda();

        emit().onResponse(new ResponseEvents(ronda.getUncommittedChanges()));
    }
}
