# TheCoffeeBreak
El código de muestra que viene con este tutorial crea una base de datos que es utilizada por un propietario de una pequeña cafetería llamada The Coffee Break, donde los granos de café se venden por libra y el café preparado se vende por taza.

# Glosario de términos sobre Bases de Datos y JDBC:
### Base de Datos (DB): Conjunto de datos organizados de manera estructurada y accesibles electrónicamente. Pueden ser de diferentes tipos, como bases de datos relacionales (SQL) o NoSQL.
Sistema de Gestión de Bases de Datos (DBMS): Software utilizado para crear, gestionar y manipular bases de datos. Ejemplos comunes son MySQL, PostgreSQL, Oracle y SQL Server.
SQL (Structured Query Language):Lenguaje estándar utilizado para gestionar bases de datos. Se usa para crear, leer, actualizar y eliminar datos (operaciones CRUD).
JDBC (Java Database Connectivity): API de Java que permite conectar y ejecutar operaciones SQL en bases de datos desde aplicaciones Java. Es el puente entre las aplicaciones Java y los sistemas de bases de datos.
Conexión a la Base de Datos: El proceso de establecer una comunicación entre la aplicación Java y el DBMS. Generalmente, se hace usando la clase DriverManager o DataSource en JDBC.
Driver JDBC: Un componente de software que permite a una aplicación Java interactuar con una base de datos específica. Hay diferentes tipos de controladores: tipo 1 (puente), tipo 2 (nativo), tipo 3 (red) y tipo 4 (protocolo de base de datos).
Conexion (Connection): Objeto en JDBC que representa la conexión abierta a una base de datos. Permite ejecutar consultas y actualizar la base de datos.
Statement:Objeto que se usa para enviar instrucciones SQL a la base de datos. Hay diferentes tipos:
Statement: Para ejecutar consultas simples.
PreparedStatement: Para ejecutar consultas precompiladas y más eficientes.
CallableStatement: Para llamar procedimientos almacenados en la base de datos.
ResultSet: Objeto que almacena los resultados de una consulta SQL ejecutada con JDBC. Permite recorrer los resultados y acceder a los datos.
Query:

Una consulta SQL que se envía a la base de datos para recuperar o manipular datos. Ejemplo: SELECT, INSERT, UPDATE, DELETE.
Transaction:
Un conjunto de operaciones SQL que se ejecutan como una única unidad de trabajo. En JDBC, se manejan con los métodos commit() y rollback() para asegurar que los cambios sean permanentes o revertidos en caso de error.
PreparedStatement:
Un tipo de Statement en JDBC que permite precompilar la consulta SQL, lo que mejora la seguridad y el rendimiento (previene inyecciones SQL y mejora la ejecución de consultas repetidas).
CallableStatement:
Similar a PreparedStatement, pero se usa para ejecutar procedimientos almacenados en la base de datos.
DriverManager:
Clase en JDBC que gestiona los controladores de base de datos. Se usa para obtener una conexión a la base de datos, eligiendo el controlador adecuado.
Auto-commit:
Una característica en JDBC que, por defecto, cada operación SQL se confirma automáticamente (commit) al ejecutarse. Se puede desactivar para manejar transacciones manualmente.
Rollback:
Operación que deshace los cambios realizados en una transacción si ocurre un error antes de un commit.
Commit:
Operación que confirma una transacción, haciendo que los cambios realizados en la base de datos sean permanentes.
SQLException:
Excepción en Java que se lanza cuando ocurre un error durante la ejecución de una operación SQL a través de JDBC.
Metadata:
Información sobre la base de datos, como sus tablas, columnas, tipos de datos, etc. En JDBC, se puede obtener a través de objetos como DatabaseMetaData.
Batch Processing:
Técnica que permite ejecutar múltiples consultas SQL de manera eficiente en un solo lote, minimizando la cantidad de comunicaciones entre la aplicación y la base de datos.
Foreign Key:
Una clave que establece una relación entre dos tablas en una base de datos, asegurando la integridad referencial.
Primary Key:
Una clave única que identifica de manera exclusiva cada fila en una tabla de base de datos.
Index:
Una estructura de datos que mejora la velocidad de las consultas de búsqueda en la base de datos, similar a un índice en un libro.
Normalization:
El proceso de organizar los datos en una base de datos para reducir la redundancia y mejorar la integridad de los datos.
JOIN:
Una operación SQL que se utiliza para combinar filas de dos o más tablas en una base de datos, basándose en una condición común entre ellas.
Database URL:
Especificación de la dirección de una base de datos, que incluye el protocolo, el nombre de host, el puerto y el nombre de la base de datos.
SQLException:
Clase de excepción en JDBC que se lanza cuando ocurre un error en la ejecución de una consulta SQL o en la conexión con la base de datos.
