## La place du marché - Microservice Order
Projet 12 - DA JAVA - Openclassrooms

Domaine : http://lpdm.order.kybox.fr

##### Liste des urls disponibles :
|              URL              | Methode |  Retour |                                                               Description                                                              |
|:-----------------------------:|:-------:|:-------:|:--------------------------------------------------------------------------------------------------------------------------------------:|
| /orders                       |   GET   |   Json  | Retourne la liste toutes les commandes disponibles en base de données                                                                  |
| /orders/{id}                  |   GET   |   Json  | Retourne la commande ayant pour ID la valeur de l'entier {id}                                                                          |
| /orders/by/customer/{id}      |   GET   |   Json  | Retourne la liste des commandes effectuées par l'utilisateur ayant pour ID la valeur de l'entier {id}                                  |
| /payments                     |   GET   |   Json  | Retourne la liste des moyens de paiement disponibles en base de données                                                                |
| /orders/save                  |   POST  |   Json  | Persiste la commande passée dans le @RequestBody au format Json                                                                        |
| /admin/add/payment            |   POST  |   Json  | Persiste le moyen de paiement passé dans le @RequestBody au format Json                                                                |
| /admin/order/delete/{id}      |   POST  | Boolean | Supprimer la commande passée dans le @RequestBody au format Json si la valeur de l'entier {id} correspond à la valeur de order.getId() |
| /admin/orders/by/product/{id} |   GET   |   Json  | Retourne la liste des commandes qui contiennent la référence d'un produit ayant pour ID la valeur de l'entier {id}                     |
| /admin/orders/by/payment/{id} |   GET   |   Json  | Retourne la liste des commandes qui contiennent la référence d'un moyen de paiement ayant pour ID la valeur de l'entier {id}           |

