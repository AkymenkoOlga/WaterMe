
from bluetooth import *

# Create the client socket
client_socket=BluetoothSocket( RFCOMM )

client_socket.connect(("00:15:83:E7:B4:A5", 3))

client_socket.send("Hello World")

print "Finished"

client_socket.close()
