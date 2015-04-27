package fr.kaddath.fightcade.server.infrastructure;

import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
public class CSVService {

    private static final String HEADERS = "hours,Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday";

    public String buildCSV(int[][] count) {
        StringBuilder sb = new StringBuilder();
        sb.append(HEADERS).append("\n");
        for (int hour = 0; hour < 24; hour++) {
            sb.append(hour);
            for (int day = 0; day < 7; day++) {
                sb.append(format(",%d", count[day][hour]));
            }
            sb.append("\n");
        }
        return sb.toString().trim();
    }

}
