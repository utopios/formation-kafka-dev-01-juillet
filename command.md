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