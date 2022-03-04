package com.antonio.Api.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import models.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount, UUID> {

}
