package fr.kaddath.fightcade.server.interfaces.rest.controller.agency;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AttendanceQueryIT extends SpringIntegrationTestCase {

    @Autowired
    private AttendanceQuery attendanceQuery;

    @Test
    public void should_create_an_attendance() {

        attendanceQuery.attendance(new Attendance("kaddath", "garou", "France"));

    }

}