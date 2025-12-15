package electronicshop.simulations.buyer;

import electronicshop.scenarios.BuyerScenario;
import electronicshop.simulations.BaseSimulation;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class Buyer_02_LoadTest extends BaseSimulation {
    {
        setUp(
            BuyerScenario.build().injectOpen(
                rampUsers(100).during(30)       // Tăng dần 100 user
            )
        ).protocols(httpProtocol)
        .assertions(
            global().responseTime().percentile(95).lt(1000),
            global().failedRequests().percent().lt(1.0)
        );
    }
}   
