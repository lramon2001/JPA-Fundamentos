package com.lucasramon.jpa.ui;

import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

import com.lucasramon.jpa.models.Pessoa;
import com.lucasramon.jpa.services.impl.PessoaService;
import com.lucasramon.jpa.services.interfaces.CrudService;
import com.lucasramon.jpa.services.interfaces.PessoaBuscaPorNome;

public class Main {

	private static Scanner SCANNER = new Scanner(System.in);

	public static void main(String[] args) {
		int opcao = 0;

		while (opcao != 6) {
		String strOpcao =  JOptionPane.showInputDialog(null, 
				    "Escolha uma ação:\n"
					+ "1. Listar pessoas\n"
					+ "2. Inserir pessoas\n"
					+ "3. Atualizar pessoas\n"
					+ "4. Excluir pessoas\n"
					+ "5.Pesquisar pessoa por nome\n"
					+ "6. Sair\n"
					, "MENU", JOptionPane.QUESTION_MESSAGE);
			
			opcao = Integer.parseInt(strOpcao);
			switch (opcao) {
			case 1:
				listarPessoas();
				break;
			case 2:
				inserirPessoa();
				break;
			case 3:
				atualizarPessoa();
				break;
			case 4:
				deletarPessoa();
				break;
			case 5:
				pesquisarPessoaPorNome();
				break;
			case 6:
				opcao=6;
				break;
			default:
				JOptionPane.showInternalMessageDialog(null, "Opção Invalida", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
				break;

			}
		}
		JOptionPane.showInternalMessageDialog(null, "Tchau!!!", "Obrigado", JOptionPane.INFORMATION_MESSAGE);
	}

	private static void pesquisarPessoaPorNome() {
		
		String nomeASerPesquisado = JOptionPane.showInputDialog(null, "Digite o nome a ser pesquisado:", "Pesquisa de pessoa por nome", JOptionPane.QUESTION_MESSAGE);
		PessoaBuscaPorNome pessoaService = new PessoaService();
		pessoaService.searchByName(nomeASerPesquisado).forEach(pessoa -> {
			JOptionPane.showInternalMessageDialog(null,String.format("- (%d) %s %s - %d anos", pessoa.getId(), pessoa.getNome(),
					pessoa.getSobrenome(), pessoa.getIdade()) , "Pessoa Pesquisada", JOptionPane.INFORMATION_MESSAGE);
		});

	}

	private static void deletarPessoa() {
		String strId = JOptionPane.showInputDialog(null,"Digite o id da pessoa." ,"Remoção de pessoa" , JOptionPane.QUESTION_MESSAGE);
		int idPessoaASerRemovida = Integer.parseInt(strId);
		CrudService<Pessoa, Integer> pessoaService = new PessoaService();
		pessoaService.deleteById(idPessoaASerRemovida);
		JOptionPane.showInternalMessageDialog(null, "Pessoa removida com sucesso","Remoção de pessoa" , JOptionPane.INFORMATION_MESSAGE);

	}

	private static void atualizarPessoa() {
		String strIdPessoa = JOptionPane.showInputDialog(null, "Digite o id da pessoa a ser atualizada." , "Atualização de pessoa", JOptionPane.QUESTION_MESSAGE);
		int idPessoa = Integer.parseInt(strIdPessoa);
		CrudService<Pessoa, Integer> pessoaService = new PessoaService();
		Pessoa pessoaAtual = pessoaService.byId(idPessoa);
		if (pessoaAtual != null) {
			JOptionPane.showInternalMessageDialog(null,String.format("- Nome: %s\n", pessoaAtual.getNome())+
					String.format("- Sobrenome: %s\n", pessoaAtual.getSobrenome())+String.format("- Sobrenome: %s\n", pessoaAtual.getIdade())
					 ,"Atualização de pessoa", JOptionPane.INFORMATION_MESSAGE);
			
			pessoaAtual.setNome(JOptionPane.showInputDialog(null, "Digite o novo nome:", "Atualização de pessoa", JOptionPane.QUESTION_MESSAGE)); 
			pessoaAtual.setSobrenome(JOptionPane.showInputDialog(null, "Digite o novo sobrenome:", "Atualização de pessoa", JOptionPane.QUESTION_MESSAGE));
			String strIdade = JOptionPane.showInputDialog(null,"Digite a nova idade:" , "Atualização de pessoa", JOptionPane.QUESTION_MESSAGE);
			pessoaAtual.setIdade(Integer.parseInt(strIdade));
			pessoaService.update(pessoaAtual);
			JOptionPane.showInternalMessageDialog(null,"Pessoa atualizada com sucesso" , "Atualização de pessoa", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showInternalMessageDialog(null, "Não existem pessoas com este ID" , "Atualização de pessoa", JOptionPane.INFORMATION_MESSAGE);
		}

	}

	private static void inserirPessoa() {
		Pessoa novaPessoa = new Pessoa();
		novaPessoa.setNome(JOptionPane.showInputDialog(null, "Digite o nome:","Inclusão de pessoa" , JOptionPane.QUESTION_MESSAGE));
		novaPessoa.setSobrenome(JOptionPane.showInputDialog(null, "Digite o sobrenome:" ,"Inclusão de pessoa", JOptionPane.QUESTION_MESSAGE));
		novaPessoa.setIdade(Integer.parseInt(JOptionPane.showInputDialog(null,"Digite a idade:" , "Inclusão de pessoa", JOptionPane.QUESTION_MESSAGE)));
		CrudService<Pessoa, Integer> pessoaService = new PessoaService();
		Pessoa pessoa = pessoaService.insert(novaPessoa);
		
		//System.out.println(pessoa.getId());
		JOptionPane.showInternalMessageDialog(null,"Pessoa inserida com sucesso!" , "Inclusão de pessoa", JOptionPane.INFORMATION_MESSAGE);
	}

	private static void listarPessoas() {
		CrudService<Pessoa, Integer> pessoaService = new PessoaService();
	
		try {
			StringBuilder list = new StringBuilder();
			List<Pessoa> pessoas = pessoaService.all();
			pessoas.forEach(pessoa -> {
				list.append(String.format("- (%d) %s %s - %d anos\n", pessoa.getId(), pessoa.getNome(),
						pessoa.getSobrenome(), pessoa.getIdade()));
			});
			
			JOptionPane.showInternalMessageDialog(null,  list.toString(),"lista de pessoas cadastradas", JOptionPane.INFORMATION_MESSAGE);

			;
			if (pessoas.isEmpty()) {
				JOptionPane.showInternalMessageDialog(null, "Não existem pessoas cadastradas." ,"Listas de Pessoas" , JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			JOptionPane.showInternalMessageDialog(null,"Houve um erro ao utilizar a JPA "+e.getMessage(),  "Listas de Pessoas", JOptionPane.ERROR_MESSAGE);
		}
	}

}
