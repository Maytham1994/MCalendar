import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import static java.util.concurrent.TimeUnit.*;
import java.text.NumberFormat;

/**
 * The MCalendar is an application which displays the calendar and lets the user request the months and the years. The year has to be greater or equal to 1900 and less than or equal to 999999. The year has been set this
 * way to allow for fast response. And the 1900 is the algorithm's restriction.
 * 
 * @author (Maytham Sabbagh, maythamsabbagh@gmail.com)
 * @version (4.0)
 */
public class MCalendar 
{
    // the frame and the main game panels, buttons and labels
    private JFrame mainFrame;
    
    // Numbers to enter year
    private JButton[] gridnum;
    private JPanel numPanel;
    
    // Month list
    private JButton[] gridmon;
    private JPanel monPanel;
    
    // Actual month layout
    private JPanel mainPanel;
    private JLabel[] labels;
    
    //button listener
    private ButtonListener blisten;
    
    // Day list above calendar
    private JLabel[] labels2;
    private JPanel dayPanel;
    
    // Current month and year
    private JLabel[] mon;
    private JPanel dispPanel;
    
    // Selecting next or previous month
    private JButton[] com;
    private JPanel comPanel;
    
    // Month and Year to print
    private int year;
    private int month;
    
    // Selected label
    private JLabel selec;
    private JPanel select;
    
    // months to select label
    private JLabel monthx;
    private JPanel months;
    
    // number buttons to select year
    private JLabel yearx;
    private JPanel years;
    
    // The partial year for the year input control
    public int partialyear;
    
    // icon for the empty date cell
    private ImageIcon empty;
    // icons for the dates
    private ImageIcon[] icons;
    // icons for the current day of the month
    private ImageIcon[] cicons;
    // icons for the days of the week
    private ImageIcon[] days;
    // icons for the numbers to select a year
    private ImageIcon[] yeaselec;
    // icons for the month numbers
    private ImageIcon[] monselec;
    //
    private ImageIcon previous;
    //
    private ImageIcon next;
    //
    private ImageIcon update;
    //
    private ImageIcon done;
    //
    private ImageIcon helps;
    
    // initial values of the year and month and day. Current Year month and day
    private int iniyear;
    private int inimonth;
    private int iniday;
    
    // creating the schedule which will allow the calendar to update at the start of the new day
    private ScheduledExecutorService sched;
    
    // the label which will show the year being inputted
    private JLabel inputyear;
    
    private KeyListen keys;
    
    private JButton help;
    
    /**
     * Constructor for the MCalendar, creates a new calendar
     */
    public MCalendar()
    {
        // calling the setup method to set up the frame, panel, buttons, labels and the layouts
        this.setup();
        
    }
    
    /**
     * The main method, this will be run at the start of the program and it will initialize the calendar
     */
    public static void main(String [ ] args)
    {
          MCalendar newCalendar = new MCalendar();
    }

    /**
     * Setting up the frame and the panels and the buttons and the labels
     */
    private void setup(){
        // making the frame
        mainFrame = new JFrame("MCalendar");
        mainFrame.setMinimumSize(new Dimension(720, 700));
        // creating the buttonlistener and keylistener
        blisten = new ButtonListener(this);
        keys = new KeyListen(this);
        
        // initialize the partialyear to zero
        partialyear = 0;
        
        // setting up all the icons
        empty = new ImageIcon(getClass().getResource("empty.png"));
        setupIcons();
        
        // setting up all the panels, buttons and labels. 
        makeMonth();
        makeYear();
        makeMain();
        makeCom();
        makeDay();
        makeDisp();
        
        // putting all the panels in the frame
        frameSetup();
        
        // initializing the year and the month with the current year and month. Initializeing the initial day
        year = Calendar.getInstance().get(Calendar.YEAR);
        month = Calendar.getInstance().get(Calendar.MONTH);
        month++;
        iniday = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        // initial year and month
        iniyear = year;
        inimonth = month;
      
        // setting the default closing operation
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // getting the frame to fit the panel and layout
        mainFrame.pack();
        // setting the frame to be visible
        mainFrame.setVisible(true);
        
        // creating the calendar with the current month and year
        editDisp(month,year);
        createCalendar(month,year);
        
        mainFrame.setFocusable(true);
        mainFrame.requestFocusInWindow();
        mainFrame.addKeyListener(keys);
        
        // outputting the welcome message
        message("start");
        
        // calling the setup for the automatic update at the end of the day
        this.waitSetup();
        
    }
    
    /**
     * 
     */
    private void waitSetup(){
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int minute = Calendar.getInstance().get(Calendar.MINUTE);
        int second = Calendar.getInstance().get(Calendar.SECOND);
        minute = minute + (hour*60);
        second = second + (minute*60);
        
        second = 86400 - second;
        
        sched = Executors.newScheduledThreadPool(1);
        final Runnable timerun = new Runnable(){
            public void run(){
                com[2].doClick();
            }
        };
        sched.scheduleAtFixedRate(timerun, second, 86400, TimeUnit.SECONDS);
    }
    
    /**
     * The method to set up the frame with all the panels
     */
    private void frameSetup(){
        // setting the layout for the frame to have panels and take the panel sizes
        mainFrame.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.VERTICAL;
        
        // adding all the panels to the frame
        
        constraints.gridx=0;
        constraints.gridy=0;       
        mainFrame.add(years,constraints);
        
        constraints.gridx=0;
        constraints.gridy=1;       
        mainFrame.add(numPanel,constraints);
        
        constraints.gridx=0;
        constraints.gridy=2;       
        mainFrame.add(months,constraints);
        
        constraints.gridx=0;
        constraints.gridy=3;       
        mainFrame.add(comPanel,constraints);
        
        constraints.gridx=0;
        constraints.gridy=4;       
        mainFrame.add(monPanel,constraints);
        
        constraints.gridx=0;
        constraints.gridy=5;       
        mainFrame.add(select,constraints);
        
        constraints.gridx=0;
        constraints.gridy=6;       
        mainFrame.add(dispPanel,constraints);
        
        constraints.gridx=0;
        constraints.gridy=7;       
        mainFrame.add(dayPanel,constraints);
        
        constraints.gridx=0;
        constraints.gridy=8;       
        mainFrame.add(mainPanel,constraints);
    }
    
    /**
     * Making the month panel, the panel with the months numbers to be selected by the user
     */
    private void makeMonth(){
        
        // making a label that says these are the months to select
        monthx = new JLabel("Months to Select");
        monthx.setFont(new Font("Serif", Font.BOLD, 18));
        monthx.setPreferredSize(new Dimension(700, 25));
        monthx.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        monthx.setHorizontalAlignment(SwingConstants.CENTER);
        monthx.setBackground(Color.lightGray);
        monthx.setOpaque(true);
        months = new JPanel(new GridLayout(1,1));
        months.add(monthx);
        
        // making the panel for the months, the buttons for the months and initializing them with the icons and adding the button listener
        monPanel = new JPanel(new GridLayout(1,12));
        gridmon = new JButton[12];
        for (int i=0;i<12;i++){
            int j = i + 1;
            gridmon[i] = new JButton("");
            //gridmon[i].setFont(new Font("Serif", Font.BOLD, 20));
            gridmon[i].setPreferredSize(new Dimension(58, 50));
            gridmon[i].setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            gridmon[i].setHorizontalAlignment(SwingConstants.LEFT);
            gridmon[i].setIcon(monselec[i]);
            gridmon[i].addActionListener(blisten);
            gridmon[i].setFocusable(false);
            monPanel.add(gridmon[i]); 
        }
    }
    
    /**
     * Making the year panel, the panel with the numbers to be selected by the user
     */
    private void makeYear(){
        // label to indicate the panel
        yearx = new JLabel("Buttons to Select a Year (1900-999999)");
        yearx.setFont(new Font("Serif", Font.BOLD, 18));
        yearx.setPreferredSize(new Dimension(625, 50));
        yearx.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        yearx.setHorizontalAlignment(SwingConstants.CENTER);
        yearx.setBackground(Color.lightGray);
        yearx.setOpaque(true);
        years = new JPanel(new GridBagLayout());
        years.add(yearx);
        
        // adding the help button
        help = new JButton("");
        help.setFont(new Font("Serif", Font.BOLD, 20));
        help.setPreferredSize(new Dimension(75, 50));
        help.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        help.setHorizontalAlignment(SwingConstants.LEFT); 
        help.setIcon(helps);
        help.addActionListener(blisten);
        help.setFocusable(false);
        years.add(help); 
        
        
        // making the panel which will contain the number to select the year from, and the buttons with the icons and buttonlistener
        numPanel = new JPanel(new GridBagLayout());
        gridnum = new JButton[11];
        for (int i=0;i<10;i++){
            gridnum[i] = new JButton("");
            //gridnum[i].setFont(new Font("Serif", Font.BOLD, 20));
            gridnum[i].setPreferredSize(new Dimension(55, 50));
            gridnum[i].setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            gridnum[i].setHorizontalAlignment(SwingConstants.LEFT);  
            gridnum[i].setIcon(yeaselec[i]);
            //gridnum[i].setForeground(Color.BLUE);
            gridnum[i].setFocusable(false);
            gridnum[i].addActionListener(blisten);
            numPanel.add(gridnum[i]); 
        }
        
        // Adding the label for the input year
        inputyear = new JLabel("");
        inputyear.setFont(new Font("Serif", Font.BOLD, 22));
        inputyear.setPreferredSize(new Dimension(75, 50));
        inputyear.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        inputyear.setHorizontalAlignment(SwingConstants.CENTER);
        inputyear.setForeground(new Color(12,125,0));
        inputyear.setBackground(Color.lightGray);
        inputyear.setOpaque(true);
        numPanel.add(inputyear); 
        
        // Adding the DONE button
        gridnum[10] = new JButton("");
        gridnum[10].setFont(new Font("Serif", Font.BOLD, 20));
        gridnum[10].setPreferredSize(new Dimension(75, 50));
        gridnum[10].setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        gridnum[10].setHorizontalAlignment(SwingConstants.LEFT); 
        gridnum[10].setIcon(done);
        gridnum[10].addActionListener(blisten);
        gridnum[10].setFocusable(false);
        numPanel.add(gridnum[10]); 
    }
    
    /**
     * Making the communication panel, this panel is used for next and previous month buttons
     */
    private void makeCom(){
        // initializing the panel and the 2 buttons
        comPanel = new JPanel(new GridLayout(1,3));
        com = new JButton[3];
        for (int i=0; i<3;i++){
            com[i] = new JButton("");
            com[i].setFont(new Font("Serif", Font.BOLD, 25));
            com[i].setPreferredSize(new Dimension(233,50));
            com[i].setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            com[i].setHorizontalAlignment(SwingConstants.LEFT); 
            com[i].setIcon(previous);
            com[i].addActionListener(blisten);
            com[i].setFocusable(false);
            comPanel.add(com[i]);
        }
        com[1].setIcon(next);
        com[2].setIcon(update);
    }
    
    /**
     * Making the days panel, the panel with the days of the week
     */
    private void makeDay(){
        // initializing the panel and the days of the week labels
        dayPanel = new JPanel(new GridLayout(1,7));
        labels2 = new JLabel[7];
        for (int i=0;i<7;i++){
            labels2[i] = new JLabel("");
            //labels2[i].setFont(new Font("Serif", Font.BOLD, 18));
            labels2[i].setPreferredSize(new Dimension(100, 50));
            labels2[i].setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            labels2[i].setHorizontalAlignment(SwingConstants.LEFT);  
            labels2[i].setIcon(days[i]);
            dayPanel.add(labels2[i]); 
        }
    }
    
    /**
     * Making the main panel, the panel which contains the actual dates
     */
    private void makeMain(){
        // initializing the panel and the labels
        mainPanel = new JPanel(new GridLayout(6,7));
        labels = new JLabel[42];
        for (int i=0;i<42;i++){
            labels[i] = new JLabel("");
            //labels[i].setFont(new Font("Serif", Font.BOLD, 30));
            //labels[i].setForeground (Color.BLUE);
            labels[i].setPreferredSize(new Dimension(100, 50));
            labels[i].setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            labels[i].setHorizontalAlignment(SwingConstants.LEFT);   
            mainPanel.add(labels[i]); 
        }
    }
    
    /**
     * Making the display panel, the panel that displayes the current month and year
     */
    private void makeDisp(){
        // initializing a label and a panel that says this is the current month and year
        selec = new JLabel("Displayed Month & Year");
        selec.setFont(new Font("Serif", Font.BOLD, 18));
        selec.setPreferredSize(new Dimension(700, 25));
        selec.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        selec.setHorizontalAlignment(SwingConstants.CENTER);
        selec.setBackground(Color.lightGray);
        selec.setOpaque(true);
        select = new JPanel(new GridLayout(1,1));
        select.add(selec);
        
        // initializing the panel and the labels that say the month and the year
        dispPanel = new JPanel(new GridLayout(1,2));
        mon = new JLabel[2];
        for (int i=0; i<2;i++){
            mon[i] = new JLabel("");
            mon[i].setFont(new Font("Serif", Font.BOLD, 30));
            mon[i].setForeground (Color.BLUE);
            mon[i].setPreferredSize(new Dimension(350, 50));
            mon[i].setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            mon[i].setHorizontalAlignment(SwingConstants.CENTER); 
            mon[i].setBackground(Color.lightGray);
            mon[i].setOpaque(true);
            dispPanel.add(mon[i]);
        }
    }
    
    /**
     * Returns the gridmon JButton[][] array, which contains the buttons for the months
     */
    public JButton[] getButtonMon(){
        return gridmon;
    }
    
    /**
     * Returns the gridnum JButton[][] array, which contains the buttons for the numbers to select the month
     */
    public JButton[] getButtonNum(){
        return gridnum;
    }
    
    /**
     * Returns the com JButton[][] array, which contains the previous, next and update buttons
     */
    public JButton[] getButtonCom(){
        return com;
    }
    
    /**
     * Returns the help JButton
     */
    public JButton getHelp(){
        return help;
    }
    
    /**
     * Output Dialogs to be sent to the user
     */
    public void message(String output){
        
        if (output.equals("Year")){
            // warns user that the year can only be greater or equal to 1900
            JOptionPane.showMessageDialog(mainFrame, "Year can only be greater or equal to 1900 and less than or equal to 999999");
        }else if(output.equals("start")){
            JOptionPane.showMessageDialog(mainFrame, new JLabel("<html><center>Welcome to the MCalendar.<br><br>Use the following keyboard buttons<br>← - Previous<br>→ - Next<br>↑ - Update<br>↓ - Help<br># Keys - Year Entry<br>Enter Key - Accept Entry (DONE)<br>J - January<br>F - February<br>M - March<br>A - April<br>Y - May<br>U - June<br>L - July<br>G - August<br>S - September<br>O - October<br>N - November<br>D - December<br><br>The current month is displayed.<center></html>"));
        }else if(output.equals("help")){
            JOptionPane.showMessageDialog(mainFrame, new JLabel("<html><center>Use the following keyboard buttons<br>← - Previous<br>→ - Next<br>↑ - Update<br>↓ - Help<br># Keys - Year Entry<br>Enter Key - Accept Entry (DONE)<br>J - January<br>F - February<br>M - March<br>A - April<br>Y - May<br>U - June<br>L - July<br>G - August<br>S - September<br>O - October<br>N - November<br>D - December<center></html>"));
        }
       
    }
    
    /**
     * Checking if the year is leap
     * 
     * Returns true if leap, false if not leap
     */
    public boolean isLeap (int year){
        //Variables to be used
        int i,
            u,
            y;
        boolean p;
        
        //Finding the remainders when the year is divided by 4,100 & 400
        i=year%4;
        u=year%100;
        y=year%400;
        if (y==0){
            //If the year divides perfectly into 400, the year is leap
            p=true;
            return p;
        }else if(u==0){
            //If the year divides perfectly into 100, the year is not leap
            p=false;
            return p;
        }else if(i==0){
            //If the year divides perfectly into 4, the year is lea p
            p=true;
            return p;
        }else{
            //Other Years Are Not Leap
            p=false;
            return p;
        }
    }
    
    /**
     * Checks how many days in the month
     * 
     * Returns the number of days in the month
     */
    public int daysInMonth (int month, int year){
        //Variables to be used
        int u;
        boolean P;
        if (month==2){
            //Since it if February, The year is checked for leap or not to determine if the month has 28 or 29 days
            P=isLeap(year);
            if(P){
                //the year is leap so the month has 29 days
                u=29;
                return u;
            }else{
                //the year is not leap so the month has 28 days. "Common Sense"
                u=28;
                return u;
            }
        }else if(month==9 || month==4 || month==6 || month==11){
            // April, June, September, and November all have 30 days
            u=30;
            return u;
        }else if(month==1 || month==3 || month==5 || month==7 || month==8 || month==10 || month==12){
            // January, March, May, July, August, October, and December(Best Month In The Year) all have 31 days
            u=31;
            return u;
        }
        u=0;
        return u;
    }
    
    /**
     * Finds the number of days since the beginning of the year
     * 
     * Returns the number of days since the beginning of the year
     */
    public int daysSinceJan1 (int day, int month, int year){
        //variables to be used
        int i,
            sum=0,
            d,
            v;
        //calculating the days since the start of the month excluding the day itself
        d=day-1;
        sum=sum+d;
        for(i=1;i<month;i++){
            //finding the days in the previous months using the function above for every month starting with January stopping at the month itself not including it
            v=daysInMonth(i,year);
            sum+=v;
        }
        
        return sum;
    }
    
    
    /**
     * Finds the number of days in previous years, starting with 1900
     */
    public int daysInPreviousYears (int year){
        // variables to be used
        int i,
            sum=0;
        boolean v;
            
        for(i=1900;i<year;i++){
            //starting with 1900, it finds how many days in every year up to the year given not including it by finding if the years are leap or not and adding
            //366 for leap years and 365 for non leap years
            v=isLeap(i);
            if (v){
                sum+=366;
            }else{
                sum+=365;
            }
        }
        return sum;
    }    

    /**
     * Finds the number of days since Jan 1st of 1900
     */
    public int daysSinceStartOfTime (int day, int month, int year){
        //variables to be used
        int i,
            u,
            v;
        //finds the number of days since the start of the year, and finds the number of days in the previous years and adds them together
        i=daysInPreviousYears(year);
        v=daysSinceJan1(day,month,year);
        u=i+v;
        
        return u;
    }    
    
    /**
     * Finds which day of the week the day specified is
     * 
     * Returns the number corresponding to the day of the week
     */
    public int dayOfWeek (int day, int month, int year){
        int i,
            u,
            v;
        i=daysSinceStartOfTime(day,month,year);
        u=i;
        v=u%7;
        if (v==0){
            return 1;
        }else if (v==1){
            return 2;
        }else if (v==2){
            return 3;
        }else if (v==3){
            return 4;
        }else if (v==4){
            return 5;
        }else if (v==5){
            return 6;
        }else{
            return 7;
        }
        
    }    
    
    /**
     * Creates the actual calendar, updates the dates cells with the dates of the month
     */
    public void createCalendar (int month, int year) {
        for (int i=0;i<42;i++){
            labels[i].setText(" ");
            labels[i].setIcon(empty);
        }
        
        int startDay, days, count, i;
    
        startDay = dayOfWeek (1, month, year);
        days = daysInMonth (month, year);
    
    
        // work out how many blank entries should precede the first number
        count = startDay % 7;
     
        // output all of the numbers
        for (i = 1; i <= days; i++) {
            if (i == iniday && month == inimonth && year == iniyear){
                labels[i+count-1].setIcon(cicons[i-1]);
            }else{
                labels[i+count-1].setIcon(icons[i-1]);
            }
        }

    
    }   
    
    /**
     * Changes the display panel and displays the current month and year
     */
    public void editDisp(int month, int year){
        if (month == 1){
            mon[0].setText("January");
        }else if(month == 2){
            mon[0].setText("February");
        }else if(month == 3){
            mon[0].setText("March");
        }else if(month == 4){
            mon[0].setText("April");
        }else if(month == 5){
            mon[0].setText("May");
        }else if(month == 6){
            mon[0].setText("June");
        }else if(month == 7){
            mon[0].setText("July");
        }else if(month == 8){
            mon[0].setText("August");
        }else if(month == 9){
            mon[0].setText("September");
        }else if(month == 10){
            mon[0].setText("October");
        }else if(month == 11){
            mon[0].setText("November");
        }else if(month == 12){
            mon[0].setText("December");
        }
        mon[1].setText(""+year);
    }
    
    /**
     * Increments to the next month, if the month is 12 then the first month of the next year
     */
    public void updateNext(){
        if (month == 12){
            // if year is 999999, display a warning message to user
            if (year == 999999){
                message("Year");
                return;
            }
            month = 1;
            year++;
        }else{
            month++;
        }
        editDisp(month,year);
        createCalendar(month,year);
    }
    
    /**
     * decrements to the previous month, if the month is 1 then the 12 month of the previous year
     */
    public void updatePrevious(){
        if (month == 1){
            // if year is 1900, display a warning message to user
            if (year == 1900){
                message("Year");
                return;
            }
            month = 12;
            year--;
        }else{
            month--;
        }
        editDisp(month,year);
        createCalendar(month,year);
    }
    
    /**
     * Updates the calendar with the month selected by the user, keeps the year unchanged
     */
    public void updateMonth(int newmonth){
        month = newmonth;
        editDisp(month,year);
        createCalendar(month,year);
    }

    /**
     * Updates the calendar with the year selected by the user, keeps the month unchanged
     */
    public void updateYear(int newyear){
        // if year is less than 1900 or greater than 999999, display a warning message to user
        if (newyear < 1900 || newyear > 999999){
            message("Year");
            return;
        }
        year = newyear;
        editDisp(month,year);
        createCalendar(month,year);
    }
    
    /**
     * Sets up all the icons to be used
     */
    private void setupIcons(){
        //BufferedImage img = ImageIO.read(getClass().getResource("1.png"));
        icons = new ImageIcon[31];
        icons[0] = new ImageIcon(getClass().getResource("1.png")); 
        icons[1] = new ImageIcon(getClass().getResource("2.png")); 
        icons[2] = new ImageIcon(getClass().getResource("3.png")); 
        icons[3] = new ImageIcon(getClass().getResource("4.png")); 
        icons[4] = new ImageIcon(getClass().getResource("5.png")); 
        icons[5] = new ImageIcon(getClass().getResource("6.png")); 
        icons[6] = new ImageIcon(getClass().getResource("7.png")); 
        icons[7] = new ImageIcon(getClass().getResource("8.png")); 
        icons[8] = new ImageIcon(getClass().getResource("9.png")); 
        icons[9] = new ImageIcon(getClass().getResource("10.png")); 
        icons[10] = new ImageIcon(getClass().getResource("11.png")); 
        icons[11] = new ImageIcon(getClass().getResource("12.png")); 
        icons[12] = new ImageIcon(getClass().getResource("13.png")); 
        icons[13] = new ImageIcon(getClass().getResource("14.png")); 
        icons[14] = new ImageIcon(getClass().getResource("15.png")); 
        icons[15] = new ImageIcon(getClass().getResource("16.png")); 
        icons[16] = new ImageIcon(getClass().getResource("17.png")); 
        icons[17] = new ImageIcon(getClass().getResource("18.png")); 
        icons[18] = new ImageIcon(getClass().getResource("19.png")); 
        icons[19] = new ImageIcon(getClass().getResource("20.png")); 
        icons[20] = new ImageIcon(getClass().getResource("21.png"));
        icons[21] = new ImageIcon(getClass().getResource("22.png")); 
        icons[22] = new ImageIcon(getClass().getResource("23.png")); 
        icons[23] = new ImageIcon(getClass().getResource("24.png")); 
        icons[24] = new ImageIcon(getClass().getResource("25.png")); 
        icons[25] = new ImageIcon(getClass().getResource("26.png")); 
        icons[26] = new ImageIcon(getClass().getResource("27.png")); 
        icons[27] = new ImageIcon(getClass().getResource("28.png")); 
        icons[28] = new ImageIcon(getClass().getResource("29.png")); 
        icons[29] = new ImageIcon(getClass().getResource("30.png")); 
        icons[30] = new ImageIcon(getClass().getResource("31.png")); 
        
        yeaselec = new ImageIcon[10];
        yeaselec[0] = new ImageIcon(getClass().getResource("100.png"));
        yeaselec[1] = new ImageIcon(getClass().getResource("101.png"));
        yeaselec[2] = new ImageIcon(getClass().getResource("102.png"));
        yeaselec[3] = new ImageIcon(getClass().getResource("103.png"));
        yeaselec[4] = new ImageIcon(getClass().getResource("104.png"));
        yeaselec[5] = new ImageIcon(getClass().getResource("105.png"));
        yeaselec[6] = new ImageIcon(getClass().getResource("106.png"));
        yeaselec[7] = new ImageIcon(getClass().getResource("107.png"));
        yeaselec[8] = new ImageIcon(getClass().getResource("108.png"));
        yeaselec[9] = new ImageIcon(getClass().getResource("109.png"));
        
        days = new ImageIcon[7];
        days[0] = new ImageIcon(getClass().getResource("sun.png"));
        days[1] = new ImageIcon(getClass().getResource("mon.png"));
        days[2] = new ImageIcon(getClass().getResource("tues.png"));
        days[3] = new ImageIcon(getClass().getResource("wed.png"));
        days[4] = new ImageIcon(getClass().getResource("thur.png"));
        days[5] = new ImageIcon(getClass().getResource("fri.png"));
        days[6] = new ImageIcon(getClass().getResource("sat.png"));
        
        monselec = new ImageIcon[12];
        monselec[0] = new ImageIcon(getClass().getResource("201.png"));
        monselec[1] = new ImageIcon(getClass().getResource("202.png"));
        monselec[2] = new ImageIcon(getClass().getResource("203.png"));
        monselec[3] = new ImageIcon(getClass().getResource("204.png"));
        monselec[4] = new ImageIcon(getClass().getResource("205.png"));
        monselec[5] = new ImageIcon(getClass().getResource("206.png"));
        monselec[6] = new ImageIcon(getClass().getResource("207.png"));
        monselec[7] = new ImageIcon(getClass().getResource("208.png"));
        monselec[8] = new ImageIcon(getClass().getResource("209.png"));
        monselec[9] = new ImageIcon(getClass().getResource("210.png"));
        monselec[10] = new ImageIcon(getClass().getResource("211.png"));
        monselec[11] = new ImageIcon(getClass().getResource("212.png"));
        
        
        cicons = new ImageIcon[31];
        cicons[0] = new ImageIcon(getClass().getResource("301.png")); 
        cicons[1] = new ImageIcon(getClass().getResource("302.png")); 
        cicons[2] = new ImageIcon(getClass().getResource("303.png")); 
        cicons[3] = new ImageIcon(getClass().getResource("304.png")); 
        cicons[4] = new ImageIcon(getClass().getResource("305.png")); 
        cicons[5] = new ImageIcon(getClass().getResource("306.png")); 
        cicons[6] = new ImageIcon(getClass().getResource("307.png")); 
        cicons[7] = new ImageIcon(getClass().getResource("308.png")); 
        cicons[8] = new ImageIcon(getClass().getResource("309.png")); 
        cicons[9] = new ImageIcon(getClass().getResource("310.png")); 
        cicons[10] = new ImageIcon(getClass().getResource("311.png")); 
        cicons[11] = new ImageIcon(getClass().getResource("312.png")); 
        cicons[12] = new ImageIcon(getClass().getResource("313.png")); 
        cicons[13] = new ImageIcon(getClass().getResource("314.png")); 
        cicons[14] = new ImageIcon(getClass().getResource("315.png")); 
        cicons[15] = new ImageIcon(getClass().getResource("316.png")); 
        cicons[16] = new ImageIcon(getClass().getResource("317.png")); 
        cicons[17] = new ImageIcon(getClass().getResource("318.png")); 
        cicons[18] = new ImageIcon(getClass().getResource("319.png")); 
        cicons[19] = new ImageIcon(getClass().getResource("320.png")); 
        cicons[20] = new ImageIcon(getClass().getResource("321.png"));
        cicons[21] = new ImageIcon(getClass().getResource("322.png")); 
        cicons[22] = new ImageIcon(getClass().getResource("323.png")); 
        cicons[23] = new ImageIcon(getClass().getResource("324.png")); 
        cicons[24] = new ImageIcon(getClass().getResource("325.png")); 
        cicons[25] = new ImageIcon(getClass().getResource("326.png")); 
        cicons[26] = new ImageIcon(getClass().getResource("327.png")); 
        cicons[27] = new ImageIcon(getClass().getResource("328.png")); 
        cicons[28] = new ImageIcon(getClass().getResource("329.png")); 
        cicons[29] = new ImageIcon(getClass().getResource("330.png")); 
        cicons[30] = new ImageIcon(getClass().getResource("331.png")); 
        
        previous = new ImageIcon(getClass().getResource("previous.png")); 
        next = new ImageIcon(getClass().getResource("next.png")); 
        update = new ImageIcon(getClass().getResource("update.png")); 
        done = new ImageIcon(getClass().getResource("done.png")); 
        helps = new ImageIcon(getClass().getResource("helps.png")); 
    }
    
    /**
     * reUpdate the calendar to the current month and year based on the computer settings
     */
    public void reUpdate(){
        // initializing the year and the month with the current year and month. Initializeing the initial day
        year = Calendar.getInstance().get(Calendar.YEAR);
        month = Calendar.getInstance().get(Calendar.MONTH);
        month++;
        iniday = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        // initial year and month
        iniyear = year;
        inimonth = month;
        
        editDisp(inimonth,iniyear);
        createCalendar(inimonth,iniyear); 
        
        
    }
    
    /**
     * Update the input as it is inputted
     */
    public void inputUpdate(){
        if(partialyear == 0){
            this.inputyear.setText("");
        }else{
            this.inputyear.setText(""+partialyear);
        }
    }
    
    /**
     * Year numbers entered
     */
    public void KeyBoardYear(int number){
        gridnum[number].doClick();
    }
    
    /**
     * Month numbers entered
     */
    public void KeyBoardMonth(int number){
        gridmon[number].doClick();
    }
   

}