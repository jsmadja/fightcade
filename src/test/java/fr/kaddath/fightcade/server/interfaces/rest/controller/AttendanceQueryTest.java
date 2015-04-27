package fr.kaddath.fightcade.server.interfaces.rest.controller;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AttendanceQueryTest extends SpringIntegrationTestCase {

    @Autowired
    private AttendanceQuery query;

    @Test
    public void should_list() {
        String game = "garou";
        String country = "France";
        int offset = -120;

        String attendance = query.attendance(game, country, offset);
        System.err.println(attendance);
    }

}