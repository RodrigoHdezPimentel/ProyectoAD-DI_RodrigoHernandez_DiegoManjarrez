package Inserts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class insertChat {

	public static void main(String[] args) {
		File lorem = new File("lorem.txt");
		try {
			FileReader fr = new FileReader(lorem);
			BufferedReader br = new BufferedReader(fr);

			String linea = "";
			String texto = "";

			while ((linea = br.readLine()) != null) {
				if (!linea.equals("")) {;
					texto += linea;
				}
			}
			br.close();
			fr.close();
			
			
			String[] textoSplit = texto.split("\\. ");
			for (String l : textoSplit) {
				System.out.println(l);
			}

			File sql = new File("insertChat.txt");
			if (!sql.exists()) {
				sql.createNewFile();
			}
			FileWriter fw = new FileWriter(sql);
			PrintWriter pw = new PrintWriter(fw);

			Random rm = new Random();
			int destino;
			int origen;
			String contenido;
			String fecha;
			for (int i = 0; i < 1000; i++) {
				destino = rm.nextInt(50) + 1;
				origen = rm.nextInt(50) + 1;
				if (destino != origen) {

					int dia = rm.nextInt(30) + 1;
					int mes = rm.nextInt(12) + 1;
					if (mes == 2 && dia > 28) {
						i--;
					}
					fecha = rm.nextInt(2000, 2024) + "-" + String.format("%02d", mes) + "-"
							+ String.format("%02d", dia);
					contenido = textoSplit[(rm.nextInt(textoSplit.length - 1))];
					pw.print("(NULL, " + origen + ", " + destino + ", \'" + fecha + "\', \'" + contenido + "\'),\n");
				}
			}
			pw.flush();
			pw.close();
			fw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}
