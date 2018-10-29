package vn.edu.ifi.tp_ro.graphe;

import java.util.ArrayList;
import java.util.LinkedList;
import vn.edu.ifi.tp_ro.fichier.ReadFichier;
import vn.edu.ifi.tp_ro.noeud.Noeud;

public class Graphe {

	protected int nbNoeuds;
	protected ArrayList<Noeud> noeuds;

	public Graphe() {
		nbNoeuds = 0;
		noeuds = new ArrayList<Noeud>();
	}

	public Graphe(Noeud[] nds) {
		nbNoeuds = nds.length;
		noeuds = new ArrayList<Noeud>(nbNoeuds);
		for (int i = 0; i < nbNoeuds; i++) {
			noeuds.add(i, nds[i]);
		}
	}

	public int getNbNoeuds() {
		return nbNoeuds;
	}

	public void setNbNoeuds(int nbNoeuds) {
		this.nbNoeuds = nbNoeuds;
	}

	public ArrayList<Noeud> getNoeuds() {
		return noeuds;
	}

	public void setNoeuds(Noeud noeuds) {
		this.setNbNoeuds(this.getNbNoeuds() + 1);
		this.noeuds.add(noeuds);
	}

	public void setNoeuds(ArrayList<Noeud> noeuds) {
		if (this.getNoeuds().size() == 0) {
			this.noeuds = noeuds;
		} else {
			for (int i = 0; i < noeuds.size(); i++) {
				this.setNoeuds(noeuds.get(i));
			}
		}

	}

	public static ArrayList<Noeud> parcoursGraphe(Graphe g) {
		LinkedList<Noeud> Q = new LinkedList<Noeud>();
		ArrayList<Noeud> S = new ArrayList<Noeud>();
		if (g.getNbNoeuds() > 0) {
			Q.add(g.getNoeuds().get(0));
			while (!Q.isEmpty()) {
				Noeud v = Q.pop();

				System.out.print("Noeud ID : " + v.getId() + " à pour successeur(s) noeud(s) :");
				for (int i = 0; i < v.getNbVoisins(); i++) {
					System.out.print(v.getSuccesseurs().get(i).getId() + " ");
					// System.out.print(" poids: "+v.getArcs().get(i)+" ");
					if (!Q.contains(v.getSuccesseurs().get(i)) && !S.contains(v.getSuccesseurs().get(i))) {
						Q.add(v.getSuccesseurs().get(i));
					}
				}
				System.out.println(" ");
				S.add(v);
			}

		}

		for (int i = 0; i < S.size(); i++) {
			// SSystem.out.print(S.get(i).getId()+" ");
		}
		System.out.println("\n");
		return S;
	}

	public LinkedList<Noeud> cheminBFS(Noeud s, Noeud t) {
		LinkedList<Noeud> chemin = new LinkedList<Noeud>();
		ArrayList<Noeud> visite = new ArrayList<Noeud>();
		LinkedList<Noeud> candidate = new LinkedList<Noeud>();
		if (s.compareTo(t) == 0) {
			System.out.println(" egalite entre " + s.getId() + " et " + t.getId());
			chemin.push(s);
		} else {
			boolean see = false;
			candidate.add(s);
			while (!candidate.isEmpty() && see == false) {
				Noeud v = candidate.pop();
				if (v.compareTo(t) != 0) {
					for (int i = 0; i < v.getSuccesseurs().size(); i++) {
						if (candidate.contains(v.getSuccesseurs().get(i)) == false
								&& !visite.contains(v.getSuccesseurs().get(i))) {
							candidate.add(v.getSuccesseurs().get(i));
						}
					}
					visite.add(v);
				} else {
					see = true;
				}
			}
			if (see == false && candidate.isEmpty()) {
				chemin = null;
			} else if (see == true) {
				for (int i = 0; i < visite.size(); i++) {
					for (int j = 0; j < visite.get(i).getSuccesseurs().size(); j++) {
					}
				}
				chemin.add(t);
				if (t.getId() != 9) {
					System.out.print("\nLa taches:" + t.getId() + " est affecté à la personne ");
				}
				Noeud m = t;
				int k = 0;
				boolean debut = false;
				while (k < visite.size() && debut == false) {
					Noeud v = visite.get(k);

					int j = 0;
					boolean trouve = false;
					while (j < v.getSuccesseurs().size() && trouve == false) {
						if (v.getSuccesseurs().get(j).compareTo(m) == 0) {
							trouve = true;
						} else {
							j = j + 1;
						}
					}
					if (trouve == true) {
						k = 0;
						m = v;
						if (v.getId() != 0) {
							System.out.print("  " + v.getId());
						}
						chemin.add(v);
						if (v.getId() == 0) {
							debut = true;
						}
					} else {
						k = k + 1;
					}

				}

			}
		}
		System.out.print(" \n\n");
		return chemin;
	}

	public static void courtCheminGraphe(Graphe g, Noeud s, Noeud t) {
		Graphe sg;
		ArrayList<Noeud> sl = null;
		if (Graphe.rechercheGraphe(g, s) == 1 && Graphe.rechercheGraphe(g, t) == 1) {
			int indexS = g.getIndexNoeud(s);
			Noeud[] tabNoeud = new Noeud[g.noeuds.size() - indexS];
			for (int i = indexS; i < tabNoeud.length; i++) {
				tabNoeud[i] = g.noeuds.get(i);
			}
			sg = new Graphe(tabNoeud);
			sl = Graphe.SousRechercheGraphe(sg, t);
			int taille = sl.size() - 1;
			Noeud v = t;
			System.out.print("\n-----La personne:" + v.getId());
			while (taille > 0) {
				int i = 0;
				while (!sg.noeuds.get(i).getSuccesseurs().contains(v)) {
					// System.out.println("Ne contient pas "+v.getId());
					i = i + 1;
				}
				if (sg.noeuds.get(i).getSuccesseurs().contains(v)) {
					v = sg.noeuds.get(i);
				}
				taille = i;
			}
		}
	}

	// retourne l'index dun noeud dans la liste des noeud du graphe
	private int getIndexNoeud(Noeud s) {
		// TODO Auto-generated method stub
		int index = 0;
		boolean trouver = false;
		while (index < this.noeuds.size() && !trouver) {
			if (this.noeuds.get(index).compareTo(s) != 0) {
				index++;
			} else {
				trouver = true;
			}
		}
		return index;
	}

	public static ArrayList<Noeud> SousRechercheGraphe(Graphe g, Noeud t) {
		// la FIFO des noeuds a parcourir
		LinkedList<Noeud> Q = new LinkedList<Noeud>();
		// liste de noeuds deja parcourus
		ArrayList<Noeud> S = new ArrayList<Noeud>();
		int trouver = -1;
		// int indexNoeud = 0 ;
		if (g.nbNoeuds > 0) {
			Q.add(g.noeuds.get(0));
			while (!Q.isEmpty() && trouver == -1) {
				Noeud v = Q.pop();
				if (v.compareTo(t) != 0) {
					System.out.println("Different :" + v.getId() + " " + t.getId());
					for (int i = 0; i < v.getNbVoisins(); i++) {
						if (!Q.contains(v.getSuccesseurs().get(i)) && !S.contains(v.getSuccesseurs().get(i))) {
							Q.add(v.getSuccesseurs().get(i));
						}
					}
					S.add(v);
				} else {
					trouver = 1;
				}
			}
			if (trouver == -1) {
				System.out.println("N existe pas ");
			} else {
				// System.out.println("Exist");
				System.out.println("Egale: " + t.getId());
			}

		}
		// System.out.println("les noeuds du graphe sont : ");
		for (int i = 0; i < S.size(); i++) {
			System.out.print(S.get(i).getId() + "kkkkkk ");
		}
		return S;
	}

	public Noeud rechercheGraphe(int id) {
		// la FIFO des noeuds a parcourir
		LinkedList<Noeud> Q = new LinkedList<Noeud>();
		// liste de noeuds deja parcourus
		ArrayList<Noeud> S = new ArrayList<Noeud>();
		Noeud trouver = null;
		// int indexNoeud = 0 ;
		if (this.getNbNoeuds() > 0) {
			Q.add(this.getNoeuds().get(0));
			while (!Q.isEmpty() && trouver == null) {
				Noeud v = Q.pop();
				if (v.getId() != id) {
					for (int i = 0; i < v.getNbVoisins(); i++) {
						if (!Q.contains(v.getSuccesseurs().get(i)) && !S.contains(v.getSuccesseurs().get(i))) {
							Q.add(v.getSuccesseurs().get(i));
						}
					}
					S.add(v);
				} else {
					trouver = v;
				}
			}

		}
		return trouver;
	}

	public static int rechercheGraphe(Graphe g, Noeud t) {
		// la FIFO des noeuds a parcourir
		LinkedList<Noeud> Q = new LinkedList<Noeud>();
		// liste de noeuds deja parcourus
		ArrayList<Noeud> S = new ArrayList<Noeud>();
		int trouver = -1;
		// int indexNoeud = 0 ;
		if (g.nbNoeuds > 0) {
			Q.add(g.noeuds.get(0));
			while (!Q.isEmpty() && trouver == -1) {
				Noeud v = Q.pop();
				if (v.compareTo(t) != 0) {
					System.out.println("Different :" + v.getId() + " " + t.getId());
					for (int i = 0; i < v.getNbVoisins(); i++) {
						if (!Q.contains(v.getSuccesseurs().get(i)) && !S.contains(v.getSuccesseurs().get(i))) {
							Q.add(v.getSuccesseurs().get(i));
						}
					}
					S.add(v);
				} else {
					trouver = 1;
				}
			}
			if (trouver == -1) {
				System.out.println("N existe pas ");
			} else {
				// System.out.println("Exist");
				System.out.println("Egale: " + t.getId());
			}

		}
		// System.out.println("les noeuds du graphe sont : ");
		for (int i = 0; i < S.size(); i++) {
			System.out.print(S.get(i).getId() + " mmmmm");
		}
		return trouver;
	}

	public boolean inverseChemin(LinkedList<Noeud> chemin) {
		System.out.println("\n iteration");
		// System.out.println("\n inverse");
		int index = chemin.size() - 1;
		boolean noChemin = true;
		while (index > 0 && noChemin == true) {
			if (this.cheminBFS(chemin.get(index), chemin.get(index - 1)) != null) {
				chemin.get(index).getSuccesseurs().remove(chemin.get(index - 1));
				chemin.get(index).setNbVoisins(chemin.get(index).getNbVoisins() - 1);
				chemin.get(index - 1).getSuccesseurs().add(chemin.get(index));
				chemin.get(index - 1).setNbVoisins(chemin.get(index - 1).getNbVoisins() + 1);
				chemin.get(index - 1).setArcs(1);
			} else {
				noChemin = false;

			}
			index = index - 1;
		}
		/*for (int i = 0; i < chemin.size(); i++) {
			// System.out.print("\n chemin :"+chemin.get(i).getId());
			for (int j = 0; j < chemin.get(i).getSuccesseurs().size(); j++) {
				//System.out.println(" "+chemin.get(i).getSuccesseurs().get(j).getId());
			}
		}*/
		return noChemin;
	}

	public static Graphe creeGraphe(String fichier) {
		System.out.println(" ");
		Graphe graphe;
		ArrayList<Integer> creeNoeud = new ArrayList<Integer>();
		if (!fichier.isEmpty()) {
			graphe = new Graphe();
			int[][] matrice = null;
			ReadFichier taskfile = new ReadFichier(fichier);
			matrice = taskfile.readFichier();
			for (int i = 0; i < matrice.length; i++) {
				System.out.print(" noeud " + i + " successeurs : ");
				for (int j = 0; j < matrice[i].length; j++) {
					System.out.print(matrice[i][j] + " ");
				}
				System.out.println(" ");
			}
			int nbnoeud = 0;
			while (nbnoeud < matrice.length) {
				System.out.print(nbnoeud + "**------**");
				if (!creeNoeud.contains(nbnoeud)) {
					Noeud newNoeud = new Noeud(nbnoeud);
					Noeud sucNoeud;
					for (int j = 0; j < matrice[nbnoeud].length; j++) {
						if (matrice[nbnoeud][j] != 0 && !creeNoeud.contains(matrice[nbnoeud][j])) {
							System.out.print(matrice[nbnoeud][j]);
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
							System.out.print(matrice[nbnoeud][j]);
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
		System.out.println(" avant return " + graphe.getNbNoeuds());

		return graphe;
	}

	public static Graphe creeReseau(String fichier) {
		System.out.println(" ");
		Graphe graphe;
		if (!fichier.isEmpty()) {
			graphe = new Graphe();
			int[][] matrice = null;
			ReadFichier taskfile = new ReadFichier(fichier);
			matrice = taskfile.readFichier();
			int nbnoeud = matrice.length - 1;

			while (nbnoeud >= 0) {
				// System.out.print(nbnoeud+"****------");
				Noeud newNoeud = new Noeud(nbnoeud);
				for (int j = 0; j < matrice[nbnoeud].length; j++) {
					if (matrice[nbnoeud][j] != 0) {
						System.out.print(matrice[nbnoeud][j]);
						newNoeud.setSuccesseurs(new Noeud(matrice[nbnoeud][j]), 1);
					}
				}
				System.out.println(" ");
				graphe.setNoeuds(newNoeud);
				nbnoeud = nbnoeud - 1;
			}
		} else {
			graphe = null;
		}

		return graphe;
	}

	public int ff(Noeud s, Noeud t) {
		boolean noChemin = false;
		LinkedList<Noeud> chemin = new LinkedList<Noeud>();
		int index = 0;
		int nbSuccesseur = s.getSuccesseurs().size();
		// System.out.println(" successeur de s : "+nbSuccesseur);
		chemin = this.cheminBFS(s, t);
		if (chemin != null) {
			noChemin = true;
			while (index < nbSuccesseur && noChemin == true) {
				chemin = this.cheminBFS(s, t);
				this.inverseChemin(chemin);
				Graphe.parcoursGraphe(this);
				index = index + 1;
				chemin = this.cheminBFS(s, t);
				if (chemin == null) {
					noChemin = false;
				}
			}
		} else {
			System.out.println(" \n Pas de chemin de S à T \n");
		}

		return index;
	}

}
