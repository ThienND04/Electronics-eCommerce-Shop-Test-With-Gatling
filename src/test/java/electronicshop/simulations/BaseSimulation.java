package electronicshop.simulations;

import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import static io.gatling.javaapi.http.HttpDsl.*;

public class BaseSimulation extends Simulation {
    public static final HttpProtocolBuilder httpProtocol = http
        .baseUrl("http://localhost:3000") 
        .acceptHeader("application/json")
        .contentTypeHeader("application/json");
}
