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


Se crean 14 pruebas de unidad sin el contexto de Spring .
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


## PARTE II GRÁFICOS

### Nuevas características de Task

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


### Gráficos


