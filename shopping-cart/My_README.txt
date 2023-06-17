1. --- PART ---
!!!
We can trigger the MapStruct processing by executing an
 > mvn clean install
This will generate the implementation class under /target/generated-sources/annotations/.
Here is the class that MapStruct auto-creates for us:
@Component
public class CartMapperImpl implements CartMapper {

    @Override
    public Cart toModel(ShoppingCartRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Cart cart = new Cart();

        cart.setUser( shoppingCartRequestDTOToUserInfo( dto ) );
        cart.setItems( toModel( dto.getItems() ) );

        return cart;
    }
    ...

https://www.baeldung.com/mapstruct

------------------------ TEST ----------------------------

http://localhost:8083/api/cart
POST
REQUEST
	{
		"userId": 901,
		"items": [
			{
			"productId": 801,
			"quantity":	2
			}
		]
	}
RESPONSE (ResponseEntity.created(URI.create(responseDTO.getId())).body(responseDTO);)
{
    "id": "55e7a1a5-e761-4f9b-a1c3-92db2364346a",
    "userId": 901,
    "userName": "Isaac Newton",
    "items": [
        {
            "productId": 801,
            "productName": "laptop",
            "quantity": 2
        }
    ],
    "totalPrice": 4000.00
}


	log
	-----------
	2023-06-15 22:15:13.876  INFO 12036 --- [nio-8083-exec-4] c.t.s.controller.ShoppingCartController  : START
    2023-06-15 22:15:13.876  INFO 12036 --- [nio-8083-exec-4] c.t.s.service.IntegrationService         : getRemoteUserInfo = userId 901
    2023-06-15 22:15:13.884  INFO 12036 --- [nio-8083-exec-4] c.t.s.service.ShoppingCartService        : 1 purchase - usr Id 901
    2023-06-15 22:15:13.927  INFO 12036 --- [nio-8083-exec-4] c.t.s.service.IntegrationService         : getRemoteProductItemsInfo - get(0) -  801
    2023-06-15 22:15:13.927  INFO 12036 --- [nio-8083-exec-4] c.t.s.service.ShoppingCartService        : 2 purchase = items g=

1.
    Feign Client
    http://localhost:8082/api/user/901
 GET
 RESPONSE:
    {
        "id": 901,
        "name": "Isaac Newton",
        "email": "isaac@newton.com",
        "birthdate": "1643-01-04",
        "address": "gravity street - England"
    }

 2. http://localhost:8081/api/product/801
 GET
 RESPONSE:
 {
     "id": 801,
     "name": "laptop",
     "description": "Super fast laptop",
     "brand": "new brand",
     "price": 2000.00
 }

2. --- PART ---- Fault tolerance and Configuration Server -----------
2.1
    In Spring Boot framework, the default implementation was Spring Cloud Netflix Hystrix,
    but since Spring Boot version 2.4.2 it’s used Spring Cloud Circuit breaker with Resilience4j
    as the underlying implementation, which is the one we are going to use in this article.

    <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-circuitbreaker-resilience4j</artifactId>
    </dependency>

2.2 Create a configuration spring bean for circuit breaker with example parameters.

2.3
    In the IntegrationService class, we can inject the circuit breaker factory and
    implement some fallback methods in case of failure of products or users requests,
    and wrap the call in a circuit breaker object.

2.4 TESTING - POSTMAN
- stop User-Info-service, then
start User-Info, and
stop Product-catalog-service.
Correct fault messages are displayed:
POST - stop User-Info-service,
{
    "id": "d360dd62-cf5f-47c5-b016-bcb5dc0421ec",
    "userId": 902,
    "userName": "name (user) info unavailable",
    "items": [
        {
            "productId": 804,
            "productName": "product name unavailable",
            "quantity": 2
        }
    ],
    "totalPrice": 0
}
RESPONSE
{
    "id": "5402a143-6fe8-48d5-8c6a-5ab7d96e3c5a",
    "userId": 904,
    "userName": "name (user) info unavailable",
    "items": [
        {
            "productId": 804,
            "productName": "smart tv",
            "quantity": 2
        }
    ],
    "totalPrice": 4000.00
}

- stop product service
{
    "id": "83e0c657-4ba2-4a3b-8660-3e4583d9ab91",
    "userId": 904,
    "userName": "Stephen Hawking",
    "items": [
        {
            "productId": 804,
            "productName": "product name unavailable",
            "quantity": 2
        }
    ],
    "totalPrice": 0
}

2.  - Configuration Server
    Another improvement we can make in our system is to use a centralized configuration server.
    Right now, we have local configurations for each microservice, usually at application.yml file.
    But we can change that to a client-server approach for storing and serving distributed configurations across
    multiple applications and environments, ideally versioned under Git version control and able to be modified
    at application runtime.

    So, let’s set up a Config Server and configure the clients to consumes the configuration on startup and then
    refreshes the configuration without restarting the client. We will use Spring Cloud Config for this purpose:

