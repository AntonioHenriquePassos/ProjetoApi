package com.antonio.Api.repositories;

import com.antonio.Api.models.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;






@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long>{
	
	
}

// existsBy não está funcionando, ver se inserindo dados funciona
