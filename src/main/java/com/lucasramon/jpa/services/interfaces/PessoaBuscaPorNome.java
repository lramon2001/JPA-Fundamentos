package com.lucasramon.jpa.services.interfaces;

import java.util.List;

import com.lucasramon.jpa.models.Pessoa;

public interface PessoaBuscaPorNome extends CrudService<Pessoa, Integer>{
	
	List<Pessoa> searchByName(String name);

}
