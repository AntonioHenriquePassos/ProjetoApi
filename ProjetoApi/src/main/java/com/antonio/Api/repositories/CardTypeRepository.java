package com.antonio.Api.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import models.CardType;

public interface CardTypeRepository extends JpaRepository<CardType, UUID> {

}
