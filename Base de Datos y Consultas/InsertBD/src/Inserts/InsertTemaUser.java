package Inserts;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

public class InsertTemaUser {

	public static void main(String[] args) {
		Random rm = new Random();
		int numUsers = 50;
		int numTemas = 32;
		File resultado = new File("insertTemaUser.txt");

		try {
			if (!resultado.exists()) {
				resultado.createNewFile();
			}

			FileWriter fw = new FileWriter(resultado);
			PrintWriter pw = new PrintWriter(fw);

			String texto;
			ArrayList<Integer> temasUser = new ArrayList<Integer>();
			int idTema;
			boolean unico = true;

			for (int x = 1; x < numUsers + 1; x++) {
				temasUser.removeAll(temasUser);
				for (int i = 1; i < rm.nextInt(5, numTemas) + 1; i++) {
					unico = true;

					idTema = rm.nextInt(numTemas) + 1;
					for (int a = 0; a < temasUser.size(); a++) {

						if (idTema == temasUser.get(a)) {
							unico = false;
							break;
						}
					}
					
					if (unico) {
						temasUser.add(idTema);
						texto = "(" + x + ", " + idTema + "),";
						System.out.println(texto);
						pw.println(texto);
						pw.flush();
					}
				}
			}
			pw.close();
			fw.close();
			System.out.println("done");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
