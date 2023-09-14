package com.github.dmitrylee.restaurantvoting.mapper;

import com.github.dmitrylee.restaurantvoting.model.Vote;
import com.github.dmitrylee.restaurantvoting.to.VoteTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VoteMapper {

    VoteMapper INSTANCE = Mappers.getMapper(VoteMapper.class);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "restaurantId", source = "restaurant.id")
    VoteTo voteToVoteDto(Vote vote);
}
