package nextstep.subway.applicaion;

import static nextstep.subway.common.exception.errorcode.EntityErrorCode.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nextstep.subway.applicaion.dto.SectionRequest;
import nextstep.subway.applicaion.dto.SectionResponse;
import nextstep.subway.common.exception.BusinessException;
import nextstep.subway.domain.Section;
import nextstep.subway.domain.SectionRepository;
import nextstep.subway.domain.Sections;

@Service
public class SectionService {

	private final SectionRepository sectionRepository;

	public SectionService(SectionRepository sectionRepository) {
		this.sectionRepository = sectionRepository;
	}

	@Transactional
	public SectionResponse createSection(long lineId, SectionRequest sectionRequest) {
		Sections sections = new Sections(sectionRepository.findByLineIdOrderById(lineId));
		sections.validationOfRegistration(sectionRequest.getUpStationId(), sectionRequest.getDownStationId());

		return createSectionResponse(sectionRepository.save(sectionRequest.toSection(lineId)));
	}

	@Transactional
	public void deleteSection(long lineId, long stationId) {
		Sections sections = new Sections(sectionRepository.findByLineIdOrderById(lineId));
		sections.validationOfDelete(stationId);
		sectionRepository.delete(sections.getLastSection());
	}

	public List<SectionResponse> getSectionList(long lineId) {
		return sectionRepository.findByLineIdOrderById(lineId)
			.stream()
			.map(this::createSectionResponse)
			.collect(Collectors.toList());
	}

	public SectionResponse getSection(long sectionId) {
		return createSectionResponse(sectionRepository.findById(sectionId)
			.orElseThrow(() -> new BusinessException(ENTITY_NOT_FOUND)));
	}

	private SectionResponse createSectionResponse(Section section) {
		return new SectionResponse(section.getId(),
			section.getUpStationId(),
			section.getDownStationId(),
			section.getDistance());
	}

}
