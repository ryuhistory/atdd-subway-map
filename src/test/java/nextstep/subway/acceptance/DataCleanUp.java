package nextstep.subway.acceptance;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.google.common.base.CaseFormat;

@Service
public class DataCleanUp {

	@PersistenceContext
	private EntityManager entityManager;
	private List<String> tableNames;

	@PostConstruct
	public void initialized() {
		tableNames = entityManager
			.getMetamodel()
			.getEntities()
			.stream()
			.filter(e -> e.getJavaType().getAnnotation(Entity.class) != null)
			.map(e -> CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, e.getName()))
			.collect(Collectors.toList());
	}

	@Transactional
	public void execute() {
		entityManager.flush();
		entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();

		for (String tableName : tableNames) {
			entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
		}

		entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
	}

}
