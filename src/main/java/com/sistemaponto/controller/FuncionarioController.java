package com.sistemaponto.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.sistemaponto.domain.Funcionario;
import com.sistemaponto.service.FuncionarioService;

@RestController
public class FuncionarioController {

    @Autowired
    private FuncionarioService service;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public String inicio(){
    	return "Api Sistema-Ponto";
    }
    
    @GetMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public String login(){
    	return "<html><head><meta charset='utf-8'></head><body><form action='/login/process' method='post'><input type='text' name='username'><br><input type='password' name='password'><br><input type='submit'></form></body></html>";
    }
    
    @PostMapping("/cadastro")
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@Valid @RequestBody Funcionario funcionario) {
    	service.salvar(funcionario);
    }
    
    @RequestMapping("/dados")
    @ResponseStatus(HttpStatus.OK)
    public String user(HttpServletResponse response, Authentication authentication) {
    	return service.buscaPorUsername(authentication.getName()).info();
    }
    
    @PutMapping("/dados/atualizar")
    public void atualizarFuncionario(HttpServletResponse response, Authentication authentication, 
    		@RequestBody Funcionario funcionario){
    	service.buscaPorUsernameO(authentication.getName()).map(funcionario1 -> {
    		modelMapper.map(funcionario, funcionario1);
    		return Void.TYPE;})
    	.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
        "Funcionario nao encontrado"));
    	service.salvar(funcionario);
    }
    
    @GetMapping("/adm/listarfuncionarios")
    @ResponseStatus(HttpStatus.OK)
    public List<Funcionario> listarFuncionario(){
    	return service.listarTodos();
    }

    @GetMapping("/adm/busca/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Funcionario buscarFuncionarioPorID(@PathVariable("id") Long id){
        return service.buscaPorCodigo(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Funcionario nao encontrado"));
    }

    @GetMapping("/accessdenied")
    public String naopermitido(HttpServletResponse response) {
    	if(response.containsHeader("username")) {
    		return "Acesso não permitido para " + response.getHeader("username") + "!";
    	}
        return "Acesso não permitido!";
    }
    
}
