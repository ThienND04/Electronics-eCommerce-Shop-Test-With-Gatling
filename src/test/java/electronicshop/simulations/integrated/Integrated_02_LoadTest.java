package electronicshop.simulations.integrated;

import electronicshop.simulations.BaseSimulation;
import electronicshop.scenarios.*; 
import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;

public class Integrated_02_LoadTest extends BaseSimulation {
    {
        // Tổng thời gian test: 3 phút 
        int duration = 180;

        setUp(
            // Guest (70% - 70 uuser ) 
            GuestUserScenario.build().injectOpen(
                rampUsers(70).during(duration)
            ),

            // Buyer  (25% - 25 User) 
            // Delay 5s để guest vào trước, tạo tải nền cho server
            BuyerScenario.build().injectOpen(
                nothingFor(5), 
                rampUsers(25).during(duration)
            ),

            // admin (5 user)
            // Delay 20s để Buyer kịp tạo đơn hàng, Admin vào mới vào
            AdminScenario.build().injectOpen(
                nothingFor(20),
                rampUsers(5).during(duration) // Admin vào rải rác
            )
        )
        .protocols(httpProtocol)
        .assertions(
            // Tiêu chí chấp nhận chung cho toàn hệ thống
            global().failedRequests().percent().lt(2.0), // Lỗi < 2%
            global().responseTime().percentile(95).lt(2000) // P95 < 2s (Do chạy localhost nặng)
        );
    }
}