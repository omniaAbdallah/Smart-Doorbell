// Initialize the Camera 
import processing.video.*;
Capture video;                 
String[] camerasList;                   // array: available Cameras 
DropDown UI_camList  = new DropDown();      // Drop List Object in GUI
UIDimensions UI_camDimensions = new UIDimensions(10,10,360,30);

// Initialize the Serial
import processing.serial.*;
Serial port;
String[] serialList;                    // array: available Ports
DropDown UI_comPorts = new DropDown();      // Drop List Object in GUI
UIDimensions UI_serialDimensions = new UIDimensions(400,10,200,30);
byte[] inBuffer = new byte[32];
String stringBuffer;

// Host , Port , endpoint 
TextInput UI_hostInput      = new TextInput("http://server.dev");
TextInput UI_portInput      = new TextInput("80");
TextInput UI_endPointInput  = new TextInput("/get_img");

UIDimensions UI_hostInDim     = new UIDimensions(25,100,250,30);
UIDimensions UI_portInDim     = new UIDimensions(300,100,100,30);
UIDimensions UI_endPointInDim = new UIDimensions(415,100,180,30);

// This client will be used to send the file to the server
Client client;


void Page_Config_init() {
    // Populate the cameras array with available cameras
  camerasList = Capture.list();
  if(camerasList.length == 0) {  // No cameras found
    println("There's no cameras");
    alert("No Camera Found", "Error");
    restart();
  } else {
    for (int i=0; i<camerasList.length; i++) {
      String el = camerasList[i];
      println(el);
    }
  }
  
  // Populate the serial array with available ports
  serialList = Serial.list();
  if(serialList.length == 0) {  // No cameras found
    println("There's no ports");
    alert("No Port Found", "Error");
    restart();
  }  
}

void Page_Config_render() {
  // Refresh Background
  page.refresh();
  
  // Render the DropList
  UI_camList.draw(
    camerasList, 
    UI_camDimensions.x, 
    UI_camDimensions.y, 
    UI_camDimensions.w, 
    UI_camDimensions.h, 
    "Please Choose Your Camera:");
    
  UI_comPorts.draw(
    serialList,
    UI_serialDimensions.x,
    UI_serialDimensions.y,
    UI_serialDimensions.w,
    UI_serialDimensions.h,
    "Please Choose Your Port:");
  
  UI_hostInput.draw(
    UI_hostInDim.x,
    UI_hostInDim.y,
    UI_hostInDim.w,
    UI_hostInDim.h);
  
  UI_portInput.draw(
    UI_portInDim.x,
    UI_portInDim.y,
    UI_portInDim.w,
    UI_portInDim.h);
  
  UI_endPointInput.draw(
    UI_endPointInDim.x,
    UI_endPointInDim.y,
    UI_endPointInDim.w,
    UI_endPointInDim.h);
    
  
    
  
  
  if(Button("Connect Application", width/2 - 200, 400, 400, 50)) {
    if(UI_hostInput.getText().isEmpty() || UI_portInput.getText().isEmpty()) {
      alert("Hostname or Port is Empty ","Empty Fields");
      restart();
      return;
    }
    try {
      client = new Client(
                    UI_hostInput.getText().trim(), 
                    Integer.parseInt(UI_portInput.getText().trim()), 
                    UI_endPointInput.getText().trim());
    } catch (Exception e) {
      alert("Please Enter Correct Data\n" + e.toString(),"Input Data Error");
      restart();
    }
    println("Choosed\nCamera: "+ camerasList[UI_camList.getSelected()]);
    try {
      video = new Capture(this, 640,480, camerasList[UI_camList.getSelected()]);
      video.start();
    } catch(Exception e) {
      alert(e.toString());
      restart();
    }
    
    println("Port: "+ serialList[UI_comPorts.getSelected()]);
    try {
      port  = new Serial(this, serialList[UI_comPorts.getSelected()], 9600);
    } catch(Exception e) {
      restart();
      alert(e.toString());
    }
    
    
    page.switchTo(Pages.Camera);
   
  } 
}