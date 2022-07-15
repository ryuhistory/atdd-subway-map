package nextstep.subway.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Line {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String color;

	@Embedded
	private Sections sections = new Sections();

	public Line() {
	}

	public Line(String stationName, String stationColor) {
		this.name = stationName;
		this.color = stationColor;
	}

	public void add(Station upStations, Station downStation, long distance) {
		this.sections
			.add(new Section(this, upStations, downStation, distance));
	}

	public void remove(Station deleteStation) {
		this.sections
			.remove(deleteStation);
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getColor() {
		return color;
	}

	public Sections getSection() {
		return sections;
	}

	public void validationOfDelete(long sectionId) {
/*
		if (ObjectUtils.isEmpty(sectionId)) {
			throw new BusinessException(INVALID_STATUS);
		}
		if (this.section.size() <= MINIMUM_COUNT) {
			throw new BusinessException(INVALID_STATUS);
		}

		if (getLastSection().getDownStationId() != sectionId) {
			throw new BusinessException(INVALID_STATUS);
		}
*/
	}

	public void updateStationLineInformation(String stationName, String stationColor) {
		this.name = stationName;
		this.color = stationColor;
	}

}
