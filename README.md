# 🛒 E-commerce Microservices Architecture

## 📋 Descripción General

Sistema de e-commerce basado en arquitectura de microservicios desarrollado con **Spring Boot 4.0.3**. Cada microservicio es independiente y se especializa en una funcionalidad específica del negocio.

## 🏗️ Arquitectura Técnica

### **Versión de Spring Boot:** `4.0.3`
### **Java Version:** `25`
### **Build Tool:** Maven

## 🚀 Microservicios

### 1. **📦 Product Service** (`product-service`)
- **Descripción:** Catálogo de productos
- **Base de Datos:** MongoDB
- **Tecnologías:**
  - Spring Boot Starter Data MongoDB
  - Spring Boot Starter Web MVC
  - Spring Boot Starter Validation
- **Puerto:** Configurable (default: 8080)

### 2. **📋 Order Service** (`order-service`)
- **Descripción:** Gestión de pedidos
- **Base de Datos:** PostgreSQL
- **Tecnologías:**
  - Spring Boot Starter Data JPA
  - Spring Boot Starter Web MVC
  - PostgreSQL Driver
- **Puerto:** Configurable (default: 8080)

### 3. **📊 Inventory Service** (`inventory-service`)
- **Descripción:** Control de stock e inventario
- **Base de Datos:** MySQL
- **Tecnologías:**
  - Spring Boot Starter Data JPA
  - Spring Boot Starter Web MVC
  - MySQL Connector
- **Puerto:** Configurable (default: 8080)

### 4. **🔔 Notification Service** (`notificaction-service`)
- **Descripción:** Sistema de notificaciones
- **Base de Datos:** Sin base de datos persistente (mensajería)
- **Tecnologías:**
  - Spring Boot Starter AMQP (RabbitMQ)
  - Spring Boot Starter Mail
  - Spring Boot Starter Web MVC
- **Puerto:** Configurable (default: 8080)


## 🛠️ Configuración y Ejecución

### Prerrequisitos
- Java 25
- Maven 3.6+
- Bases de datos:
  - MongoDB
  - PostgreSQL
  - MySQL
  - RabbitMQ (para notificaciones)

### Ejecutar un microservicio
```bash
cd product-service
mvn spring-boot:run
```

### Construir todos los microservicios
```bash
mvn clean install
```
## 📊 Resumen de Tecnologías

| Microservicio | Base de Datos | Framework | Comunicación |
|---------------|---------------|-----------|--------------|
| Product Service | MongoDB | Spring Boot 4.0.3 | REST |
| Order Service | PostgreSQL | Spring Boot 4.0.3 | REST |
| Inventory Service | MySQL | Spring Boot 4.0.3 | REST |
| Notification Service | - | Spring Boot 4.0.3 | AMQP/REST |

