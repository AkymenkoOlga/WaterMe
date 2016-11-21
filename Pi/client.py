from bluetooth import *

# Create the client socket
client_socket=BluetoothSocket( RFCOMM )

client_socket.connect(("00:15:83:E7:B4:A5", 3))

while 1:
 text = raw_input()
 client_socket.send(text)
 if text == "quit":
  break

print "Finished"

client_socket.close()