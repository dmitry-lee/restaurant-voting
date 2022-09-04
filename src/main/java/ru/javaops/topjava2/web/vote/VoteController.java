package ru.javaops.topjava2.web.vote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.topjava2.error.IllegalRequestDataException;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.repository.VoteRepository;
import ru.javaops.topjava2.to.VoteTo;
import ru.javaops.topjava2.util.validation.ValidationUtil;
import ru.javaops.topjava2.web.AuthUser;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {

    public static final String REST_URL = "/api/votes";

    @Value("${app.params.vote.time-threshold}")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    public LocalTime timeThreshold;

    private final VoteRepository repository;

    public VoteController(VoteRepository repository) {
        this.repository = repository;
    }

    @PostMapping("{restaurantId}")
    public ResponseEntity<VoteTo> createWithLocation(@PathVariable Integer restaurantId, @AuthenticationPrincipal AuthUser user) {
        log.info("create vote for restaurant id = {}", restaurantId);
        Vote vote = new Vote(null, user.getUser(), LocalDate.now(), new Restaurant(restaurantId));
        Vote created = repository.save(vote);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uri).body(new VoteTo(created));
    }

    @PutMapping("{restaurantId}")
    public void update(@PathVariable Integer restaurantId, @AuthenticationPrincipal AuthUser user) {
        log.info("update vote for restaurant id = {}", restaurantId);
        ValidationUtil.checkVotingTimeThreshold(timeThreshold);
        Optional<Vote> optionalVote = repository.getByDateAndUser(LocalDate.now(), user.id());
        if (optionalVote.isPresent()) {
            Vote vote = optionalVote.get();
            vote.setRestaurant(new Restaurant(restaurantId));
            repository.save(vote);
        } else {
            throw new IllegalRequestDataException("vote not found");
        }
    }
}
