package comptes.model.db.dao;

import java.sql.Connection;
import java.util.ArrayList;

import comptes.model.db.SqliteConnector;

public abstract class DAO<T> {

	Connection connection = SqliteConnector.getInstance();

	/**
	 * Permet de récupérer un objet via son ID
	 * 
	 * @param id
	 * @return
	 */
	public abstract T find(int id);

	/**
	 * Permet de créer une entrée dans la base de données par rapport à un objet
	 * 
	 * @param obj
	 */
	public abstract void create(T obj);

	/**
	 * Permet de mettre à jour les données d'une entrée dans la base * @param
	 * obj
	 */

public abstract void update(T obj);

	/**
	 * Permet la suppression d'une entrée de la base
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
