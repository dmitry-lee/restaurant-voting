package com.github.dmitrylee.restaurantvoting.repository;

import com.github.dmitrylee.restaurantvoting.model.Vote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface VoteRepository extends BaseRepository<Vote> {

    @Query("select v from Vote v where v.date=?1 and v.user.id=?2")
    Optional<Vote> getByDateAndUser(LocalDate date, int id);
}
