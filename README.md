# 🍷 Viña Cascas - Gestión de Vinos (API)

API RESTful desarrollada con Java 17 y Spring Boot, usando MySQL para gestionar de manera eficiente compras, ventas e inventario de vinos de Viña Cascas.

---
## 🚀 Características

- 📦 **Gestión de productos**
	- Registro, edición y eliminación de vinos
	- Control de stock disponible

- 🛒 **Gestión de compras**
	- Registro de ingresos de productos
	- Actualización automática del inventario

- 💰 **Gestión de ventas**
	- Registro de ventas
	- Descuento automático del stock

- 📊 **Control de inventario**
	- Actualización dinámica del stock
	- Seguimiento de movimientos de productos

- 📄 **Reportes básicos**
	- Consulta de historial de compras y ventas

- 🔒 **API preparada para integración**
	- Endpoints REST listos para consumir desde frontend (Angular, React, etc.)
    - 

--- 
## 🛠 Tecnologías

| Tecnología           | Descripción |
|----------------------|-------------|
| **Java 17+**         | Lenguaje principal |
| **Spring Boot**      | Framework para desarrollo de APIs |
| **Spring Data JDBC** | Persistencia y acceso a datos |
| **MySQL**            | Base de datos relacional |
| **Maven / Gradle**   | Gestión de dependencias |
| **REST API**         | Arquitectura de comunicación |

--- 

## 📂 Estructura del proyecto

```
src/main/java/com/gamm/vinos_api

├── config                  # Configuraciones generales (CORS, WebConfig, etc.)
│ 
├── controller              # Controladores REST de la API
│ 
├── domain                  # Modelo del dominio
│   ├── model               # Entidades principales (Usuario, Producto, etc.)
│   └── enums               # Enumeraciones del sistema
│ 
├── dto                     # Objetos de transferencia de datos
│   ├── cbo                 # DTOs de salida para combos o selects (requests)
│   ├── request             # DTOs de entrada (requests)
│   ├── response            # DTOs de salida (responses)
│   │   └── ResponseVO
│   └── view	            # DTOs de salida (queries)
│ 
├── exception               # Manejo global de excepciones
│   ├── business            # Excepciones de lógica de negocio
│   ├── handler             # GlobalExceptionHandler
│   └── security            # Excepciones relacionadas con seguridad
│ 
├── jdbc                    # Infraestructura JDBC
│   ├── base                # Plantilla base para acceso a datos
│   │   └── SimpleJdbcDAOBase
│   └── rowmapper           # Mapeadores ResultSet → Objetos
│ 
├── repository              # Acceso a datos
│   └── impl                # Implementaciones personalizadas
│ 
├── security                # Módulo de seguridad (JWT + autenticación)
│   ├── annotations         # Anotaciones personalizadas (SoloAdministrador, etc.)
│   ├── config              # Configuración de Spring Security
│   ├── controller          # Controlador de autenticación (AuthController)
│   ├── dto                 # DTOs de autenticación
│   ├── jwt                 # Manejo de tokens JWT
│   ├── service             # Servicios de autenticación
│   └── util                # Clases auxiliares de seguridad
│ 
├── service                 # Lógica de negocio
│   └── impl                # Implementaciones de servicios
│ 
└── util                    # Utilidades generales
    └── ResultadoSP         # Objeto estándar de respuesta para SP
```


---

## ⚙️ Configuración del proyecto

### 1️⃣ Clonar el repositorio

```bash
git clone https://github.com/tu-usuario/vina-cascas-api.git
cd vina-cascas-api
```

2️⃣ Configurar la base de datos
```bash
src/main/resources/application.properties
```
---
# 👨‍💻 Autor

- Desarrollado por Jhonatan
- Backend Developer — Java & Spring Boot