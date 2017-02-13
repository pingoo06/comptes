package comptes.model.db.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import comptes.model.bo.RapproBO;
import comptes.model.db.entity.Bnp;
import comptes.model.db.entity.Bnp.OperationType;
import comptes.model.db.entity.Operation;
import comptes.util.MyDate;
import comptes.util.log.LogRappro;

public class RapproDAO extends DAO<RapproBO> {
	public ArrayList<RapproBO> rapproAuto() {
		ArrayList<RapproBO> myRapproBOList = new ArrayList<>();
		rapproche(myRapproBOList, "b.typeOpeBnp in ('ECH_PRET', 'PRLV') and  o.typeOpe = b.typeOpeBnp and o.montantOpe=b.montantBnp;");
		rapproche(myRapproBOList, "b.typeOpeBnp in ('VIR_EMIS', 'CB','RETRAIT') and  o.typeOpe = b.typeOpeBnp and o.montantOpe=b.montantBnp and b.dateBnpCalc = o.dateOpeLong");
		rapproche(myRapproBOList, "b.typeOpeBnp ='CHQ' and b.chqNumberBnp= o.typeOpe and  o.montantOpe=b.montantBnp");
		return myRapproBOList;
	}

	private void rapproche(ArrayList<RapproBO> myRapproBOList, String whereClause){
		Statement statement;
		try {
			statement = connection.createStatement();
			Operation myOperation;
			Bnp myBnp;
			String myLibTiers="";
			
			ResultSet rs = statement.executeQuery("SELECT * FROM  operation as o INNER JOIN bnp as b INNER JOIN tiers as t  on o.tiersId=t.id and o.etatOpe='NR' and " + whereClause );
			while (rs.next()) {
				myOperation = OperationDAO.operationFromRow(rs);
				myBnp = new Bnp (rs.getInt(11), new MyDate(rs.getString(12)),rs.getString(13),rs.getString(14), rs.getString(15),
						rs.getDouble(16),rs.getString(17),OperationType.valueOf(rs.getString(18)),new MyDate(rs.getLong(19)),rs.getString(20));
				myLibTiers=rs.getString(22);
				RapproBO myRapproBo = new RapproBO(myBnp,myOperation,myLibTiers);
				myRapproBOList.add(myRapproBo);
				
			}
		} catch (SQLException e) {
			LogRappro.logError("Fail rapproche", e);
		}
	}

	





	@Override
	public void create(RapproBO obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(RapproBO obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(RapproBO obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public RapproBO find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<RapproBO> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}