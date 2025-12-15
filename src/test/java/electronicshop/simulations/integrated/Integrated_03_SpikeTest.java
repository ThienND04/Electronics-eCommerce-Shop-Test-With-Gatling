package electronicshop.simulations.integrated;

import electronicshop.simulations.BaseSimulation;
import electronicshop.scenarios.*; 
import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;

public class Integrated_03_SpikeTest extends BaseSimulation {
    {
        setUp(
            GuestUserScenario.build().injectOpen(
                rampUsers(300).during(10)
            ),

            // Buyer (150 User) 
            BuyerScenario.build().injectOpen(
                nothingFor(5), 
                atOnceUsers(150)
            ),

            // admin (5 user)
            AdminScenario.build().injectOpen(
                nothingFor(6),
                atOnceUsers(10)
            )
        )
        .protocols(httpProtocol)
        .assertions(
            global().failedRequests().percent().lt(5.0), // Lỗi < 5%
            global().responseTime().percentile(95).lt(3000) // P95 < 3s 
        );
    }
}