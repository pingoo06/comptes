package comptes.model.db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import comptes.model.bo.EcheancierBO;
import comptes.model.db.entity.Categorie;
import comptes.model.db.entity.Echeancier;
import comptes.model.db.entity.Tiers;
import comptes.util.MyDate;
import comptes.util.log.LogEcheancier;
import comptes.util.log.Logger;

public class EcheancierDAO extends DAO<Echeancier> {
	
	public void create(Echeancier myEcheancier) {
		PreparedStatement statement=null;
		LogEcheancier.logDebug("Dans try dans create echeancier dans DAO echeancier : ECHEANCIER ; '" +myEcheancier	+"'");
		try {
			
			statement = connection.prepareStatement("INSERT INTO echeancier (id,typeEch, tiersEchId, categEchId,  montantEch,dateEch, nbEch,dateEchLong ) VALUES(?,?,?,?,?,?,?,?)");
		
			statement.setString(2, myEcheancier.getTypeEch());
			statement.setInt(3, myEcheancier.getTiersEchId());
			statement.setInt(4, myEcheancier.getCategEchId());
			statement.setDouble(5, myEcheancier.getMontantEch());
			statement.setString(6, myEcheancier.getDateEch().toDbFormat());
			statement.setInt(7, myEcheancier.getNbEch());
			statement.setLong(8, myEcheancier.getDateEch().toLongValue());
			
			statement.executeUpdate();
//			System.out.println("dans Echeancier DAO create arrive après execute statement de create echeancier");	
			} catch (SQLException e) {
				System.out.println("SQL Exception dans echeancier DAO create "); 
				e.printStackTrace();
			}
			finally {
				try {
					statement.close();
				} catch (SQLException e) {
					System.out.println("SQL Exception dans Echeancier DAO create sur le statement Close "); 
					e.printStackTrace();
				}
			}
		}

		public Echeancier find(int id) {
				Echeancier myEcheancier = null;
				try {
					// System.out.println("debut try find Echeancier");
					// System.out.println("select unique Echeancier début try");
					Statement statement = connection.createStatement();
					ResultSet rs = statement.executeQuery("SELECT * FROM echeancier WHERE id = '" + id + "'");
					// System.out.println("rs = " + rs.getInt("id"));
					if (rs.next()) {
						myEcheancier = new Echeancier(rs.getInt("id"), rs.getString("typeEch"),rs.getInt("tiersEchId"), rs.getInt("categEchId") ,new MyDate(rs.getString("dateEch")) ,rs.getDouble("montantEch"),rs.getInt("nbEchrs"));
					}
				} catch (SQLException e) {
					System.out.println("SQL Exception dans Operation DAO find  ");
					e.printStackTrace();
				}
				return myEcheancier;
			}
			//

			public ArrayList<Echeancier> findAll() {
				Echeancier myEcheancier = null;
				ArrayList<Echeancier> myEcheancierList = null;
				try {
					myEcheancierList = new ArrayList<Echeancier>();
					// System.out.println("select all Operation début try");
					Statement statement = connection.createStatement();
					ResultSet rs = statement.executeQuery("SELECT * FROM echeancier");
					// System.out.println("rs = " + rs.getInt("id"));
					while (rs.next()) {
						myEcheancier = new Echeancier(rs.getInt("id"), rs.getString("typeEch"),rs.getInt("tiersEchId"), rs.getInt("categEchId") , new MyDate(rs.getString("dateEch")) ,rs.getDouble("montantEch"),rs.getInt("nbEchrs"));
						myEcheancierList.add(myEcheancier);
					}
					statement.close();

				} catch (SQLException e) {
					System.out.println("SQL Exception dans Operation DAO findAll "); 
					e.printStackTrace();
				}
				return myEcheancierList;
				}
	//j'en suis ici		
			
			//nico est ce ici qu'il fallait créer cette fonction ?
			public ArrayList<EcheancierBO> findAllEchBO() {
				EcheancierBO myEcheancierBO = null;
				Echeancier myEcheancier=null;
				Tiers myTiers = null;
				Categorie myCategorie = null;
				ArrayList<EcheancierBO> myEcheancierBOList = null;
				try {
					myEcheancierBOList = new ArrayList<EcheancierBO>();
					// System.out.println("select all EcheancierBO début try");
					Statement statement = connection.createStatement();
					ResultSet rs = statement.executeQuery("SELECT * FROM echeancier as e INNER JOIN tiers as t on e.tiersEchId = t.id INNER JOIN categorie as c on e.categEchId = c.id");
					while (rs.next()) {
						myEcheancier = new Echeancier(rs.getInt(1), rs.getString(2),rs.getInt(3), rs.getInt(4) , new MyDate(rs.getString(5)) ,rs.getDouble(6),rs.getInt(7));
						myEcheancierBO = new EcheancierBO(myEcheancier);
						myTiers=new Tiers(rs.getInt(9),rs.getString(10),rs.getString(11));
						myEcheancierBO.setTiersBo(myTiers);
						myCategorie=new Categorie(rs.getInt(12), rs.getString(13));
						myEcheancierBO.setCategorieBo(myCategorie);
						myEcheancierBOList.add(myEcheancierBO);
					}
					statement.close();
				} catch (SQLException e) {
					System.out.println("SQL Exception dans Echeancier DAO findAllEchBO "); 
					e.printStackTrace();
				}
				return myEcheancierBOList;
			}
			
			
//	
			public void update(Echeancier myEcheancier) {
				Statement statement = null;
				try {
					// System.out.println("debut try update operation");
					Logger.logDebug("myEcheancier" + myEcheancier);
					statement = connection.createStatement();
					statement.executeUpdate("UPDATE echeancier SET typeEch='" + myEcheancier.getTypeEch() + "',tiersEchId='"
							+ myEcheancier.getTiersEchId() + "',categEchId=" + myEcheancier.getCategEchId() + ",dateEch= '"
							+ myEcheancier.getDateEch().toDbFormat() + "',montantEch=" + myEcheancier.getMontantEch() + ", nbEch='"
							+ myEcheancier.getNbEch() + "',dateEchLong=" + myEcheancier.getDateEch().toLongValue()  
							+ " where Id=" +myEcheancier.getId());
					 System.out.println("dans try update echeancier : Update : UPDATE echeancier SET typeEch='" + myEcheancier.getTypeEch() + "',tiersEchId='"
								+ myEcheancier.getTiersEchId() + "',categEchId=" + myEcheancier.getCategEchId() + ",dateEch="
								+ myEcheancier.getDateEch().toDbFormat() + ",montantEch=" + myEcheancier.getMontantEch() + ", nbEch='"
								+ myEcheancier.getNbEch() + "',dateEchLong=" + myEcheancier.getDateEch().toLongValue()  
								+ " where Id=" +myEcheancier.getId());
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					try {
						statement.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}

			public void delete(Echeancier myEcheancier) {
				Statement statement = null;
				try {
					statement = connection.createStatement();
					statement.executeUpdate("Delete from echeancier where Id=" + myEcheancier.getId());
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					try {
						statement.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}

			}
		}
