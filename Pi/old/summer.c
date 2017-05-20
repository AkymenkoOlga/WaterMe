#include <bcm2835.h>
#include <stdio.h>

#define beep_on  i2c_writeByte(0x7F & i2c_readByte())
#define beep_off i2c_writeByte(0x80 | i2c_readByte())


void i2c_writeByte(char byte)
{
        char buf[] = {byte};
        bcm2835_i2c_write(buf,1);
}
char i2c_readByte()
{
        char buf[1];
        bcm2835_i2c_read(buf,1);
        return buf[0];
}


int main(int argc, char **argv)
{


  if (!bcm2835_init())return 1;
  bcm2835_i2c_begin();
  bcm2835_i2c_setSlaveAddress(0x20);
  bcm2835_i2c_set_baudrate(10000);
  beep_on;
  bcm2835_delay(100);
  beep_off;
}
