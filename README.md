# 📦 iStore ltd

Application Java complète pour la gestion d'inventaire multi-magasins avec contrôle d'accès et sécurité renforcée.

## 🌟 Aperçu du Projet
L'application iStore ltd permet de :  
- Centraliser la gestion des stocks sur plusieurs magasins  
- Contrôler l'accès des employés via un système de rôles  
- Sécuriser les données sensibles (mots de passe hashés, whitelist emails)  

### 🚀 iStore - Guide d'Installation
Créez une base de données MYSQL : CREATE DATABSE istore;  
Ensuite importer le fichier istore.sql disponible dans le dossier.  
Insérez un email pour le premier admin : INSERT INTO whitelisted (email) VALUES ('admin@istore.com');  

Afin de pouvoir utiliser JDBC pour pouvoir connecter l'application et la base de donnée, vous devez ajouter le fichier jar de mysqlconnecter dans le projet. Pour ajouter le fichier jar : File > Project Structure > Librairies > Cliquez sur Java > Enfin sélectionner le fichier jar.  
Le fichier jar est diponible dans le dossier mysqlconnecter du projet.  

Le projet est maintenant utilisable.


