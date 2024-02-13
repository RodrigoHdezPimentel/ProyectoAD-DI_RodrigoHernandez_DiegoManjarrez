package Inserts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Random;

public class InsertPublicaciones {

	public static void main(String[] args) {
		Random rm = new Random();
		File publicaciones = new File("textoPublicaciones.txt");
		File sql = new File("insertPublicaciones.txt");
		try {
			FileReader fr = new FileReader(publicaciones);
			BufferedReader br = new BufferedReader(fr);

			String[] listaPublicaciones;
			String[] listaTitulos;
			String poemario = "";
			String texto;
			while ((texto = br.readLine()) != null) {
				poemario += texto;
			}
			listaPublicaciones = poemario.split(";");
			listaTitulos = poemario.split(" ");
			br.close();
			fr.close();

			FileWriter fw = new FileWriter(sql);
			PrintWriter pw = new PrintWriter(fw);

			boolean isComment;
			String numRef;
			String query = "";
			for(String s : listaPublicaciones) {
				System.out.println(s);
			}
			
			for (int x = 1; x < 2000; x++) {

				isComment = (rm.nextInt(100) > 25);
				if (isComment) {
					numRef = rm.nextInt(x-2)+1 + "";
				} else {
					numRef = null;
				}
				String fecha;

				int dia = rm.nextInt(30) + 1;
				int mes = rm.nextInt(12) + 1;
				if (mes == 2 && dia > 28) {
					x--;
				}else {
					fecha = rm.nextInt(2020, 2024) + "-" + String.format("%02d", mes) + "-"
							+ String.format("%02d", dia);
					
					query = "(NULL, " + (rm.nextInt(50) + 1) + ", " + (rm.nextInt(27) + 1) + ", " + numRef + ", \"" + fecha + "\", 0, \""+ listaTitulos[rm.nextInt(listaTitulos.length-1)] + "\", \"" + listaPublicaciones[rm.nextInt(listaPublicaciones.length)] + "\"),";
					pw.println(query);
					pw.flush();
				}
			}
			pw.close();
			fw.close();
			System.out.println("done");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
