import socket
import sys
import oled
import Controller
from thread import allocate_lock
import time

class BluetoothManager:

    hostMACAddress = '00:15:83:E7:B4:A5'
    port = 3
    backlog = 1
    size = 1024
    controller = Controller.Controller(3600)
    oled = oled.Oled()
    lock = allocate_lock()

    def __init__(self):
        refreshRate = 0

        if (len(sys.argv) < 2):
            refreshRate = 3600
        elif (len(sys.argv) == 2):
            refreshRate = int(sys.argv[1])
            self.controller.rate = refreshRate
        else:
            print("WaterMePy.py takes only one parameter")
        self.controller.startReading()
        return

    def btlisten(self):
        s = socket.socket(socket.AF_BLUETOOTH, socket.SOCK_STREAM, socket.BTPROTO_RFCOMM)
        s.bind((self.hostMACAddress, self.port))
        s.listen(self.backlog)
        self.oled.showtext('wait for clients')
        print ('Waiting for connection on RFCOMM channel %d' % self.port)

        try:
            client, address = s.accept()
            print "Accepted connection from", address
            self.oled.showtext('connected')
            while 1:
                data = client.recv(self.size)
                if data == 'alarm on':
                    self.controller.setBeepEnable(True)
                    print('alarm on')
                if data == 'alarm off':
                    self.controller.setBeepEnable(False)
                    print('alarm off')
                if data == "LED on":
                    self.controller.startLedControl()
                    client.send('LEDs on!')
                if data == "LED off":
                    self.controller.stopLedControl()
                    client.send('LEDs off!')
                if data == "graph":
                    client.send('#total' + str(getNumberOfLines()) + '!')
                    self.sendValues(client)
                if data == "request":
                    try:
                        val = self.controller.readChannel(0)
                        client.send('begin' + str(val) + '!')
                    except KeyboardInterrupt:
                        print ('Cancel')
        except:
            print('Unexpected error:' + str(sys.exc_info()[0]))
            print('Closing socket')
            self.oled.showtext('socket closed')
            client.close()
            s.close()
            self.btlisten()
            return

    def sendValues(self, client):
        data = client.recv(1024)

        if (not (data == "next")):
            print('Error in transmission')
            return
        print('next received\n')

        self.lock.acquire()
        fobj = open("/home/pi/WaterMe/WaterMePy/HumidityValues.txt", "r")
        for line in fobj:
            client.send('#'+ line + '!')
            print('send: '+ line)
            data = client.recv(1024)
            if (not (data == "next")):
                print('Error in transmission')
                return
        client.send('#EOT!')
        fobj.close()
        self.lock.release()

        print('End of Transmission')
        return
		
def getNumberOfLines():
    lock = allocate_lock()
    lineCounter = 0
    lock.acquire()
    fobj = open("/home/pi/WaterMe/WaterMePy/HumidityValues.txt", "r")
    for line in fobj:
        lineCounter = lineCounter + 1
    fobj.close()
    lock.release()
    print('Total Lines: ' + str(lineCounter))
    return lineCounter


if __name__ == "__main__":
    btmgr = BluetoothManager()
    btmgr.btlisten()

