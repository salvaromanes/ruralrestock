package com.tfg.ruralrestock;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MongoDBContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RuralrestockApplicationTests {
	@ServiceConnection
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.5");

	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setup(){
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	static {
		mongoDBContainer.start();
	}

	@Test
	void CRUDuser(){
		String requestBody = """
				{
				    "dni": "96325841T",
				    "nombre": "José",
				    "apellidos": "Palomo Reina",
				    "email": "jpreina@uma.es",
				    "password": "palomo_jreina_15",
				    "municipio": "Sevilla"
				}
				""";

		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/user/create")
				.then()
				.statusCode(201)
				.body("dni", Matchers.equalTo("96325841T"))
				.body("nombre", Matchers.equalTo("José"))
				.body("apellidos", Matchers.equalTo("Palomo Reina"))
				.body("email", Matchers.equalTo("jpreina@uma.es"))
				.body("municipio", Matchers.equalTo("Sevilla"))
				.body("rol", Matchers.equalTo("visitante"));

		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.get("/api/user/getUser/dni/96325841T")
				.then()
				.statusCode(200)
				.body("dni", Matchers.equalTo("96325841T"))
				.body("nombre", Matchers.equalTo("José"))
				.body("apellidos", Matchers.equalTo("Palomo Reina"))
				.body("email", Matchers.equalTo("jpreina@uma.es"))
				.body("municipio", Matchers.equalTo("Sevilla"))
				.body("rol", Matchers.equalTo("visitante"));

		requestBody = """
				{
				    "dni": "96325841T",
				    "nombre": "José",
				    "apellidos": "Palomo Reina",
				    "email": "jpreina@uma.es",
				    "password": "jreina_15_el_palomo",
				    "municipio": "Utrera"
				}
				""";

		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.put("/api/user/update")
				.then()
				.statusCode(200);

		requestBody = "96325841T";

		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.delete("/api/user/delete/"+requestBody)
				.then()
				.statusCode(204);
	}

	@Test
	void CRUDtown() {
		String requestBody = """
					{
						"nombre" : "Canillas de Aceituno",
						"ubicacion" : "Malaga",
						"historia" : ""
					}
				""";

		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/town/create")
				.then()
				.statusCode(201)
				.body("nombre", Matchers.equalTo("Canillas de Aceituno"))
				.body("ubicacion", Matchers.equalTo("Malaga"));

		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.get("/api/town/getAll")
				.then()
				.statusCode(200);

		requestBody = """
					{
						"nombre" : "Canillas de Aceituno",
						"ubicacion" : "Malaga",
						"historia" : "Canillas de Aceituno es un municipio español de la provincia de Málaga, en la comunidad autónoma de Andalucía. Está situado en el este de la provincia, siendo uno de los municipios que conforman la comarca de la Axarquía y el partido judicial de Vélez-Málaga."
					}
				""";

		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.put("/api/town/update")
				.then()
				.statusCode(200);

		requestBody = "Canillas de Aceituno";

		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.delete("/api/town/delete/"+requestBody)
				.then()
				.statusCode(204);
	}
}
