package dam.prueba.springPrueba;

import dam.prueba.springPrueba.controllers.UsuarioController;
import dam.prueba.springPrueba.uploadingFiles.Storage.StorageProperties;
import dam.prueba.springPrueba.uploadingFiles.Storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties(StorageProperties.class)
@SpringBootApplication
public class SpringPruebaApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(SpringPruebaApplication.class, args);

		UsuarioController usuarioController = context.getBean(UsuarioController.class);
		System.out.println(usuarioController);
	}
	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.init();
		};
	}

}
