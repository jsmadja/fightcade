package fr.kaddath.fightcade.server.interfaces.rest.controller.agency;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Embeddable
public class PlayerId implements Serializable {

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private String rom;

    private String player;

    public PlayerId(Date date, String player, String rom) {
        this.date = date;
        this.player = player;
        this.rom = rom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerId playerId = (PlayerId) o;

        if (!date.equals(playerId.date)) return false;
        if (!player.equals(playerId.player)) return false;
        if (!rom.equals(playerId.rom)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = date.hashCode();
        result = 31 * result + rom.hashCode();
        result = 31 * result + player.hashCode();
        return result;
    }
}
