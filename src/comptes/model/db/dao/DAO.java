package comptes.model.db.dao;

import java.sql.Connection;
import java.util.ArrayList;

import comptes.model.db.SqliteConnector;

public abstract class DAO<T> {

	Connection connection = SqliteConnector.getInstance();

	/**
	 * Permet de r�cup�rer un objet via son ID
	 * 
	 * @param id
	 * @return
	 */
	public abstract T find(int id);

	/**
	 * Permet de cr�er une entr�e dans la base de donn�es par rapport � un objet
	 * 
	 * @param obj
	 */
	public abstract void create(T obj);

	/**
	 * Permet de mettre � jour les donn�es d'une entr�e dans la base * @param
	 * obj
	 */

public abstract void update(T obj);

	/**
	 * Permet la suppression d'une entr�e de la base
	 * 
	 * @param obj
	 */
	 public abstract void delete(T obj);

	/**
	 * Finds all Events in the database.
	 * 
	 * @return
	 */
	public abstract ArrayList<T> findAll();
	
}
