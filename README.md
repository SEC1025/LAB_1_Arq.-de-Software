[![CI/CD Pipeline](https://github.com/SEC1025/LAB_1_Arq.-de-Software/actions/workflows/build.yml/badge.svg)](https://github.com/SEC1025/LAB_1_Arq.-de-Software/actions/workflows/build.yml)

[![Known Vulnerabilities](https://snyk.io/test/github/SEC1025/LAB_1_Arq.-de-Software/badge.svg)](https://snyk.io/test/github/SEC1025/LAB_1_Arq.-de-Software)

Backend del Laboratorio 1 (banco) con el pipeline del Laboratorio 2.

* Consultar y crear clientes
* Transferir entre cuentas y ver el historial
* Health check
* Versión de la aplicación

Incluye GitHub Actions, SonarCloud, JaCoCo, Docker Hub, Render y Snyk.

### Estructura

En `src/main` está el código de la aplicación.
En `src/test` están las pruebas.
En `.github/workflows/build.yml` está el pipeline. Se ejecuta en `main`, `backend` y `mi-rama`.

### Cómo ejecutarla

Con MySQL local (`banco2025`, usuario `root`):

```shell
./mvnw spring-boot:run
```

En Windows:

```shell
mvnw.cmd spring-boot:run
```

Sin MySQL, con base en memoria:

```shell
mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=h2
```

### Cómo probarla

```shell
mvnw.cmd clean test
```

Cobertura JaCoCo (HTML en `target/site/jacoco/index.html`):

```shell
mvnw.cmd -B test
```

### Endpoints

| Método | Ruta | Uso |
|---|---|---|
| GET | `/health` | Estado de la app |
| GET | `/version` | Versión |
| GET | `/api/customers` | Listar clientes |
| GET | `/api/customers/{id}` | Cliente por id |
| POST | `/api/customers` | Crear cliente |
| POST | `/api/transactions` | Transferencia |
| GET | `/api/transactions/{accountNumber}` | Historial |

### Despliegue

La imagen Docker se publica como `DOCKER_USERNAME/lab1v20206:latest`.
Render usa `render.yaml`. Para la demo, la variable `SPRING_PROFILES_ACTIVE=h2` levanta la app sin MySQL. Si tienes MySQL en la nube, quita ese perfil y define `DB_URL`, `DB_USER` y `DB_PASSWORD`.
