import json
import requests

url = 'http://neogeodb.com:8765/players'
payload = {
    "player": "kaddath",
    "rom": "garou",
    "country": "France"
}
headers = {'content-type': 'application/json'}
response = requests.post(url, data=json.dumps(payload), headers=headers)
print response  # should be equal to 200 (HTTP OK)