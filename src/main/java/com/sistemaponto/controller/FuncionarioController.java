package com.sistemaponto.controller;

import com.sistemaponto.domain.Funcionario;
import com.sistemaponto.service.FuncionarioService;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class FuncionarioController {

    @Autowired
    private FuncionarioService service;

    @Autowired
    private ModelMapper modelMapper;
    
    @GetMapping("/login")
    public ModelAndView home() {
    	ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login.html");
        return modelAndView;
    }
    
    @PostMapping("/cadastrar")
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@Valid @RequestBody Funcionario funcionario) {
    	service.salvar(funcionario);
    }
    
    @RequestMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public String user(HttpServletResponse response, Authentication authentication) {
    	return service.buscaPorUsername(authentication.getName()).info();
    }
    
    @GetMapping("/adm")
    @ResponseStatus(HttpStatus.OK)
    public String user_adm(HttpServletResponse response, Authentication authentication) {
    	//Tentar aqui
    	if (authentication != null)
    		return service.buscaPorUsername(authentication.getName()).info();
		return null;
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

    @PutMapping("/adm/atualizar/{id}")
    public void atualizarFuncionario(@PathVariable("id") Long id,
                                     @RequestBody Funcionario funcionario){
        service.buscaPorCodigo(id)
                .map(funcionario1 -> {
                    modelMapper.map(funcionario, funcionario1);
                    return Void.TYPE;})
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Funcionario nao encontrado"));
    }

    @GetMapping("/accesdenied")
    public String naopermitido() {
        return "Acesso não permitido!";
    }

}
