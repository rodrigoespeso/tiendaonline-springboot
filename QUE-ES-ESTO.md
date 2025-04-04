# Qué es esto

Ejercicio acometido con patrón de arquitectura en tres capas (**Controller**, **Service**, **Repository**) utilizando **Spring Boot**.

- Uso de **JPA** con **PostgreSQL** desplegado en **Docker**
- Uso de **entidades** y **DTOs**
- Manejo de **excepciones** mediante un `@ControllerAdvice` handler
- Conexión a **API externa** utilizando `RestTemplate` en la capa `service`
- Pruebas unitarias con **JUnit** y **Mockito**
- **Colecciones de Postman** para validación de endpoints

---

# Qué tengo que hacer más (deuda técnica)

- Mejorar el mensaje de validación en `GET /users/{userId}/orders`
- Segurizar servicios con **Spring Security**, **JWT** y **OAuth2**
- Documentar con **JavaDoc** y **OpenAPI**
- Evaluar el uso de `@MockBean` frente a `@Mock` y `@InjectMocks`
- **Chapuza**: Casteo en `OrderItemsService`
- Crear clase `Utils` con métodos:
  - `fromObjectToBigDecimal`
  - `fromObjectToInteger`
- Incluir **paginación** en endpoints que devuelvan colecciones
