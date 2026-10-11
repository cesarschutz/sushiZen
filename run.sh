#!/bin/bash
# Sobe o Firebird 2.5 no Docker, compila e abre o sushiZen.
set -e
cd "$(dirname "$0")"

mkdir -p docker/data
[ -f docker/data/DBSUSHIZEN.FDB ] || cp DBSUSHIZEN.FDB docker/data/
docker compose up -d

CP="build/classes:$(ls src/*.jar | tr '\n' ':')"
mkdir -p build/classes
find src -name '*.java' > build/sources.txt
javac --release 8 -nowarn -encoding UTF-8 -cp "$CP" -d build/classes @build/sources.txt
rsync -a --exclude='*.java' --exclude='*.form' --exclude='*.jar' src/ build/classes/
# os relatórios (.jasper e Placa.jpg) são procurados ao lado das classes
cp src/Relatorios/*.jasper src/Relatorios/Placa.jpg build/classes/

# o caminho dos relatórios é montado sem a "/" inicial (herança do Windows), por isso roda a partir de /
CP_ABS=$(echo "$CP" | tr ':' '\n' | sed "s#^#$PWD/#" | paste -sd: -)
cd /
java -Dsushizen.db.url=jdbc:firebirdsql:localhost/3051:/firebird/data/${SUSHIZEN_DB:-DBSUSHIZEN.FDB} \
     -cp "$CP_ABS" br.sushi.zen.interfacesGraficas.frames.JanelaPrincipal
