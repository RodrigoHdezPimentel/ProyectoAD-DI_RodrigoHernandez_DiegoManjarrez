package Inserts;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class insertGrupos {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			File sql = new File("insertGrupos.txt");
			if (!sql.exists()) {
				sql.createNewFile();
			}
			FileWriter fw = new FileWriter(sql);
			PrintWriter pw = new PrintWriter(fw);

			Random rm = new Random();

			String codigo = "";
			char letra;
			for (int x = 0; x < 300; x++) {
				
				for (int y = 0; y < 10; y++) {
					letra = (char)rm.nextInt(48,123);
					if(letra == '\\' || letra == ';') {
						y--;
					}else {
						codigo += letra;
					}
				}
				pw.print("(NULL, \"Ruta\", \"" + codigo + "\"),\n");
				codigo = "";
				
			}

			pw.flush();
			pw.close();
			fw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("ok");
	}

}
