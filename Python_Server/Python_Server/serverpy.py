import socket
import os

def start_server():
    server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server.bind(('192.168.100.17', 3001))
    server.listen()

    print("Server listening on 192.168.100.17:3001")

    while True:
        client_socket, client_address = server.accept()
        print(f"Client connected: {client_address}")

        try:
            handle_client(client_socket)
        except Exception as e:
            print(f"Error handling client: {e}")
        finally:
            client_socket.close()

def handle_client(client_socket):
    with open('received_file.txt', 'wb') as file:
        while True:
            data = client_socket.recv(4096)
            if not data:
                break
            file.write(data)

    print("File received.")
    client_socket.send(b"File received.")

if __name__ == "__main__":
    start_server()
