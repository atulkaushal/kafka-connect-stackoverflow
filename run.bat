call mvn clean package
call docker build . -t atulkaushal/kafka-connect-stackoverflow:1.0
call docker run --net=host --rm -it -v C:/Madhurima/Personal/Atul/workspace/kafka-connect-stackoverflow/offsets:/usr/share/java/kafka-connect-stackoverflow/offsets atulkaushal/kafka-connect-stackoverflow:1.0
call connect-standalone config/worker.properties config/StackOverFlowSourceConnector.properties