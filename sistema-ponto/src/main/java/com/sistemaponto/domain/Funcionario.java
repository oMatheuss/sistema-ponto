package com.sistemaponto.domain;

import static javax.persistence.GenerationType.AUTO;
import static javax.persistence.EnumType.STRING;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.sistemaponto.security.model.Authority;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Funcionario implements Serializable {

	
	private static final long serialVersionUID = 5282847683204788424L;
	
	@Id
    @GeneratedValue(strategy = AUTO)
    private Long codigo;
    
	@Column(name = "username", nullable = false, unique = true)
    private String username;
	
	@Column(name = "senha", nullable = false )
    private String senha;

    @Column(name = "nome")
    private String nome;

    @Column(name = "email")
    private String email;
    
    @Column(name = "authorities", nullable = false)
    @MapKeyEnumerated(STRING)
    private HashSet<Authority> authorities;

    public Funcionario(String username, String senha, String nome, String email) {
    	this.username = username;
    	this.senha = senha;
        this.nome = nome;
        this.email = email;
        this.authorities.add(Authority.USER);
    }

    public Funcionario() {
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

	public HashSet<Authority> getAuthorities() {
		return authorities;
	}
	
	public void setAuthorities(HashSet<Authority> authorities) {
		this.authorities = authorities;
	}

	public String info() {
		return String.format("Usuario: %s, Nome: %s, Email: %s, Nivel de Acesso: %s",
				getUsername(), getNome(), getEmail(), getAuthorities());
	}
	
	public Set<GrantedAuthority> toRole() {
    	Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		for (Authority r : authorities) {
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + r.name()));
		}
		return grantedAuthorities;
    }
}
