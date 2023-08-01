package com.llye.springboot.springbootthree;

import com.llye.springboot.springbootthree.dto.AccountRequestDto;
import com.llye.springboot.springbootthree.dto.CustomerDto;
import com.llye.springboot.springbootthree.dto.CustomerRequestDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SpringBootThreeApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

	//region customer CRU operations
	@Order(1)
    @Test
    @DisplayName("POST /api/v1/customers works")
    void givenValidJWT_whenCreateCustomer_thenReturn2xx() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "application/json");

        CustomerRequestDto requestDto = CustomerRequestDto.builder()
                                                          .firstName("John")
                                                          .lastName("Smith")
                                                          .build();
        HttpEntity requestEntity = new HttpEntity<>(requestDto, headers);
        ResponseEntity<CustomerDto> response = restTemplate.exchange("/api/v1/customers",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });
        // Assert
        assertTrue(response.getStatusCode()
                           .is2xxSuccessful(), "HTTP Response status code should be 2xx");
    }

	@Order(2)
    @Test
    @DisplayName("GET /api/v1/customers works")
    void givenValidJWT_whenGetCustomers_thenReturn2xx() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "application/json");

        HttpEntity requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<List<CustomerDto>> response = restTemplate.exchange("/api/v1/customers",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });
        // Assert
        assertTrue(response.getStatusCode()
                           .is2xxSuccessful(), "HTTP Response status code should be 2xx");
    }

	@Order(3)
	@Test
	@DisplayName("GET /api/v1/customers/{id} works")
	void givenValidJWT_whenGetCustomerById_thenReturn2xx() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("accept", "application/json");

		HttpEntity requestEntity = new HttpEntity<>(null, headers);
		ResponseEntity<CustomerDto> response = restTemplate.exchange("/api/v1/customers/1",
				HttpMethod.GET,
				requestEntity,
				new ParameterizedTypeReference<>() {
				});
		// Assert
		assertTrue(response.getStatusCode()
						   .is2xxSuccessful(), "HTTP Response status code should be 2xx");
	}

	@Order(4)
	@Test
	@DisplayName("PUT /api/v1/customers/{id} works")
	void givenValidJWT_whenUpdateCustomer_thenReturn2xx() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("accept", "application/json");

		CustomerRequestDto requestDto = CustomerRequestDto.builder()
														  .firstName("John")
														  .lastName("Doe")
														  .build();
		HttpEntity requestEntity = new HttpEntity<>(requestDto, headers);
		ResponseEntity<CustomerDto> response = restTemplate.exchange("/api/v1/customers/1",
				HttpMethod.PUT,
				requestEntity,
				new ParameterizedTypeReference<>() {
				});
		// Assert
		assertTrue(response.getStatusCode()
						   .is2xxSuccessful(), "HTTP Response status code should be 2xx");
	}
	//endregion

	//region account CRU operations
	@Order(5)
	@Test
	@DisplayName("POST /api/v1/customers/{customerId}/accounts works")
	void givenValidJWT_whenCreateAccountForCustomer_thenReturn2xx() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("accept", "application/json");

		AccountRequestDto requestDto = AccountRequestDto.builder()
														.type("saving")
														.amount(new BigDecimal("200.50"))
														.build();
		HttpEntity requestEntity = new HttpEntity<>(requestDto, headers);
		ResponseEntity<CustomerDto> response = restTemplate.exchange("/api/v1/customers/1/accounts",
				HttpMethod.POST,
				requestEntity,
				new ParameterizedTypeReference<>() {
				});
		// Assert
		assertTrue(response.getStatusCode()
						   .is2xxSuccessful(), "HTTP Response status code should be 2xx");
	}

	@Order(6)
	@Test
	@DisplayName("GET /api/v1/customers/{customerId}/accounts works")
	void givenValidJWT_whenGetAllAccountsForCustomer_thenReturn2xx() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("accept", "application/json");

		HttpEntity requestEntity = new HttpEntity<>(null, headers);
		ResponseEntity<List<CustomerDto>> response = restTemplate.exchange("/api/v1/customers/1/accounts",
				HttpMethod.GET,
				requestEntity,
				new ParameterizedTypeReference<>() {
				});
		// Assert
		assertTrue(response.getStatusCode()
						   .is2xxSuccessful(), "HTTP Response status code should be 2xx");
	}

	@Order(7)
	@Test
	@DisplayName("GET /api/v1/customers/{customerId}/accounts/{accountId} works")
	void givenValidJWT_whenGetAccountForCustomer_thenReturn2xx() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("accept", "application/json");

		HttpEntity requestEntity = new HttpEntity<>(null, headers);
		ResponseEntity<CustomerDto> response = restTemplate.exchange("/api/v1/customers/1/accounts/1",
				HttpMethod.GET,
				requestEntity,
				new ParameterizedTypeReference<>() {
				});
		// Assert
		assertTrue(response.getStatusCode()
						   .is2xxSuccessful(), "HTTP Response status code should be 2xx");
	}

	@Order(8)
	@Test
	@DisplayName("PUT /api/v1/customers/{customerId}/accounts/{accountId} works")
	void givenValidJWT_whenUpdateAccountForCustomer_thenReturn2xx() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("accept", "application/json");

		AccountRequestDto requestDto = AccountRequestDto.builder()
														.amount(new BigDecimal("200.50"))
														.build();
		HttpEntity requestEntity = new HttpEntity<>(requestDto, headers);
		ResponseEntity<CustomerDto> response = restTemplate.exchange("/api/v1/customers/1/accounts/1",
				HttpMethod.PUT,
				requestEntity,
				new ParameterizedTypeReference<>() {
				});
		// Assert
		assertTrue(response.getStatusCode()
						   .is2xxSuccessful(), "HTTP Response status code should be 2xx");
	}
	//endregion

	@Order(9)
	@Test
	@DisplayName("DELETE /api/v1/customers/{customerId}/accounts/{accountId} works")
	void givenValidJWT_whenDeleteAccount_thenReturn2xx() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("accept", "application/json");

		HttpEntity requestEntity = new HttpEntity<>(null, headers);
		ResponseEntity<CustomerDto> response = restTemplate.exchange("/api/v1/customers/1/accounts/1",
				HttpMethod.DELETE,
				requestEntity,
				new ParameterizedTypeReference<>() {
				});
		// Assert
		assertTrue(response.getStatusCode()
						   .is2xxSuccessful(), "HTTP Response status code should be 2xx");
	}

	@Order(10)
	@Test
	@DisplayName("DELETE /api/v1/customers/{id} works")
	void givenValidJWT_whenDeleteCustomer_thenReturn2xx() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("accept", "application/json");

		HttpEntity requestEntity = new HttpEntity<>(null, headers);
		ResponseEntity<CustomerDto> response = restTemplate.exchange("/api/v1/customers/1",
				HttpMethod.DELETE,
				requestEntity,
				new ParameterizedTypeReference<>() {
				});
		// Assert
		assertTrue(response.getStatusCode()
						   .is2xxSuccessful(), "HTTP Response status code should be 2xx");
	}
}
