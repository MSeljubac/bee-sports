package io.beesports.services;

import io.beesports.domain.entities.Changelog;
import io.beesports.repositories.IChangelogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ChangelogService {

    private final IChangelogRepository changelogRepository;

    @Autowired
    public ChangelogService(IChangelogRepository changelogRepository) {
        this.changelogRepository = changelogRepository;
    }

    public Changelog createChangelog(Changelog changelog){
        if(changelog.getId() == null){
            changelog.setId(UUID.randomUUID());
        }

        return changelogRepository.save(changelog);
    }

    public List<Changelog> createChangelogsBulk(List<Changelog> changelogs){
        changelogs.forEach(changelog -> {
            if(changelog.getId() == null){
                changelog.setId(UUID.randomUUID());
            }
        });

        return changelogRepository.saveAll(changelogs);
    }

    public String deleteChangelog(UUID id){
        changelogRepository.deleteById(id);
        return "Changelog with ID " + id + " deleted!";
    }

    public List<Changelog> getAllChangelogs(){
        return changelogRepository.findAll();
    }

}
