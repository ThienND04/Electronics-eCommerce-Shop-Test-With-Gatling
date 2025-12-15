package electronicshop.simulations.admin;

import electronicshop.scenarios.AdminScenario;
import electronicshop.scenarios.BuyerScenario;
import electronicshop.simulations.BaseSimulation;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class Admin_03_DataVolume_StressTest extends BaseSimulation {

    {
        setUp(
            AdminScenario.build()
                .injectOpen(atOnceUsers(10))
        ).protocols(httpProtocol)
        .assertions(
            global().failedRequests().percent().lt(5.0), // Lỗi < 5%
            global().responseTime().percentile(95).lt(2000) // P95 < 2s 
        );
    }
}