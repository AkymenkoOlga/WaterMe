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
    top = 0
    x = 1

    def showtext(self,text):
        self.draw.rectangle((0, 0, self.width, self.height), outline=0, fill=0)
        self.draw.text((self.x, self.top), 'status:', font = self.font, fill = 255)
        self.draw.text((self.x, self.top + 20), text, font = self.font, fill =255)
        self.disp.image(self.image)
        self.disp.display()
        return