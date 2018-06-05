/*--------------------------------------------------------------------------------+
 |This sketch contains all unstable new methods and classes of cards_ui UIkit      |
 |author: Lucas Cassiano                                                           |
 |creation date: August 7, 2016                                                    |
 |last update: August 7, 2016                                                      |
 ----------------------------------------------------------------------------------*/

//Drag&Drog Object
//Needs a Spawn object (also template), a receiver and a 
public void Draggable() {
}
public class DraggableObject {
  private int originX = -1;
  private int originY = -1;
  private boolean selected = false;  
  private int x=0, y=0, w=0, h=0;
  private boolean hover = false;
  private boolean setOriginPos = false;
  private int originPosX =0; //original Positions
  private int originPosY = 0;
  private boolean hasNewOrigin = false;
  private int gridSize = 1;

  private void setOrigin(int x, int y) {
    originPosX = x;
    originPosY = y;
  }
  public DraggableObject() {
    //Empty Constructor
  }

  public DraggableObject(int x, int y, int w, int h) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
  }


  public void drag(int x, int y, int w, int h) {
    if (!setOriginPos) {
      if (!hasNewOrigin) {
        this.x = x;
        this.y = y;
        setOrigin(x, y);
        this.w = w;
        this.h = h;
      } else {
        setOrigin(this.x, this.y);
      }

      setOriginPos = true;
    }

    if (clicked && selected) {
      this.x = originPosX-originX+mouseX;
      this.y = originPosY-originY+mouseY;
      hasNewOrigin = true;
    } else {
      selected=false;
      setOriginPos = false;
    }

    if (mouseX >= this.x && mouseX <= this.x+w && 
      mouseY >= this.y && mouseY <= this.y+h) {
      if (clicked && !selected) {
        selected = true;
        originX = mouseX;
        originY = mouseY;
      }

      hover = true;
    } else {
      hover = false;
    }
  }

  public boolean getSelected() {
    return selected;
  }
};

public class DropprableObject {
  private int x, y, w, h;
  private StringList canReceive = new StringList();

  public void setPosition(int x, int y) {
    this.x = x;
    this.y = y;
  }

  //Verifies if the point _x,_y is inside the rectangle of the droppableObject
  public boolean Contains(int _x, int _y) {
    if (_x >= this.x && _x <= this.x+w && 
      _y >= this.y && _y <= this.y+h) {
      return true;
    } else {
      return false;
    }
  }
}

/*--UI elements--*/
public class DragDropCard extends DraggableObject {
  public DragDropCard() {
    super();
  }

  public void BeginCard(String title, int x, int y, int w, int h) {
    super.drag(x, y, w, y+40);
    if (super.getSelected() || super.hover)
      beginCard( title+" hover", super.x, super.y, w, h);
    else {
      beginCard( title, super.x, super.y, w, h);
    }
  }
};

//Basic Text Button
public boolean Draggable(int x, int y, int w, int h) {
  if (mouseX >= x && mouseX <= x+w && 
    mouseY >= y && mouseY <= y+h) {
    fill(c_hover);
    rect(x, y, w, h);
    fill(c_text_color);
    if (clicked) {
      fill(c_light);
      rect(x+mouseX, y+mouseY, w, h);
      //canClick = false;
      //line(mouseX, 20, mouseX, 80);
      return true;
    }
  } else {
    fill(c_light);
    rect(x, y, w, h);
    return false;
  }
  return false;
}


public class DropDown {
  private int selected = 0;
  private boolean open = false;
  private String labelText = "";

  public DropDown() {
  }
  public void draw(String args[], int x, int y, int w, int h, String labelText) {
    this.labelText = labelText;
    this.draw(args, x, y, w, h);
  }
  
  public void draw(String args[], int x, int y, int w, int h) {
    int labelOffset = 0;
    
    // Render the label first if avaible
    if(this.labelText != "") {
      textSize(default_fontSize);
      fill(c_very_dark);
      textAlign(LEFT);
      text(labelText, x, y, w, h);
      
      labelOffset = h;
    }
    // Render the first element in dropdown, aka : (activated element)
    //fill(c_light);
    //rect(x, y+labelOffset, w, h);
    //fill(c_text_color);
    //textSize(default_fontSize);
    //textAlign(CENTER, CENTER);
    //text(args[0], x, y+labelOffset, w, h);
    
    
    if((args.length >= 1))
    {
      // Render the dropdown button    
      if (Button("▼", x+w, y+labelOffset, h, h)) {
        open = !open;
      }   
      
      // If dropdown button is clicked show the list
      if (!open){
        if(Button(args[selected], x, y+labelOffset, w, h)) {
          open = !open;
        }
      } else {
        // Render the selected element
        fill(c_hover);
        rect(x, y+labelOffset, w, h);
        fill(c_text_color);
        textSize(15);
        textAlign(CENTER, CENTER);
        text(args[selected], x, y+labelOffset, w, h);
        // Render the drop list
        for ( int i=0; i<args.length; i++) {
          if(Button(args[i], x, (y+labelOffset)+h*(i+1), w, h)) {
            selected = i;
            open = false;
            // User added code
            println(selected);
          }
        }
      }
    }
  }

  public int getSelected() {
    return selected;
  }
}