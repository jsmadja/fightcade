package fr.kaddath.fightcade.server.interfaces.rest.controller;

import com.wordnik.swagger.annotations.ApiOperation;
import fr.kaddath.fightcade.server.infrastructure.CSVService;
import fr.kaddath.fightcade.server.infrastructure.FightcadeService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Service
@RestController
public class AttendanceQuery {

    @Autowired
    private FightcadeService fightcadeService;

    @Autowired
    private CSVService csvService;

    @ApiOperation(value = "Attendance in CSV")
    @RequestMapping(value = "/line", method = GET)
    public String attendance(
            @RequestParam(value = "game", required = false) String game,
            @RequestParam(value = "country", required = false) String country,
            @RequestParam(value = "offset", required = false) Integer offset
    ) {
        if (offset == null) {
            offset = new Date().getTimezoneOffset();
        }
        if ("Anywhere".equals(country)) {
            country = null;
        }
        int offsetInHours = (new Date().getTimezoneOffset() - offset) / 60;
        Collection<Object[]> results = fightcadeService.getAttendance(game, country);
        int[][] count = transformAsArray(results, offsetInHours);
        return csvService.buildCSV(count);
    }

    private int[][] transformAsArray(Collection<Object[]> results, int offsetInHours) {
        int[][] count = new int[7][24];
        for (Object[] result : results) {
            DateTime date = new DateTime(result[0]).plusHours(offsetInHours);
            int day = (date.getDayOfWeek() + 6) % 7;
            int hour = date.getHourOfDay();
            int players = ((BigInteger) result[1]).intValue();
            if (count[day][hour] == 0) {
                count[day][hour] = players;
            }
        }
        return count;
    }

    @ApiOperation(value = "Post Attendance")
    @RequestMapping(value = "/players", method = POST)
    public void attendance(@RequestBody Attendance attendance) {
        if (!"lobby".equalsIgnoreCase(attendance.rom)) {
            fightcadeService.save(attendance);
        }
    }

    @ApiOperation(value = "Games")
    @RequestMapping(value = "/games", method = GET)
    public Collection games() {
        return fightcadeService.getGames();
    }

    @ApiOperation(value = "Countries")
    @RequestMapping(value = "/countries", method = GET)
    public Collection countries() {
        return fightcadeService.getCountries();
    }

    @Scheduled(fixedDelay = 60 * 60 * 1000)
    public void cleanDatabase() {
        fightcadeService.deleteOldValues();
    }

}
