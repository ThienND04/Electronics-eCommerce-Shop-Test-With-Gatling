package electronicshop.simulations.integrated;

import electronicshop.simulations.BaseSimulation;
import electronicshop.scenarios.*; 
import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;

public class Integrated_03_SpikeTest extends BaseSimulation {
    {
        setUp(
            GuestUserScenario.build().injectOpen(
                atOnceUsers(300)
            ),

            // Buyer (250 User) 
            BuyerScenario.build().injectOpen(
                atOnceUsers(125)
            ),

            // admin (10 user)
            AdminScenario.build().injectOpen(
                atOnceUsers(10)
            )
        )
        .protocols(httpProtocol)
        .assertions(
            global().failedRequests().percent().lt(5.0), // Lỗi < 5%
            global().responseTime().percentile(95).lt(2000) // P95 < 2s 
        );
    }
}