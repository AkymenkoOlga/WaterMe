import RPi.GPIO as GPIO
import smbus
import oled
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
    beepEnable = False
    oled = oled.Oled()

    def __init__(self, refreshrate):
        self.rate = refreshrate
        GPIO.setmode(GPIO.BCM)
        GPIO.setup(17, GPIO.OUT) #red LED
        GPIO.setup(27, GPIO.OUT) #yellow LED
        GPIO.setup(22, GPIO.OUT) #green LED

    def startLedControl(self):
        start_new_thread(self.ledControl,())
        print("LEDs on and LED Thread started\n")
        return

    def stopLedControl(self):
        self.lock.acquire()
        self.ThreadLed = False
        self.lock.release()
        print("LEDs off and LED Thread closed")
        return

    def ledControl(self):

        self.ThreadLed = True
        self.beepInhibit = 0
        self.readChannel(0)
        while self.ThreadLed:
            #green
            if (self.currentHumidity >= 60):
                self.setLed(False, False, True)
            #yellow
            if (self.currentHumidity < 60 and self.currentHumidity > 20):
                self.setLed(True, False, False)
            #red
            if(self.currentHumidity <= 20):
                self.setLed(False, True, False)
                if (self.beepEnable):
                    if (self.beepInhibit % 4 == 0):
                        self.beep()
                    self.beepInhibit += 1   
                             
            #checks every 0.1s if Thread should be terminated
            for i in range(self.rate * 10):
                sleep(0.1)
                if(not self.ThreadLed):
                    break

        self.setLed(False,False,False)
        return

    def setBeepEnable(self, bool):
        self.lock.acquire()
        self.beepEnable = bool
        self.lock.release()
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
         self.readChannel(0x40)
         self.readChannel(0x41)
         self.readChannel(0xA2)
         self.readChannel(0xA3)
         sleep(self.rate)
     return
    
    def readChannel(self,channel):
        self.bus.write_byte(0x48 , channel)	  #A0 = 0x40 A1 = 0x41 A2 = 0xA2 A3 = 0xA3
        dataRaw = self.bus.read_byte(0x48) *4
        if (dataRaw != 0):
            data = int(round(100 - dataRaw/1020.0*100))
        if (dataRaw == 0):
            data = 100       
        self.writeToFile(data,channel)
        self.lock.acquire()
        self.currentHumidity = data
        self.lock.release()
        self.oled.showsmiley(data)
        return data
    
    def writeToFile(self,val, channel):
        if val >= 1000:
            myString = strftime("%Y-%m-%dT%H:%M:%SZ", localtime()) + "\t" + str(val) + "\n"
        elif val >=100:
            myString = strftime("%Y-%m-%dT%H:%M:%SZ", localtime()) + "\t0" + str(val) + "\n"
        elif val >= 10:
            myString = strftime("%Y-%m-%dT%H:%M:%SZ", localtime()) + "\t00" + str(val) + "\n"
        else:
            myString = strftime("%Y-%m-%dT%H:%M:%SZ", localtime()) + "\t000" + str(val) + "\n"
        print(myString)
        self.lock.acquire()
        if(channel == 0x40):
        fobj = open("/home/pi/WaterMe/WaterMePy/HumidityValues.txt","a")
        fobj.write(myString)
        fobj.close()
        self.lock.release() 
    

