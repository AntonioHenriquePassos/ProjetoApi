package com.antonio.Api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.antonio.Api.models.CardType;
import com.antonio.Api.models.Type;



public interface CardTypeRepository extends JpaRepository<CardType, Integer> {
    Optional <CardType> findByTypeOfCard (Type typeOfCard);

}
