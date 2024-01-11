package com.todocine.dao.impl;

import com.todocine.dao.MovieDAORepo;
import com.todocine.dto.MovieDTO;
import com.todocine.model.Paginator;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;
@Repository
public class MovieDAORepoImpl implements MovieDAORepo {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<MovieDTO> findByUserId(String userId) {
        MatchOperation matchStage = Aggregation.match(Criteria.where("usuarios").in(new ObjectId(userId)));
        ProjectionOperation projectStage = Aggregation.project().andExclude("usuarios");

        Aggregation aggregation
                = Aggregation.newAggregation(matchStage, projectStage);

        AggregationResults<MovieDTO> output
                = mongoTemplate.aggregate(aggregation, "Movie", MovieDTO.class);

        return output.getMappedResults();
    }
}
