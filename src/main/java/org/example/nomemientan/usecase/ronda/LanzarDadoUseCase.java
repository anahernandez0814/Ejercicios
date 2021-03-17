package org.example.nomemientan.usecase.ronda;

import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.ResponseEvents;
import co.com.sofka.business.support.TriggeredEvent;
import org.example.nomemientan.domain.ronda.Ronda;
import org.example.nomemientan.domain.ronda.events.RondaInicializada;
import org.example.nomemientan.domain.ronda.values.RondaId;

//Tercer caso de uso en el paquete ronda
//Se crea un caso de uso para cada necesidad (acciones que se van resolviendo para las condiciones del juego)
//Por cada caso de uso que se crea, se realiza una prueba que se evidencia en el paquete de test
public class LanzarDadoUseCase extends UseCase<TriggeredEvent<RondaInicializada>, ResponseEvents> {
    @Override
    public void executeUseCase(TriggeredEvent<RondaInicializada> input) {
        var event = input.getDomainEvent();
        var ronda = Ronda.from(RondaId.of(event.aggregateRootId()), retrieveEvents());

        ronda.tirarDados();

        emit().onResponse(new ResponseEvents(ronda.getUncommittedChanges()));
    }
}
