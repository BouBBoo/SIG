Pour créer la BD, il faut tout d'abord suivre les instructions suivantes pour créer la BD:
	1. Installer docker

	2. Installer le client postgres (on peut aussi installer pg admin..)

	sudo apt-get install postgres-client-10  (ou quelque-chose dans le genre. Dans le doute passer par synaptic)



	2. Lancer une image docker appropriée, avec postgis dedans

	On lance un postgres avec postgis et on publie le porte 5432 (attention l'ordre des paramètres a de l'importance)

	docker run --name some-postgis -d -p 5432:5432 -e POSTGRES_PASSWORD=mysecretpassword postgis/postgis

	2. dans postgres, créer un compte utilisateur (par ex. toto avec mdp toto) avec une base et autoriser postgis dans sa base

	psql -h 127.0.0.1 -p 5432 -U postgres -c "CREATE USER toto with password 'toto'"

	psql -h 127.0.0.1 -p 5432 -U postgres -c "CREATE DATABASE toto OWNER=toto"

	(il vous sera demandé le mot de passe de postgres : mysecretpassword)


	psql  -h 127.0.0.1 -p 5432 -d toto -U postgres

	toto=# create extension postgis;

	(attention : il faut bien faire cela en tant que postgres, pas toto !!!)

Puis après avoir installé postgis:
	Ouvrir un terminal dans le répertoire BD du projet
	Executer la commande suivante: psql -h 127.0.0.1 -p 5432 -d toto -U toto < BD.dump


