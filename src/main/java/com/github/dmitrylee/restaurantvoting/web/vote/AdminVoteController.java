package com.github.dmitrylee.restaurantvoting.web.vote;

import com.github.dmitrylee.restaurantvoting.model.Vote;
import com.github.dmitrylee.restaurantvoting.repository.VoteRepository;
import com.github.dmitrylee.restaurantvoting.to.VoteTo;
import com.github.dmitrylee.restaurantvoting.util.VoteUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
                .map(VoteUtil::getTo).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public VoteTo get(@PathVariable Integer id) {
        log.info("get vote id = {}", id);
        Vote vote = repository.findById(id).orElseThrow();
        return VoteUtil.getTo(vote);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        log.info("delete vote id = {}", id);
        repository.deleteExisted(id);
    }
}
