curl --request POST \
  --url http://localhost:8082/topics/Balance \
  --header 'accept: application/vnd.kafka.v2+json' \
  --header 'content-type: application/vnd.kafka.avro.v2+json' \
  --data '{
    "key_schema": "{\"name\":\"key\",\"type\": \"string\"}",
    "value_schema_id": "1",
    "records": [
        {
            "key" : "22",
            "value": {
                "balanceId": "22",
                "accountId" : "15",
                "balance" : 208.06
            }
        }
    ]
}'