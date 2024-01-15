import requests

SERVER_IP = '192.168.100.17'  # Replace with your server's IP address
SERVER_PORT = 3001
SERVER_URL = f'http://{SERVER_IP}:{SERVER_PORT}/receive'

try:
    # Send data to the server using HTTP POST request
    message = 'Hello, server!'
    response = requests.post(SERVER_URL, data=message.encode())
    
    print('Connected to the server.')
    print(f'Sent message to server: {message}')

    # Check the response from the server
    if response.status_code == 200:
        print('File received successfully.')
    else:
        print(f'Response from server: {response.text}')

except Exception as e:
    print(f'Error: {str(e)}')

finally:
    print('Connection closed.')
