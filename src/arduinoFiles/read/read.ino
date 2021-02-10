/*
This is for use with Sparkfun's CAN Bus Shield:
https://www.sparkfun.com/products/10039
*/

#include <Canbus.h>
#include <defaults.h>
#include <global.h>
#include <mcp2515.h>
#include <mcp2515_defs.h>
#include <SPI.h>

unsigned long time;

void setup() {
  Serial.begin(9600); // For debug use
  Serial.println("Retrieving CAN messages");  
  delay(1000);
  if(Canbus.init(CANSPEED_256)) 
  //Initialise MCP2515 CAN controller at the specified speed
    Serial.println("CAN Init ok");
  else
    Serial.println("Can't init CAN");
    
  delay(1000);
}

void loop(){
time = millis();
  tCAN message;
if (mcp2515_check_message()) 
	{
    if (mcp2515_get_message(&message)) 
	{
        if(message.id == 0x358 || message.id == 0x60D || message.id == 0x625)  
        //uncomment when you want to filter
             {
               Serial.print(time);
               Serial.print(" - ID: ");
               Serial.print(message.id,HEX);
               Serial.print(", ");
               Serial.print("Data length: ");
               Serial.print(message.header.length,DEC);
               Serial.print("Data: ");
               for(int i=0;i<message.header.length;i++) {	
                  Serial.print(message.data[i],HEX);
                  Serial.print(" ");
                }
               Serial.println("");
             }
           }}
}
