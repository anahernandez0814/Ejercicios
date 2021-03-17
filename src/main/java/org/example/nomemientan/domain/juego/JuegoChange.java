package org.example.nomemientan.domain.juego;

import co.com.sofka.domain.generic.EventChange;
import org.example.nomemientan.domain.juego.events.JuegoCreado;
import org.example.nomemientan.domain.juego.events.JuegoInicializado;
import org.example.nomemientan.domain.juego.events.JugadorAdicionado;

import java.util.HashMap;


public class JuegoChange extends EventChange {
    public JuegoChange(Juego juego) {

        apply((JuegoCreado event) -> {
            juego.juegoInicializado = Boolean.FALSE;
            juego.jugadores = new HashMap<>();
        });

        apply((JuegoInicializado event) -> {
            juego.juegoInicializado = Boolean.TRUE;
        });

        /*Cuando el juador es adicionado entonces deberia validar si el juego esta inicializado y
        mostrar la excepcion.*/
        apply((JugadorAdicionado event) -> {
            if (juego.juegoInicializado.equals(Boolean.TRUE)) {
                throw new IllegalArgumentException("No se puede crear un nuevo jugador si el juego esta en marcha");
            }

            /*Se crea un nuevo jugar que es traido por medio de los eventos para luego adicionar
            el jugador.Pero antes de adicionarlo este pasa por un evento donde el juego creado
            es falso y los jugadores estan inicializados sin ningun valor.*/
            juego.jugadores.put(event.getJugadorId(),
                    new Jugador(
                            event.getJugadorId(),
                            event.getNombre(),
                            event.getCapital()
                    )
            );
        });
    }
}
