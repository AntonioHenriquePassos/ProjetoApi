package com.antonio.Api.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import models.Cards;

public interface CardsRepository extends JpaRepository<Cards, UUID> {

}


