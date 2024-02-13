package Inserts;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class insertLikes {

	public static void main(String[] args) {
		File likes = new File("insertLikes.txt");
		
		try {
			Random rm = new Random();
			FileWriter fw = new FileWriter(likes);
			PrintWriter pw = new PrintWriter(fw);
			
			
			String sql = "";
			for(int x = 0; x < 3000; x++) {
				sql = "(NULL, " + (rm.nextInt(1999)+1) + ", "+ (rm.nextInt(50)+1) + "),";
				pw.println(sql);
				pw.flush();
			}
			pw.close();
			fw.close();
			System.out.println("done");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
