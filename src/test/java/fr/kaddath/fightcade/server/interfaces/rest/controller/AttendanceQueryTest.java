package fr.kaddath.fightcade.server.interfaces.rest.controller;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.contentOf;

public class AttendanceQueryTest extends SpringIntegrationTestCase {

    private static final int OFFSET = -120;
    private static final String ANY_GAME = null;
    private static final String ANY_WHERE = null;

    @Autowired
    private AttendanceQuery query;

    @Test
    public void should_get_garou_france_attendance() {
        String attendance = query.attendance("garou", "France", OFFSET);
        assertThat(attendance).isEqualTo(contentOf(new File("src/test/resources/data/attendance_garou_France.csv")));
    }

    @Test
    public void should_get_garou_attendance() {
        String attendance = query.attendance("garou", ANY_WHERE, OFFSET);
        assertThat(attendance).isEqualTo(contentOf(new File("src/test/resources/data/attendance_garou.csv")));
    }

    @Test
    public void should_get_global_attendance() {
        String attendance = query.attendance(ANY_GAME, ANY_WHERE, OFFSET);
        assertThat(attendance).isEqualTo(contentOf(new File("src/test/resources/data/attendance.csv")));
    }

    @Test
    public void should_get_france_attendance() {
        String attendance = query.attendance(ANY_GAME, "France", OFFSET);
        assertThat(attendance).isEqualTo(contentOf(new File("src/test/resources/data/attendance_France.csv")));
    }

    @Test
    public void should_get_global_attendance_with_offset_120() {
        String attendance = query.attendance(ANY_GAME, ANY_WHERE, 120);
        assertThat(attendance).isEqualTo(contentOf(new File("src/test/resources/data/attendance_offset_120.csv")));
    }

    @Test
    public void should_get_countries() {
        assertThat(query.countries()).hasSize(102);
    }

    @Test
    public void should_get_games() {
        assertThat(query.games()).hasSize(196);
    }

}