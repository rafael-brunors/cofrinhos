package com.cofrinhos.dao;

import java.util.List;

import com.cofrinhos.util.DAOException;

/**
 * Interface que define as assinaturas padrões das DAOs do sistema
 * @param <T> - Entidade que será persistida/pesquisada
 */
public interface IDAO <T> {
		/**
		 * Assinatura padrão para os métodos Inserir/Alterar
		 * @param model - entidade a ser inserida/alterada
		 * @throws DAOException
		 */
		void saveOrUpdate(T model) throws DAOException;
		
		/**
		 * Assinatura padrão para o m�todo Excluir
		 * @param model - entidade a ser excluida
		 * @throws DAOException
		 */
		void delete(T model) throws DAOException;
		
		/**
		 * Assinatura padrão para o que busca um registro pela PK
		 * @param model - entidade contendo a PK populada
		 */
		T findById(T filtro);

		/**
		 * Assinatura padrão para o que pesquisar por todos
		 * os registros
		 */
		List<T> findAll();
		
}