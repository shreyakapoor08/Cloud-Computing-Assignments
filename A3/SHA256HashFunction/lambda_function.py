import json
import hashlib
import requests


def lambda_handler(event, context):
    input_data = event
    value = input_data['value']
    
    hashed_value = hashlib.sha256(value.encode('utf-8')).hexdigest()
    
    payload = {
        "banner": "B00957587",
        "result": hashed_value,
        "arn": "arn:aws:lambda:us-east-1:339712816963:function:SHA256HashFunction",
        "action": "sha256",
        "value": value
    }
    
    print(payload);
    
    response = requests.post(input_data['course_uri'], json=payload)
    print(response)
    
    return {
        'statusCode': response.status_code,
        'body': response
    }
