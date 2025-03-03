package example.cashcard;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/*@JsonTest*/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CashCardApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;


	//use the test to the getById methode to cashcardController
	@Test
	void shouldReturnACashCardWhenDataIsSaved() {
		ResponseEntity<String> response = restTemplate.getForEntity("/cashcards/99", String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		Number id = documentContext.read("$.id");
		assertThat(id).isEqualTo(99);

		Double amount = documentContext.read("$.amount");
		assertThat(amount).isEqualTo(123.45);
	}




	@Test
	void shouldNotReturnACashCardWithAnUnknownId() {
		ResponseEntity<String> response = restTemplate.getForEntity("/cashcards/1000", String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(response.getBody()).isBlank();
	}

	/*private JacksonTester<CashCard> json;

	@Test
	void cashCardSerializationTest() throws IOException {
		CashCard cashCard = new CashCard(99L, 123.45);
		assertThat(json.write(cashCard))
				.isStrictlyEqualToJson(new ClassPathResource("example/cashcard/expected.json"));
		assertThat(json.write(cashCard)).hasJsonPathNumberValue("@.id");
		assertThat(json.write(cashCard)).extractingJsonPathNumberValue("@.id")
				.isEqualTo(99);
		assertThat(json.write(cashCard)).hasJsonPathNumberValue("@.amount");
		assertThat(json.write(cashCard)).extractingJsonPathNumberValue("@.amount")
				.isEqualTo(123.45);
	}

	@Test
	void cashCardDeserializationTest() throws IOException {
		String expected = """
           {
               "id":99,
               "amount":123.45
           }
           """;
		assertThat(json.parse(expected))
				.isEqualTo(new CashCard(99L, 123.45));
		assertThat(json.parseObject(expected).id()).isEqualTo(99);
		assertThat(json.parseObject(expected).amount()).isEqualTo(123.45);
	}*/

}
