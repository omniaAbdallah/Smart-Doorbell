import http.requests.*;
String currentFolder = sketchPath("").replace('\\','/');


//PImage img = createImage();

class Client {
  String url = "";
  Client(String host, int port, String endPoint) {
    // Check if the host starts with http, https prefex
    
    if(!(host.startsWith("http://") || host.startsWith("https://"))) {
      host = "http://" + host;
    }
    
    if(host.startsWith("https://")) {
      port = 433;// SSL port
    }
    
    if(!endPoint.startsWith("/")) {
        endPoint = "/" + endPoint;
    }
    println(host.charAt(host.length() -1) == '/');
    // If the host string have a '/' at the end of it
    if(host.charAt((host.length()) -1) == '/' ) {
      host = host.substring(0, host.length()-1);
      
    } 
    
    this.url = host+":"+port+endPoint;
    println(this.url);
  }
  
  public boolean sendFile(String filePath) {
    try {
      PostRequest post = new PostRequest(this.url,"multipart/form-data");
      post.addHeader("boundary","----WebKitFormBoundary7MA4YWxkTrZu0gW");
      post.addFile("fileToUpload", filePath);
      post.addData("submit","ok");
      post.send();
      println("Reponse Content: " + post.getContent());
      println("Reponse Content-Length Header: " + post.getHeader("Content-Length"));
      return true;
    } catch (Exception e) {
      alert("Problem while sending file to server \n"+this.url+"\n"+e.toString(), "Server Error");
      return false;
    }
  }
}

void capture() {
  String fileName = dataFile("test"+hour()+minute()+second()+".png").toString(); 
  save(fileName);
  println(fileName);
  if(client.sendFile(fileName))
    println("File sent successfully"); 
  else
    println("Please check connectivity and try again");
}
