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
            global().failedRequests().percent().lt(1.0),      // Tỉ lệ lỗi < 1% 
            global().responseTime().percentile(95).lt(1000),  // P95 < 1000ms 
            global().responseTime().mean().lt(800)            // Trung bình < 800ms 
        );
    }
}   
