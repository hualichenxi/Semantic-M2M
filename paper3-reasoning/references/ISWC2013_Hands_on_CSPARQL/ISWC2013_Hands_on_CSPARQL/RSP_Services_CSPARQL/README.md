C-SPARQL implementation of RDF stream Processors RESTful Interfaces
===================

To configure the C-SPARQL implementation of RDF stream Processors RESTful Interfaces please open the setup.properties file

Configuration:
csparql_server.port : configure the port of the server
csparql_server.version : configure the version of the server
csparql_stream.stream.base_uri : configure the base URI of all new stream automatically created on the engine
csparql_engine.enable_timestamp_function : configure the possibility to enable the timestamp function into the engine
csparql_engine.send_empty_results : allow the server to avoid to send empty query results
csparql_engine.activate_inference : if true, the inference is activated in the C-SPARQL Engine
csparql_engine.inference_rule_file : set the path of the file containing the rules for the custom reasoner. If this property is null, empty or the path is unreachable, a RDFS reasoner is activated.
To use RDFS reasoner leave this property empty.


Execute the Server:
Run rsp-services-csparql.sh or directly run the executable jar: java -jar rsp-services-csparql-0.3.2.3.jar
Setting the memory variables of jvm could be useful, e.g. java -Xms512m -Xmx1g -jar rsp-services-csparql-0.3.2.3.jar

The jar needed by server jar to be executed are in the lib folder. The lib folder NEEDS TO BE IN THE SAME DIRECTORY of rsp-services-csparql-0.3.2.3.jar
