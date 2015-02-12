import java.awt.event.*;
import javax.swing.*;


/**
 * ButtonListener records the actions done on the buttons
 * 
 * @author (Maytham Sabbagh, maythamsabbagh@gmail.com) 
 * @version (4.0)
 */
public class ButtonListener implements ActionListener
{ 
    MCalendar calend;
    /**
     * The listener stores a reference to the calendar's model.
     */
    public ButtonListener(MCalendar calend)
    {
        // initializing the listener with the calendar
        this.calend = calend;
    }
   
    /**
     * Checks what action was performed (which button was pressed), and updates the 
     * calendar accordingly 
     */
    public void actionPerformed(ActionEvent event)
    {
        /* Get a reference to the button that was clicked. */ 
        JButton button = (JButton) event.getSource();
        // get the different buttons from the calendar
        JButton[] gridmon = calend.getButtonMon();
        JButton[] gridnum = calend.getButtonNum();
        JButton[] com = calend.getButtonCom();
        
        // if button pressed is the previous, call the decrement method, and reset 
        // count and partial year. if the button is next, call the increment method
        // and reset the count and partial year.
        if (button == com[0]){
            calend.updatePrevious();
            calend.partialyear = 0;
            calend.inputUpdate();
        }else if(button == com[1]){
            calend.updateNext();
            calend.partialyear = 0;
            calend.inputUpdate();
        }else if(button == com[2]){
            calend.reUpdate();
            calend.partialyear = 0;
            calend.inputUpdate();
        }
        
        // if the button pressed is one of the months, then update the calendar with 
        // that month, and reset count and partial year
        for(int i=0; i<12;i++){
            if (button == gridmon[i]){
                calend.updateMonth(i+1);
                calend.partialyear = 0;
                calend.inputUpdate();
            }
        }
        
        // check if the button pressed is a number for the year
        for(int i=0; i<11;i++){
            if(button == gridnum[i]){
                if(i == 10){
                    // the button pressed is the DONE button
                    calend.updateYear(calend.partialyear);
                    calend.partialyear = 0;
                    calend.inputUpdate();
                }else{
                    calend.partialyear = (calend.partialyear*10) + i;
                    calend.inputUpdate();
                }
            }
        }
        
        if(calend.getHelp() == button){
            calend.message("help");
        }
        
    }
}