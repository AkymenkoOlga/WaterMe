import socket
import RPi.GPIO as GPIO
import spidev
import os
import time
import subprocess

GPIO.setwarnings(False)

delay = 4
spi = spidev.SpiDev()
spi.open(0,0)

def readChannel(channel):
  val = spi.xfer2([1,(8+channel)<<4,0])
  data = ((val[1]&3) << 8) + val[2]
  return data

GPIO.setmode(GPIO.BCM)
GPIO.setup(2, GPIO.OUT) #red LED
GPIO.setup(3, GPIO.OUT) #yellow LED
GPIO.setup(4, GPIO.OUT) #green LED

hostMACAddress = '00:15:83:E7:B4:A5'
port = 3
backlog = 1
size = 1024

def btlisten():
  s=socket.socket(socket.AF_BLUETOOTH, socket.SOCK_STREAM, socket.BTPROTO_RFCOMM)
  s.bind((hostMACAddress,port))
  s.listen(backlog)
  print "Waiting for connection on RFCOMM channel %d" % port


  try:
    client, address = s.accept()
    print "Accepted connection from", address
    client.send('Connection successfull!')
    while 1:
        data = client.recv(size)
        if data == "LED on":
            p=subprocess.Popen(['/usr/bin/python','/home/pi/WaterMe/ledcontrol.py'])
            print "LEDs on"
            client.send('LEDs on!')
        if data == "LED off":
            p.kill()
            GPIO.output(2,GPIO.LOW)
            GPIO.output(3,GPIO.LOW)
            GPIO.output(4,GPIO.LOW)
            print "LEDs off"
            client.send('LEDs off!')
        if data == "request":
                if __name__ == "__main__":
                 try:
                        val = readChannel(0)
                        if (val != 0):
                         print(val)
                        client.send(str(val) + '!')

                 except KeyboardInterrupt:
                  print "Cancel."

  except:
    print("Closing socket")
    client.close()
    s.close()
    btlisten()
  return

btlisten()
