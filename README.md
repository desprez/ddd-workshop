Domain Driven Design Workshop
=============================

## Domain Driven Design Credit

Le but de l’exercice est d’analyser le code et les règles fonctionnelles afin d’introduire les notions métiers dans le code à l’aide des concepts issues du DDD.

L’application est un back end dans lequel des utilisateurs peuvent valoriser des emprunts.

Un emprunt possède les caractéristiques suivantes :
 - il est identifié par le code de référence qui est unique
 - il possède des échéances
 - il possède des devises (currencies)

Une échéance correspond à des remboursements périodiques:
 - une date de remboursement
 - un capital restant dû (crd) : c’est le capital total qui reste après paiement de l’échéance

Une devise (EUR, USD, CHF, etc) correspond à la monnaie d’un pays. Cela sert notamment à savoir le taux de conversion d’une devise à l’autre. Dans les devises, nous avons la notion de funding : ce sont des devises pour lesquelles nous n’avons pas à effectuer de conversion. Le projet contient deux fundings : EUR et USD. La devise de référence est EUR.

L’application est en relation avec une autre application pour récupérer les taux de changes (conversions en devises)

Enfin un service est présent permettant d'appliquer le taux de conversion sur les échéances si besoin.

## But de l'exercice

L'exercice fournit un projet où le domaine est identifié. L'organisation du domaine se base sur l'architecture hexagonale (Port & Adapter). Le but de l'exercice est de poursuivre le refactoring entamé du domaine.

Les entités et values object ont été identifié. Pour renforcer cet aspect, notre entité Credit hérite de la classe Entity et les autres classes héritent de IdValueObject.

Les Entités sont construite à l'aide de Builder (Builder pattern).

Afin d'appliquer un des principes de l'architecture hexagonale, la configuration JPA se fait par des fichiers ORM.xml. Cela nous permet de décorréler la technique de notre domaine.

###1. Installation

- Cloner le projet : https://github.com/desprez/ddd-workshop.git
- Lancer un mvn verify ou install


###2. Constats

Que constatez-vous ?
 - Les objets sont ils bien rangés ?
 - Distingue-t-on les couches ? Les modules ?
 - Les objets (ValueObject & entities) sont ils immutables ?

Avant de vous lancer, vérifiez les tests et leur taux de couverture : est-il sufisant ?

- Dans le package com.ddd.training.domain créer les sous packages suivants : credit, currency et echeance.
- Déplacer les objects dans leur package respectifs (n'oubliez pas de modifier les fichier orm.xml pour prendre en compte ces nouveaux packages)
- Lancer les tests


###3. CreditId

Une entité doit avoir une identité unique. Nous savons qu'un emprunt est identifié par un code de référence. En DDD nous devons typer cet aspect d'identité : c'est le but de la classe CreditId. Cette dernière possède de l'intelligence pour valider ses attributs (id et code de référence). Les méthodes equals et hashcode sont basées sur le code de référence.
Nous devons ajouter cette classe à notre entité Credit :

- Modifier le fichier de configuration Credit.orm.xml et décommenter le code correspondant
- Ajouter l'attribut CreditId dans la classe Credit
- Modifier les méthodes equals et hashcode pour prendre en compte cet attribut
- Ajouter de la validation dans le setter de l'attribut CreditId : ce dernier ne peut pas être null.


###4. Echeance & Currency

Nous allons encapsuler les échéances et les currencies. En effet en terme objet, ces notions doivent être encapsulées dans une notion de book: un EcheanceBook gère un ensemble d'EcheanceRequest.
Aussi en DDD, le point d'entrée pour les opérations sur les objets doit passer par l'entité. L'ajout d'une échéance doit se faire pour la classe Credit et non par la classe EcheanceRequest.

- Créer la classe EcheanceRequestBook avec comme attribut la liste des échéances du la classe Credit dans le package approprié
- Créer la classe CurrencyBook avec comme attribut la liste des currencies du la classe Credit dans le package aproprié
- Ajouter les attributs echeanceBook et currencyBook à la classe Credit
- Ajouter la méthode addEcheanceRequest à la classe Credit
- Ajouter la méthode addCurrency à la classe Credit
- Modifier le fichier Credit.orm.xml (EcheanceBook et CurrencyBook deviennent des component)
- Supprimer les listes echeanceRequests et currencies
- Relancer les tests

Après ce refactoring, les tests peuvent de nouveau passer.


###5. CreditRepository

La classe CreditRepositoryImpl se trouve dans le domaine. Pour respecter les principes de l'architecture hexagonale, cette classe doit se trouver en dehors du domaine. Nous allons également la renommer afin d'avoir un nom plus parlant

- Ajouter un package com.ddd.training.infrastructure
- Déplacer la classe CreditRepositoryImpl dans ce package


###6. CreditDecimal

Nous allons modifier la classe EcheanceRequest et son attribut crd. En effet cet attribut est un BigDecimal : pour effectuer des opérations nous devons tester la nullité et gérer les cas d'exception.
C'est dans cette optique que la classe CreditDecimal a été créée. Cette dernière encapsule les traitements (gestion des opérations et arrondis). De plus nous avons également défini cette classe comme un attribut Hibernate.

- Modifier la classe EcheanceRequest et utiliser CreditDecimal pour le crd
- Modifier le fichier orm.xml correspondant
- Modifier les tests


###7. CreditDataService

A cette étape, le code ne compile plus car la classe CreditService applique des taux de change. Cependant notre crd est un CreditDecimal et la classe DataService nous retourne des BigDecimal. Etant donnée que ce service est externe, nous allons ajouter une couche anticorruption.
Cette couche permet de traduire des notions similaires mais utilisées différemment suivant les domaines.
La classe CreditDataService a déjà été ajoutée. Nous remarquons que cette classe se situe dans le package port.adapter.service pour respecter l'architecture hexagonale.

- Modifier la classe CreditDataService qui doit avoir comme attribut la classe DataService. Cette classe aura comme méthode getCrossChange(Date date) et doit retourner un CreditDecimal
- Modifier la classe CreditService
- Modifier les tests


###8. CreditApplicationService

Maintenant que la classe CreditService a été modifié, nous remarquons que son emplacement ne va pas. En effet cette dernière contient une référence vers CreditRepository (le domaine) et vers CreditDataService (extérieur). Toujours pour respecter notre architecture, nous devons déplacer cette classe.

- Renommer CreditService en CreditApplicationService
- Déplacer les tests dans le bon package et voir ci ceux-ci sont toujours au vert

Ce package permet de regrouper les services qui sont en relation avec toute l'application. Le nommage de la classe renforce cet aspect. Nous n'avons pas besoin de chercher pendant des heures pour comprendre ce que la classe fait.


###9. java.util.date

- Remplacer dans les entities les type java.util.date par des java.time.LocalDate.
- Corriger les tests untaires.


###10. Bravo !

Vous avez maintenant du code conçu autour du modèle métier en s'inspirant de l'architecture hexagonale.
Vous pouvez continuer en implémentant la couche exposition sous forme d'API Rest.


