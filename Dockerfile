FROM confluentinc/cp-kafka-connect

WORKDIR /usr/share/java/kafka-connect-stackoverflow
COPY config config
COPY target target
COPY target/kafka-connect-stackoverflow-1.0.jar kafka-connect-stackoverflow-1.0.jar

VOLUME /kafka-connect-stackoverflow/config
VOLUME /kafka-connect-stackoverflow/offsets

CMD CLASSPATH ="/usr/share/java/kafka-connect-stackoverflow/kafka-connect-stackoverflow-1.0.jar" connect-standalone config/worker.properties config/StackOverFlowSourceConnector.properties