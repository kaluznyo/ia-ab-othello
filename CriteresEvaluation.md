# Introduction #

Notre fonction d'évaluation repose sur 4 critères différents :
**Le matériel** La position
**La mobilité** La stabilité

Ces 4 critères sont chacun pondérés par un coefficient qui varie durant la partie.


## Variation des coefficients durant la partie ##

Nous distinguons 3 phases de jeu :
  1. Le début du jeu (moins de 30 pions sur le plateau de jeu)
  1. Le milieu du jeu (entre 15 et 45 pions sur le plateau)
  1. La fin du jeu (entre 45 et 64 pions sur le plateau)

Nous avons les coefficients suivants pour les différentes phases de jeu:
Durant la première phase:
  * position = important
  * mobilité = très important
  * matériel = très faible

Durant la seconde phase:
  * position = important
  * mobilité = très important
  * matériel = moyen

Durant la troisième phase:
  * position = très faible
  * mobilité = nul
  * matériel = faible

Le coefficient de stabilité devrait normalement évolué à partir du moment où le joueur possède un coin. Cependant, nous avons remarqué que ceci n'était pas très efficace.
Nous avons donc finalement choisi d'utiliser un coefficient fixe pour la stabilité.


# Explications des différents coefficients #

## L'évaluation de position ##

Cette évaluation permet de déterminer quelles cases sont plus intéressantes que d'autres.
On possède un tableau contenant la valeur de chaque case.
Ce tableau évolue au cours de la partie lorsque les coins sont pris. La valeur des cases adjacentes au coin ont alors une valeur plus grande.

## L'évaluation de matériel ##

Cette évaluation permet de connaître qui a le plus de pions.
  * Si cette valeur est positive, le joueur actuel a le plus de pions.
  * Si cette valeur est négative, le joueur adverse possède plus de pions.


## L'évaluation de mobilité ##

Cette évaluation tient compte du nombre de coups jouables. Plus le nombre de coups jouables est élevé, plus l'évaluation sera grande.

## L'évaluation de stabilité ##

Cette évaluation permet de déterminer quelle pions ne pourront plus être retournés.
Ceci nous permet de consolider notre jeu.
Il s'agit d'un critère très important: