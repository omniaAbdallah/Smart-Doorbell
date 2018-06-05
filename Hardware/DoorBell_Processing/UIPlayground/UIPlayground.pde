// GUI pages
enum Pages {Config, Camera};
Page page = new Page(Pages.Config);

void setup() {
  // Size and GUI Style
  size(640, 480);
  uiLight();

  Page_Config_init();
  Page_Camera_init();
}

void draw() {
  
  switch(page.router) {
    case Config:
      Page_Config_render();   
    break;
    case Camera:
      Page_Camera_render();
    break;
  }

}