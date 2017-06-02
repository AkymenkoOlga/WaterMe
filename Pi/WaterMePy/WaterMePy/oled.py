import Image
import ImageDraw
import ImageFont
import SSD1306
import spidev as SPI

class Oled:

    RST = 19
    DC = 16
    device = 0

    # 128x64 display with hardware SPI:
    disp = SSD1306.SSD1306(RST, DC, SPI.SpiDev(0,device))
    
    # Create blank image for drawing.
    # Make sure to create image with mode '1' for 1-bit color.
    width = disp.width
    height = disp.height
    image = Image.new('1', (width, height))

    # Get drawing object to draw on image.
    draw = ImageDraw.Draw(image)

    font = ImageFont.truetype('/usr/share/fonts/truetype/dejavu/DejaVuSerif.ttf',16)
    top = 0
    x = 1

    def __init__(self):
        # Initialize library.
        self.disp.begin()
        #clear display
        self.disp.clear()
        self.disp.display()
        return

    def showtext(self,text):
        self.draw.rectangle((0,0,self.width,self.height), outline=0, fill=0)
        self.draw.rectangle((0, 0, self.width, self.height), outline=0, fill=0)
        self.draw.text((self.x, self.top), 'status:', font = self.font, fill = 255)
        self.draw.text((self.x, self.top + 20), text, font = self.font, fill =255)
        self.disp.image(self.image)
        self.disp.display()
        return

    def showsmiley(self, val):
        self.draw.rectangle((0,0,self.width,self.height), outline=0, fill=0)
        self.disp.clear()
        self.disp.display()
        self.draw.ellipse((36,4,92,60),'white','blue') # face
        self.draw.ellipse((46,20,56,30),0,0) # left eye
        self.draw.ellipse((72,20,82,30),0,0) # right eye
        if(val >= 60):
            self.draw.arc((43,22,85,52), 20, 180, 0) # smile
            self.disp.image(self.image)
            self.disp.display()
        if (val < 60 and val > 20):
            self.draw.line((50,47,78,47), 0, 1) # smile
            self.disp.image(self.image)
            self.disp.display()
        if (val <= 20):
            self.draw.arc((48,37,80,57), 180, 20, 0) # smile
            self.disp.image(self.image)
            self.disp.display()        
        return