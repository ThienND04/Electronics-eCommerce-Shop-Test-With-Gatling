package electronicshop.scenarios;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import io.gatling.javaapi.core.*;
import java.util.Map;
import java.util.stream.Stream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class BuyerScenario {

    // Tạo Feeder chứa tài khoản test
    private static final FeederBuilder<Object> userFeeder = listFeeder(List.of(
            Map.of("email", "test001@gmail.com", "password", "Password123!"),
            Map.of("email", "test002@gmail.com", "password", "Password123!"),
            Map.of("email", "test003@gmail.com", "password", "Password123!")
        )).circular(); // Dùng vòng tròn (hết thì quay lại đầu)

    // Tạo feeder chứa các sản phẩm
    private static final Iterator<Map<String, Object>> searchFeeder = Stream.generate(() -> {
            Map<String, Object> map = new HashMap<>();
            String[] keywords = { "phone", "camera", "computer", "earbud", "headphone" };
            int randomIndex = (int) (Math.random() * keywords.length);
            map.put("keyword", keywords[randomIndex]);
            return map;
        }).iterator();

    public static ScenarioBuilder build() {
        return scenario("Buyer Flow: Login & Purchase")
                .feed(userFeeder)
                .feed(searchFeeder)

                // Buóc 1: Lấy CSRF token 
                .exec(http("Get CSRF Token")
                        .get("/api/auth/csrf")
                        .check(status().is(200))
                        .check(jsonPath("$.csrfToken").saveAs("csrfToken")) 
                )
                .pause(1)

                // Bước 2: Gửi yêu cầu đăng nhập
                .exec(http("Login Credentials")
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
                .pause(2)

                // Bước 3: Kiểm tra phiên làm việc  (session)
                .exec(http("Check Session")
                        .get("/api/auth/session")
                        .check(status().is(200))
                        .check(jsonPath("$.user.email").saveAs("userEmail"))
                        .check(jsonPath("$.user.id").saveAs("userId"))
                        .check(bodyString().saveAs("sessionResponse")))
                .pause(2)

                // Bước 4: Tìm kiếm sản phẩm và trích xuất ID ngẫu nhiên
                .exec(http("Search Product: #{keyword}")
                        .get("http://localhost:3001/api/search?query=#{keyword}") 
                        .check(status().is(200))
                        .check(jsonPath("$[*].id").findRandom().saveAs("randomProdId")))
                .pause(1, 3) // Nghĩ ngẫu nhiên từ 1-3 giây

                // Bước 5: Tạo đơn hàng
                .exec(http("Create Order Record")
                        .post("http://localhost:3001/api/orders") 
                        .header("Content-Type", "application/json")
                        .body(StringBody("{" +
                                "\"name\": \"TestUser\"," +
                                "\"lastname\": \"Gatling\"," +
                                "\"phone\": \"0123456789\"," +
                                // Random email để tránh 409
                                "\"email\": \"rnd-#{randomUuid()}@test.com\"," +
                                "\"adress\": \"Xuan Thuy\"," +
                                "\"apartment\": \"Room 404\"," +
                                "\"city\": \"Hanoi\"," +
                                "\"company\": \"UETeej\"," +
                                "\"country\": \"Vietnam\"," +
                                // Thêm randomUuid() để tránh lỗi 409 Duplicate
                                "\"orderNotice\": \"Order-#{randomUuid()}\"," +
                                "\"postalCode\": \"10000\"," +
                                "\"status\": \"pending\"," +
                                "\"total\": 261," +
                                "\"userId\": \"#{userId}\"" +
                                "}"))
                        .asJson()
                        .check(status().in(200, 201))
                        .check(bodyString().saveAs("createOrderResponse"))
                        .check(jsonPath("$.id").saveAs("createdOrderId")))

                .pause(1)

                // Bước 6: Thêm sản phẩm vào đơn
                .exec(http("Add Product To Order")
                        .post("http://localhost:3001/api/order-product")
                        .header("Content-Type", "application/json")
                        .body(StringBody(
                                "{" +
                                        "\"customerOrderId\": \"#{createdOrderId}\"," +
                                        "\"productId\": \"#{randomProdId}\"," +
                                        "\"quantity\": 1" +
                                        "}"))
                        .asJson()
                        .check(status().is(201)));
    }
}