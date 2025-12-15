package electronicshop.simulations.buyer;

import electronicshop.scenarios.BuyerScenario;
import electronicshop.simulations.BaseSimulation;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class Buyer_03_StressTest extends BaseSimulation {
    {
       setUp(
            BuyerScenario.build().injectOpen(
                rampUsers(1500).during(30) //  1500 user trong 30 giây
            )
        ).protocols(httpProtocol)
        .assertions(
            global().responseTime().percentile(95).lt(1000),
            global().failedRequests().percent().lt(1.0)
        );
    }
}   
