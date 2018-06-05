// GUI pages
//enum Pages {Page1, Page2};
class Page {
  // this router used to draw the current page using switch case and render function
  private Pages router;
  
  
  // Constructor
  Page(Pages landingPage) {
    this.router = landingPage; 
  }
  
  // This function is to set the current 
  public void switchTo(Pages pageName) {
    this.refresh();
    this.router = pageName;
  }
  
  public void refresh() {
    background(c_mid);
  }
}