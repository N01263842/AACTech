#Name: Akeem Abrahams
#Project: Pulse Oxymetry (MAX30100)
#Student#: N01263842

import max30100
import time

mx30 = max30100.MAX30100()

mx30.set_mode(max30100.MODE_SPO2)

mx30.set_spo_config()

mx30.set_led_current()

while True:
    mx30.read_sensor()
    if(mx30.red > 0 and mx30.ir > 0):
        print("Raw IR Readings: "+str(mx30.ir))
        print("Raw Red Readings: "+str(mx30.red))
        print("")
        print("Calculating SPO2....")
        time.sleep(1)
        value = (float)(mx30.red)/(float)(mx30.ir)
        print("Calculated Value: "+str(value*100)+"%")
        print("")
    else:
        print("Finger not detected")
    time.sleep(1)
