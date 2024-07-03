## Commande pour démarrer le cluster
```bash
docker compose up -d
```

### Commande pour créer un TOPIC 70f=> identifiant du conteneur docker
```bash
docker exec 70f kafka-topics --create --topic demo_topic --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
```

### Commande pour un flux interactif pour la production des messages
```bash
docker exec -it 70f kafka-console-producer --topic demo_topic --bootstrap-server localhost:9092 
```


### Commande pour lire les message à partir un flux interactif 
```bash
docker exec -it 70f kafka-console-consumer --topic demo_topic --bootstrap-server localhost:9092 
```


### Commande pour augmenter un nombre de partition
```bash
docker exec 70f kafka-topics --alter --topic critical_notifications --bootstrap-server localhost:9092 --partitions 3
```

### Commande pour soumettre une tache connector
```bash
curl -X POST -H "Content-type: application/json" --data @connector-mysql.json http://localhost:8083/connectors
```

## Commande pour démarrer un conteneur postgres
```bash
docker run --name postgres-container -e POSTGRES_PASSWORD=yourpassword -p 5432:5432 -d --network kafka-docker-composer_default postgres
```


### Commande partie registry
- Enregistrement manuel par l'api
```bash
curl -X POST -H "Content-Type: application/vnd.schemaregistry.v1+json" --data @user.json http://localhost:8081/subjects/user-value/versions
```


### Utilisation de kafa Streams
## Exemple 1
1. Collecte des logs "TOPIC"
2. Traitement des logs "Stream"
3. Alert "TOPIC"

### Ajout d'un serveur ksqldb

```bash
docker run -d \
--name=ksqldb-server \
--network=kafka-docker-composer_default \
-e KSQL_LISTENERS=http://0.0.0.0:8088 \
-e KSQL_BOOTSTRAP_SERVERS=kafka-1:19091 \
-e KSQL_KSQL_SCHEMA_REGISTRY_URL=http://schema-registry:8081 \
-e KSQL_KSQL_LOGGING_PROCESSING_STREAM_AUTO_CREATE='true' \
-e KSQL_KSQL_LOGGING_PROCESSING_TOPIC_AUTO_CREATE='true' \
-p 8088:8088 \
confluentinc/cp-ksqldb-server:latest
```

### pour avoir un client à l'interieur du conteneur du serveur ksqldb
```bash
docker exec -it ksqldb-server ksql http://localhost:8088

```sql
CREATE STREAM users_stream (user_id INT KEY, first_name STRING,last_name STRING,email STRING) WITH (kafka_topic='users',value_format='JSON',partitions=4);
````

## Exemple 
```
CREATE STREAM orders (
  order_id INT,
  customer_id INT,
  product_id INT
) WITH (
  KAFKA_TOPIC='orders_topic',
  VALUE_FORMAT='JSON'
);

CREATE STREAM customers (
  customer_id INT,
  customer_name STRING
) WITH (
  KAFKA_TOPIC='customers_topic',
  VALUE_FORMAT='JSON'
);

CREATE STREAM enriched_orders AS
SELECT orders.order_id,
       orders.product_id,
       orders.customer_id,
       customers.customer_name
FROM orders
JOIN customers WITHIN 10 MINUTES
ON orders.customer_id = customers.customer_id;

```
