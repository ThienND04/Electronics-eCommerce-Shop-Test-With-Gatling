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
    private static final FeederBuilder<Object> loggedInUserFeeder = listFeeder(List.of(
            Map.of("userId", "user_id_001_tu_database", "email", "test001@gmail.com"),
            Map.of("userId", "user_id_002_tu_database", "email", "test002@gmail.com"),
            Map.of("userId", "user_id_003_tu_database", "email", "test003@gmail.com")
    // ... Bạn nên export danh sách ID thật từ DB ra file CSV rồi dùng
    // csvFeeder("users.csv") sẽ tiện hơn
    )).circular();

    // Tạo feeder chứa các sản phẩm
    private static final Iterator<Map<String, Object>> searchFeeder = Stream.generate(() -> {
        Map<String, Object> map = new HashMap<>();
        String[] keywords = { "phone", "camera", "table", "computer", "earbud", "headphone" };
        int randomIndex = (int) (Math.random() * keywords.length);
        map.put("keyword", keywords[randomIndex]);
        return map;
    }).iterator();

    public static ScenarioBuilder build() {
        return scenario("Buyer Flow: Login & Purchase")
            .feed(loggedInUserFeeder)
            .feed(searchFeeder)
            
            // Bước 1: Vào trang chủ 
            .exec(http("Buyer: Home Page API").get("http://localhost:3001/api/products?page=1"))
            .pause(2) 
            // Bước 2: Tìm kiếm sản phẩm và trích xuất ID ngẫu nhiên
            .exec(http("Buyer: Search Product")
                    .get("http://localhost:3001/api/search?query=#{keyword}") 
                    .check(status().is(200))
                    .check(jsonPath("$[*].id").findRandom().optional().saveAs("randomProdId")))
            .pause(1, 3) // Nghĩ ngẫu nhiên từ 1-3 giây
            
            // nếu có sản phẩm -> đặt hàng
            .doIf(session -> session.contains("randomProdId")).then(
                exec(http("Buyer: Create Order Record")
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

                // Bước 4: Thêm sản phẩm vào đơn 
                .exec(http("Buyer: Add Product To Order")
                    .post("http://localhost:3001/api/order-product")
                    .header("Content-Type", "application/json")
                    .body(StringBody(
                        "{" +
                        "\"customerOrderId\": \"#{createdOrderId}\"," +
                        "\"productId\": \"#{randomProdId}\"," +
                        "\"quantity\": 1" +
                        "}"))
                    .asJson()
                    .check(status().is(201))
            ));
    }
}