curl --request POST \
  --url http://localhost:8082/topics/Customer \
  --header 'accept: application/vnd.kafka.v2+json' \
  --header 'content-type: application/vnd.kafka.avro.v2+json' \
  --data '{
    "key_schema": "{\"name\":\"key\",\"type\": \"string\"}",
    "value_schema_id": "2",
    "records": [
        {
            "key" : "681",
            "value": {
                "customerId": "681",
                "name": "abid Hattak",
                "phoneNumber": "888-888-0007",
                "accountId": "16"
            }
        }
    ]
}'