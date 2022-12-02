# Pfad zum Ordner eintragen, in dem dieses Skript liegt!
PATH_TO_THIS_SCRIPT="" # Wichtig: Kein abschließender Slash!
# Beispiel: PATH_TO_THIS_SCRIPT="/Users/maxmustermann/Project"

PATH_TO_THIS_SCRIPT="/Volumes/PRIV/Cloud_PRIV/Hochschule/Semester_05/VS/praktikum/VS-HS-Co-SE5/Blatt3Aufgabe4"

# Alle Projekte (proj1: Aufgabe 1, proj2: Aufgabe 2, proj3: Aufgabe 3 (bestehend aus Aufgabe 1 und 2 in einem Verzeichnis)) kompilieren
echo ""
echo ""
echo "Alle Aufgaben kompilieren..."
echo "Aufgabe 1"
cd $PATH_TO_THIS_SCRIPT/proj1/Blatt3Aufgabe1 || exit
javac *.java
echo "Aufgabe 2"
cd $PATH_TO_THIS_SCRIPT/proj2/Blatt3Aufgabe2 || exit
javac *.java
echo "Aufgabe 3"
cd $PATH_TO_THIS_SCRIPT/proj3/Blatt3Aufgabe3 || exit
javac *.java

# RMI-Registry mit entsprechenden Pfaden aufrufen
echo ""
echo ""
echo "RMI-Registry starten"
rmiregistry -J-Djava.rmi.server.codebase="\
file:$PATH_TO_THIS_SCRIPT/proj1/ \
file:$PATH_TO_THIS_SCRIPT/proj2/ \
file:$PATH_TO_THIS_SCRIPT/proj3/ \
" &
registryPid=$!

sleep 2

# Server von Aufgabe 1 aufrufen
cd $PATH_TO_THIS_SCRIPT/proj1/ || exit
echo ""
echo ""
echo "Server von Aufgabe 1 ausführen"
java Blatt3Aufgabe1.TokenServer Servername &
server1Pid=$!

sleep 2

# Server von Aufgabe 2 aufrufen
cd $PATH_TO_THIS_SCRIPT/proj2/ || exit
echo ""
echo ""
echo "Server von Aufgabe 2 ausführen"
java Blatt3Aufgabe2.FibServer &
server2Pid=$!

sleep 2

# Server von Aufgabe 3 aufrufen
cd $PATH_TO_THIS_SCRIPT/proj3/ || exit
echo ""
echo ""
echo "Server von Aufgabe 3 ausführen"
java Blatt3Aufgabe3.TokenFibMain &
server3Pid=$!

sleep 2
echo ""
echo ""
echo "Alle Clients ausführen..."
sleep 2

# Client von Aufgabe 1 aufrufen
cd $PATH_TO_THIS_SCRIPT/proj1/ || exit
echo ""
echo ""
echo "Client von Aufgabe 1 ausführen"
java Blatt3Aufgabe1.TokenClient

# Client von Aufgabe 2 aufrufen
cd $PATH_TO_THIS_SCRIPT/proj2/ || exit
echo ""
echo ""
echo "Client von Aufgabe 2 ausführen"
java Blatt3Aufgabe2.FibClient 5

# Client von Aufgabe 3 aufrufen
cd $PATH_TO_THIS_SCRIPT/proj3/ || exit
echo ""
echo ""
echo "Client von Aufgabe 3 ausführen"
java Blatt3Aufgabe3.TokenFibClient

sleep 2
echo ""
echo ""
echo "Alle Prozesse beenden"

sleep 3

kill $registryPid
kill $server1Pid
kill $server2Pid
kill $server3Pid
