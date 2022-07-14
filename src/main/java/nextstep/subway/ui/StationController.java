package nextstep.subway.ui;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import nextstep.subway.applicaion.StationService;
import nextstep.subway.applicaion.dto.StationRequest;
import nextstep.subway.applicaion.dto.StationResponse;
import nextstep.subway.common.response.CommonResponse;

@RestController
public class StationController {
	private StationService stationService;

	public StationController(StationService stationService) {
		this.stationService = stationService;
	}

	@PostMapping("/stations")
	public ResponseEntity<CommonResponse> createStation(@RequestBody StationRequest stationRequest) {
		StationResponse station = stationService.saveStation(stationRequest);
		return ResponseEntity.created(URI.create("/stations/" + station.getId())).body(CommonResponse.success(station));
	}

	@GetMapping(value = "/stations")
	public ResponseEntity<CommonResponse> showStations() {
		return ResponseEntity.ok().body(CommonResponse.success(stationService.findAllStations()));
	}

	@DeleteMapping("/stations/{id}")
	public ResponseEntity<CommonResponse> deleteStation(@PathVariable Long id) {
		stationService.deleteStationById(id);
		return ResponseEntity.ok(CommonResponse.success());
	}
}
