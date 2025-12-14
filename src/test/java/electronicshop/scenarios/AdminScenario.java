package electronicshop.scenarios;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import io.gatling.javaapi.core.*;
import java.util.Map;
import java.util.List;

public class AdminScenario {

    // Tạo feeder chứa các tài khoản admin
    private static final FeederBuilder<Object> adminFeeder = listFeeder(List.of(
            Map.of("email", "admin01@gmail.com", "password", "adminpw123!"),
            Map.of("email", "admin02@gmail.com", "password", "adminpw123!"),
            Map.of("email", "admin03@gmail.com", "password", "adminpw123!")
        )).circular(); 

    public static ScenarioBuilder build() {
        return scenario("Admin Flow: Manage Orders & Dashboard")
            .feed(adminFeeder)
            
            // Bước 1: Login 
            .exec(http("Get CSRF Token")
                .get("/api/auth/csrf")
                .check(status().is(200))
                .check(jsonPath("$.csrfToken").saveAs("csrfToken"))
            )
            .exec(http("Admin Login")
                .post("/api/auth/callback/credentials")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .formParam("redirect", "false")
                .formParam("email", "#{email}")
                .formParam("password", "#{password}")
                .formParam("csrfToken", "#{csrfToken}")
                .formParam("callbackUrl", "http://localhost:3000/login")
                .formParam("json", "true")
                .check(status().is(200))
            )
            .pause(1)

            // Bước 2: Kiểm tra role
            .exec(http("Check Admin session")
                .get("/api/auth/session")
                .check(status().is(200))
                // Kiểm tra xem role có phải là "admin" không
                .check(jsonPath("$.user.role").is("admin")) 
            )
            .pause(2)

            // Bước 3: Vào dashboard tổng quan
            // Thường Admin sẽ vào xem dashboard đầu tiên
            .exec(http("Admin: Load dashboard")
                .get("/admin/dashboard") 
                .check(status().in(200, 304, 404)) 
            )
            .pause(2)

            // Bước 4: quản lý các sản phẩm
            .exec(http("Admin: Get All Products")
                .get("http://localhost:3001/api/products") 
                .check(status().is(200))
                .check(responseTimeInMillis().lt(1000)) // Mong đợi load dưới 1s
            )
            .pause(3)

            // Bước 5: Quản lý các đơn hàng
            .exec(http("Admin: Get All Orders")
                .get("http://localhost:3001/api/orders") 
                .check(status().is(200))
                // Kiểm tra xem có lấy được danh sách không
                // Lưu ID đơn hàng ngẫu nhiên để xem chi tiết (nếu có đơn)
                .check(jsonPath("$.orders[*].id").findRandom().optional().saveAs("randomOrderId"))
            )
            .pause(3)
            
            // Nếu có đơn hàng, thử xem chi tiết đơn đó
            .doIf(session -> session.contains("randomOrderId")).then(
                exec(http("Admin: View Order Detail")
                    .get("http://localhost:3001/api/orders/#{randomOrderId}")
                    .check(status().is(200))
                )
            )
            .pause(2)

            // Bước 6: Xem danh sách người dùng
            .exec(http("Admin: Get All Users")
                .get("http://localhost:3001/api/users") 
                .check(status().in(200)) 
            );
    }
}