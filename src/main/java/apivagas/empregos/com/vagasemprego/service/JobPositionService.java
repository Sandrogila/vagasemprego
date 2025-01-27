package apivagas.empregos.com.vagasemprego.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import apivagas.empregos.com.vagasemprego.dto.jobPositionDTO;
import apivagas.empregos.com.vagasemprego.exception.ResourceNotFoundException;
import apivagas.empregos.com.vagasemprego.exception.UnauthorizedOperationException;
import apivagas.empregos.com.vagasemprego.model.JobPosition;
import apivagas.empregos.com.vagasemprego.model.User;
import apivagas.empregos.com.vagasemprego.repository.JobPositionRepository;
import apivagas.empregos.com.vagasemprego.model.Company;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobPositionService {

    private final JobPositionRepository jobRepository;
    private final UserService userService;
    private final CompanyService companyService;

    public List<JobPosition> getAllJobs() {
        return jobRepository.findAll();
    }

    public JobPosition getJobById(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vaga não encontrada"));
    }

    @Transactional
    public JobPosition createJob(jobPositionDTO jobDTO) {
        Company company = companyService.getCurrentCompany();
        
        JobPosition job = new JobPosition();
        job.setTitle(jobDTO.getTitle());
        job.setDescription(jobDTO.getDescription());
        job.setSalary(jobDTO.getSalary());
        job.setLocation(jobDTO.getLocation());
        job.setRequirements(jobDTO.getRequirements());
        job.setBenefits(jobDTO.getBenefits());
        job.setExpiresAt(jobDTO.getExpiresAt());
        job.setCompany(company);
        
        return jobRepository.save(job);
    }

    @Transactional
    public JobPosition updateJob(Long id, jobPositionDTO jobDTO) {
        JobPosition job = getJobById(id);
        Company company = companyService.getCurrentCompany();
        
        if (!job.getCompany().getId().equals(company.getId())) {
            throw new UnauthorizedOperationException("Você não tem permissão para atualizar esta vaga");
        }
        
        job.setTitle(jobDTO.getTitle());
        job.setDescription(jobDTO.getDescription());
        job.setSalary(jobDTO.getSalary());
        job.setLocation(jobDTO.getLocation());
        job.setRequirements(jobDTO.getRequirements());
        job.setBenefits(jobDTO.getBenefits());
        job.setExpiresAt(jobDTO.getExpiresAt());
        
        return jobRepository.save(job);
    }

    @Transactional
    public void deleteJob(Long id) {
        JobPosition job = getJobById(id);
        Company company = companyService.getCurrentCompany();
        
        if (!job.getCompany().getId().equals(company.getId())) {
            throw new UnauthorizedOperationException("Você não tem permissão para deletar esta vaga");
        }
        
        jobRepository.delete(job);
    }

    @Transactional
    public void applyForJob(Long jobId) {
        JobPosition job = getJobById(jobId);
        User user = userService.getCurrentUser();
        
        if (job.getCandidates().contains(user)) {
            throw new IllegalStateException("Você já se candidatou para esta vaga");
        }
        
        job.getCandidates().add(user);
        jobRepository.save(job);
    }
}
