

// Alert
import javax.swing.JOptionPane;
public void alert(String message, String title) {
  JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
}
public void alert(String message) {
  alert(message, "");
}
// Restart program 
void restart() {
  page.switchTo(Pages.Config);
  setup();
}

// UI dimensions
class UIDimensions {
  int x = 0;
  int y = 0;
  int w = 0;
  int h = 0;
  UIDimensions(int x, int y, int w, int h) {
    this.x = x; 
    this.y = y;
    this.w = w; 
    this.h = h;
  }
}