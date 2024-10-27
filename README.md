# LABORATORIO 4

### Implementación


La implementación consta de tres componentes principales:

**Controlador**: La clase TaskController maneja las solicitudes HTTP para las tareas, mapeándolas a métodos para recuperar, crear, actualizar y eliminar tareas. Utiliza anotaciones como @GetMapping y @PostMapping para gestionar las interacciones con el cliente.

**Servicio**: La clase TaskService contiene la lógica de negocio para la gestión de tareas. Interactúa con el TaskRepository para realizar operaciones CRUD y encapsula la lógica de manejo de tareas, lo que facilita las pruebas y el mantenimiento.

**Repositorio**: La interfaz TaskRepository extiende MongoRepository, proporcionando métodos para el acceso a datos en MongoDB. Permite que la capa de servicio realice operaciones en la base de datos sin implementar la lógica subyacente.


### Metodología TDD

**Añadir Tareas**\
Se realizaron las pruebas necesarias para el método `addTask`

![image](https://github.com/user-attachments/assets/e5b8ae30-7666-406a-b2d0-666b3095664e)

Posteriormente se realizó su implementación para que las pruebas pasaran.

![image](https://github.com/user-attachments/assets/4163de7b-fe14-4d8a-8460-f02e5434cc03)

**Marcar Tareas como completadas**\
Se realizaron las pruebas necesarias para el método `markAsCompleted`

![image](https://github.com/user-attachments/assets/9760c60e-91d5-41a1-8a96-36f7c7278e5b)

Posteriormente se realizó su implementación para que las pruebas pasaran.

![image](https://github.com/user-attachments/assets/8aac1f5b-4e9e-42da-94c1-160a14deea5f)

**Eliminar Tareas**\
Se realizaron las pruebas necesarias para el método `deleteTask`

![image](https://github.com/user-attachments/assets/cfddcd37-ea01-4f23-a29e-ccc110a5a9aa)

Posteriormente se realizó su implementación para que las pruebas pasaran.

![image](https://github.com/user-attachments/assets/8aac1f5b-4e9e-42da-94c1-160a14deea5f)


## Pruebas de unidad y cobertura en Jacoco


Se crean las pruebas de unidad sin el contexto de Spring .
Se modifica el pom para incluir el reporte de cobertura de Jacoco.

Después de implementar el código se obtiene el siguiente reporte:

![image](https://github.com/user-attachments/assets/b52f5638-5c06-4e04-ac1a-8c586c719b59)


## Conexión a Base de Datos en MongoDB Atlas

**Configuración**

Se crea una base de datos llamada `taskManager`.
Luego, una colección llamada `tasks`.

Se asigna a un único usuario todos los recursos del Proyecto:

![image](https://github.com/user-attachments/assets/65be6963-6dae-449f-8dac-958b8b5b1248)


Se configura acceso de red: 

![image](https://github.com/user-attachments/assets/f2e1e94e-28c0-402b-b2cd-e4a00e13e343)

En `application.propierties` se añade la siguiente línea de código para conectarse a la base de datos ya creada:

```sh
spring.data.mongodb.uri=mongodb+srv://admin:admin@cluster0.ftr2l.mongodb.net/taskManager?retryWrites=true&w=majority&appName=Cluster0
```

Se define el siguiente puerto:

```sh
server.port=8081
```

## Conexión a Base de Datos en PostgreSQL - Local

Para integrar PostgreSQL en el proyecto backend con Spring Boot, primero necesitamos agregar las siguientes dependencias en el archivo `pom.xml`

![image](https://github.com/user-attachments/assets/be1713c4-107f-4eea-bbeb-82f3d13d416b)

Configuramos la Conexión a PostgreSQL en application.properties

![image](https://github.com/user-attachments/assets/139426f4-9702-4a25-bae5-0d1fa257ab6a)

Y finalmente en psql o pgAdmin de postgreSQL, creamos la base de datos y las tablas

![image](https://github.com/user-attachments/assets/18fdcf33-7e4d-4c25-bc77-b0fad06f61c9)
![image](https://github.com/user-attachments/assets/add2fe04-b9ef-4f4a-9dc2-db0524b8274f)


### Pruebas con PostMan

Primero, se ejecuta la aplicación con:

```sh
mvn spring-boot:run
```

Desde PostMan se crea nueva colección y tantas nuevas solicitudes que se requieran:

***GET***

Se obtiene de la colección task el único documento que hay:

![image](https://github.com/user-attachments/assets/7a573a7e-631b-47c5-9f8c-8a9eaaf5bb24)

***PUT***

Se define como completada la tarea anterior con su respectivo `id`:

![image](https://github.com/user-attachments/assets/7546d05f-314b-4259-a121-d2143c9fcea1)


***DELETE***

Se elimina la única tarea:

![image](https://github.com/user-attachments/assets/16846a5d-5ab9-4ec3-a23c-93b8de960283)


Se valida que ya no hay tareas:

![image](https://github.com/user-attachments/assets/e4eab38e-cd7d-4d9f-9aec-93a5f494c264)


Se hace prueba con POST:

![image](https://github.com/user-attachments/assets/7a20ecdd-a5f8-4e6a-967a-fe96caa6e535)

Luego, se verifica en la base de datos en MongDB Cloud:

![image](https://github.com/user-attachments/assets/b9e6e7fb-ad64-4412-bb15-ee8c11293adf)


# LABORATORIO 5

## PARTE I. DEVOPS / CI-CD

### Creando los Pipelines (CI - Continous Integration)

- Primero se empieza creando las variables necesarias dentro de "secrets and variables" en la configuracion del repositorio

![imagen](https://github.com/user-attachments/assets/e8f103a3-ebac-4267-87bb-1215d7e442c7)

- Ahora configuramos un "Action" que pueda desplegar los trabajos solicitados por el laboratorio y además despliegue la variable que acabamos de crear:

- Empezamos por el "Job" de construcción:

![imagen](https://github.com/user-attachments/assets/693a5ea1-58ac-49ae-9dbc-2cb7b532f1d8)

- Ahora realizamos la logica necesaria para que se ejecuten las pruebas:

![imagen](https://github.com/user-attachments/assets/7eabed12-2311-4759-bc7f-22736476c274)

- Finalmente hacemos un "deploy" pero momentaneamente solo ejecutara la linea: "En construcción":

![imagen](https://github.com/user-attachments/assets/f24ed757-4900-40ef-b8fd-cfc47506238d)

- Verificamos que al hacer un commit se esten ejecutando las pruebas:

![imagen](https://github.com/user-attachments/assets/c793a317-b851-4ee8-8ec9-a5f4b75b3a14)

#### Creacion de test pedidos en el laboratorio:

- Iniciamos creando la clase de pruebas "TaskServiceTest"

- Implementamos la logica necesaria de cada test para que se ejecute y verificamos que lo haga correctamente.

![imagen](https://github.com/user-attachments/assets/a816390f-b0a9-43f7-bf35-1c158458e8b0)

### Desplegando en Azure usando CI/CD (Continous Deployment / Continous Delivery)

#### Conexión a Base de Datos Postgres - Azure


Para este proceso se crea un nuevo recurso de tipo `Azure Database for PostgreSQL flexible servers`:


![image](https://github.com/user-attachments/assets/d91e3974-5440-42e2-8f1b-40363786871b)


Se realiza la configuración con las mínimas características: 

![image](https://github.com/user-attachments/assets/08b9a995-facd-403d-a29c-988e8395f09a)


Se crea la base de datos `taskmanagerdb`:

![image](https://github.com/user-attachments/assets/2352b5af-60f1-402b-9fef-3531ef89b6a9)


Desde Cloud Shell se accede a la base de datos y se procede a crear la tabla `tasks`:

```sh
CREATE TABLE tasks (id SERIAL PRIMARY KEY, description TEXT NOT NULL, completed BOOLEAN DEFAULT FALSE);
```

Se hace una correción del tipo para id:

```sh
ALTER TABLE tasks ALTER COLUMN id TYPE VARCHAR;
```

Luego el usuario con la respectiva clave de acceso:

```sh
CREATE USER user_taskmanager WITH PASSWORD 'taskmanager';
```

Y se otorgan privilegios a este usuario:

```sh
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO user_taskmanager;
```

```sh
GRANT ALL PRIVILEGES ON DATABASE taskmanagerdb TO user_taskmanager;
```

Se realizan los siguientes ajustes en el proyecto:

- En `application.properties`

Se modifica la línea correspondiente a la cadena de conexión para PostgreSQL con:

```sh
spring.datasource.url=jdbc:postgresql://taskmanagerdb.postgres.database.azure.com:5432/taskmanagerdb?sslmode=require
```


- En `edu.eci.cvds.taskManager.databasePostgres`

De igual manera, se cambiar la URL por la misma anterior ya que se pasa de tener la base de datos en local a alojarla en la nube con Azure.


#### Validaciones

- Base de datos con Postgres - Relacional
  
Desde Cloud Shell se valida con consultas la información mostrada en la interfaz gráfica:

![image](https://github.com/user-attachments/assets/e9242d23-a210-407f-9bdb-6190e9d3e515)


- Base de datos con MongoDB - No Relacional

![image](https://github.com/user-attachments/assets/f5e692fc-7f7a-45b9-8275-04f6c218628f)


La información registrada en ambas bases de datos corresponde a lo que se ve en interfaz gráfica:

![image](https://github.com/user-attachments/assets/54d664ea-d39a-4bc4-8103-b1790f58d1fb)


## DESPLIEGUE EN AZURE

Se crea App Service para la capa de backend:

![image](https://github.com/user-attachments/assets/70698c10-55c3-4c10-90e4-9b670c7d6e64)

En `Deployment Center - settings` se realiza la vinculación con el repositorio:

![image](https://github.com/user-attachments/assets/3794578a-cd86-4073-bf05-09426c9feb6f)

Se descarga el `publish profile`:


![image](https://github.com/user-attachments/assets/7fdcb828-382b-48b1-b32d-5126917c0d03)



Seguido a esto, se actualizan variables de entorno:

![image](https://github.com/user-attachments/assets/308c3fa3-b370-4b81-a1cc-8969e1de61bb)

Se configura desde `Settings` del repositorio un nuevo secreto cuyo valor corresponde al contenido del `publish profile`:

![image](https://github.com/user-attachments/assets/40909276-bcc9-4bcd-b887-ddbe922c461b)



Y se actualiza también el archivo YAML:

![image](https://github.com/user-attachments/assets/bb566847-81f3-433c-9a5c-e709276bb115)



## PARTE II GRÁFICOS

### Nuevas características de Task

Añadir 3 nuevos atributos para Task, nivel de dificultad, prioridad y tiempo promedio de desarrollo. Para esto se adaptaron las pruebas (y se añadieron más para cubrir las nuevas características) e implementación, así como las clases responsables de trasladar la información de los objetos a la base de datos.
Probando la refactorización:

![image](https://github.com/user-attachments/assets/ec5db27d-5463-4ffa-8fd6-9b16eee065f8)

Covertura de Jacoco:

![image](https://github.com/user-attachments/assets/ea44128d-9f19-4239-9ef7-c2808106bfd3)



### Tareas aleatorias

Para generar tareas aleatorias se creó en TaskService y en TaskContoller el método 

```sh
generateRandomTasks()
```

#### En TaskService

Se implementa la lógica necesaria para realizar este método:

![image](https://github.com/user-attachments/assets/4a8daadd-fd82-4a27-bff2-32524e1ca553)


#### En TaskController

Hace el llamado al método en TaskService:

![image](https://github.com/user-attachments/assets/b0d695a1-4ad4-4a23-b02b-43614294dc32)


### Bibliotecas de Gráficos

Para elegir la biblioteca de gráficos adecuada para el proyecto, primero revisemos las características de algunas opciones populares como Chart.js, D3.js, Google Charts, y c3.js, evaluando su facilidad de uso, flexibilidad y soporte.

| **Biblioteca**   | **Pros**                                                                                                                                                                                                                   | **Contras**                                                                                                                                                                                                                             |
|------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **D3.js**        | - Gran flexibilidad y control total sobre la visualización. <br> - Amplia personalización y capacidad de integración con otras bibliotecas. <br> - Soporte para manipulación directa del DOM y creación de gráficos interactivos y complejos. <br> - Comunidad activa y extensa documentación. | - Curva de aprendizaje alta. <br> - Requiere conocimientos profundos de JavaScript y SVG. <br> - Crear gráficos sencillos puede ser más complejo en comparación con otras bibliotecas.                                                                                         |
| **C3.js**        | - Basado en D3.js, pero con una API más sencilla para la creación de gráficos comunes. <br> - Ideal para desarrolladores que no necesitan la flexibilidad completa de D3.js. <br> - Buena documentación y ejemplos.                                                | - Menor flexibilidad que D3.js. <br> - Al ser una capa superior de D3, la optimización puede ser menor en gráficos más complejos. <br> - Personalización limitada en comparación con D3.js.                                                                                      |
| **Chart.js**     | - Muy fácil de usar y con una curva de aprendizaje baja. <br> - Soporte para gráficos comunes como barras, líneas, pie, entre otros. <br> - Opciones de animación y capacidad para añadir plugins personalizados. <br> - Buena integración con frameworks modernos (React, Angular).                      | - Personalización limitada comparada con D3.js. <br> - Menor soporte para gráficos personalizados y gráficos complejos. <br> - Puede volverse lento para conjuntos de datos muy grandes.                                                                                         |
| **Google Charts**| - Sencillo de usar y fácil de integrar con cualquier proyecto. <br> - Gráficos atractivos y predefinidos con buena estética. <br> - Capacidad para integrar datos directamente desde Google Sheets y otras plataformas de Google. <br> - Interactividad y accesibilidad. | - Requiere acceso a la API de Google Charts, lo que puede no ser ideal para aplicaciones sin acceso a Internet. <br> - Limitaciones en la personalización y flexibilidad en comparación con D3.js. <br> - Dependencia de los servicios de Google y problemas de privacidad.        |

Basado en las características del proyecto y las gráficas que necesitamos crear, **Chart.js** es la mejor opción.

### Implementacion de Charts.js

Para realizar la implementacion primero creamos la nueva pagina insights junto a sus respectivos .css y .js

![image](https://github.com/user-attachments/assets/3e3f6717-8154-4430-a0f8-92a0b6a66f9b)

Ahora realizamos la estructura principal de la pagina y incluimos Chart.js en la Página

![image](https://github.com/user-attachments/assets/5ffb6a41-0e55-47bf-98c2-cd4029bbb609)

Para mayor comodidad agregamos y diseñamos un menu lateral funcional que permita acceder a ambos modulos (Tasks y Insights) desde la pagina principal

![image](https://github.com/user-attachments/assets/42c3a7e0-23a5-4ef6-9f9c-37c86fe2e1f5)

Y finalmente se implementa los graficos desde el javascript de insights, abstrayendo y calculando los datos provenientes de MongoBD o PostgreSQL

![image](https://github.com/user-attachments/assets/5a9e38ab-20a8-4a77-8c80-302251cac036)

# LABORATORIO - SEGURIDAD

### Autenticación

Se crea la clase `AuthController` para manejar las solicitudes relacionadas con la autenticación de los usuarios.

La clase `SecurityConfig` para configurar la seguridad de la aplicación.

Y la clase `UserDetailsServiceImpl` que implementa `UserDetailsService` y proporciona la lógica para cargar los datos de usuario para la autenticación.


### Roles en TaskManager

Se definen los siguientes roles en la aplicación:

Usuario: Administración de tareas.

Administrador: Acceso a gráficas.


Posteriomente, se crea la tabla correspondiente a los roles en la base de datos relacional:

```sh
CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);
```

Se registran los roles:

```sh
INSERT INTO roles (name) VALUES ('USER'), ('ADMIN');
```

Se actualiza la tabla `users`:

```sh
ALTER TABLE users ADD COLUMN role_id INTEGER REFERENCES roles(id);
```

Y se asignan de la siguiente manera a ciertos usuarios sus roles respectivos:

```sh
UPDATE users SET role_id = (SELECT id FROM roles WHERE name = 'USER') WHERE username = 'juanMedina';
UPDATE users SET role_id = (SELECT id FROM roles WHERE name = 'USER') WHERE username = 'haiderRodriguez';
UPDATE users SET role_id = (SELECT id FROM roles WHERE name = 'ADMIN') WHERE username = 'lauraRodriguez';
```


### Generación de certificado autofirmado

Con el siguiente comando se crea el formato de almacén de claves PKCS12:

```sh
keytool -genkeypair -alias baeldung -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore baeldung.p12 -validity 3650
```

**Nota:**
PKCS12: Public Key Cryptographic Standards es un formato protegido por contraseña que puede contener múltiples certificados y claves; es un formato utilizado en toda la industria.

![image](https://github.com/user-attachments/assets/4542f7cd-59d4-4df6-9abf-3993485ab709)


![image](https://github.com/user-attachments/assets/a94a7e2f-a341-4e20-8dcf-028b22826cbe)


![image](https://github.com/user-attachments/assets/4db9d52b-b43a-4bb9-adfd-55d28a1d2a8a)


En el application.propierties se agrega la siguiente sección, correspondiente a la onfiguración de las propiedades de SSL:

```sh
server.port=8443  
server.ssl.enabled=true
server.ssl.key-store=classpath:ssl/baeldung.p12
server.ssl.key-store-password=taskManager
server.ssl.key-store-type=PKCS12
server.ssl.key-alias=baeldung
```


