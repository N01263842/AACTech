# Authors  :   Akeem Abrahams, Asmaa Alzoubi, Cedric Wambe
# Team Name: AAC-Tech

from pulsesensor import Pulsesensor
import max30100
import time

# Initializing MCP3008 Heart Rate Sensor
p = Pulsesensor()
p.startAsyncBPM()

# Initializing Max30100
mx30 = max30100.MAX30100()
mx30.set_mode(max30100.MODE_SPO2) # Enabling SPO2 mode on MAX30100
mx30.set_spo_config()
mx30.set_led_current()

print("-----------------------------------------------------------------------")
print("--------               PARAMED SENSOR READINGS                 --------")
print("-----------------------------------------------------------------------")


try:
    while True:
        bpm = p.BPM
        mx30.read_sensor()
        if bpm > 0: # If bpm greater than 0 then print the current heart rate pulse to the screen
            print("BPM: %d" % bpm)
        else:
            print("No Heartbeat found")
            
        if(mx30.red > 0 and mx30.ir > 0):
        #print("Raw IR Readings: "+str(mx30.ir))
        #print("Raw Red Readings: "+str(mx30.red))
        #print("")
        #print("Calculating SPO2....")
        #time.sleep(1)
            value = (float)(mx30.red)/(float)(mx30.ir)
            print("SPO2: "+str(value*100)+"%")
            print("")
        else:
            print("Finger not detected")    
        
        time.sleep(2)
except:
    p.stopAsyncBPM()
    