import spidev as SPI
import SSD1306
import Image
import ImageDraw
import ImageFont

# Raspberry Pi pin configuration:
RST = 19
DC = 16
bus = 0
device = 0

# 128x64 display with hardware SPI:
disp = SSD1306.SSD1306(RST, DC, SPI.SpiDev(bus, device))

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

# Draw a black filled box to clear the image.
draw.rectangle((0,0,width,height), outline=0, fill=0)

# Draw some shapes.
# First define some constants to allow easy resizing of shapes.
draw.ellipse((32,0,96,64),'white','blue') # face
draw.ellipse((43,20,53,30),0,0) # left eye
draw.ellipse((75,20,85,30),0,0) # right eye
draw.arc((39,25,89,55), 0, 180, 0) # smile
font = ImageFont.load_default()

# Display image.
disp.image(image)
disp.display()
