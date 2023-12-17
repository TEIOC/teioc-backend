package dev.astranfalio.teioc.service;

import dev.astranfalio.teioc.entity.InternEntity;
import dev.astranfalio.teioc.entity.PathwayAnswerEntity;
import dev.astranfalio.teioc.entity.PathwayEntity;
import dev.astranfalio.teioc.repository.InternRepository;
import dev.astranfalio.teioc.repository.PathwayAnswerRepository;
import dev.astranfalio.teioc.repository.PathwayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeleteInactiveInternsService {

    private final InternRepository internRepository;
    private final PathwayRepository pathwayRepository;
    private final PathwayAnswerRepository pathwayAnswerRepository; // Add this repository

    @Autowired
    public DeleteInactiveInternsService(
            InternRepository internRepository,
            PathwayRepository pathwayRepository,
            PathwayAnswerRepository pathwayAnswerRepository) {
        this.internRepository = internRepository;
        this.pathwayRepository = pathwayRepository;
        this.pathwayAnswerRepository = pathwayAnswerRepository;
    }

    public void deleteInactiveInterns() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);

        Date oneMonthAgo = calendar.getTime();

        // Fetch all interns
        List<InternEntity> interns = internRepository.findAll();

        // Filter inactive interns
        List<InternEntity> inactiveInterns = interns.stream()
                .filter(intern -> !intern.isStatus() && intern.getLastConnection() != null && intern.getLastConnection().before(oneMonthAgo))
                .collect(Collectors.toList());

        // Find and delete pathways for inactive interns
        for (InternEntity intern : inactiveInterns) {
            List<PathwayEntity> pathways = pathwayRepository.findAllByInternId(intern.getId());
            pathwayRepository.deleteAll(pathways);

            // Delete related PathwayAnswerEntity records
            List<PathwayAnswerEntity> pathwayAnswers = pathwayAnswerRepository.findByInternId(intern.getId());
            pathwayAnswerRepository.deleteAll(pathwayAnswers);
        }

        // Delete inactive interns
        internRepository.deleteAll(inactiveInterns);
    }
}



