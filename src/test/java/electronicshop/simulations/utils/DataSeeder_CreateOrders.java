package electronicshop.simulations.utils;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import electronicshop.scenarios.BuyerScenario;
import electronicshop.simulations.BaseSimulation;

public class DataSeeder_CreateOrders extends BaseSimulation {

    {
        setUp(
            BuyerScenario.build()
                .injectOpen(
                    // Bơm 10.000 user trong 15 phút (900s)
                    // Tốc độ: ~11 request/giây 
                    rampUsers(10000).during(900)
                )
        ).protocols(httpProtocol);
    }
}