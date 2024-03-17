package Inserts;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class insertGrupoUsuario {

	public static void main(String[] args) {
		try {
			File sql = new File("insertGrupoUsuario.txt");
			if (!sql.exists()) {
				sql.createNewFile();
			}
			FileWriter fw = new FileWriter(sql);
			PrintWriter pw = new PrintWriter(fw);

			Random rm = new Random();
			int personaID;
			for (int x = 1; x <= 300; x++) {
				int numPersonas = rm.nextInt(5)+1;
				int [] personas = new int[numPersonas];
				
				for(int y = 0; y < numPersonas; y++) {
					
					personaID = rm.nextInt(50) + 1;
					for(int z = 0; z < personas.length; z++) {
						
						if(personas[z] == personaID) {
							y--;
							break;
						}else if(z == personas.length -1) {
							personas[y] = personaID;
							pw.print("(NULL, " + x + ", "+ personaID + ", \"Nombre Grupo\", null),\n");
						}
					}
				}
				
				
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
