# 📚 LiteAluraDesafio  

Practicando Spring Boot: Challenge Literalura - LiteAluraDesafio

Aplicación en **Java + Spring Boot** para gestionar libros, consultar información desde la API **Gutendex** y almacenarlos en **PostgreSQL**.  

🚀 Cómo instalar
1️⃣ Clonar el repositorio
git clone https://github.com/AlexTech777/LiteAluraDesafio.git

2️⃣ Configurar la base de datos
CREATE DATABASE: LiteAluraDesafio

En application.properties, agrega:
spring.datasource.url=jdbc:postgresql://${DB_HOST}/${DB_NAME}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}

3️⃣ Ejecutar
mvn spring-boot:run 

🏗 Funciones principales
✅ Buscar libros por título, autor o idioma
✅ Guardar libros en la base de datos
✅ Consultar estadísticas por idioma
✅ Interfaz de consola interactiva
✅ Endpoints REST para consulta

🔗 Endpoints REST
🔹 Guardar libro
POST /libros/guardar 
🔹 Buscar libros por idioma

GET /libros/idiomas?idioma=en 
🛠 Tecnologías usadas
•	Java 21
•	Spring Boot
•	PostgreSQL
•	Hibernate (JPA)
•	Maven
•	Gutendex API

![image](https://github.com/user-attachments/assets/9bdbd3bc-26b1-4a96-b4b6-a6d9bf663eec)

![image](https://github.com/user-attachments/assets/94e2e8dc-9adc-4930-ac62-86efe325f44b)

Realizado Por Alexander Gonzalez







