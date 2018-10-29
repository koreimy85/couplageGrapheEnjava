package vn.edu.ifi.tp_ro.fichier;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadFichier {

	private static String nameFichier;

	public ReadFichier(String filepath) {
		ReadFichier.nameFichier = filepath;
	}

	public ReadFichier() {

	}

	public int[][] readFichier() {
		int[][] listNoeud = null;
		BufferedReader br = null;
		FileReader fr = null;

		try {

			fr = new FileReader(ReadFichier.nameFichier);
			br = new BufferedReader(fr);

			String sCurrentLine;

			int indicate = 0;
			int tableSize = 0;
			while ((sCurrentLine = br.readLine()) != null) {
				// System.out.println(sCurrentLine);
				if (indicate == 0) { // noeud source
					tableSize = Integer.parseInt(sCurrentLine);
					listNoeud = new int[2 * tableSize + 2][tableSize];
					int[] noeuds = new int[tableSize];
					for (int i = 0; i < tableSize; i++) {
						noeuds[i] = i + 1;
					}
					listNoeud[indicate] = noeuds;
				} else { // reste de noeud
					int[] noeuds = new int[tableSize];
					String[] nameNoeud = sCurrentLine.split(" ");
					for (int i = 0; i < nameNoeud.length; i++) {
						noeuds[i] = Integer.parseInt(nameNoeud[i]) + tableSize;
					}
					listNoeud[indicate] = noeuds;
				}
				indicate = indicate + 1;
			}
			System.out.println("|-----------------------------------------------------------|");
			System.out.println("|          " + "Nombre total des noeuds(personnes,taches, S et T)"       +"|");
			System.out.println("|                               " + listNoeud.length + "                          |");
			System.out.println("|-----------------------------------------------------------|");
			int[] noeuds = new int[tableSize];
			listNoeud[listNoeud.length - 1] = noeuds;
			for (int i = indicate; i < listNoeud.length - 1; i++) {
				int[] n = { listNoeud.length - 1, 0, 0 };
				listNoeud[i] = n;
			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
		// int derN=0;
		for (int i = 1; i < listNoeud.length / 2; i++) {
			System.out.print("|personne " + i + " à pour choix le(s) tâche(s)--> ");
			for (int j = 0; j < listNoeud[i].length; j++) {
				if (listNoeud[i][j] != 0) {
					System.out.print(listNoeud[i][j] + " ");
				}
				// derN=listNoeud[i][j];
			}
			System.out.println(" ");
		}

		System.out.println("-------------------------------------------------------------|\n");
		return listNoeud;
	}

}
