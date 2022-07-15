package nextstep.subway.acceptance;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.jpa.repository.JpaRepository;

import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AcceptanceTest {
	static final String SIN_BOONDANG_LINE = "신분당선";
	static final String LINE2 = "2호선";
	static final String DOGOK_STATION = "도곡역";
	static final String SOO_SEO_STATION = "수서역";
	static final String SAMSUNG_STATION = "삼성역";
	static final String GANGNAM_STATION = "강남역";
	static final String LINE_COLOR_YELLOW = "bg-yellow-600";
	static final String LINE_COLOR_BLUE = "bg-blue-600";
	static final String LINE_COLOR_GREEN = "bg-green-600";

	static final int DISTANCE_1 = 10;

	@LocalServerPort
	int port;

	@Autowired
	List<JpaRepository> jpaRepositories;

	@Autowired
	DataCleanUp dataCleanUp;

	@BeforeEach
	void setup() {
		RestAssured.port = port;
		dataCleanUp.execute();
	}
}
