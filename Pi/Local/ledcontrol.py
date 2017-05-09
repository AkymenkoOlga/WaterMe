import RPi.GPIO as GPIO
import spidev
import os
import time
import smbus
from subprocess import call
GPIO.setwarnings(False)

GPIO.setmode(GPIO.BCM)
GPIO.setup(2, GPIO.OUT) #red LED
GPIO.setup(3, GPIO.OUT) #yellow LED
GPIO.setup(4, GPIO.OUT) #green LED
GPIO.setup(17,GPIO.OUT) #summer

delay = 3 
spi = spidev.SpiDev()
spi.open(0,0)
i = 0

def readChannel(channel):
  val = spi.xfer2([1,(8+channel)<<4,0])
  data = ((val[1]&3) << 8) + val[2]
  return data

red = 800
yellow = 400
green = 200
if __name__ == "__main__":
  try:
	while 1:
          val = 900#readChannel(0)
          if (val != 0):
            print('current value: '+str(val))

            if (val > red):
             GPIO.output(2,GPIO.HIGH)
             GPIO.output(3,GPIO.LOW)
             GPIO.output(4,GPIO.LOW)

	     #-------acustic signal-------#
	     if (i%16==0):
	      call(["./summer","args","to", "summer"])
	      #GPIO.output(17,GPIO.HIGH)
	      #time.sleep(0.2)
	      #GPIO.output(17,GPIO.LOW)
	     i+=1

            if (val > yellow and val < red):
              GPIO.output(2,GPIO.LOW)
              GPIO.output(3,GPIO.HIGH)
              GPIO.output(4,GPIO.LOW)

            if (val < yellow):
              GPIO.output(2,GPIO.LOW)
              GPIO.output(3,GPIO.LOW)
              GPIO.output(4,GPIO.HIGH)
          time.sleep(delay)

  except KeyboardInterrupt:
        print "Cancel."

