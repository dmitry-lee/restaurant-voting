package ru.javaops.topjava2.web.vote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.repository.VoteRepository;
import ru.javaops.topjava2.to.VoteTo;
import ru.javaops.topjava2.web.AuthUser;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping(value = AdminVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminVoteController {

    public final static String REST_URL = "/api/admin/votes";

    private final VoteRepository repository;

    public AdminVoteController(VoteRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<VoteTo> getAll() {
        log.info("get all votes");
        return repository.findAll().stream()
                .map(VoteTo::new).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public VoteTo get(@PathVariable Integer id) {
        log.info("get vote id = {}", id);
        Vote vote = repository.findById(id).orElseThrow();
        return new VoteTo(vote);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        log.info("delete vote id = {}", id);
        repository.deleteExisted(id);
    }
}
