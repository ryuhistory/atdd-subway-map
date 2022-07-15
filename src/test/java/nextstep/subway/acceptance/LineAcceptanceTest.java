package nextstep.subway.acceptance;

import static nextstep.subway.acceptance.StationAcceptanceTest.*;
import static nextstep.subway.common.exception.errorcode.EntityErrorCode.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;

@DisplayName("지하철 노선 테스트")
public class LineAcceptanceTest extends AcceptanceTest {

	int stationId1;
	int stationId2;
	int stationId3;
	int stationId4;

	@BeforeEach
	void setupStation() {
		stationId1 = 지하철역_생성(DOGOK_STATION);
		stationId2 = 지하철역_생성(SOO_SEO_STATION);
		stationId3 = 지하철역_생성(SAMSUNG_STATION);
		stationId4 = 지하철역_생성(GANGNAM_STATION);
	}

	/**
	 * When 지하철 노선을 생성하면
	 * Then 지하철 노선 목록 조회 시 생성한 노선을 찾을 수 있다
	 */
	@DisplayName("지하철노선_생성")
	@Test
	void createLine() {
		//when
		지하철_노선_생성(SIN_BOONDANG_LINE, LINE_COLOR_YELLOW, stationId1, stationId2, DISTANCE_1);

		//then
		assertThat(지하철_노선_전체_조회()).containsAnyOf(SIN_BOONDANG_LINE);

	}

	/**
	 * Given 2개의 지하철 노선을 생성하고
	 * When 지하철 노선 목록을 조회하면
	 * Then 지하철 노선 목록 조회 시 2개의 노선을 조회할 수 있다.
	 */
	@DisplayName("지하철노선_목록_조회")
	@Test
	void getLines() {
		//given
		지하철_노선_생성(SIN_BOONDANG_LINE, LINE_COLOR_YELLOW, stationId1, stationId2, DISTANCE_1);
		지하철_노선_생성(LINE2, LINE_COLOR_GREEN, stationId3, stationId4, DISTANCE_1);

		//when
		List<String> names = 지하철_노선_전체_조회();

		//then
		assertThat(names).hasSize(2)
			.containsExactly(SIN_BOONDANG_LINE, LINE2);
	}

	/**
	 * Given 지하철 노선을 생성하고
	 * When 생성한 지하철 노선을 조회하면
	 * Then 생성한 지하철 노선의 정보를 응답받을 수 있다.
	 */
	@DisplayName("지하철노선_조회")
	@Test
	void getLine() {
		//given
		long stationId = 지하철_노선_생성(SIN_BOONDANG_LINE, LINE_COLOR_YELLOW, stationId1, stationId2, DISTANCE_1);

		//when
		ExtractableResponse response = 지하철_노선_조회_BY_ID(stationId, HttpStatus.OK);

		//then
		assertEquals(SIN_BOONDANG_LINE, response.jsonPath().get("data.name"));
	}

	/**
	 * Given 지하철 노선을 생성하고
	 * When 생성한 지하철 노선을 수정하면
	 * Then 해당 지하철 노선 정보는 수정된다
	 */
	@DisplayName("지하철노선_수정")
	@Test
	void updateLine() {
		//given
		long stationId = 지하철_노선_생성(SIN_BOONDANG_LINE, LINE_COLOR_YELLOW, stationId1, stationId2, DISTANCE_1);

		//when
		지하철_노선_수정(stationId, SAMSUNG_STATION, LINE_COLOR_BLUE);

		//then
		ExtractableResponse response = 지하철_노선_조회_BY_ID(stationId, HttpStatus.OK);
		assertEquals(SAMSUNG_STATION, response.jsonPath().get("data.name"));
		assertEquals(LINE_COLOR_BLUE, response.jsonPath().get("data.color"));

	}

	/**
	 * Given 지하철 노선을 생성하고
	 * When 생성한 지하철 노선을 삭제하면
	 * Then 해당 지하철 노선 정보는 삭제된다
	 */
	@DisplayName("지하철노선_삭제")
	@Test
	void deleteLine() {
		//given
		long stationId = 지하철_노선_생성(SIN_BOONDANG_LINE, LINE_COLOR_YELLOW, stationId1, stationId2, DISTANCE_1);

		//when
		지하철_노선_삭제(stationId);

		//then
		ExtractableResponse response = 지하철_노선_조회_BY_ID(stationId, HttpStatus.OK);
		assertEquals(ENTITY_NOT_FOUND.toString(), response.jsonPath().get("errorCode"));

	}

	static Map<String, Object> 지하철노선_생성_파라미터생성(String stationName, String stationColor, long upStationId,
		long downStationId, long distance) {
		Map<String, Object> requestParameter = new HashMap<>();
		requestParameter.put("name", stationName);
		requestParameter.put("color", stationColor);
		requestParameter.put("upStationId", upStationId);
		requestParameter.put("downStationId", downStationId);
		requestParameter.put("distance", distance);

		return requestParameter;
	}

	private Map<String, Object> 지하철라인_수정_파라미터생성(String stationName, String stationColor) {
		Map<String, Object> requestParameter = new HashMap<>();
		requestParameter.put("name", stationName);
		requestParameter.put("color", stationColor);
		return requestParameter;
	}

	static int 지하철_노선_생성(String stationName, String stationColor, long upStationId, long downStationId,
		long distance) {
		return RestAssured.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(지하철노선_생성_파라미터생성(stationName, stationColor, upStationId, downStationId, distance))
			.when().post("/lines")
			.then().log().all()
			.statusCode(HttpStatus.CREATED.value())
			.extract()
			.jsonPath().getInt("data.id");
	}

	private void 지하철_노선_수정(long stationId, String stationName, String stationColor) {
		RestAssured.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(지하철라인_수정_파라미터생성(stationName, stationColor))
			.when().put("/lines/" + stationId)
			.then().log().all()
			.statusCode(HttpStatus.OK.value());
	}

	private void 지하철_노선_삭제(long stationId) {
		RestAssured.given().log().all()
			.when().delete("/lines/" + stationId)
			.then().log().all()
			.statusCode(HttpStatus.OK.value());
	}

	private List<String> 지하철_노선_전체_조회() {
		return RestAssured.given().log().all()
			.when().get("/lines")
			.then().log().all()
			.statusCode(HttpStatus.OK.value())
			.extract().jsonPath().getList("data.name", String.class);
	}

	private ExtractableResponse 지하철_노선_조회_BY_ID(Long id, HttpStatus httpStatus) {

		return RestAssured.given().log().all()
			.when().get("/lines/" + id)
			.then().log().all()
			.statusCode(httpStatus.value())
			.extract();
	}
}
