package nextstep.subway.ui;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nextstep.subway.applicaion.LineService;
import nextstep.subway.applicaion.dto.LineRequest;
import nextstep.subway.applicaion.dto.LineResponse;
import nextstep.subway.applicaion.dto.SectionRequest;
import nextstep.subway.applicaion.dto.SectionResponse;
import nextstep.subway.common.response.CommonResponse;

@RestController
public class LineController {
	private LineService lineService;

	public LineController(LineService lineService) {
		this.lineService = lineService;
	}

	@PostMapping("/lines")
	public ResponseEntity<CommonResponse> createLine(@RequestBody LineRequest LineRequest) {
		LineResponse LineResponse = lineService.createLines(LineRequest);
		return ResponseEntity.created(URI.create("/lines/" + LineResponse.getId()))
			.body(CommonResponse.success(LineResponse));
	}

	@GetMapping(value = "/lines")
	public ResponseEntity<CommonResponse> showAllStationsLines() {
		return ResponseEntity.ok().body(CommonResponse.success(lineService.findAllLines()));
	}

	@GetMapping("/lines/{id}")
	public ResponseEntity<CommonResponse> showLine(@PathVariable Long id) {
		return ResponseEntity.ok().body(CommonResponse.success(lineService.findLine(id)));
	}

	@PutMapping("/lines/{id}")
	public ResponseEntity<CommonResponse> updateLine(@PathVariable Long id,
		@RequestBody LineRequest LineRequest) {
		lineService.updateLine(id, LineRequest);
		return ResponseEntity.ok(CommonResponse.success());
	}

	@DeleteMapping("/lines/{id}")
	public ResponseEntity<CommonResponse> deleteLine(@PathVariable Long id) {
		lineService.deleteLine(id);
		return ResponseEntity.ok(CommonResponse.success());
	}

	/**
	 * Sections
	 */
	@PostMapping(value = "/lines/{lineId}/section")
	public ResponseEntity<CommonResponse> createSection(@PathVariable long lineId,
		@RequestBody SectionRequest sectionRequest) {
		lineService.createSection(lineId, sectionRequest);
		return ResponseEntity.created(URI.create("/lines/" + lineId + "/section"))
			.body(CommonResponse.success());
	}

	@DeleteMapping(value = "/lines/{lineId}/section")
	public ResponseEntity<CommonResponse> deleteSection(@PathVariable long lineId,
		@RequestParam("stationId") long stationId) {
		lineService.deleteSection(lineId, stationId);
		return ResponseEntity.ok().body(CommonResponse.success());
	}

	@GetMapping(value = "/lines/{lineId}/section")
	public ResponseEntity<CommonResponse<List<SectionResponse>>> getSectionList(@PathVariable long lineId) {
		return ResponseEntity.ok().body(CommonResponse.success(lineService.getSectionList(lineId)));
	}

}
