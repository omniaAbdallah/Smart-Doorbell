#include <ESP8266WiFi.h>
#include <WiFiClient.h>
#include <ESP8266WebServer.h>
#include <ESP8266mDNS.h>

#include "DIO.h"

// DIO 
#define BTN D6
#define LED D7

const char* ssid = "Smart Door Bell";
const char* password = "D00RBE11";

//IPAddress ip(192, 168, 1, 222);
//IPAddress gateway(192, 168, 1, 1);
//IPAddress mask(255, 255, 255, 0);



ESP8266WebServer server(80);

bool doorState = false; // false: closed, true: opened

void setup() {
  pinMode(BTN, INPUT_PULLUP);
  pinMode(LED, OUTPUT);
  Serial.begin(9600);   

  // Static ip for ESP
//  WiFi.config(ip, gateway, mask);
  // Connecting to the WiFi 
  WiFi.mode(WIFI_AP);
//  WiFi.begin(ssid, password);
  Serial.println("");

  // Wait for connection
//  while (WiFi.status() != WL_CONNECTED) {
//    delay(500);
//    Serial.print(".");
//  }

  // AP
  WiFi.softAP(ssid, password);


  Serial.println("");
  Serial.print("Connected to ");
  Serial.println(ssid);
  Serial.print("IP address: ");
//  Serial.println(WiFi.localIP());
  Serial.println(WiFi.softAPIP());
  
//TO make DNS address for esp
  if (MDNS.begin("esp")) {
    Serial.println("MDNS responder started");
  }

  server.on("/door/open", openDoor);
  server.on("/door/close", closeDoor);
  server.on("/door/state", doorStateCheck);

  server.onNotFound(handle404);

  server.begin();
  Serial.println("HTTP server started");
}

void loop() {
  server.handleClient();

  if(digitalRead(BTN) == LOW) // Button is Pressed
  {
    delay(50);
    while(digitalRead(BTN) == LOW) {}
    if(digitalRead(BTN) == HIGH){
      Serial.println("Pressed");
      delay(50);
    }
  }
  
}

void closeDoor() {
  // Hardware
  digitalWrite(LED, LOW);
  Serial.println("Door is closed");
  doorState = false;
  // Server
  server.send(200, "text/plain", "Door is closed");
}


void openDoor() {
  // Hardware
  digitalWrite(LED, HIGH);
  Serial.println("Door is opened");
  doorState = true;
  // Server
  server.send(200, "text/plain", "Door is opened");
}

void doorStateCheck() {
  String message = "";
  message += "Door is :";
  message += (doorState)? "opened" : "closed" ;
  server.send(200, "text/plain", message);
}

void handle404 () {
  server.send(404, "text/plain", "ERROR : 404");
}

