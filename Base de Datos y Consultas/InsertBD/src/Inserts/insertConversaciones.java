package Inserts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class insertConversaciones {

	public static void main(String[] args) {
		File lorem = new File("Conversaciones.txt");
		int numRegistros = 0;
		try {
			FileReader fr = new FileReader(lorem);
			BufferedReader br = new BufferedReader(fr);

			String linea = "";
			String texto = "";

			while ((linea = br.readLine()) != null) {
				if (!linea.equals("")) {
					;
					texto += linea;
				}
			}
			br.close();
			fr.close();

			String[] textoSplit = texto.split(";");

			File sql = new File("insertConversaciones.txt");
			if (!sql.exists()) {
				sql.createNewFile();
			}
			FileWriter fw = new FileWriter(sql);
			PrintWriter pw = new PrintWriter(fw);

			Random rm = new Random();
			String contenido;
			String fecha;
			try {
			int sec;
			int dia;
			int mes;
			int min;
			int hora;
			int numMens;
			for (int i = 0; i < 5; i++) {

				// Num de registros Grupo_Usuario
				for (int y = 1; y < 838; y++) {

					// Mensajes por persona
					numMens = rm.nextInt(7) + 1;
					for (int z = 0; z < numMens; z++) {
						
						dia = rm.nextInt(30) + 1;
						mes = rm.nextInt(12) + 1;
						hora = rm.nextInt(24);
						min = rm.nextInt(60);
						sec = rm.nextInt(60);
						if (mes == 2 && dia > 28) {
							dia = 27;
						}
						fecha = rm.nextInt(2000, 2024) + "-" + String.format("%02d", mes) + "-" + String.format("%02d", dia)
								+ " " + String.format("%02d", hora) + ":"
								+ String.format("%02d", min) + ":" + String.format("%02d", sec);
						
						contenido = textoSplit[(rm.nextInt(textoSplit.length - 1))];
						pw.print("(NULL, " + y + ", \"" + fecha + "\", \"" + contenido + "\"),\n");
						numRegistros++;
						System.out.println(numRegistros);
					}
				}
			}
			pw.flush();
			pw.close();
			fw.close();
			System.out.println(numRegistros);
			}catch(Exception e) {
				System.out.println("Mal");
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
