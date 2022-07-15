package nextstep.subway.applicaion.dto;

import nextstep.subway.domain.Line;

public class LineRequest {
	private String name;
	private String color;
	private long upStationId;
	private long downStationId;
	private long distance;

	public Line toLine() {
		return new Line(this.name, this.color);
	}

	public String getName() {
		return name;
	}

	public String getColor() {
		return color;
	}

	public long getUpStationId() {
		return upStationId;
	}

	public long getDownStationId() {
		return downStationId;
	}

	public long getDistance() {
		return distance;
	}
}
