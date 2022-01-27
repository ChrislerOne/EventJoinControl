# Event Join Control - Backend

## Deployment

### Backend

EJC wurde auf einer Debian 10 Cloud-Instanz von Hetzner deployed. Das Projekt wurde dabei über Maven als JAR verpackt
und anschließend über einen Service angebunden. Als Proxy fungiert ein NGINX, welcher auf die Requests auf die
Adresse https://ejc-api.fööd.de reagiert.

### Frontend

Das Frontend wurde in React geschrieben und liegt somit in einem Verzeichnis auf dem gleichen Server. Für das Frontend
wurde extra ein eigener User eingerichtet, der begrenzten Zugriff auf das System hat. Dieser User kann somit lediglich
die Dateien in seinem Home-Verzeichnis und darunter verwalten, lesen und ausführen. Das Verzeichnis des React-Builds
liegt unter diesem User. Auch hier fungiert der NGINX als Proxy, welcher für
https://ev-jo-co.fööd.de verantwortlich ist. Das Routing übernimmt danach React selbst.

### Maria DB

Als Datenbank benutzen wir eine Maria-DB welche ebenfalls auf dem gleichen Server unter anderen Ports zu erreichen ist.
Die Datenbank ist von innen, als auch von außen zu erreichen und kann somit ohne Einschränkungen von der API genutzt
werden. Das Frontend kommuniziert nicht mit dem Datenbankserver direkt. Hierfür ist die API zuständig.

Ein Datenbank-Dump haben wir nicht erstellt. Da die Verwendung der DB auch im lokalen Modus ohne Einschränkungen möglich
ist. **BITTE BEACHTE**: Live erstelle Daten und jene im lokalen Modus sind dementsprechend identisch.

## Lokales Deployment

Das lokale Deployment ist natürlich ebenfalls möglich. Es müssen nur einige Sachen beachtet werden.

### Backend

Das Backend läuft auf Port 8090 und kann ohne Probleme benutzt werden. Lediglich eine programmatische Einschränkung ist
zu machen:

- Der QR-Code hat die Adresse https://ev-jo-co.fööd.de eingebunden und kann im Nachhinein nicht mehr abgeändert werden
    - Dementsprechend sind alle bereits erstellten QR-Codes trotz lokalem Deployment auf das Live System geroutet.
- Natürlich ist es möglich die entsprechende Zeile im Code zu ändern.
    - Diese befindet sich im UserService in Zeile 207. Diese muss mit entsprechende "localhost:3000/checkQrCode/"
      ersetzt werden.

Natürlich kann der QR-Code dennoch bei neuen Accounts ohne Probleme erstellt werden und im Frontend lokal eingesehen
werden. Beim Scannen des QR-Codes ist auch die Darstellung im LIVE-Backend möglich. Da wir keinen Unterschied zwischen
Test- und Live-Datenbank haben. Solange das Routing nicht über localhost auf dem QR erfolgt.

### Frontend

Das Frontend kann ebenfalls ohne Probleme lokal genutzt werden. Beim erstmaligen Ausführen muss unter Umständen ein
einmaliges "npm install" durchgeführt werden, welcher die nötigen Packages installiert. Danach kann mit "npm run start"
das Frontend gestartet werden.

Sollte das Front- und Backend beides simultan genutzt werden, dann muss im Frontend unter
"src/components/api/requests.js" in Zeile 4 und 7 die LIVE "baseURL" mit der LOKAL "baseURL" getauscht werden.
Anschließend sollte alles erwartungsgemäß laufen.


