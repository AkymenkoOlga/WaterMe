import socket
import RPi.GPIO as GPIO

GPIO.setmode(GPIO.BCM)
GPIO.setup(2, GPIO.OUT)

hostMACAdress = '00:15:83:E7:b4:A5'
port = 3
backlog = 1
size = 1024
s = socket.socket(socket.AF_BLUETOOTH, socket.SOCK_STREAM, socket.BTPROTO_RFCOMM)
s.bind(hostMACAddress,port)
s.listen(backlog)
try:
  client, address = s.accept()
  print "Client connected"
  while 1:
    data= client.rec(size)
    if data:
      print(data)
    if data == "on":
      print "turning LEDs on"
      GPIO.output(2, GPIO.HIGH)
    if data == "off":
      print "turning LEDs off"
      GPIO.output(2, GPIO.LOW)

except:
  print "Closing socket"
  client.close()
  s.close()
