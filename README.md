## La place du marché - Microservice Order
Projet 12 - DA JAVA - Openclassrooms

**Domaine** : https://order.lpdm.kybox.fr

**Docker** : [https://hub.docker.com/r/vyjorg/lpdm-order](https://hub.docker.com/r/vyjorg/lpdm-order)

##### Liste des urls disponibles 
| URL                                             | METHODE | DESCRIPTION                                                                                                                                                                   |
|-------------------------------------------------|---------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| /orders                                         | GET     | retourne la liste de TOUTES les commandes disponibles en base de données                                                                                                      |
| /orders/{id}                                    | GET     | retourne la commande ayant pour identifiant le paramètre {id}                                                                                                                 |
| /orders/payments                                | GET     | retourne la liste des moyens de paiement disponibles                                                                                                                          |
| /orders/all/customer/{id}                       | GET     | retourne toutes les commandes effectuées par l'utilisateur ayant pour identifiant le paramètre {id}                                                                           |
| /orders/all/customer/{id}/date/{ordered}        | GET     | retourne toutes les commandes effectuées par l'utilisateur ayant pour identifiant le paramètre {id} classées par en ordre ("asc" / "desc") définit par le paramètre {ordered} |
| /orders/all/customer/{userId}/status/{statusId} | GET     |  retourne toutes les commande effectuées par l'utilisateur ayant pour identifiant le paramètre {userId} et le statut de commande définit par le paramètre {statusId}          |
| /orders/save                                    | POST    | persiste la commande présente dans la requête                                                                                                                                 |
|                                                 |         |                                                                                                                                                                               |
| /admin/payment/add                              | POST    | persiste le moyen de paiement présent dans le requête                                                                                                                         |
| /admin/payment/delete                           | POST    | supprime le moyen de paiement présent dans la requête                                                                                                                         |
| /admin/order/delete                             | POST    | supprime la commande présente dans la requête                                                                                                                                 |
| /admin/orders/all/date/asc                      | GET     | retourne toutes les commandes classées par date en ordre ascendant                                                                                                            |
| /admin/orders/all/date/desc                     | GET     | retourne toutes les commandes classées par date en ordre descendant                                                                                                           |
| /admin/orders/all/product/{id}                  | GET     | retourne la liste des commandes ayant le produit dont l'identifiant est passé en paramètre {id}                                                                               |
| /admin/orders/all/payment/{id}                  | GET     | retourne la liste des commandes ayant pour moyen de paiement l'identifiant {id} passé en paramètre                                                                            |
| /admin/orders/all/status/{id}                   | GET     | retourne la liste des commandes ayant pour status l'identifiant {id} passé en paramètre                                                                                       |