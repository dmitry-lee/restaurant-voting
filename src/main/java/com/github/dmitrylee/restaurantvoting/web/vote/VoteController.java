package com.github.dmitrylee.restaurantvoting.web.vote;

import com.github.dmitrylee.restaurantvoting.error.IllegalRequestDataException;
import com.github.dmitrylee.restaurantvoting.model.Restaurant;
import com.github.dmitrylee.restaurantvoting.model.Vote;
import com.github.dmitrylee.restaurantvoting.repository.VoteRepository;
import com.github.dmitrylee.restaurantvoting.to.VoteTo;
import com.github.dmitrylee.restaurantvoting.util.VoteUtil;
import com.github.dmitrylee.restaurantvoting.util.validation.ValidationUtil;
import com.github.dmitrylee.restaurantvoting.web.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {

    public static final String REST_URL = "/api/votes";

    @Value("${app.params.vote.time-deadline}")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime deadline;

    private final VoteRepository repository;

    public VoteController(VoteRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public VoteTo get(@RequestParam Optional<LocalDate> date, @AuthenticationPrincipal AuthUser user) {
        return VoteUtil.getTo(repository.getByDateAndUser(date.orElseGet(LocalDate::now), user.getUser().getId()).orElseThrow());
    }

    @PostMapping
    public ResponseEntity<VoteTo> createWithLocation(@RequestParam Integer restaurantId, @AuthenticationPrincipal AuthUser user) {
        log.info("create vote for restaurant id = {}", restaurantId);
        Vote vote = new Vote(null, user.getUser(), LocalDate.now(), new Restaurant(restaurantId));
        try {
            Vote created = repository.save(vote);
            URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(REST_URL + "/{id}")
                    .buildAndExpand(created.getId()).toUri();
            return ResponseEntity.created(uri).body(VoteUtil.getTo(created));
        } catch (DataIntegrityViolationException e) {
            throw new IllegalRequestDataException("You have already voted today!");
        }
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestParam Integer restaurantId, @AuthenticationPrincipal AuthUser user) {
        log.info("update vote for restaurant id = {}", restaurantId);
        ValidationUtil.checkVotingTimeDeadline(deadline);
        Optional<Vote> optionalVote = repository.findById(id);
        if (optionalVote.isPresent()) {
            Vote vote = optionalVote.get();
            vote.setRestaurant(new Restaurant(restaurantId));
            repository.save(vote);
        } else {
            throw new IllegalRequestDataException("vote not found");
        }
    }
}
