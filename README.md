# CAR-FTPServer
=====================
Développement d'un serveur FTP en Java
Quentin Warnant
17/02/15

HOW TO
------------
```bash
    java -jar tp01-quentin-warnant.jar ftpMainDirectory ftpLoggerLogFile
```
* Le dossier spécifié en paramètre pour le serveur ftp doit avoir les permissions en 664 au moins.
* Le fichier spécifié en paramètre pour le logger doit avoir été créé et doit avoir les permissions en 664 au moins.

Introduction
------------
Ce package logiciel est une implémentation partielle du protocole FTP grâce à l'API Net.Socket de Java.

Architecture
------------
* Packages
    * main
    * server
        * request
            * data
            * control
    * test
    * util
* Classes abstraites
    * FtpRequest (Classe abstraite représentant une requête FTP et également à l'interface Command du pattern Command)
    * FtpDataRequest (Classe abstraite représentant une requête FTP nécessitant une connexion donnée)
* Erreurs
    * catch dans FtpThread lorsque le client se déconnecte impromptement ou que le socket engendre une IOException
    * catch dans FtpServer lorsqu'il se produit une erreur lors de l'acceptation de nouvelle connexion.
    
Le diagramme UML associé se trouve dans le dossier data du projet.
    
Code samples
------------
1. Au lieu de fonctionner avec des exceptions (sauf pour les IOException), nous avons utilisé un mécanisme de FtpResponse
avec un code de retour et un message, comme l'implémentation du protocole HTTP en Java. La principale raison est que nous ne 
voulions pas avoir beaucoup trop d'exceptions à gérer, et beaucoup trop de classes à créer rendant le projet bien trop complexe.
Sur l'exemple ci-dessous, nous renvoyons une réponse FTP commande non implémentée lorsque le client nous envoie une requête
que notre serveur ne gère pas encore.

```java
    // Request unknown : Return a FTP response error
    FtpResponse response = new FtpResponse(
            FtpConstants.FTP_ERR_INVALID_COMMAND_CODE,
            FtpConstants.FTP_ERR_INVALID_COMMAND_MSG);
    controlOutStream.writeBytes(response.toString());
```

2. Nous avons implémenté le pattern command dans le projet. Les classes liées à ce pattern sont la classe FtpThread (Invoker)
avec la méthode storeAndExecute(), toutes les classes FtpXXXRequest sont des commandes étendant la classe abstraite 
FtpRequest (Command) et étant obligée de définir la méthode process() pour le pattern. La classe FtpThread (Invoker) 
contient également un historique de commandes qui pourrait être connecté à une base de données. 

```java
    public void storeAndExecute(FtpRequest request) throws IOException {
        history.add(request);

        FtpResponse response = request.process();

        FtpServer.getFtpLogger().info(request.toString());
        FtpServer.getFtpLogger().info(response.toString());

        // Write the answer to the socket
        controlOutStream.writeBytes(response.toString());
        controlOutStream.flush();
    }
```
3. Nous avons implémenté un FTP logger qui permet d'obtenir le même comportement qu'un vrai serveur ftp sous Linux par exemple. 
Par défaut, nos loggons toutes les informations de niveau INFO sur le serveur, mais c'est modifiable dans le Logger. Le Logger
peut également être désactivé via la constante LOGGER_ENABLED dans l'interface FtpConstants. 

```java
    // Client disconnect : end of the thread and free the data port if needed
    FtpServer.getFtpLogger().error("Internal error on the server, closing the thread #" + this.id + " with error " + e.getMessage());
```