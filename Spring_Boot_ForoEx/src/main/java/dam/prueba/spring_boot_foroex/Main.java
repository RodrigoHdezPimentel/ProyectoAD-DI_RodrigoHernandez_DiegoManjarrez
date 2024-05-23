package dam.prueba.spring_boot_foroex;


import dam.prueba.spring_boot_foroex.uploadingFiles.Storage.StorageProperties;
import dam.prueba.spring_boot_foroex.uploadingFiles.Storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;


@EnableConfigurationProperties(StorageProperties.class)
@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Main.class, args);

	}
	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.init();
		};
	}

}
