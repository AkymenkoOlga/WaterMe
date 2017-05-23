import RPi.GPIO as GPIO
import smbus
from thread import start_new_thread, allocate_lock
from time import*

GPIO.setmode(GPIO.BCM)

class Controller:

    bus = smbus.SMBus(1)
    lock = allocate_lock()

    rate = 0
    beepInhibit = 0
    ThreadLed = True
    currentHumidity = 0

    def __init__(self, refreshrate):
        self.rate = refreshrate
        GPIO.setmode(GPIO.BCM)
        GPIO.setup(17, GPIO.OUT) #red LED
        GPIO.setup(27, GPIO.OUT) #yellow LED
        GPIO.setup(22, GPIO.OUT) #green LED

    def startLedControl(self):
        start_new_thread(self.ledControl,())
        print("LEDs on and LED Thread started")
        return

    def stopLedControl(self):
        self.lock.acquire()
        self.ThreadLed = False
        self.lock.release()
        print("LEDs off and LED Thread closed")
        return

    def ledControl(self):
      
        red = 800
        yellow = 400

        self.ThreadLed = True
        self.beepInhibit = 0

        while self.ThreadLed:

            if (self.currentHumidity > red):
                self.setLed(1, 0, 0)
                if (self.beepInhibit % 4 == 0):
                    self.beep()
                self.beepInhibit += 1
            
            if (self.currentHumidity > yellow and self.currentHumidity < red):
                self.setLed(0, 1, 0)
            
            if (self.currentHumidity < yellow):
                self.setLed(False, False, True)
            #checks every 0.1s if Thread should be terminated
            for i in range(self.rate * 10):
                sleep(0.1)
                if(not self.ThreadLed):
                    break

        self.setLed(0,0,0)
        return

    def setLed(self,rot, gelb, gruen):
                
        if rot:
            GPIO.output(17,GPIO.HIGH)
        else:
            GPIO.output(17,GPIO.LOW)
        if gelb:
            GPIO.output(27,GPIO.HIGH)
        else:
            GPIO.output(27,GPIO.LOW)
        if gruen:
            GPIO.output(22,GPIO.HIGH)
        else:
            GPIO.output(22,GPIO.LOW)
        return
    
    def beep(self):
         self.bus.write_byte(0x20,0x7F & self.bus.read_byte(0x20))
         sleep(0.15)
         self.bus.write_byte(0x20,0x80 | self.bus.read_byte(0x20))
         return
         
    def startReading(self):
        start_new_thread(self.readChannelFrequent, ())
        return

    def readChannelFrequent(self):
     while 1:
         self.readChannel(0)
         sleep(self.rate)
     return
    
    def readChannel(self,channel):
        self.bus.write_byte(0x48 , 0x40)	  #A0 = 0x40 A1 = 0x41 A2 = 0xA2 A3 = 0xA3
        data = self.bus.read_byte(0x48) *4
        self.writeToFile(data)
        self.lock.acquire()
        self.currentHumidity = data
        self.lock.release()
        return data
    
    def writeToFile(self,val):
        myString = strftime("%Y-%m-%d %H:%M:%S", localtime()) + "\t" + str(val) + "\n"
        print(myString)
        self.lock.acquire()
        fobj = open("/home/pi/WaterMe/WaterMePy/HumidityValues.txt","a")
        fobj.write(myString)
        fobj.close()
        self.lock.release()

    def readFromFile(self):
        dataString = ""
        self.lock.acquire()
        fobj = open("/home/pi/WaterMe/WaterMePy/HumidityValues.txt", "r")
        for line in fobj:
            dataString = dataString + line
        fobj.close()
        self.lock.release()
        return dataString
    

