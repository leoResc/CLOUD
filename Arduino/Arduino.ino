/*
    Project CLOUD
    Code for Arduino Uno
    Version 1.1
    authors: Leo Reschetko, Philipp Perez Heil
    Visit http://blog.licua.de for more information about this project!
*/

#define MIC A0
#define LED11 11
#define LED12 12
#define LED13 13

long sensorValue=0;  

void setup() {
  Serial.begin(9600);
  
  pinMode(MIC, INPUT);
  pinMode(LED11, OUTPUT);
  pinMode(LED12, OUTPUT);
  pinMode(LED13, OUTPUT);
  digitalWrite(LED11,LOW);
  digitalWrite(LED12,LOW);
  digitalWrite(LED13,LOW);
}

void loop() {
  // Serial Communication between Arduino and Rapsberry Pi
  int mode;
  if (Serial.available()) {
    mode = (Serial.read() - '0');
  };
  
  sensorValue = analogRead(MIC); 
  if(sensorValue >= 378)
  {
    lightUp(LED11,59);
    if(sensorValue >= 400)
    {
      lightUp(LED12,59);
      if(sensorValue >= 410)
      {
        lightUp(LED13,59);
      }
    }    
  }
}

void lightUp(int led, int duration) {
  digitalWrite(led,HIGH);
  delay(duration);
  digitalWrite(led,LOW);
  delay(2 * duration);
}
