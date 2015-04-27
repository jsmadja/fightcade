package fr.kaddath.fightcade.server.infrastructure;

import fr.kaddath.fightcade.server.interfaces.rest.controller.Attendance;
import fr.kaddath.fightcade.server.interfaces.rest.controller.Player;
import fr.kaddath.fightcade.server.interfaces.rest.controller.PlayerId;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static java.lang.String.format;

@Service
public class FightcadeService {

    private static final String SELECT_GAMES = "SELECT rom,name FROM stat ORDER BY name ASC";
    private static final String SELECT_COUNTRIES = "SELECT DISTINCT(country) FROM player WHERE country !='' ORDER BY country ASC";
    @Autowired
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public Collection getCountries() {
        List<String> countries = entityManager.createNativeQuery(SELECT_COUNTRIES).getResultList();
        countries.add(0, "Anywhere");
        return countries;
    }

    @Transactional
    public void save(Attendance attendance) {
        entityManager.persist(new Player(new PlayerId(new Date(), attendance.player, attendance.rom), attendance.country));
    }

    @Transactional(readOnly = true)
    public Collection<Object[]> getAttendance(String game, String country) {
        String query = "SELECT date, COUNT(DISTINCT player) FROM player ";
        if (country != null) {
            query += format("WHERE country = '%s' ", country);
        }
        if (game != null) {
            if (country == null) {
                query += format("WHERE rom = '%s'", game);
            } else {
                query += format("AND rom = '%s'", game);
            }
        }
        query += " GROUP BY DAY(date), HOUR(date)";
        return entityManager.createNativeQuery(query).getResultList();
    }

    @Transactional(readOnly = true)
    public Collection getGames() {
        List<Object[]> games = entityManager.createNativeQuery(SELECT_GAMES).getResultList();
        games.add(0, new Object[]{"", "Any game"});
        return games;
    }

    @Transactional
    public void deleteOldValues() {
        DateTime oneWeekAgo = new DateTime().
                withMinuteOfHour(0).
                withSecondOfMinute(0).
                minusDays(7).
                toDateTimeISO();
        int deletedRows = entityManager.createNativeQuery(format("DELETE FROM player WHERE date < '%s'", oneWeekAgo)).executeUpdate();
        System.err.println(deletedRows + " deleted rows");
    }

}
