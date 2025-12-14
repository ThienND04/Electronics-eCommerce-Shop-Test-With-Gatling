package electronicshop.simulations.admin;

import electronicshop.scenarios.AdminScenario;
import electronicshop.simulations.BaseSimulation;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class Admin_01_SmokeTest extends BaseSimulation {
    public static final HttpProtocolBuilder httpProtocol = http
        .baseUrl("http://localhost:3000") 
        .acceptHeader("application/json")
        .contentTypeHeader("application/json");

    {
        setUp(
            AdminScenario.build().injectOpen(atOnceUsers(1))
        ).protocols(httpProtocol);
    }
}