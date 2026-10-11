package metiers;

import entities.Option;
import entities.Etudiant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EtudiantBusiness {
    private static List<Etudiant> etudiants;
    private OptionBusiness optionBusiness = new OptionBusiness();

    public EtudiantBusiness() {
        // ✅ CORRECTION : On n'initialise la liste que si elle n'existe pas encore
        if (etudiants == null) {
            etudiants = new ArrayList<Etudiant>();
            etudiants.add(new Etudiant("I001", "Doe", "Jean", optionBusiness.getOptionByCode(1), 2023, "jean.doe@example.com"));
            etudiants.add(new Etudiant("I002", "Smith", "Alice", optionBusiness.getOptionByCode(1), 2022, "alice.smith@example.com"));
            etudiants.add(new Etudiant("I003", "Durand", "Pierre", optionBusiness.getOptionByCode(2), 2023, "pierre.durand@example.com"));
        }
    }

    // Ajouter un etudiant (Code du professeur)
    public boolean addEtudiant(Etudiant etudiant) {
        int codeOption = etudiant.getOption().getCodeOption();
        Option option = optionBusiness.getOptionByCode(codeOption);
        if (option != null) {
            etudiant.setOption(option);
            return etudiants.add(etudiant);
        }
        return false;
    }

    public Etudiant getEtudiantByIdentifiant(String identifiant) {
        for (Etudiant e : etudiants) {
            if (e.getIdentifiant().equals(identifiant)) {
                return e;
            }
        }
        return null;
    }

    public List<Etudiant> getEtudiantsByOption(Option option) {
        List<Etudiant> result = new ArrayList<>();
        for (Etudiant e : etudiants) {
            if (e.getOption() != null && e.getOption().getCodeOption() == option.getCodeOption()) {
                result.add(e);
            }
        }
        return result;
    }

    public List<Etudiant> getAllEtudiants() {
        return etudiants;
    }

    // Mettre à jour un etudiant
    public boolean updateEtudiant(String identifiant, Etudiant updatedEtudiant) {
        for (int i = 0; i < etudiants.size(); i++) {
            // ✅ Protection : on saute les éléments null
            if (etudiants.get(i) != null && etudiants.get(i).getIdentifiant().equals(identifiant)) {
                etudiants.set(i, updatedEtudiant);
                return true;
            }
        }
        return false;
    }

    public boolean deleteEtudiant(String identifiant) {
        Iterator<Etudiant> iterator = etudiants.iterator();
        while (iterator.hasNext()) {
            Etudiant e = iterator.next();
            if (e.getIdentifiant().equals(identifiant)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }
}