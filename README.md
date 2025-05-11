# LabTBD2

Trabajo de laboratorio 2 para proyecto de Taller de Base de Datos 1-2025. Paso a paso para la ejecución:

1. Primero hay que crear un archivo en \LabTBD2\Lab2BackEnd\src\main\resources llamado application.properties, parecido al siguiente ejemplo. Debes reemplazar tu contraseña de la base de datos en .password y tu usuario de postgres en .username

spring.application.name=Lab2
server.port=8090
spring.datasource.url=http://localhost:5432/lab2tbd
spring.datasource.username=postgres
spring.datasource.password={tu-contraseña}

2. Luego, en la carpeta Confing en la dirección LabTBD2\Lab2BackEnd\src\main\java\Grupo4\Lab2\Config accedes al archivo Sql2oConfig.java y de nuevo reemplazas tus credenciales

3. En el archivo entrega.sql dentro de la carpeta LabTBD2 ejecutas la creación de tablas en postgres, luego ejecutas los insert a las tablas (poblarlas), o si conectaste la base de datos en el Intelliji solo corres el programa entrega.sql.

4. Ahora, ejecutas el programa Lab2Application.java y ya puedes realizar las consultas. Estas se pueden realizar a través de Postman, ingresando las rutas de los controlladores e indicando el tipo de consulta(post, delete, get, etc), ej: DELETE http://localhost:8090/api/detalle-pedidos/delete/16
