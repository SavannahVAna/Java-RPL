# Pile RPL

### version :

The version of java used is openjdk-23

## usage :

utilisez la comme votre calculette pile RPL. Pour traiter des n tuples de nombres, séparez les d'une virgule (ex: *4,5 8,9 +* additionne les deux tuples de nombres et retourne [12,14])
### server

pour lancer le server en mode multi user 
>java ServerCalcRPL -stack=multiple

pour lancer le server en mode calculette partagée
>java ServerCalcRPL -stack=shared

### client

>java FooRPL -user=<remote/local> -log=<rec/replay>

par defaut ce sera un user local sans replay de logs qui sera lancé