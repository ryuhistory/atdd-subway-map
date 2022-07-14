package nextstep.subway.ui;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nextstep.subway.applicaion.SectionService;
import nextstep.subway.applicaion.dto.SectionRequest;
import nextstep.subway.applicaion.dto.SectionResponse;
import nextstep.subway.common.response.CommonResponse;

@RestController
@RequestMapping("/lines")
public class SectionController {

	private SectionService sectionService;

	public SectionController(SectionService sectionService) {
		this.sectionService = sectionService;
	}

	@PostMapping(value = "{lineId}/section")
	public ResponseEntity<CommonResponse> createSection(@PathVariable long lineId,
		@RequestBody SectionRequest sectionRequest) {
		SectionResponse sectionResponse = sectionService.createSection(lineId, sectionRequest);
		return ResponseEntity.created(URI.create("/lines/" + lineId + "/section"))
			.body(CommonResponse.success(sectionResponse));
	}

	@DeleteMapping(value = "{lineId}/section")
	public ResponseEntity<CommonResponse> deleteSection(@PathVariable long lineId,
		@RequestParam("stationId") long stationId) {
		sectionService.deleteSection(lineId, stationId);
		return ResponseEntity.ok().body(CommonResponse.success());
	}

	@GetMapping(value = "{lineId}/section")
	public ResponseEntity<CommonResponse<List<SectionResponse>>> getSectionList(@PathVariable long lineId) {
		return ResponseEntity.ok().body(CommonResponse.success(sectionService.getSectionList(lineId)));
	}

	@GetMapping(value = "/section/{sectionId}")
	public ResponseEntity<CommonResponse> getSection(@PathVariable long sectionId) {
		return ResponseEntity.ok().body(CommonResponse.success(sectionService.getSection(sectionId)));
	}

}
