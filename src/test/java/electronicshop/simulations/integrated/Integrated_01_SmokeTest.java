package electronicshop.simulations.integrated;

import electronicshop.simulations.BaseSimulation;
import electronicshop.scenarios.*; 
import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;

public class Integrated_01_SmokeTest extends BaseSimulation {
    {
        setUp(
            GuestUserScenario.build().injectOpen(atOnceUsers(1)),
            BuyerScenario.build().injectOpen(atOnceUsers(1)),
            AdminScenario.build().injectOpen(atOnceUsers(1))
        ).protocols(httpProtocol);
    }
}