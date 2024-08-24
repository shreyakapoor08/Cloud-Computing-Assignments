import json
import requests
import bcrypt

def lambda_handler(event, context):
    input_data = event
    value = input_data['value']
    
    salt = bcrypt.gensalt()
    hashed_value = bcrypt.hashpw(value.encode('utf-8'), salt).decode('utf-8')
    
    payload = {
        "banner": "B00957587",
        "result": hashed_value,
        "arn": "arn:aws:lambda:us-east-1:339712816963:function:BcryptHashFunction",
        "action": "bcrypt",
        "value": value
    }
    
    print(payload);
    
    response = requests.post(input_data['course_uri'], json=payload)
    
    return {
        'statusCode': response.status_code,
        'body': response
    }
