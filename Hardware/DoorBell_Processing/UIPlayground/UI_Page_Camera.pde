void Page_Camera_init() {
  
}
void Page_Camera_render() {

  if(port.available() > 0) {
    String buf = port.readStringUntil('\r');
    if(buf != null && buf.indexOf("Pressed") != -1) {
      capture();
    }
  }
  
  if(video.available() == true) {
    video.read();
    image(video, 0, 0);   
  }
  if(Button("C", 0, 0, 30, 30)) {
    capture();
  }
}