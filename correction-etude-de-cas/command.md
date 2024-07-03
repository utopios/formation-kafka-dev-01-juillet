## Création du topic
kafka-topics --create --topic tracking-events --bootstrap-server localhost:9092 --partitions 3 --replication-factor 2

### Commande pour enregistrer un connecteur 

```bash
curl -X POST -H "Content-Type: application/json" --data @source-csv.json http://localhost:8083/connectors
```

## Commande pour enregister le schema
```bash
curl -X POST -H "Content-Type: application/vnd.schemaregistry.v1+json" \
--data @schema.json http://localhost:8081/subjects/tracking-events-value/versions
```


### Requete KSQLDB
```sql
CREATE STREAM tracking_events_stream (idColis VARCHAR, timestampScan VARCHAR, lieuScan VARCHAR, etatColis VARCHAR) WITH (KAFKA_TOPIC='tracking-events', VALUE_FORMAT='JSON');

CREATE TABLE package_status AS
SELECT idColis, LATEST_BY_OFFSET(etatColis) AS latest_status
FROM tracking_events_stream
GROUP BY idColis;
```

- Les requêtes KSQLDB permettent de maintenir une vue agrégée du statut des colis.
- L’utilisation de LATEST_BY_OFFSET assure que le statut le plus récent est toujours conservé.
- L’impact sur les performances dépend de la fréquence des événements de suivi et du nombre de colis.


### Ajouter un connector

{
"name": "custom-tracking-connector",
"config": {
"connector.class": "utopios.net.CustomTrackingConnector",
"tasks.max": "1",
"api.url": "http://api.logitrack.com/trackings",
"topic": "tracking-events",
"key.converter": "org.apache.kafka.connect.storage.StringConverter",
"value.converter": "org.apache.kafka.connect.json.JsonConverter",
"value.converter.schemas.enable": "false"
}
}

```bash
curl -X POST -H "Content-Type: application/json" --data @custom-tracking-connector-config.json http://localhost:8083/connectors
```