#!/bin/bash

. /opt/search-heimdall/storm.properties
storm=${storm_location}/bin/./storm

topology_jar=$(find ${topology_location} -name "*-jar-with-dependencies.jar")

params=''
for property in $(cat /opt/search-heimdall/heimdall.properties)
do
  params="${params} -${property/=/ }"
done

echo " ########################## Killing topology ${topology_name} #############################"
${storm} kill -w ${topology_seconds_to_kill} ${topology_name}
echo "done"

echo "Waiting kill topology ..."
sleep ${topology_seconds_to_kill}

echo "########################## Deploying new version of topology ${topology_name} #############"
echo " => params: ${params}"
echo " => topology_jar: ${topology_jar}"
echo " => topology_mainClass: ${topology_mainClass}"

_user="$(id -u -n)"

${storm} jar "${topology_jar}" "${topology_mainClass}" "${params} -deployed_by ${_user}"

rm *.jar

echo "done"