# HexaPodGenetics

Ce projet se décompose en deux parties.

## Réalisation de l'hexapode
La première est la réalisation d'un hexapode (engin robotisé à 6 pattes). la structure ainsi que les pattes
seront réalisée grâce à une imprimante 3D. Les pattes seront articulé grâce à des servomoteurs 
(pour l'instant : http://akizukidenshi.com/download/ds/towerpro/SG90.pdf). Le tout sera commandé par un raspberry pi.
Le robot embarquera aussi une batterie, un système de commande des servos, et un capteur de distance (type : https://www.gotronic.fr/pj2-hc-sr04-utilisation-avec-picaxe-1343.pdf)

## Apprentissage de la marche
La seconde partie consiste à implémenter dans la puce du robot un algorithme génétique permettant au robot d'apprendre à marcher tout seul, à partir de plusieurs enchainements de séquences.

## Tips pour Git
### Avant commencer à travailler :
git fetch origin

git rebase

### après chaque modification du projet : 
git add --all

git commit -m "[description succinte de l'ajout]

### à la fin de la séance de travail : 
git push origin master
