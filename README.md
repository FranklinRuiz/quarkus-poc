# Microservicio de Gestión de Cuentas Bancarias con Quarkus

Este proyecto es una **prueba de concepto (POC)** que implementa un microservicio RESTful para la gestión de cuentas bancarias utilizando **Quarkus**, un framework Java cloud-native optimizado para entornos de alto rendimiento y compilación nativa con **GraalVM**.

> ⚠️ Este proyecto tiene fines demostrativos y educativos. No está listo para producción sin revisiones adicionales de seguridad, validación y escalabilidad.

---

## 📚 Tabla de Contenidos

- [Descripción del Proyecto](#descripción-del-proyecto)
- [Arquitectura del Proyecto](#arquitectura-del-proyecto)
- [Tecnologías Utilizadas](#tecnologías-utilizadas)
- [Requisitos Previos](#requisitos-previos)
- [Ejecución de la Aplicación](#ejecución-de-la-aplicación)
- [Despliegue con Docker](#despliegue-con-docker)
- [API REST](#api-rest)
- [Recursos Adicionales](#recursos-adicionales)

---

## 📄 Descripción del Proyecto

El microservicio implementa las siguientes operaciones para la gestión de cuentas bancarias:

- Crear cuentas
- Actualizar información de cuentas
- Consultar cuentas por ID
- Listar todas las cuentas
- Eliminar cuentas
- Activar y desactivar cuentas

---

## 🧱 Arquitectura del Proyecto

Este proyecto sigue los principios de la **Clean Architecture** con enfoque modular, separación de responsabilidades y uso del **patrón Hexagonal**.

### 🔹 Capas de la Aplicación

1. **Dominio (`domain`)**
  - Entidades, objetos de valor y reglas de negocio
  - Completamente independiente de frameworks

2. **Aplicación (`application`)**
  - Casos de uso y orquestación de lógica
  - Define puertos para comunicación externa

3. **Infraestructura (`infrastructure`)**
  - Controladores REST
  - Repositorios y adaptadores para tecnologías externas

### 📁 Estructura de Paquetes

```
pe.poc.account
├── domain           → Entidades y lógica de negocio
├── application      → Casos de uso, DTOs, puertos
└── infrastructure   → REST API y persistencia

pe.poc.common
└── exception        → Manejo global de errores
```

### 🧩 Patrones de Diseño Utilizados

- **Arquitectura Hexagonal (Ports and Adapters)**: Enfoque arquitectónico para aislar el núcleo de negocio de detalles técnicos
- **Dependency Injection (CDI)**
- **Builder Pattern**
- **Repository Pattern**

---

## 🛠️ Tecnologías Utilizadas

- **Java 21+**
- **Quarkus** (RESTEasy Reactive, Hibernate Panache)
- **H2 Database** (modo desarrollo)
- **Docker / Docker Compose**
- **GraalVM** (compilación nativa)
- **Maven Wrapper** (`./mvnw`)

---

## ✅ Requisitos Previos

- Java 17
- Maven 3.8+
- Docker (si se desea desplegar en contenedores)

---

## 🚀 Ejecución de la Aplicación

### ▶️ Modo Desarrollo (hot reload)

```bash
./mvnw quarkus:dev
```

> Accede a la UI de desarrollo: [http://localhost:8080/q/dev/](http://localhost:8080/q/dev/)

---

### 📦 Empaquetado y Ejecución

#### Empaquetado JVM

```bash
./mvnw package
java -jar target/quarkus-app/quarkus-run.jar
```

#### Empaquetado Über-jar

```bash
./mvnw package -Dquarkus.package.jar.type=uber-jar
java -jar target/*-runner.jar
```

---

### ⚙️ Ejecutable Nativo

Si tienes GraalVM instalado:

```bash
./mvnw package -Dnative
```

Si **no** tienes GraalVM:

```bash
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

Para ejecutar el binario:

```bash
./target/poc-quarkus-1.0-SNAPSHOT-runner
```

---

## 🐳 Despliegue con Docker

### Modo JVM

```bash
./mvnw package
docker-compose up -d
```

Aplicación disponible en: [http://localhost:8080](http://localhost:8080)

---

### Modo Nativo

```bash
./mvnw package -Pnative "-Dquarkus.native.container-build=true"
docker-compose -f docker-compose-native.yml up -d
```

---

## 📡 API REST

Los endpoints están disponibles en `/api/accounts`:

| Método | Endpoint                               | Descripción         |
|--------|----------------------------------------|---------------------|
| POST   | `/api/accounts`                        | Crear cuenta        |
| GET    | `/api/accounts`                        | Listar cuentas      |
| GET    | `/api/accounts/{id}`                   | Obtener cuenta por ID |
| PUT    | `/api/accounts/{id}`                   | Actualizar cuenta   |
| DELETE | `/api/accounts/{id}`                   | Eliminar cuenta     |
| PUT    | `/api/accounts/{id}/activate`          | Activar cuenta      |
| PUT    | `/api/accounts/{id}/deactivate`        | Desactivar cuenta   |

---

## 📘 Recursos Adicionales

- [Documentación oficial de Quarkus](https://quarkus.io/guides/)
- [Guía REST con Quarkus](https://quarkus.io/guides/rest)
- [Compilación nativa con GraalVM](https://quarkus.io/guides/building-native-image)