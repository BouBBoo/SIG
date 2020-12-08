Pour démarrer le serveur openlayers, il faut:
    - installer npm (sudo apt install npm)
    - faire : npm install
    - modifier l'adresse ip dans l'url situé dans 'index.js' pour y mettre l'adresse IP de votre machine.
    - puis npm start
    - Pour vérifer que ça fonctionne il faut ouvrir un onglet avec l'url : "ipmachine":1234, ou ipmachine correspond à l'IP de votre machine.
    - dans le web.xml de geoserver, décommentez la balise filter avec Jetty et la balise filter avec Cors
Pour vérifier que ça fonctionne sur l'android il faudra modifier l'IP utilisée lors du chargement de la webview.
