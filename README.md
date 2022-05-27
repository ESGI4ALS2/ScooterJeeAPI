# ScooterJEE


## Objectifs :

Permettre au client de trouver quelqu'un pour l'aider rapidement pour la réparation de son scooter ou encore de pouvoir poser des questions concernant son scooter.


## Fonctionnalités :

- L’application permettra aux utilisateurs de devenir “référent” s’ils le souhaitent.
- Quand un utilisateur de Brumaire rencontrera un problème sur son scooter, l’application lui permettra de localiser les garages proches de lui susceptible de l’aider, l’utilisateur aura également le choix de “Pinger” les référents autour de lui.
- Les référents recevront une notification sur leurs application mobile qu’un utilisateur a besoin d’aide. Si le référent accepte d’aider l’autre utilisateurs, ils seront alors mis en contact via un chat en ligne sur l’application (ou un service de messagerie)


## Conseil du prof :

- Ajouter un système de recommandation, daté
- Via le système de recommandation : avoir un référencement des utilisateurs
- Système de votes sur les réponses à un problème
- Visualisation d'un classement des référents, via les votes et les réponses
- Info : les votes n'ont pas le même poid en fonction de la date, exemple : si la date est >= à 1 mois : 1 vote vaut 0,75, >= 3 mois : 1 vote vaut 0,5 et >= 6 mois : 1 vote vaut 0,25.

## Corrections à apporter dans le projet :

- `@CrossOrigin` a déporter sur la configuration globale
- Le `@Valid` sur `@Valid Long id` ne sert à rien.
- Les controllers n'ont pas besoin d'extend ErrorHandler, `#RestControllerAdvide` suffit à enregistrer le ErrorHandler.
- Test à revoir :
    - Surtout `SimpleServiceTest` qui teste un service donc qui ne fait que du CRUD.
    - Dans les tests d'un service, on s'attend plus à des tests sur des spécifications fonctionnels.


