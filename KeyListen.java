import java.awt.event.*;
/**
 * Write a description of class KeyListen here.
 * 
 * @author (Maytham Sabbagh, maythamsabbagh@gmail.com) 
 * @version (4.0)
 */
public class KeyListen implements KeyListener
{
    MCalendar calend;
    
    /**
     * Constructor for objects of class KeyListen
     */
    public KeyListen(MCalendar calend)
    {
        // initializing the listener with the calendar
        this.calend = calend;
    }

    /**
     * 
     */
    @Override
    public void keyPressed(KeyEvent e){
        int keyCode = e.getKeyCode();
        switch( keyCode ) { 
            case KeyEvent.VK_LEFT:
                calend.updatePrevious();
                break;
            case KeyEvent.VK_RIGHT:
                calend.updateNext();
                break;
            case KeyEvent.VK_UP:
                calend.reUpdate();
                break;  
            case KeyEvent.VK_DOWN:
                calend.message("help");
                break;  
                
            case KeyEvent.VK_MINUS:
                calend.updatePrevious();
                break;
            case KeyEvent.VK_EQUALS:
                calend.updateNext();
                break;    
                
            case KeyEvent.VK_ENTER :
                calend.KeyBoardYear(10);
                break;
            case KeyEvent.VK_0:
                calend.KeyBoardYear(0);
                break;
            case KeyEvent.VK_1:
                calend.KeyBoardYear(1);
                break;
            case KeyEvent.VK_2:
                calend.KeyBoardYear(2);
                break;
            case KeyEvent.VK_3:
                calend.KeyBoardYear(3);
                break;
            case KeyEvent.VK_4:
                calend.KeyBoardYear(4);
                break;
            case KeyEvent.VK_5:
                calend.KeyBoardYear(5);
                break;
            case KeyEvent.VK_6:
                calend.KeyBoardYear(6);
                break;
            case KeyEvent.VK_7:
                calend.KeyBoardYear(7);
                break;
            case KeyEvent.VK_8:
                calend.KeyBoardYear(8);
                break;
            case KeyEvent.VK_9:
                calend.KeyBoardYear(9);
                break;
            case KeyEvent.VK_NUMPAD0:
                calend.KeyBoardYear(0);
                break;
            case KeyEvent.VK_NUMPAD1:
                calend.KeyBoardYear(1);
                break;
            case KeyEvent.VK_NUMPAD2:
                calend.KeyBoardYear(2);
                break;
            case KeyEvent.VK_NUMPAD3:
                calend.KeyBoardYear(3);
                break;
            case KeyEvent.VK_NUMPAD4:
                calend.KeyBoardYear(4);
                break;
            case KeyEvent.VK_NUMPAD5:
                calend.KeyBoardYear(5);
                break;
            case KeyEvent.VK_NUMPAD6:
                calend.KeyBoardYear(6);
                break;
            case KeyEvent.VK_NUMPAD7:
                calend.KeyBoardYear(7);
                break;
            case KeyEvent.VK_NUMPAD8:
                calend.KeyBoardYear(8);
                break;
            case KeyEvent.VK_NUMPAD9:
                calend.KeyBoardYear(9);
                break;
            
            case KeyEvent.VK_J:
                calend.KeyBoardMonth(0);
                break;    
            case KeyEvent.VK_F:
                calend.KeyBoardMonth(1);
                break; 
            case KeyEvent.VK_M:
                calend.KeyBoardMonth(2);
                break; 
            case KeyEvent.VK_A:
                calend.KeyBoardMonth(3);
                break; 
            case KeyEvent.VK_Y:
                calend.KeyBoardMonth(4);
                break; 
            case KeyEvent.VK_U:
                calend.KeyBoardMonth(5);
                break; 
            case KeyEvent.VK_L:
                calend.KeyBoardMonth(6);
                break; 
            case KeyEvent.VK_G:
                calend.KeyBoardMonth(7);
                break; 
            case KeyEvent.VK_S:
                calend.KeyBoardMonth(8);
                break; 
            case KeyEvent.VK_O:
                calend.KeyBoardMonth(9);
                break;     
            case KeyEvent.VK_N:
                calend.KeyBoardMonth(10);
                break;     
            case KeyEvent.VK_D:
                calend.KeyBoardMonth(11);
                break;     
         }
    }
    
    /**
     * 
     */
    @Override
    public void keyReleased(KeyEvent e){
        //
    }
    
    /**
     * 
     */
    @Override
    public void keyTyped(KeyEvent e){
        int keyCode = e.getKeyCode();
        switch( keyCode ) { 
            case KeyEvent.VK_LEFT:
                calend.updatePrevious();
                break;
            case KeyEvent.VK_RIGHT :
                calend.updateNext();
                break;
         }
    }
}
