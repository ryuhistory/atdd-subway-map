package nextstep.subway.domain;

import static nextstep.subway.common.exception.errorcode.StatusErrorCode.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.springframework.util.ObjectUtils;

import nextstep.subway.common.exception.BusinessException;

public class Sections {

	@OneToMany(mappedBy = "line",
		cascade = {CascadeType.PERSIST, CascadeType.MERGE},
		orphanRemoval = true)
	@OrderBy("id asc")
	private List<Section> section = new ArrayList<>();

	public Sections() {
	}

	public List<Section> getSectionList() {
		return section;
	}

	public void add(Section newSection) {
		System.out.println("1111");
		nullValidation(newSection);
		validationOfRegistration(newSection);
		this.section
			.add(newSection);
	}

	public void remove(Station station) {
		nullValidation(station);
		validationOfDelete(station);
		section.removeIf(section -> section.getDownStation().getId() == station.getId());

	}

	public List<Station> getAllStationsBySection() {
		List<Station> stations = new ArrayList<>();
		section.forEach(station -> stations.addAll(station.getAllStations()));
		return stations;
	}

	private void validationOfRegistration(Section newSection) {

		if (this.section.size() <= 0) {
			return;
		}

		if (!getLastSection().isSameWithDownStation(newSection.getUpStation().getId())) {
			throw new BusinessException(INVALID_STATUS);
		}

		if (this.section
			.stream()
			.anyMatch(value -> value.isSameAnyOfStation(newSection.getDownStation().getId()))) {
			throw new BusinessException(INVALID_STATUS);
		}

	}

	public void validationOfDelete(Station station) {

		if (this.section.size() <= 1) {
			throw new BusinessException(INVALID_STATUS);
		}

		if (!getLastSection().isSameWithDownStation(station.getId())) {
			throw new BusinessException(INVALID_STATUS);
		}

	}

	private void nullValidation(Object object) {
		if (ObjectUtils.isEmpty(object)) {
			throw new BusinessException(INVALID_STATUS);
		}
	}

	private Section getLastSection() {
		return this.section.stream()
			.sorted(Comparator.comparing(Section::getId).reversed())
			.findFirst()
			.orElseThrow(() -> new BusinessException(INVALID_STATUS));
	}

}
