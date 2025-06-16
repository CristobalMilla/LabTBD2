# LabTBD2

**Trabajo de laboratorio 2 - Taller de Base de Datos 1-2025**  
Este proyecto contiene un sistema completo con Backend y Frontend para manejar información geoespacial. A continuación, se describe el paso a paso para la ejecución.

---

## Base de Datos

1. **Ejecutar el contenido de `no.sql`** en la base de datos `lab2TBD`.

2. **Crear la base de datos:**

   ```sql
   CREATE DATABASE lab2TBD;
   ```

3. **Importar archivo `calles_cleaned`:**  
   Ubicado en la carpeta `archivos importar`. Luego, ejecutar:

   ```sql
   SELECT pgr_createTopology('calles_cleaned', 0.0001, 'geom', 'cleaned_street_id');
   ```

3. **Importar archivo vertices_pgr** también ubicado en `archivos importar`.


---

## Backend

1. **Configurar el archivo `application.properties`:**  
   Crear este archivo en:

   ```
   LabTBD2/Lab2BackEnd/src/main/resources/application.properties
   ```

   Ejemplo:

   ```properties
   spring.application.name=Lab2

   DB_NAME=lab2TBD
   DB_USERNAME=postgres
   DB_PASSWORD=tu_contraseña

   spring.datasource.url=jdbc:postgresql://localhost:5432/lab2TBD
   spring.datasource.username=postgres
   spring.datasource.password=tu_contraseña
   server.port=8080
   ```

2. **Ejecutar `entrega.sql`:**  
   Ubicado en la carpeta principal del proyecto (`LabTBD2`). Puedes:

   - Ejecutarlo manualmente en PostgreSQL (crear y poblar tablas), **o**
   - Ejecutarlo directamente desde IntelliJ si tu proyecto está conectado a la base de datos.

3. **Ejecutar la clase `Lab2Application.java`.**

---

## Frontend

1. Abrir una terminal y dirigirse al directorio:

   ```bash
   cd LabTBD2/Lab2Frontend/
   ```

2. Instalar dependencias:

   ```bash
   npm install
   ```

3. Ejecutar el entorno de desarrollo:

   ```bash
   npm run dev
   ```

4. Abrir en el navegador:

   ``` importar 
   http://localhost:5173
   ```

---

## Requisitos

- PostgreSQL con extensión PostGIS y Pgrouting.
- Node.js y npm.
- IntelliJ IDEA o cualquier entorno Java compatible con Spring Boot.

---
