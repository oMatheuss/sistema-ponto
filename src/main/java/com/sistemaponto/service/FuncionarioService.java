package com.sistemaponto.service;

import com.sistemaponto.domain.Funcionario;
import com.sistemaponto.repository.FuncionarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;
    
    @PersistenceContext
	private EntityManager entityManager;
    
    @Autowired
    private PasswordEncoder encoder;

    public Funcionario salvar(Funcionario funcionario){
    	funcionario.setSenha(encoder.encode(funcionario.getSenha()));
        return funcionarioRepository.save(funcionario);
    }

    public List<Funcionario> listarTodos(){
        return funcionarioRepository.findAll();
    }

    public Optional<Funcionario> buscaPorCodigo(Long codigo) {
        return funcionarioRepository.findById(codigo);
    }
    
    public Funcionario buscaPorUsername(String username) throws NoResultException {
	    return entityManager
				.createQuery("SELECT obj FROM Funcionario obj WHERE obj.username LIKE :target",
						Funcionario.class)
				.setParameter("target", username)
				.getSingleResult();
    }
    
    public String getName(Long cod) throws NoResultException {
    	return entityManager
				.createQuery("SELECT obj.nome FROM Funcionario obj WHERE obj.codigo LIKE :target",
						String.class)
				.setParameter("target", cod)
				.getSingleResult();
    }
}
