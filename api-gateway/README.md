# api-gateway

Servicio Spring Boot que actúa como punto de entrada único para el ecosistema de microservicios. Combina dos responsabilidades: **enrutamiento dinámico** vía Spring Cloud Gateway y **balanceo de carga** automático vía Spring Cloud LoadBalancer, ambos integrados con Eureka.

---

## ¿Qué hace?

- Recibe todas las peticiones externas y las redirige al microservicio correcto según el nombre registrado en Eureka.
- Balancea la carga automáticamente entre instancias del mismo servicio.
- No requiere definir rutas en código: se auto-descubren desde Eureka.

**Patrón de URL:**
```
http://localhost:<puerto>/api-gateway/<nombre-servicio>/<endpoint>
```

Ejemplo:
```
GET /api-gateway/user-service/api/users
```

---

## Dependencias

| Dependencia | Función |
|---|---|
| `spring-boot-starter-actuator` | Health checks y métricas del gateway |
| `spring-cloud-starter-gateway` (WebFlux) | Gateway reactivo — enrutamiento de peticiones |
| `spring-cloud-starter-loadbalancer` | Balanceo de carga entre instancias |
| `spring-cloud-starter-netflix-eureka-client` | Registro y descubrimiento de servicios |

---

## Configuración principal

### `application.yml`

```yaml
spring:
  application:
    name: api-gateway
  cloud:
    gateway:
      server:
        webflux:
          discovery:
            locator:
              enabled: true
              lower-case-service-id: true

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka
```

**`discovery.locator.enabled: true`** — habilita el auto-descubrimiento: el gateway lee el registro de Eureka y crea rutas dinámicamente para cada servicio sin que tengas que definirlas en código.

**`lower-case-service-id: true`** — normaliza el nombre del servicio a minúsculas en la URL (Eureka los registra en mayúsculas por defecto).

---

## ⚙️ VM Options en IntelliJ

Cuando corres múltiples instancias de un servicio, usás las VM Options para configurarlas de forma independiente sin tocar el `application.yml`.

IntelliJ concatena estas opciones al comando `java` que ejecuta el `.jar`:

```bash
java -Dserver.port=0 -Deureka.instance.instance-id=... -jar app.jar
```

### Opciones comunes

| VM Option | Efecto |
|---|---|
| `-Dserver.port=0` | Puerto aleatorio — evita conflictos al levantar varias instancias |
| `-Deureka.instance.instance-id=${spring.application.name}:${random.uuid}` | ID único por instancia en el registro de Eureka |

### Flujo interno

```
IntelliJ agrega -D al comando java
        ↓
JVM guarda como System Properties
        ↓
Spring Boot las lee como configuración
```

> Las VM Options no modifican el `application.yml` — solo sobreescriben o agregan propiedades en tiempo de ejecución.

---

## Levantar múltiples instancias (IntelliJ)

1. `Run > Edit Configurations` → duplicar la configuración del servicio destino.
2. En cada copia agregar en **VM Options**:
   ```
   -Dserver.port=0
   -Deureka.instance.instance-id=${spring.application.name}:${random.uuid}
   ```
3. Correr ambas configuraciones — Eureka las registra como instancias distintas.
4. El LoadBalancer del gateway distribuye las peticiones entre ellas automáticamente.