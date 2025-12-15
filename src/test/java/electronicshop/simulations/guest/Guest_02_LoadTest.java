package electronicshop.simulations.guest;

import static io.gatling.javaapi.core.CoreDsl.atOnceUsers;
import static io.gatling.javaapi.core.CoreDsl.constantUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.global;
import static io.gatling.javaapi.core.CoreDsl.rampUsers;

import electronicshop.scenarios.GuestUserScenario;
import electronicshop.simulations.BaseSimulation;

public class Guest_02_LoadTest extends BaseSimulation {
    {   
        setUp(
            GuestUserScenario.build().injectOpen(
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
