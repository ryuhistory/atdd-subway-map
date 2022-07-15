package nextstep.subway.applicaion;

import static nextstep.subway.common.exception.errorcode.EntityErrorCode.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nextstep.subway.applicaion.dto.LineRequest;
import nextstep.subway.applicaion.dto.LineResponse;
import nextstep.subway.applicaion.dto.SectionRequest;
import nextstep.subway.applicaion.dto.SectionResponse;
import nextstep.subway.applicaion.dto.StationResponse;
import nextstep.subway.common.exception.BusinessException;
import nextstep.subway.domain.Line;
import nextstep.subway.domain.LineRepository;
import nextstep.subway.domain.Section;
import nextstep.subway.domain.Station;
import nextstep.subway.domain.StationRepository;

@Service
public class LineService {

	private final LineRepository lineRepository;
	private final StationRepository stationRepository;

	public LineService(LineRepository lineRepository, StationRepository stationRepository) {
		this.lineRepository = lineRepository;
		this.stationRepository = stationRepository;
	}

	@Transactional
	public LineResponse createLines(LineRequest lineRequest) {
		Station upStation = getStation(lineRequest.getUpStationId());
		Station downStation = getStation(lineRequest.getDownStationId());

		Line line = lineRepository.save(lineRequest.toLine());
		line.add(upStation, downStation, lineRequest.getDistance());
		return createLineResponse(line);
	}

	public List<LineResponse> findAllLines() {
		return lineRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))
			.stream()
			.map(this::createLineResponse)
			.collect(Collectors.toList());

	}

	public LineResponse findLine(Long stationId) {
		return createLineResponse(lineRepository.findById(stationId)
			.orElseThrow(() -> new BusinessException(ENTITY_NOT_FOUND)));
	}

	@Transactional
	public void updateLine(long lineId, LineRequest lineRequest) {
		Line line = findLineById(lineId);
		line.updateStationLineInformation(lineRequest.getName(), lineRequest.getColor());
	}

	@Transactional
	public void deleteLine(Long stationId) {
		lineRepository.delete(lineRepository.findById(stationId)
			.orElseThrow(() -> new BusinessException(ENTITY_NOT_FOUND)));
	}

	private StationResponse createStationResponse(Station station) {
		return new StationResponse(
			station.getId(),
			station.getName()
		);
	}

	private LineResponse createLineResponse(Line line) {
		List<StationResponse> stationResponseList = line.getSection()
			.getAllStationsBySection()
			.stream()
			.map(this::createStationResponse)
			.collect(Collectors.toList());

		return new LineResponse(line.getId(),
			line.getName(),
			line.getColor(),
			stationResponseList);
	}

	/********************************************************************
	 * Station
	 *********************************************************************/
	private Station getStation(long stationId) {
		return stationRepository.findById(stationId)
			.orElseThrow(() -> new BusinessException(ENTITY_NOT_FOUND));
	}

	/********************************************************************
	 * Section
	 *********************************************************************/
	@Transactional
	public void createSection(long lineId, SectionRequest sectionRequest) {
		Line line = findLineById(lineId);
		Station upStation = getStation(sectionRequest.getUpStationId());
		Station downStation = getStation(sectionRequest.getDownStationId());

		line.add(upStation, downStation, sectionRequest.getDistance());

	}

	@Transactional
	public void deleteSection(long lineId, long stationId) {
		Line line = findLineById(lineId);
		Station downStation = getStation(stationId);
		line.remove(downStation);

	}

	public List<SectionResponse> getSectionList(long lineId) {
		Line line = findLineById(lineId);
		return line.getSection()
			.getSectionList()
			.stream()
			.map(this::createSectionResponse)
			.collect(Collectors.toList());
	}

	private Line findLineById(long lineId) {
		return lineRepository.findById(lineId)
			.orElseThrow(IllegalArgumentException::new);
	}

	private SectionResponse createSectionResponse(Section section) {
		return new SectionResponse(section.getId(),
			section.getUpStation().getId(),
			section.getDownStation().getId(),
			section.getDistance());
	}

}
