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

	@Test
	void CRUDeventos() {
		String requestBody = """
				{
				    "nombre": "Ruta de la tapa",
				    "fecha_inicio": "2024-05-24",
				    "fecha_fin": "2024-05-26",
				    "tipo": "ocio",
				    "descripcion": "Ruta de la tapa",
				    "municipio": "Alcaucín"
				}
				""";

		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/event/create")
				.then()
				.statusCode(201)
				.body("nombre", Matchers.equalTo("Ruta de la tapa"))
				.body("fecha_inicio", Matchers.equalTo("24/05/2024"))
				.body("fecha_fin", Matchers.equalTo("26/05/2024"))
				.body("tipo", Matchers.equalTo("ocio"))
				.body("descripcion", Matchers.equalTo("Ruta de la tapa"))
				.body("municipio", Matchers.equalTo("Alcaucín"));

		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.get("/api/event/getEvent/type/ocio")
				.then()
				.statusCode(200);

		requestBody = """
				{
				    "nombre": "Ruta de la tapa",
				    "fecha_inicio": "2024-05-17",
				    "fecha_fin": "2024-05-19",
				    "tipo": "ocio",
				    "descripcion": "Ruta de la tapa",
				    "municipio": "Sedella"
				}
				""";

		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.put("/api/event/update")
				.then()
				.statusCode(200);

		requestBody = "0";

		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.delete("/api/event/delete/" + requestBody)
				.then()
				.statusCode(204);
	}

	@Test
	void CRUDempresas() {
		String requestBody = """
				{
				    "cif" : "1234ASD",
					"nombre" : "TFG_SOB",
					"propietario" : "Salvador Ortiz Bazaga",
					"direccion" : "",
					"descripcion" : "",
					"municipio" : "La Viñuela"
				}
				""";

		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/company/create")
				.then()
				.statusCode(201)
				.body("cif", Matchers.equalTo("1234ASD"))
				.body("nombre", Matchers.equalTo("TFG_SOB"))
				.body("propietario", Matchers.equalTo("Salvador Ortiz Bazaga"))
				.body("direccion", Matchers.equalTo(""))
				.body("descripcion", Matchers.equalTo(""))
				.body("municipio", Matchers.equalTo("La Viñuela"));

		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.get("/api/company/getCompany/propietario/Salvador Ortiz Bazaga")
				.then()
				.statusCode(200);

		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.get("/api/company/getCompany/name/TFG_SOB")
				.then()
				.statusCode(200);

		requestBody = """
				{
					"cif" : "1234ASD",
					"nombre" : "TFG_SOB",
					"propietario" : "Salvador Ortiz Bazaga",\s
					"direccion" : "C/ El Cerro 2",
					"descripcion" : "",
					"municipio" : "La Viñuela"
				}
				""";

		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.put("/api/company/update")
				.then()
				.statusCode(200);

		requestBody = "1234ASD";

		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.delete("/api/company/delete/" + requestBody)
				.then()
				.statusCode(204);
	}

	@Test
	void CRUDempleos() {
		String requestBody = """
				{
					"nombre" : "oferta 1",
					"empresa_ofertante" : "empresa 1",
					"requisitos" : "",
					"descripcion" : "",
					"informacion_extra" : ""
				}
				""";

		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/employment/create")
				.then()
				.statusCode(201)
				.body("nombre", Matchers.equalTo("oferta 1"))
				.body("requisitos", Matchers.equalTo(""))
				.body("descripcion", Matchers.equalTo(""))
				.body("informacion_extra", Matchers.equalTo(""))
				.body("empresa_ofertante", Matchers.equalTo("empresa 1"));

		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.get("/api/employment/getEmployment/company/empresa 1")
				.then()
				.statusCode(200);

		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.get("/api/employment/getEmployment/name/oferta 1")
				.then()
				.statusCode(200);

		requestBody = """
				{
					"nombre" : "oferta 1",
					"empresa_ofertante" : "empresa 1",
				    "requisitos" : "ser informatico",
				    "descripcion" : "",
				    "informacion_extra" : ""
				}
				""";

		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.put("/api/employment/update")
				.then()
				.statusCode(200);

		requestBody = "0";

		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.delete("/api/employment/delete/" + requestBody)
				.then()
				.statusCode(204);
	}

	@Test
	void CRUDviviendas() {
		String requestBody = """
				{
					"propietario" : "Juan Gutierrez Mora",
				    "direccion" : "C/Aqui 2",
				    "tipo" : "alquiler",
				    "precio" : 955.50,
				    "informacion" : "",
				    "contacto" : "987654321",
				    "municipio" : "Coín"
				}
				""";

		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/livingPlace/create")
				.then()
				.statusCode(201)
				.body("propietario", Matchers.equalTo("Juan Gutierrez Mora"))
				.body("direccion", Matchers.equalTo("C/Aqui 2"))
				.body("tipo", Matchers.equalTo("alquiler"))
				.body("precio", Matchers.equalTo(955.5F))
				.body("informacion", Matchers.equalTo(""))
				.body("contacto", Matchers.equalTo("987654321"))
				.body("municipio", Matchers.equalTo("Coín"));

		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.get("/api/livingPlace/getLivingPlace/tipo/alquiler")
				.then()
				.statusCode(200);

		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.get("/api/livingPlace/getLivingPlace/municipio/Coín")
				.then()
				.statusCode(200);

		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.get("/api/livingPlace/getLivingPlace/propietario/Juan Gutierrez Mora")
				.then()
				.statusCode(200);

		requestBody = """
				{
					"propietario" : "Juan Gutierrez Mora",
				    "direccion" : "C/Aqui 2",
				    "tipo" : "venta",
				    "precio" : 255800.96,
				    "informacion" : "",
				    "contacto" : "987654321",
				    "municipio" : "Coín"
				}
				""";

		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.put("/api/livingPlace/update")
				.then()
				.statusCode(200);

		requestBody = "0";

		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.delete("/api/livingPlace/deleteById/" + requestBody)
				.then()
				.statusCode(204);

		requestBody = "Juan Gutierrez Mora";

		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.delete("/api/livingPlace/deleteByPropietario/" + requestBody)
				.then()
				.statusCode(204);
	}
}
