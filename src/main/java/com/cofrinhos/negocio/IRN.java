package com.cofrinhos.negocio;

import java.util.List;

import com.cofrinhos.util.DAOException;
import com.cofrinhos.util.RNException;

/**
 * Interface que define as assinaturas padrões das RNs do sistema
 * @param <T> - Entidade que será persistida/pesquisada
 */
public interface IRN<T> {

	/**
	 * Assinatura padrão para os métodos Inserir/Alterar
	 * @param model - entidade a ser inserida/alterada
	 * @throws RNException
	 * @throws DAOException 
	 */
	void saveOrUpdate(T model) throws RNException;
	
	/**
	 * Assinatura padrão para o método Excluir
	 * @param model - entidade a ser excluida
	 * @throws RNException
	 * @throws DAOException 
	 */
	void delete(T model) throws RNException;
	
	/**
	 * Assinatura padrão para o que busca um registro pela PK
	 * @param filtro - entidade contendo a PK populada
	 */
	T findById(T filtro);
	
	/**
	 * Assinatura padrão para o que pesquisar por todos
	 * os registros
	 */
	List<T> findAll();
}
