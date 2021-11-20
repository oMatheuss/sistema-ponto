package com.sistemaponto.repository;

import com.sistemaponto.domain.Funcionario;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface FuncionarioRepository extends CrudRepository<Funcionario, Long> {
	
	public Funcionario findByEmail(String email);
	public Funcionario findByUsername(String username);
}
