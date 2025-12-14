package electronicshop.simulations.admin;

import electronicshop.scenarios.AdminScenario;
import electronicshop.scenarios.BuyerScenario;
import electronicshop.simulations.BaseSimulation;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class Admin_02_DataVolume_StressTest extends BaseSimulation {

    {
        setUp(
            BuyerScenario.build()
                .injectOpen(rampUsers(500).during(120))
            .andThen(
                AdminScenario.build()
                    .injectOpen(atOnceUsers(5))
            )
        ).protocols(httpProtocol);
    }
}