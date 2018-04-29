import http.requests.*;
import processing.video.*;

import processing.serial.*; 
Serial myPort;    // The serial port
String inString;  // Input string from serial port
int lf = 10;      // ASCII linefeed 

Capture video;
String fullPath = sketchPath("");
String currentFolder = "D:/python_web/webser/doorBellCamera";
String host = "http://localhost:5005/get_img";

public void setup() 
{
	size(640, 480);
	smooth();
  // Listing all the cameras available
  String[] cameras = Capture.list();
  
  if(cameras.length == 0) {
    println("There are no cameras available for capture.");
    exit();
  } else {
    println("Available cameras:");
    for(int i=0; i<cameras.length; i++)
      println(cameras[i]+"//"+i);
  }
  video = new Capture(this, 640, 480, cameras[0]);
  video.start();
  
  //serial
  myPort = new Serial(this, Serial.list()[0], 9600); 
  myPort.bufferUntil(lf);
}

void draw() {
  if(video.available() == true)
    video.read();
  image(video, 0, 0);  
  

}

// Handler event mousePressed
void mousePressed() {
  if(mouseButton == LEFT) {
    capture();
  }
}

void sendPost(String fileName) {
  PostRequest post = new PostRequest(host,"multipart/form-data");
  post.addHeader("boundary","----WebKitFormBoundary7MA4YWxkTrZu0gW");
  post.addFile("fileToUpload", fileName);
  post.addData("submit","ok");
  post.send();
  System.out.println("Reponse Content: " + post.getContent());
  System.out.println("Reponse Content-Length Header: " + post.getHeader("Content-Length"));
}


void serialEvent(Serial p) { 
  inString = p.readString();
  println(inString);
  //if(inString == "Pressed")
    capture();
} 

void capture() {
 String fileName = "/data/test"+hour()+minute()+second()+".png"; 
    save(fileName);
    println(fullPath);
    println(currentFolder+fileName);
    sendPost(currentFolder+fileName); 
  
}
