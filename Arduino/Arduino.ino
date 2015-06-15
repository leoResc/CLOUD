// #######################################################
// Arduino Sketch v2.0 CLOUD
// Device: Arduino Uno
// COPYRIGHT: Leo Reschetko, Philipp Perez,  Project CLOUD
// Visit blog.licua.de for more information!
// #######################################################

int command = 3; //default wert
boolean LedOn[12];
int vol[3]; //für die Lautstärke
int aveMin;
int aveMax;
int bassV;
int bright[6];
  

#define MIC A0

void setup(){
  
  bright[0] = 3;
  bright[1] = 5;
  bright[2] = 6;
  bright[3] = 9;
  bright[4] = 10;
  bright[5] = 11;
  
  pinMode(2, OUTPUT);
  pinMode(3, OUTPUT);
  pinMode(4, OUTPUT);
  pinMode(5, OUTPUT);
  pinMode(6, OUTPUT);
  pinMode(7, OUTPUT);
  pinMode(8, OUTPUT);
  pinMode(9, OUTPUT);
  pinMode(10, OUTPUT);
  pinMode(11, OUTPUT);
  pinMode(13, OUTPUT);  
  
  //set all off = 0
  for(int i=0;i<12;i++) {
    LedOn[i] = false;
  }

Serial.begin(9600);
}

void loop(){
 
if (Serial.available()) {
    command = (Serial.read()-'0');
  }

switch(command) {
  case 1: ledTest();      command = 3;   break;
  case 2: thunder();      command = 3;   break;
  case 3: music();                       break;
  case 4: pulsatingLight();              break;
  case 5: thunderstorm(); command = 3;   break;
  default: command = 3;                  break;
}

}

void turnOff(int led, int time = 0){
   digitalWrite(led, LOW);
   LedOn[led-2] = false;
   delay(time);
}

void activate(int led, int time = 500, boolean turnOf = false) {
  
  digitalWrite(led, HIGH);
  LedOn[led-2] = true;
  delay(time);
    
    if(turnOf) {
      turnOff(led,0);
    }
}

void activateAll(int dell = 0){
  for(int i=2; i<13; i++) {
    activate(i,dell);
  }
}

void activateRandom(boolean noDelay = false, boolean turnOf = false){
    int randLED = random(0,12);
    int randTIME = random(70,150);
    
    if(noDelay) {
      randTIME = 0;
    }
    
    activate((randLED+2),randTIME, turnOf);
    LedOn[randLED] = true; //LED ON
}

void turnRestOff(int time = 0){
  for(int i =0; i<12;i++) {
    if(LedOn[i]){
      turnOff((i+2),time);
    }
  }
}

//////////////////////////////////////////////////////////////////
void brightLED(int LED, int speedUP=50, int speedDOWN = 50) {

  int brightness = 0;
  
  while(brightness <= 250){
    brightness += 5;
        analogWrite(LED, brightness);
        delay(speedUP);
   }
   //activate(LED);
   while(brightness >= 5){
    brightness -= 5;
        analogWrite(LED, brightness);
        delay(speedDOWN);
   }
   //turnOff(LED);
}

int readMIC() {
  int volume = abs(analogRead(MIC)-330);
  
  return volume;
}

void calculateVolume(){
  
  int sv; //sensor value
  int minV = 1000;
  int maxV = 0;
  int modus;
  int modusHelper=0;
  
  for(int i=0; i< 150; i++) {
    
    sv = readMIC();
    
    
    
    if(sv > maxV) {
      maxV = sv;
    }
    if(sv < minV) {
      minV = sv;
    }
    
  }
  
  Serial.println("min: ");
  Serial.println(minV);
  Serial.println("max: ");
  Serial.println(maxV);
  
  vol[0] = minV;
  vol[1] = minV + (maxV-minV)/2;
  vol[2] = maxV;

}

//'2' default
void thunder(){
    Serial.println("Thunder mode activated");

  //play thunder.mp3
  music();
}

void blitz(int start, int endd, int times=1, int time=20) {
  while(times > 0) {
    
    for(int i=start; i<=endd; i++) {
      activate(i, 0);
    }
    delay(time);
    turnRestOff(20);
    times --;
  }
}

void blitzTop(int times=1, int time = 20 ) {
 
  blitz(9,13,times,time);
}

void blitzBot(int times=1, int time = 20) {
  
   blitz(2,8,times,time);
}

void blitzBotLeft(int times=1, int time=20) {
  
  blitz(2,4,times,time);
}

void blitzBotRight(int times=1, int time=20) {
  
  blitz(5,8, times, time);
}

void blitzAll(int times=1, int time=20) {
  
  blitz(2,13,times,time);
}

//////////////////////////////////////////////////// <-------------------------------
//'5'
void thunderstorm(){
  
    delay(489);
    blitzAll(1,20);

    delay(500);
    blitzTop();
    delay(200);
    blitzBotLeft();
    
    delay(2500);
    blitzBot(2);
    
    delay(2000);
    
    blitzTop();
    
    delay(200);
    
    delay(500);
    blitz(2,3,3,10);
    
    delay(3800);
    blitzAll(1,300);
    delay(3990);
    
    blitzBotRight(1);
    delay(1111);
    blitz(8,9);    
    delay(4444);
    
    blitzBot(1);
    delay(500);
    blitz(10,13,3);


    delay(3800);
    blitz(5,10,4);
    delay(5000);
    blitzTop(1);
      
      
    delay(500);
    blitzAll(1,20);

    delay(2300);
    blitzTop();
    delay(200);
    blitzBotLeft();
    
    delay(3000);
  }


//'4'
void pulsatingLight(){
  
  turnOff(random(2,13),1000);
  
  brightLED(bright[random(0,6)]);
  activateRandom();
  brightLED(bright[random(0,6)]);
  activateRandom(); 
  
  turnOff(random(2,13),1000);
}

int getLevel(){
  int schleife = 15;
  int lvl = 0;
  for(int i=0; i< schleife; i++){
    lvl += readMIC();
  }
  
  return lvl/schleife;
  
}
//'3'
void music(){
  //Serial.println("Music mode activated");
  
  //turnRestOff(); 
  
  calculateVolume();
  int minV = vol[0];
  int maxV = vol[2];
  int aveV = vol[1];
  
  if( (maxV-minV) >= 300) {
    bassV = 3;
  }
  else if( (maxV-minV) >= 200 && (maxV-minV) < 300) {
    bassV = 2;
  }
  else {
    bassV = 1;
  }
  
  Serial.println(bassV);
  
  aveMin = minV + (aveV - minV)/2;
  aveMax = aveV + ((maxV - aveV)/5)*bassV;
  
  int sv = getLevel();
  //Serial.println("READ in music: ");
  //Serial.println(sv);
  
  if(sv > 15) {
 
    if(bassV == 3 && sv >= aveMax){
      level_one();
    }
    if(sv > aveMin && sv < aveMax){
      level_two();
    }
    if (sv < aveMin && sv > minV) {
      level_three();
    }
    //activateRandom();
    
    //activate(12);
    
  }
}

void level_one() {
  turnOff(8);
  turnOff(3);
  turnOff(6);
  turnOff(5);
  turnOff(9);
  turnOff(2);
  activate(8,0);
  activate(3,0);
  activate(6,0);
  activate(5,0);
  activate(9,0);
  activate(2,0);
  
  while(readMIC() >= aveMax){
    delay(11);
  }
  
  delay(200);
  turnRestOff(); 
  delay(50);
}
void level_two() {
  int rand = random(5,11);
  boolean noDelay = true;
  if(bassV == 1) {
    noDelay = false;
  }
  for(int i =0; i < rand; i++) {
    
    activateRandom(noDelay); 
  }
  activateRandom(false);
  
  
  while(readMIC() >= aveMin && readMIC() <= aveMax){
    delay(50);
  }
  
  
    turnRestOff(); 
    delay(300/bassV);


}
void level_three() {
   int rand = random(0,9/bassV);

  boolean noDelay = true;
  if(bassV == 1) {
    noDelay = false;
  }
  for(int i =0; i < rand+5; i++) {
    
    activateRandom(noDelay); 
  }
  activateRandom(false);
  
  if(rand == 0) {

    turnRestOff(); 
    delay(300/bassV);

  }
  
}

//'1'
void ledTest() {
    Serial.println("LED test mode activated");

  for(int i=2;i<14;i++) {
    activate((i),500);
  }
  
  turnRestOff();
}



