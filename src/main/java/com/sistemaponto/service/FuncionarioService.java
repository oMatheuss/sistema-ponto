package com.sistemaponto.service;

import com.sistemaponto.domain.Funcionario;
import com.sistemaponto.repository.FuncionarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.Iterable;
import java.util.Optional;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;
    
    @Autowired
    private PasswordEncoder encoder;

    public Funcionario salvar(Funcionario funcionario){
    	funcionario.setSenha(encoder.encode(funcionario.getSenha()));
        return funcionarioRepository.save(funcionario);
    }

    public Iterable<Funcionario> listarTodos(){
        return funcionarioRepository.findAll();
    }

    public Optional<Funcionario> buscaPorCodigo(Long codigo) {
        return funcionarioRepository.findById(codigo);
    }
    
    public Funcionario buscaPorUsername(String username) {
    	return funcionarioRepository.findByUsername(username);
    }
}
