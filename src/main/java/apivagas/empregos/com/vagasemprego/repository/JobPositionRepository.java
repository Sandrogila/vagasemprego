package apivagas.empregos.com.vagasemprego.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import apivagas.empregos.com.vagasemprego.model.JobPosition;

import java.util.List;

@Repository
public interface JobPositionRepository extends JpaRepository<JobPosition, Long> {
    List<JobPosition> findByCompanyId(Long companyId);
}
