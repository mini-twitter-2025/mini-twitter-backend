# Mini Twitter Backend

Backend REST API para una aplicación estilo Twitter construida con Spring Boot, siguiendo principios de Domain-Driven Design (DDD) con modelos de dominio ricos.

## 📋 Descripción

Este proyecto implementa un backend completo para una aplicación de microblogging con las siguientes características:

- ✅ Gestión de usuarios
- ✅ Creación de tweets (máximo 280 caracteres)
- ✅ Funcionalidad de retweets
- ✅ Timeline home con tweets originales
- ✅ Timeline de usuario (tweets + retweets)
- ✅ Paginación en listados
- ✅ Validaciones de negocio en el dominio
- ✅ Base de datos H2 en memoria

## 🛠️ Tecnologías

- **Java 21**
- **Spring Boot 3.5.4**
- **Spring Data JPA**
- **H2 Database** (en memoria)
- **Maven**
- **JUnit 5** (testing)
- **JaCoCo** (cobertura de código)

## 📁 Estructura del Proyecto

El proyecto incluye un archivo `src/main/resources/data.sql` que carga automáticamente datos de prueba (usuarios y tweets) al iniciar la aplicación.

```
src/
├── main/
│   ├── java/ar/edu/unrn/chamorro/minitwitter/
│   │   ├── model/              # Entidades de dominio
│   │   │   ├── Usuario.java
│   │   │   └── Tweet.java
│   │   ├── repository/         # Repositorios JPA
│   │   │   ├── UsuarioRepository.java
│   │   │   └── TweetRepository.java
│   │   ├── service/            # Lógica de negocio
│   │   │   ├── UsuarioService.java
│   │   │   └── TweetService.java
│   │   ├── web/
│   │   │   ├── dto/            # DTOs para API REST
│   │   │   │   ├── CrearUsuarioRequest.java
│   │   │   │   ├── CrearTweetRequest.java
│   │   │   │   ├── CrearRetweetRequest.java
│   │   │   │   ├── TweetResponse.java
│   │   │   │   └── TweetMapper.java
│   │   │   └── controller/     # Controladores REST
│   │   │       ├── UsuarioController.java
│   │   │       └── TweetController.java
│   │   └── MiniTwitterApplication.java
│   └── resources/
│       ├── application.properties
│       └── test-data.sql
└── test/
    └── java/ar/edu/unrn/chamorro/minitwitter/
        ├── model/              # Tests unitarios de dominio
        │   ├── UsuarioTest.java
        │   └── TweetTest.java
        └── integration/        # Tests de integración
            ├── BaseIntegrationTest.java
            └── TweetWebIntegrationTest.java
```

## 🚀 Inicio Rápido

### Prerrequisitos

- Java 21 o superior
- Maven 3.6+

### Instalación y Ejecución

1. **Clonar el repositorio** (o descargar el proyecto)

2. **Compilar el proyecto**
   ```bash
   mvn clean install
   ```

3. **Ejecutar la aplicación**
   ```bash
   mvn spring-boot:run
   ```

4. **Verificar que está corriendo**
   
   La aplicación estará disponible en: `http://localhost:8080`

## 🧪 Testing

### Ejecutar todos los tests

```bash
mvn test
```

**Resultado esperado**: 17 tests (7 UsuarioTest + 7 TweetTest + 3 TweetWebIntegrationTest)

### Ejecutar tests específicos

```bash
# Solo tests de Usuario
mvn test -Dtest=UsuarioTest

# Solo tests de Tweet
mvn test -Dtest=TweetTest

# Solo tests de integración
mvn test -Dtest=TweetWebIntegrationTest
```

### Ver cobertura de código

```bash
# Generar reporte de cobertura
mvn clean test

# Abrir reporte HTML
start target/site/jacoco/index.html
```

El reporte de cobertura se genera automáticamente en `target/site/jacoco/index.html`

## 📡 API REST Endpoints

### Usuarios

| Método | Endpoint | Descripción | Body |
|--------|----------|-------------|------|
| POST | `/api/usuarios` | Crear usuario | `{"userName":"nombre"}` |
| GET | `/api/usuarios` | Listar todos los usuarios | - |
| DELETE | `/api/usuarios/{id}` | Eliminar usuario | - |

### Tweets

| Método | Endpoint | Descripción | Body |
|--------|----------|-------------|------|
| POST | `/api/tweets` | Crear tweet | `{"usuarioId":1,"texto":"..."}` |
| POST | `/api/tweets/{id}/retweet` | Hacer retweet | `{"usuarioId":2}` |
| GET | `/api/tweets/home?page=0&size=10` | Timeline home (solo originales) | - |
| GET | `/api/tweets/usuario/{id}?page=0&size=15` | Tweets de usuario | - |

## 💻 Ejemplos de Uso

### Usando PowerShell

#### 1. Crear un usuario

```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/usuarios" `
  -Method POST `
  -ContentType "application/json" `
  -Body '{"userName":"alice01"}'
```

#### 2. Crear un tweet

```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/tweets" `
  -Method POST `
  -ContentType "application/json" `
  -Body '{"usuarioId":1,"texto":"Mi primer tweet!"}'
```

#### 3. Ver timeline home

```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/tweets/home" -Method GET
```

#### 4. Hacer un retweet

```powershell
# Primero crear otro usuario
Invoke-RestMethod -Uri "http://localhost:8080/api/usuarios" `
  -Method POST `
  -ContentType "application/json" `
  -Body '{"userName":"bob_user"}'

# Luego hacer retweet del tweet con ID 1
Invoke-RestMethod -Uri "http://localhost:8080/api/tweets/1/retweet" `
  -Method POST `
  -ContentType "application/json" `
  -Body '{"usuarioId":2}'
```

### Usando curl (Git Bash / Linux / Mac)

```bash
# Crear usuario
curl -X POST http://localhost:8080/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{"userName":"testuser"}'

# Crear tweet
curl -X POST http://localhost:8080/api/tweets \
  -H "Content-Type: application/json" \
  -d '{"usuarioId":1,"texto":"Hola mundo!"}'

# Ver timeline
curl http://localhost:8080/api/tweets/home
```

## 🗄️ Base de Datos

### H2 Console

La aplicación incluye la consola H2 para inspeccionar la base de datos:

1. Abrir en el navegador: `http://localhost:8080/h2-console`
2. Configurar la conexión:
   - **JDBC URL**: `jdbc:h2:mem:mini_twitter`
   - **User Name**: `sa`
   - **Password**: (dejar vacío)
3. Click en "Connect"

### Consultas SQL útiles

```sql
-- Ver todos los usuarios
SELECT * FROM usuarios;

-- Ver todos los tweets
SELECT * FROM tweets;

-- Ver tweets con información del autor
SELECT t.id, u.username, t.texto, t.fecha_creacion, t.tweet_origen_id
FROM tweets t
JOIN usuarios u ON t.autor_id = u.id
ORDER BY t.fecha_creacion DESC;

-- Ver solo retweets
SELECT t.id, u.username AS retweeter, t.texto, t.tweet_origen_id
FROM tweets t
JOIN usuarios u ON t.autor_id = u.id
WHERE t.tweet_origen_id IS NOT NULL;
```

## 🏗️ Arquitectura y Diseño

### Domain-Driven Design (DDD)

El proyecto sigue principios de DDD con:

- **Modelos de dominio ricos**: Las entidades (`Usuario`, `Tweet`) contienen lógica de negocio
- **Validaciones en constructores**: Las reglas de negocio se validan al crear objetos
- **Métodos de dominio**: `twittear()`, `retwittear()` en lugar de setters anémicos
- **Constantes de error**: Mensajes de error como constantes con visibilidad de paquete

### Validaciones Implementadas

#### Usuario
- Username obligatorio (no nulo, no vacío)
- Longitud entre 5 y 25 caracteres
- Username único en el sistema

#### Tweet
- Texto obligatorio (no nulo, no vacío)
- Longitud entre 1 y 280 caracteres
- No se puede retwittear un tweet propio
- El tweet original es obligatorio para retweets

## 📊 Cobertura de Tests

El proyecto incluye:

- **7 tests unitarios** para `Usuario`
- **7 tests unitarios** para `Tweet`
- **3 tests de integración** para la capa web

Para ver el reporte de cobertura detallado:

```bash
mvn clean test
start target/site/jacoco/index.html
```

## 🔧 Configuración

### application.properties

```properties
# Nombre de la aplicación
spring.application.name=mini-twitter

# Configuración H2
spring.datasource.url=jdbc:h2:mem:mini_twitter;DB_CLOSE_DELAY=-1;MODE=PostgreSQL
spring.datasource.username=sa
spring.datasource.password=

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

## 📝 Comandos Maven Útiles

```bash
# Compilar sin ejecutar tests
mvn clean compile

# Compilar y ejecutar tests
mvn clean test

# Empaquetar en JAR
mvn clean package

# Ejecutar la aplicación
mvn spring-boot:run

# Generar reporte de cobertura
mvn jacoco:report

# Limpiar archivos compilados
mvn clean
```

## 🎯 Características Destacadas

1. **Dominio Rico**: Las entidades no son simples contenedores de datos, sino que encapsulan comportamiento
2. **Inmutabilidad**: Los métodos de acceso devuelven copias inmutables cuando es necesario
3. **Validación Temprana**: Las validaciones ocurren en los constructores, no permitiendo estados inválidos
4. **Separación de Responsabilidades**: Clara separación entre capas (dominio, servicio, web)
5. **Testing Completo**: Tests unitarios para dominio y tests de integración para la capa web
6. **Paginación**: Soporte para paginación en los endpoints de listado

## 👥 Autor

Proyecto desarrollado como trabajo práctico para la materia de Taller de Tecnologías y Producción de Software.

## 📄 Licencia

Este proyecto es de código abierto y está disponible para fines educativos.

---

