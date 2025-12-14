package electronicshop.simulations.guest;

import static io.gatling.javaapi.core.CoreDsl.atOnceUsers;

import electronicshop.scenarios.GuestUserScenario;
import electronicshop.simulations.BaseSimulation;

public class Guest_01_Smoke extends BaseSimulation {
    {   
        setUp(
            GuestUserScenario.build().injectOpen(atOnceUsers(1))
        ).protocols(httpProtocol); 
    }
}
