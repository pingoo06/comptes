package comptes.model.db.entity;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import comptes.util.MyDate;
import comptes.util.log.LogBnp;

public class Bnp {
	public enum OperationType {
		CHQ, PRLV, CB, RETRAIT, VIR_EMIS, VIR_RECU, ECH_PRET, REMISE_CHQ, DEPOT, OTHER
	}

	private int id = 0;
	private MyDate dateBnp;
	private String libCourtBnp = "";
	private String libTypeOpeBnp = "";
	private String libOpeBnp = "";
	private double montantBnp = 0;
	private String etatBnp = "";
	private OperationType typeOpeBnp;
	private MyDate dateBnpCalc;
	private String chqNumberBnp;



	public Bnp() {

	}

	public Bnp(int id, MyDate dateBnp, String libCourtBnp, String libTypeOpeBnp, String libOpeBnp, double montantBnp,
			String etatBnp) {
		this.id = id;
		this.dateBnp = dateBnp;
		this.libCourtBnp = libCourtBnp;
		this.libTypeOpeBnp = libTypeOpeBnp;
		this.libOpeBnp = libOpeBnp;
		this.montantBnp = montantBnp;
		this.etatBnp = etatBnp;
		update();
	}

	public Bnp(int id, MyDate dateBnp, String libCourtBnp, String libTypeOpeBnp, String libOpeBnp, double montantBnp,
			String etatBnp, OperationType typeOpeBnp, MyDate dateBnpCalc, String chqNumberBnp) {
		super();
		this.id = id;
		this.dateBnp = dateBnp;
		this.libCourtBnp = libCourtBnp;
		this.libTypeOpeBnp = libTypeOpeBnp;
		this.libOpeBnp = libOpeBnp;
		this.montantBnp = montantBnp;
		this.etatBnp = etatBnp;
		this.typeOpeBnp = typeOpeBnp;
		this.dateBnpCalc = dateBnpCalc;
		this.chqNumberBnp = chqNumberBnp;
	}

	public void update() {
		// cheque
		if ("CHEQUE".equals(libTypeOpeBnp)) {
			typeOpeBnp = OperationType.CHQ;
			chqNumberBnp = libOpeBnp.substring(2);
			dateBnpCalc = dateBnp;
		}
		// prelevement
		else if ("COMMISSIONS".equals(libTypeOpeBnp) || "PRLV SEPA".equals(libTypeOpeBnp)) {
			typeOpeBnp = OperationType.PRLV;
			dateBnpCalc = dateBnp;
		}
		// prelevement
		else if ("FACTURE CARTE".equals(libTypeOpeBnp)) {
			typeOpeBnp = OperationType.CB;
			Pattern p = Pattern.compile("DU ([0123][0-9][01][0-9][0-9][0-9]) .*");
			Matcher m = p.matcher(libOpeBnp);
			if (m.matches()) {
				MatchResult matchResult = m.toMatchResult();
				String extractedDate = matchResult.group(1);
				dateBnpCalc = new MyDate(extractedDate);
			}
		}
		// retrait
		else if ("RETRAIT DAB".equals(libTypeOpeBnp)) {
			typeOpeBnp = OperationType.RETRAIT;

			Pattern p = Pattern.compile("([0123][0-9]/[01][0-9]/[0-9][0-9]) .*");
			Matcher m = p.matcher(libOpeBnp);
			if (m.matches()) {
				MatchResult matchResult = m.toMatchResult();
				String extractedDate = matchResult.group(1);
				dateBnpCalc = new MyDate(extractedDate);
			}
		}
		// virement emis
		else if (libTypeOpeBnp.matches("VIR.*EMIS.*")) {
			typeOpeBnp = OperationType.VIR_EMIS;
			dateBnpCalc = dateBnp;
		}
		// virement recu
		else if (libTypeOpeBnp.matches("VIR.*RECU.*")) {
			typeOpeBnp = OperationType.VIR_RECU;
			dateBnpCalc = dateBnp;
		}
		// Echeance pret
		else if (libTypeOpeBnp.equals("ECHEANCE PRET")) {
			typeOpeBnp = OperationType.ECH_PRET;
			dateBnpCalc = dateBnp;
		}
		// DEPOT d ESPECES
		else if (libTypeOpeBnp.equals("VRST ESPECES AUTOMATE")) {
			typeOpeBnp = OperationType.DEPOT;
			dateBnpCalc = dateBnp;
		}
		// REMISE DE CHEQUE A REVOIR QUAND J EN AURAI FAIT UN
		else if (libTypeOpeBnp.equals("REMISE CHEQUES")) {
			typeOpeBnp = OperationType.REMISE_CHQ;
			dateBnpCalc = dateBnp;
		}

		else {
			typeOpeBnp = OperationType.OTHER;
			final JOptionPane frame;
			frame = new JOptionPane();
			JOptionPane.showMessageDialog(frame, libTypeOpeBnp, "Type opération inconnu dans le fichier de la BNP",
					JOptionPane.WARNING_MESSAGE);
			dateBnpCalc = dateBnp;
			LogBnp.logWarning("Type operation inconnu : " + libTypeOpeBnp);
		}
	}

	public MyDate getDateBnp() {
		return dateBnp;
	}

	public void setDateBnp(MyDate dateBnp) {
		this.dateBnp = dateBnp;
	}

	public String getLibCourtBnp() {
		return libCourtBnp;
	}

	public void setLibCourtBnp(String libCourtBnp) {
		this.libCourtBnp = libCourtBnp;
	}

	public String getLibTypeOpeBnp() {
		return libTypeOpeBnp;
	}

	public void setLibTypeOpeBnp(String libTypeOpeBnp) {
		this.libTypeOpeBnp = libTypeOpeBnp;
	}

	public String getLibOpeBnp() {
		return libOpeBnp;
	}

	public void setLibOpeBnp(String libOpeBnp) {
		this.libOpeBnp = libOpeBnp;
	}

	public double getMontantBnp() {
		return montantBnp;
	}

	public void setMontantBnp(double montantBnp) {
		this.montantBnp = montantBnp;
	}

	public String getEtatBnp() {
		return etatBnp;
	}

	public void setEtatBnp(String etatBnp) {
		this.etatBnp = etatBnp;
	}

	public OperationType getTypeOpeBnp() {
		return typeOpeBnp;
	}

	public void setType(OperationType type) {
		this.typeOpeBnp = type;
	}

	public MyDate getDateBnpCalc() {
		return dateBnpCalc;
	}

	public String getChqNumberBnp() {
		return chqNumberBnp;
	}

	public void setChqNumber(String chqNumberBnp) {
		this.chqNumberBnp = chqNumberBnp;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Bnp [id=" + id + ", dateBnp=" + dateBnp + ", libCourt=" + libCourtBnp + ", libTypeOpe=" + libTypeOpeBnp
				+ ", libOpe=" + libOpeBnp + ", montantBnp=" + montantBnp + ", etatBnp=" + etatBnp + ", type="
				+ typeOpeBnp + ", dateBnpCalc=" + dateBnpCalc + ", chqNumber=" + chqNumberBnp + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chqNumberBnp == null) ? 0 : chqNumberBnp.hashCode());
		result = prime * result + ((dateBnp == null) ? 0 : dateBnp.hashCode());
		result = prime * result + ((dateBnpCalc == null) ? 0 : dateBnpCalc.hashCode());
		result = prime * result + ((etatBnp == null) ? 0 : etatBnp.hashCode());
		result = prime * result + id;
		result = prime * result + ((libCourtBnp == null) ? 0 : libCourtBnp.hashCode());
		result = prime * result + ((libOpeBnp == null) ? 0 : libOpeBnp.hashCode());
		result = prime * result + ((libTypeOpeBnp == null) ? 0 : libTypeOpeBnp.hashCode());
		long temp;
		temp = Double.doubleToLongBits(montantBnp);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((typeOpeBnp == null) ? 0 : typeOpeBnp.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bnp other = (Bnp) obj;
		if (chqNumberBnp == null) {
			if (other.chqNumberBnp != null)
				return false;
		} else if (!chqNumberBnp.equals(other.chqNumberBnp))
			return false;
		if (dateBnp == null) {
			if (other.dateBnp != null)
				return false;
		} else if (!dateBnp.equals(other.dateBnp))
			return false;
		if (dateBnpCalc == null) {
			if (other.dateBnpCalc != null)
				return false;
		} else if (!dateBnpCalc.equals(other.dateBnpCalc))
			return false;
		if (etatBnp == null) {
			if (other.etatBnp != null)
				return false;
		} else if (!etatBnp.equals(other.etatBnp))
			return false;
		if (id != other.id)
			return false;
		if (libCourtBnp == null) {
			if (other.libCourtBnp != null)
				return false;
		} else if (!libCourtBnp.equals(other.libCourtBnp))
			return false;
		if (libOpeBnp == null) {
			if (other.libOpeBnp != null)
				return false;
		} else if (!libOpeBnp.equals(other.libOpeBnp))
			return false;
		if (libTypeOpeBnp == null) {
			if (other.libTypeOpeBnp != null)
				return false;
		} else if (!libTypeOpeBnp.equals(other.libTypeOpeBnp))
			return false;
		if (Double.doubleToLongBits(montantBnp) != Double.doubleToLongBits(other.montantBnp))
			return false;
		if (typeOpeBnp != other.typeOpeBnp)
			return false;
		return true;
	}



}