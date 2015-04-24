package fr.kaddath.fightcade.server.interfaces.rest.controller;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class Player {

    @EmbeddedId
    private PlayerId id;
    private String country;

    public Player(PlayerId playerId, String country) {
        id = playerId;
        this.country = country;
    }
}
