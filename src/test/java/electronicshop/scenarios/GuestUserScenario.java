package electronicshop.scenarios;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;
import java.util.HashMap;

public class GuestUserScenario {

    public static ScenarioBuilder build() {
        // Tạo feeder cho chức năng tìm kiếm
        // Giả lập người dùng tìm các từ khóa khác nhau
        Iterator<Map<String, Object>> searchFeeder = Stream.generate(() -> {
            Map<String, Object> map = new HashMap<>();
            String[] keywords = { "phone", "camera", "computer", "earbud", "headphone" };
            int randomIndex = (int) (Math.random() * keywords.length);
            map.put("keyword", keywords[randomIndex]);
            return map;
        }).iterator();

        ScenarioBuilder scn = scenario("Guest User - Browse Products")

                // Bước 1: Vào trang chủ 
                .exec(http("Home Page API").get("http://localhost:3001/api/products?limit=10"))
                .pause(2) // Thời gian người dùng quan sát

                // Bước 2: Tìm kiếm sản phẩm và trích xuất ID ngẫu nhiên
                .feed(searchFeeder)
                .exec(http("Search Product: #{keyword}")
                        .get("http://localhost:3001/api/search?query=#{keyword}") 
                        .check(status().is(200))
                        .check(jsonPath("$[*].id").findRandom().saveAs("randomProdId")))
                .pause(1, 3) // Nghĩ ngẫu nhiên từ 1-3 giây

                // Bước 3: Xem chi tiết sản phẩm
                .exec(http("View Product Detail")
                        .get("http://localhost:3001/api/products/#{randomProdId}") 
                        .check(status().is(200))
                        // Kiểm tra xem tên sản phẩm có tồn tại trong response không để chắc chắn API đúng
                        .check(jsonPath("$.title").exists()));
        return scn;
    }
}
