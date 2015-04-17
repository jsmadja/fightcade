package fr.kaddath.fightcade.server.interfaces.rest.controller.agency;

import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
            @RequestParam(value = "country", required = false) String country
    ) {
        StringBuilder sb = new StringBuilder();
        sb.append("x, y, value").append("\n");
        for (int day = 0; day < 7; day++) {
            for (int hour = 0; hour < 24; hour++) {
                sb.append(hour).append(",").append(day).append(",").append(attendance(game, country, day, hour)).append("\n");
            }
        }
        return sb.toString();
    }

    @Transactional
    @ApiOperation(value = "Post Attendance")
    @RequestMapping(value = "/players", method = POST)
    public void attendance(@RequestBody Attendance attendance) {
        entityManager.persist(new Player(new PlayerId(new Date(), attendance.player, attendance.rom), attendance.country));
    }

    private int attendance(String game, String country, int day, int hour) {
        String query = "SELECT COUNT(DISTINCT player) FROM player WHERE HOUR(date) = " + hour + " AND WEEKDAY(date) = " + day;
        if (game != null) {
            query += " AND rom = '" + game + "'";
        }
        if (country != null) {
            query += " AND country = '" + country + "'";
        }
        return ((BigInteger) entityManager.createNativeQuery(query).getSingleResult()).intValue();
    }

    @ApiOperation(value = "Games")
    @RequestMapping(value = "/games", method = GET)
    public Collection games() {
        return entityManager.createNativeQuery("SELECT rom,name FROM stat ORDER BY name ASC").getResultList();
    }

    @ApiOperation(value = "Countries")
    @RequestMapping(value = "/countries", method = GET)
    public Collection countries() {
        return entityManager.createNativeQuery("SELECT DISTINCT(country) FROM player WHERE country !='' ORDER BY country ASC").getResultList();
    }

}
