package DHS.temporary.server.repository;

import DHS.temporary.server.domain.Form;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormRepository extends JpaRepository<Form, Long> {
	List<Form> findByProjectId(Long projectId);

}
