docker-compose exec schema-registry \
    kafka-avro-console-consumer \
        --bootstrap-server kafka:29092 \
        --topic Balance \
        --from-beginning

        bin/bin/kafka-console-consumer --topic Balance --from-beginning \
                                                 --bootstrap-server localhost:9092 \
                                                 --property print.key=true


kafka-avro-console-consumer --bootstrap-server localhost:9092 --from-beginning --topic Balance --property schema.registry.url=http://localhost:8081