package electronicshop.simulations.admin;

import electronicshop.scenarios.AdminScenario;
import electronicshop.simulations.BaseSimulation;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class Admin_01_SmokeTest extends BaseSimulation {

    {
        setUp(
            AdminScenario.build().injectOpen(atOnceUsers(1))
        ).protocols(httpProtocol);
    }
}