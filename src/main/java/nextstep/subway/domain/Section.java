package nextstep.subway.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Section {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "line_id")
	private Line line;

	@ManyToOne
	@JoinColumn(name = "up_station_id")
	private Station upStation;
	@ManyToOne
	@JoinColumn(name = "down_station_id")
	private Station downStation;
	private Long distance;

	public Section() {
	}

	public Section(Line line, Station upStation, Station downStation, Long distance) {
		this.line = line;
		this.upStation = upStation;
		this.downStation = downStation;
		this.distance = distance;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Section section = (Section)o;
		return Objects.equals(id, section.id) && Objects.equals(line, section.line)
			&& Objects.equals(upStation, section.upStation) && Objects.equals(downStation,
			section.downStation) && Objects.equals(distance, section.distance);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, line, upStation, downStation, distance);
	}

	public Long getId() {
		return id;
	}

	public Station getUpStation() {
		return upStation;
	}

	public Station getDownStation() {
		return downStation;
	}

	public Long getDistance() {
		return distance;
	}

	public List<Station> getAllStations() {
		return Arrays.asList(upStation, downStation);
	}

	public boolean isSameWithDownStation(long stationId) {
		return this.downStation.getId() == stationId;
	}

	public boolean isSameAnyOfStation(long stationId) {
		return this.downStation.getId() == stationId || this.upStation.getId() == stationId;
	}
}
