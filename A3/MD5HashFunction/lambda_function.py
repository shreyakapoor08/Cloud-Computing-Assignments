import json
import hashlib
import requests


def lambda_handler(event, context):
    print("event is: ", event);
    
    input_data = event
    value = input_data['value']
    
    hashed_value = hashlib.md5(value.encode('utf-8')).hexdigest()
    
    payload = {
        "banner": "B00957587",
        "result": hashed_value,
        "arn": "arn:aws:lambda:us-east-1:339712816963:function:MD5HashFunction",
        "action": "md5",
        "value": value
    }
    
    print(payload);
   
    response = requests.post(input_data['course_uri'], json=payload)
    
    return {
        'statusCode': response.status_code,
        'body': response
    }
