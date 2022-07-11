package nextstep.subway.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nextstep.subway.applicaion.dto.SelectionRequest;
import nextstep.subway.applicaion.dto.SelectionResponse;

@RestController
@RequestMapping("/lines")
public class SelectionController {

	@PostMapping(value = "{lineId}/selection")
	public ResponseEntity<Void> createSelection(@PathVariable long lineId,
		@RequestBody SelectionRequest selectionRequest) {
		return null;
	}

	@DeleteMapping(value = "{lineId}/selection/{stationId}")
	public ResponseEntity<Void> deleteSelection(@PathVariable long lineId, @PathVariable long stationId) {
		return null;
	}

	@GetMapping(value = "{lineId}/selection")
	public ResponseEntity<Void> getSelectionList(@PathVariable long lineId,
		@RequestBody SelectionRequest selectionRequest) {
		return null;
	}

	@GetMapping(value = "{lineId}/selection/{stationId}")
	public ResponseEntity<SelectionResponse> getSelection(@PathVariable long lineId, @PathVariable long stationId) {
		return null;
	}

}
