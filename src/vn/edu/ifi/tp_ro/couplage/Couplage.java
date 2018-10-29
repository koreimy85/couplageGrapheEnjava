package vn.edu.ifi.tp_ro.couplage;

import java.util.ArrayList;
import vn.edu.ifi.tp_ro.fichier.ReadFichier;
import vn.edu.ifi.tp_ro.graphe.Graphe;
import vn.edu.ifi.tp_ro.noeud.Noeud;

public class Couplage {
	public static Graphe creeReseau(String fichier) {
		Graphe graphe;
		ArrayList<Integer> creeNoeud = new ArrayList<Integer>();
		if (!fichier.isEmpty()) {
			graphe = new Graphe();
			int[][] matrice = null;
			ReadFichier taskfile = new ReadFichier(fichier);
			matrice = taskfile.readFichier();
			int nbnoeud = 0;
			while (nbnoeud < matrice.length) {
				if (!creeNoeud.contains(nbnoeud)) {
					Noeud newNoeud = new Noeud(nbnoeud);
					Noeud sucNoeud;
					for (int j = 0; j < matrice[nbnoeud].length; j++) {
						if (matrice[nbnoeud][j] != 0 && !creeNoeud.contains(matrice[nbnoeud][j])) {
							sucNoeud = new Noeud(matrice[nbnoeud][j]);
							creeNoeud.add(matrice[nbnoeud][j]);
							newNoeud.setSuccesseurs(sucNoeud, 1);
						} else if (creeNoeud.contains(matrice[nbnoeud][j])) {
							sucNoeud = graphe.rechercheGraphe(matrice[nbnoeud][j]);
							newNoeud.setSuccesseurs(sucNoeud, 1);
						}
					}
					graphe.setNoeuds(newNoeud);
				} else {
					Noeud newNoeud = graphe.rechercheGraphe(nbnoeud);
					Noeud sucNoeud;
					for (int j = 0; j < matrice[nbnoeud].length; j++) {
						if (matrice[nbnoeud][j] != 0 && !creeNoeud.contains(matrice[nbnoeud][j])) {
							sucNoeud = new Noeud(matrice[nbnoeud][j]);
							creeNoeud.add(matrice[nbnoeud][j]);
							newNoeud.setSuccesseurs(sucNoeud, 1);
						} else if (creeNoeud.contains(matrice[nbnoeud][j])) {
							sucNoeud = graphe.rechercheGraphe(matrice[nbnoeud][j]);
							newNoeud.setSuccesseurs(sucNoeud, 1);
						}
					}
					graphe.setNoeuds(newNoeud);
				}

				nbnoeud = nbnoeud + 1;
			}
		} else {
			graphe = null;
		}

		return graphe;
	}

	public static void main(String[] args) {
		String fichier = "/home/brahim/eclipse-workspace/graphe-couplage/data/tache2.txt";
		Graphe graphe = Couplage.creeReseau(fichier);
		graphe.ff(graphe.getNoeuds().get(0), graphe.getNoeuds().get(graphe.getNoeuds().size() - 1));

		System.out.println("nombre de chemin à inverser :"
				+ graphe.ff(graphe.getNoeuds().get(0), graphe.getNoeuds().get(graphe.getNoeuds().size() - 1)));
		Graphe.parcoursGraphe(graphe);

	}

}
