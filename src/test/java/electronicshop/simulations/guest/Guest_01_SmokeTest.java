package electronicshop.simulations.guest;

import static io.gatling.javaapi.core.CoreDsl.atOnceUsers;
import static io.gatling.javaapi.core.CoreDsl.global;

import electronicshop.scenarios.GuestUserScenario;
import electronicshop.simulations.BaseSimulation;

public class Guest_01_SmokeTest extends BaseSimulation {
    {   
        setUp(
            GuestUserScenario.build().injectOpen(atOnceUsers(1))
        ).protocols(httpProtocol)
        .assertions(
            global().failedRequests().percent().is(0.0) // Lỗi bắt buộc phải là 0%
        );
    }
}
