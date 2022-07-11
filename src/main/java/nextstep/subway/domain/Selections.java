package nextstep.subway.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

public class Selections {
	private final List<Selection> values;

	public Selections(List<Selection> selectionList) {
		values = new ArrayList<>(selectionList);
	}

	public boolean isDeletable(long stationId) {
		if (CollectionUtils.isEmpty(values)) {
			throw new RuntimeException();
		}

		Selection selection = values.stream()
			.reduce((first, second) -> second)
			.orElseThrow(RuntimeException::new);

		if (selection.getDownStationId() != stationId) {
			throw new RuntimeException();
		}

		return true;
	}

}
