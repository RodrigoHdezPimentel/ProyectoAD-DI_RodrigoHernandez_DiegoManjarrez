package dam.prueba.springPrueba;

import dam.prueba.springPrueba.controllers.ChatController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringPruebaApplication {

	public static void main(String[] args) {
		//Arrancar el hilo server chat
		SpringApplication.run(SpringPruebaApplication.class, args);

	}


}
