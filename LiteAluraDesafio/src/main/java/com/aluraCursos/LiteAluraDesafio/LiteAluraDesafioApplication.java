package com.aluraCursos.LiteAluraDesafio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import com.aluraCursos.LiteAluraDesafio.principal.Principal;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
@EntityScan("com.aluraCursos.LiteAluraDesafio.model")
public class LiteAluraDesafioApplication implements CommandLineRunner {

	private final Principal principal;

	@Autowired
	public LiteAluraDesafioApplication(Principal principal) {
		this.principal = principal;
	}

	public static void main(String[] args) {
		SpringApplication.run(LiteAluraDesafioApplication.class, args);
	}

	@Override
	public void run(String... args) {
		principal.muestraElMenu();
	}
}