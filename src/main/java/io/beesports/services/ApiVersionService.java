package io.beesports.services;

import io.beesports.domain.dtos.commands.LinkChangelogsToApiVersionCommand;
import io.beesports.domain.entities.ApiVersion;
import io.beesports.domain.entities.Changelog;
import io.beesports.repositories.IApiVersionRepository;
import io.beesports.repositories.IChangelogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ApiVersionService {

    private final IApiVersionRepository apiVersionRepository;
    private final IChangelogRepository changelogRepository;

    @Autowired
    public ApiVersionService(IApiVersionRepository apiVersionRepository, IChangelogRepository changelogRepository) {
        this.apiVersionRepository = apiVersionRepository;
        this.changelogRepository = changelogRepository;
    }

    public List<ApiVersion> getAllApiVersions(){
        return apiVersionRepository.findAll();
    }

    public ApiVersion addApiVersion(String version){
        ApiVersion apiVersion = new ApiVersion();
        apiVersion.setId(UUID.randomUUID());
        apiVersion.setApiVersion(version);
        apiVersion.setChanges(new ArrayList<>());
        apiVersionRepository.save(apiVersion);
        return apiVersion;
    }

    public ApiVersion addChangelogsToApiVersion(LinkChangelogsToApiVersionCommand command){
        List<Changelog> changelogs = changelogRepository.findAllById(command.getChangelogIds());
        ApiVersion apiVersion = apiVersionRepository.getOne(command.getApiVersionId());

        List<Changelog> existingChangeLogs = apiVersion.getChanges();
        existingChangeLogs.addAll(changelogs);
        apiVersion.setChanges(existingChangeLogs);

        return apiVersionRepository.save(apiVersion);
    }

    public String deleteApiVersion(UUID id){
        apiVersionRepository.deleteById(id);
        return "ApiVersion with ID " + id + " deleted!";
    }

}
