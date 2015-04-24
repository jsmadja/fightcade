package fr.kaddath.fightcade.server.interfaces.rest.controller;

import com.wordnik.swagger.annotations.ApiOperation;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static java.lang.String.format;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Service
@RestController
public class AttendanceQuery {

    @Autowired
    private EntityManager entityManager;

    @ApiOperation(value = "Attendance in CSV")
    @RequestMapping(value = "/", method = GET)
    public String attendance(
            @RequestParam(value = "game", required = false) String game,
            @RequestParam(value = "country", required = false) String country,
            @RequestParam(value = "offset", required = false) Integer offset
    ) {
        if (offset == null) {
            offset = new Date().getTimezoneOffset();
        }
        int offsetInHours = (new Date().getTimezoneOffset() - offset) / 60;
        int[][] count = toTable(game, country, offsetInHours);
        StringBuilder sb = new StringBuilder();
        sb.append("x, y, value").append("\n");
        for (int day = 0; day < 7; day++) {
            for (int hour = 0; hour < 24; hour++) {
                sb.append(format("%s,%s,%d\n", hour, day, count[day][hour]));
            }
        }
        return sb.toString().trim();
    }

    @ApiOperation(value = "Attendance in CSV")
    @RequestMapping(value = "/line", method = GET)
    public String attendanceForLine(
            @RequestParam(value = "game", required = false) String game,
            @RequestParam(value = "country", required = false) String country,
            @RequestParam(value = "offset", required = false) Integer offset
    ) {
        if (offset == null) {
            offset = new Date().getTimezoneOffset();
        }
        int offsetInHours = (new Date().getTimezoneOffset() - offset) / 60;
        int[][] count = toTable(game, country, offsetInHours);
        StringBuilder sb = new StringBuilder();
        sb.append("hours,Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday").append("\n");
        for (int hour = 0; hour < 24; hour++) {
            sb.append(hour);
            for (int day = 0; day < 7; day++) {
                sb.append(format(",%d", count[day][hour]));
            }
            sb.append("\n");
        }
        return sb.toString().trim();
    }

    private int[][] toTable(String game, String country, int offsetInHours) {
        if("Anywhere".equals(country)) {
            country = null;
        }
        Collection<Object[]> results = attendanceSql(game, country, offsetInHours);
        int[][] count = new int[7][24];
        for (Object[] result : results) {
            Date date = (Date) result[0];
            count[(date.getDay() + 6) % 7][date.getHours()] = ((BigInteger) result[1]).intValue();
        }
        return count;
    }

    @Transactional
    @ApiOperation(value = "Post Attendance")
    @RequestMapping(value = "/players", method = POST)
    public void attendance(@RequestBody Attendance attendance) {
        entityManager.persist(new Player(new PlayerId(new Date(), attendance.player, attendance.rom), attendance.country));
    }

    private Collection attendanceSql(String game, String country, int offsetInHours) {
        String query = format("SELECT date, COUNT(DISTINCT player) FROM player WHERE date >= '%s' ", new DateTime().plusHours(offsetInHours).minusDays(7).toDateTimeISO());
        if (country != null) {
            query += " AND country = '" + country + "'";
        }
        if (game != null) {
            query += " AND rom = '" + game + "'";
        }
        query += " GROUP BY DAY(date), HOUR(date)";
        return entityManager.createNativeQuery(query).getResultList();
    }

    @ApiOperation(value = "Games")
    @RequestMapping(value = "/games", method = GET)
    public Collection games() {
        List games = entityManager.createNativeQuery("SELECT rom,name FROM stat ORDER BY name ASC").getResultList();
        games.add(0, new Object[]{"", "Any game"});
        return games;
    }

    @ApiOperation(value = "Countries")
    @RequestMapping(value = "/countries", method = GET)
    public Collection countries() {
        List countries = entityManager.createNativeQuery("SELECT DISTINCT(country) FROM player WHERE country !='' ORDER BY country ASC").getResultList();
        countries.add(0, "Anywhere");
        return countries;
    }

    @Scheduled(fixedDelay = 60 * 60 * 1000)
    public void cleanDatabase() {
        entityManager.createNativeQuery(format("DELETE FROM player WHERE date < '%s'", new DateTime().minusDays(7).toDateTimeISO()));
    }

}
