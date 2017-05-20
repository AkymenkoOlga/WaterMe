import socket
import RPi.GPIO as GPIO
import spidev
import os
import time
import subprocess
import spidev as SPI
import SSD1306
import Image
import ImageDraw
import ImageFont

GPIO.setwarnings(False)
GPIO.setmode(GPIO.BCM)
GPIO.setup(2, GPIO.OUT) #red LED
GPIO.setup(3, GPIO.OUT) #yellow LED
GPIO.setup(4, GPIO.OUT) #green LED

delay = 4
spi = spidev.SpiDev()
spi.open(0,0)
hostMACAddress = '00:15:83:E7:B4:A5'
port = 3
backlog = 1
size = 1024

def readChannel(channel):
  val = spi.xfer2([1,(8+channel)<<4,0])
  data = ((val[1]&3) << 8) + val[2]
  return data

def btlisten():
  s=socket.socket(socket.AF_BLUETOOTH, socket.SOCK_STREAM, socket.BTPROTO_RFCOMM)
  s.bind((hostMACAddress,port))
  s.listen(backlog)
  showtext('wait for clients')
  print "Waiting for connection on RFCOMM channel %d" % port

  try:
    client, address = s.accept()
    print "Accepted connection from", address
    client.send('Connection successfull!')
    showtext('connected')
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
                        if (val != 10000):
                         print(val)
                        client.send(str(val)+ '!')                     
                 except KeyboardInterrupt:
                  print "Cancel."

  except:
    print("Closing socket")
    showtext('socket closed')
    client.close()
    s.close()
    btlisten()
  return

#~~~~~~~~~~~~~~~~~~~~~~~~~OLED~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
RST = 19
DC = 16
bus = 0
device = 0

# 128x64 display with hardware SPI:
disp = SSD1306.SSD1306(RST, DC, SPI.SpiDev(bus,device))

# Initialize library.
disp.begin()

# Clear display.
disp.clear()
disp.display()

# Create blank image for drawing.
# Make sure to create image with mode '1' for 1-bit color.
width = disp.width
height = disp.height
image = Image.new('1', (width, height))

# Get drawing object to draw on image.
draw = ImageDraw.Draw(image)

font = ImageFont.truetype('/usr/share/fonts/truetype/dejavu/DejaVuSerif.ttf',16)
top =0
x = 1


def showtext(text):
 draw.rectangle((0,0,width,height), outline=0, fill=0)
 draw.text((x, top),'status:', font=font, fill = 255)
 draw.text((x, top + 20), text, font =font, fill =255)
 disp.image(image)
 disp.display()
 return
#---------------------------------------------------------------------

btlisten()
