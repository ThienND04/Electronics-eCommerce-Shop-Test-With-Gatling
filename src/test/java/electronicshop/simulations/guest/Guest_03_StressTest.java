package electronicshop.simulations.guest;

import static io.gatling.javaapi.core.CoreDsl.atOnceUsers;
import static io.gatling.javaapi.core.CoreDsl.constantUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.global;
import static io.gatling.javaapi.core.CoreDsl.rampUsers;

import electronicshop.scenarios.GuestUserScenario;
import electronicshop.simulations.BaseSimulation;

public class Guest_03_StressTest extends BaseSimulation {
    {   
        setUp(
            GuestUserScenario.build().injectOpen(
                rampUsers(1000).during(120) // Đẩy cực mạnh 1000 user trong 120 giây
            )
        ).protocols(httpProtocol)
        .assertions(
            global().failedRequests().percent().lt(5.0), // Lỗi < 5%
            global().responseTime().percentile(95).lt(2000) // P95 < 2s 
        );
    }
}
