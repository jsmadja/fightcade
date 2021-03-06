package fr.kaddath.fightcade.server.interfaces.rest.controller;

import fr.kaddath.fightcade.server.Application;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = Application.class)
public abstract class SpringIntegrationTestCase {

    static {
        System.setProperty("test.in.progress", "true");
    }

}
