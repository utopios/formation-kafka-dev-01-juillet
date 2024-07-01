### TP : Implémentation de CQRS avec Spring Boot et Kafka

#### Contexte
Le système de commande en ligne sera divisé en deux parties principales :
1. **Command Side (Order Service)** : Gère la prise de commandes et publie les événements de commande sur un topic Kafka.
2. **Query Side (Fulfillment Service)** : Écoute les événements de commande et met à jour une vue (base de données ou cache) utilisée pour répondre aux requêtes sur l'état des commandes.



1. **Configuration de Kafka**
   - Assurez-vous que Kafka et Zookeeper sont configurés et fonctionnent.
   - Créez un topic Kafka `orders`.

2. **Développement du Order Service (Command Side)**
   - Créez un microservice Spring Boot.
   - Ajoutez les dépendances pour Spring Kafka.
   - Configurez un producteur Kafka pour envoyer des messages au topic `orders`.
   - Développez une API REST pour prendre des commandes, les transformer en événements de commande, et les publier sur Kafka.

3. **Développement du Fulfillment Service (Query Side)**
   - Créez un autre microservice Spring Boot.
   - Ajoutez les dépendances pour Spring Kafka et Spring Data.
   - Configurez un consommateur Kafka qui écoute sur le topic `orders` et reçoit les événements de commande.
   - Mettez à jour une base de données à chaque fois qu'un événement de commande est consommé pour maintenir une vue des commandes qui peut être interrogée.
   - Développez une API REST pour fournir des informations sur le statut des commandes (par exemple, si une commande a été expédiée).

