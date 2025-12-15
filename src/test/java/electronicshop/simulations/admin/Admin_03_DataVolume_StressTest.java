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
            global().responseTime().percentile(95).lt(2000), // Cho phép load chậm tới 2 giây
            global().failedRequests().percent().lt(1.0) // Lỗi < 1%
        );
    }
}