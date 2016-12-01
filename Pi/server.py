import socket
import RPi.GPIO as GPIO
import spidev
import os
import time

delay = 0.2
spi = spidev.SpiDev()
spi.open(0,0)

def readChannel(channel):
  val = spi.xfer2([1,(8+channel)<<4,0])
  data = ((val[1]&3) << 8) + val[2]
  return data

GPIO.setmode(GPIO.BCM)
GPIO.setup(2, GPIO.OUT)
GPIO.setup(3, GPIO.OUT)
GPIO.setup(4, GPIO.OUT)

hostMACAddress = '00:15:83:E7:B4:A5'
port = 3
backlog = 1
size = 1024
s = socket.socket(socket.AF_BLUETOOTH, socket.SOCK_STREAM, socket.BTPROTO_RFCOMM)
s.bind((hostMACAddress,port))
s.listen(backlog)
print "Waiting for connection on RFCOMM channel %d" % port

try:
    client, address = s.accept()
    print "Accepted connection from", address
    client.send('accepted')
    
    while 1:
        data = client.recv(size)
        if data == "on":
            GPIO.output(2,GPIO.HIGH)
            GPIO.output(3,GPIO.HIGH)
            GPIO.output(4,GPIO.HIGH)
            print "turning LEDs on"
            client.send('LEDs on')
        if data == "off":
            GPIO.output(2,GPIO.LOW)
            GPIO.output(3,GPIO.LOW)
            GPIO.output(4,GPIO.LOW)
            print "turning LEDs off"
            client.send('LEDs off')
        if data == "get humidity":
                if __name__ == "__main__":
                 try:
                        val = readChannel(0)
                        if (val != 0):
                         print(val)
                        client.send(str(val))
                        time.sleep(delay)

                 except KeyboardInterrupt:
                  print "Cancel."


except:
    print("Closing socket")
    client.close()
    s.close()
