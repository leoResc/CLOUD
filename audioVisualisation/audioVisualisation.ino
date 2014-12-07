/* Audio Visualisation
   Version 1.0
   author: Leo Reschetko
*/

const int aPin = A0; 
const int LED_1 = 13;
const int LED_2 = 12;
const int LED_3 = 11;
long sensorValue=0;  
int vol;

void setup() {
  pinMode(aPin, INPUT);
  pinMode(LED_1, OUTPUT);
  pinMode(LED_2, OUTPUT);
  pinMode(LED_3, OUTPUT);
  digitalWrite(LED_1,LOW);
  digitalWrite(LED_2,LOW);
  digitalWrite(LED_3,LOW);
  Serial.begin(9600); 
  }

void loop() {  
  
   sensorValue = analogRead(aPin); 
    
   Serial.println(sensorValue);
  
  if(sensorValue >= 360)
  {
    lightUp(LED_1,59);
      if(sensorValue >= 370)
    {
      lightUp(LED_2,59);
      if(sensorValue >= 380)
      {
        lightUp(LED_3,59);
      }
    }
    
  }
  
       
}

void lightUp(int led, int duration) {
  digitalWrite(led,HIGH);
  delay(duration);
  digitalWrite(led,LOW);
}
