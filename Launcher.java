
/**
 * Launcher is a class which creates an instance of the CoffeeShop class and performs
 * calculations on the statistics of the coffee shop
 *
 * Trang Le
 * Professor Xia
 * CS 150-02: Data Structures & Algorithms
 * v1.0 Sep 25, 2018
 */

import java.util.Scanner;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Launcher
{
    //Initiate an instance of the CoffeeShop class
    private static CoffeeShop cs;

    //Cashiers calculation variables
    public int availableCashiers; //A variable to keep track of the number of cashiers available at the counter

    //Customer calculation variables
    private int overflowCustomers;//Number of overflow customers
    private int totalCustomers;//The total number of customers arriving at the coffee shop
    private float overflowRate;

    //Wait time calculation variables
    private float totalWaitDuration;//A variable to keep track of the total wait duration
    private float avgWaitDuration;//A variable to keep track of average wait duration
    private float maxWaitDuration;//A variable to keep track of the max wait duration
    private float waitDuration; //A variable to keep track of wait duration of the customer in the event currently being processed

    //Profit calculation variables
    private float totalProfit;
    private float totalCost; 
    private float netProfit; 
    /**
     * Constructor for objects of class Launcher
     */
    public Launcher(String[] args)
    {
        cs = new CoffeeShop();
        cs.numberOfCashiers = Integer.valueOf(args[0]); //Initialize the number of cashiers to the input 
        availableCashiers = cs.numberOfCashiers; //Original number of available cashiers
    }

    /**
     * @param   args   the number of cashiers
     */
    public static void main(String[] args) { 
        Launcher launcher = new Launcher(args);
        launcher.run();
    } 

    public void run() {
        //Read an input file
        String fileName = "input.txt";
        File inputFile = new File(fileName);
        readInputFile(inputFile);

        //Remove and process each event while the PriorityQueue is not empty
        while (!cs.events.isEmpty()) {

            Event e = cs.removeEvent(); 

            if (e.getEventType().equals("arrival")) {
                processArrival(e);
            }
            if (e.getEventType().equals("departure")) {
                processDeparture(e);
            }

            //Remove and serve a customer when there is at least one available cashier and the line is not empty
            if (availableCashiers > 0 && (!cs.customers.isEmpty())) {
                Customer firstCustomerInLine = serveCustomer();
                createDepartureEvent(e, firstCustomerInLine);//Create a departure event for the recently served customer
                calculateWaitData(firstCustomerInLine); //Calculate wait data after the customer
            } 
        }

        //Calculate profit and overflow statistics
        {
            calculateTotalProfit(); //Recalculate the total profit every time a customer leaves
            calculateTotalCost();
            calculateNetProfit();
            calculateOverflowRate();
        }
        //Write the calculated data to an output file
        createOutputFile();

        /*
        System.out.println("Total profit: " + totalProfit);
        System.out.println("Total cost: " + totalCost);
        System.out.println("Net profit: " + netProfit);
        System.out.println("Average wait duration: " + (int)avgWaitDuration);
        System.out.println("Max wait duration: " + (int)maxWaitDuration);
        System.out.println("Overflow rate: " + overflowRate + "%");
         */

    }

    /**
     * A method that converts the strings of time stamps to seconds from 12AM
     * @param     arrival   a string of time stamp
     * @return    the time duration (in seconds) from 12AM to the time the string indicates
     */
    public static int convertTimeToSecond(String arrival) {
        arrival = arrival.substring(0, arrival.indexOf(" ")); //remove the AM/PM part
        String[] timeUnits = arrival.split(":"); //split the time stamp into string elements using ":"
        int hours = Integer.parseInt(timeUnits[0]); //pass the first element into hours
        int minutes = Integer.parseInt(timeUnits[1]); //pass the second element into minutes
        int seconds = Integer.parseInt(timeUnits[2]); //pass the third element into seconds
        int arr = 3600*hours + 60*minutes + seconds; //convert to seconds   
        return arr;
    }

    //Event processing methods
    /**
     * A method that processes an arrival event
     * @param     e     an arrival event
     */
    public void processArrival(Event e) { 
        Customer c = new Customer(e.getCustomerNum(), e.getEventTime()); //Create a new customer for each arrival event    
        totalCustomers++; //Increase the total number of customer

        //System.out.print("Customer " + c.getCustomerName() + " arrives at " +  c.getArrivalTime());

        //Only add the created customer to the line if the length of the line is less than 8 * number of cashiers
        if (cs.customers.size() < 8 * cs.numberOfCashiers) { 
            cs.addCustomer(c);
        } else {
            overflowCustomers++; 
            //System.out.println(" but leaves");
        }
    }

    /**
     * A method that processes a departure event
     * @param     e     a departure event
     */
    public void processDeparture(Event e) {
        availableCashiers++; //Increase the number of available cashiers every time a customer leaves

        //System.out.println("Customer " + e.getCustomerNum() + " leaves at " + e.getEventTime());
    }

    public Customer serveCustomer() { //Method to serve and return the first customer in line (for later process)
        Customer firstCustomerInLine = cs.removeCustomer();
        availableCashiers--; //Decrease the number of cashier when a customer is being served
        return firstCustomerInLine;
    }

    /**
     * A method that creates a departure event for the customer recently served
     * @param    e      the previous event being processed
     * @param    firstCustomerInLine       the element at the front of the Linked List    
     */
    public void createDepartureEvent(Event e, Customer firstCustomerInLine) { 
        int depTime = e.getEventTime() + Customer.serveDuration; //The customer leaves t seconds (t = serveDuration) after the time of the event 
        firstCustomerInLine.departureTime = depTime; 
        Event departureEvent = new Event(firstCustomerInLine.getCustomerName(), depTime, "departure");
        cs.addEvent(departureEvent); //Add the created event to the PriorityQueue

        //System.out.println(" and waits for " + firstCustomerInLine.getWaitDuration());
    }

    /**
     * A method that reads an input file and return a string
     * @param   inputFile   the file for the program to read from
     */
    public static String readInputFile(File inputFile) {
        String result = "";  //The result string is returned and used for unit testing
        try {
            //Initialize a scanner to read the file
            Scanner sc = new Scanner(new FileReader(inputFile));
            int k = 0; //Counter to count the line of the input file
            int customerName = 0; //Counter to number the customer's order of arrival

            while (sc.hasNextLine()) {
                String str = sc.nextLine();
                //System.out.println(str);

                //First line of the file is the profit earned per customer
                if(k==0) {
                    cs.profit = Float.valueOf(str); 
                    result += "First line: " + cs.profit + System.lineSeparator();
                }

                //Second line of the file is the cost of hiring one cashier per day
                if (k==1) {
                    cs.cashierCost = Float.valueOf(str); 
                    result += "Second line: " + cs.cashierCost + System.lineSeparator();
                }

                //Third line of the file is the avg. time to serve a customer
                if (k==2) {
                    Customer.serveDuration = Integer.valueOf(str); 
                    result += "Third line: " + Customer.serveDuration + System.lineSeparator();
                }

                //The fourth to last lines are time stamps for arrival events
                if(k > 2) { 
                    int arr;
                    arr = convertTimeToSecond(str); //Convert the time format to seconds from 12AM
                    if (arr >= convertTimeToSecond("06:00:00 AM") && arr < convertTimeToSecond("22:00:00 PM")) { //Only serve the customers between 6AM and 10PM
                        Event arrivalEvent = new Event(++customerName, arr, "arrival"); //Create an arrival event with each line
                        cs.addEvent(arrivalEvent); //Populate the PriorityQueue with arrival events
                        result += "Event " + arrivalEvent.getCustomerNum() + " happens at: " + arrivalEvent.getEventTime() + System.lineSeparator();
                    }
                }                
                k++;

            }

        }
        catch(FileNotFoundException e) {
        }
        //System.out.print(result);
        return result;
    }

    /**
     * A method that writes an output file from the processed data
     */
    public void createOutputFile() {
        try{
            //Create an output file
            String fileName = "output_" + cs.numberOfCashiers + "_cashier(s).txt";
            File output = new File(fileName);
            //Reformat floats to print with 2 digits after decimal points
            DecimalFormat df = new DecimalFormat();
            df.setMinimumFractionDigits(2);
            df.setMaximumFractionDigits(2);
            //Use PrintWriter to write the output file
            PrintWriter pw = new PrintWriter(output); //Initiate a PrintWriter object
            pw.println("Total profit: " + df.format(totalProfit));
            pw.println("Total cost: " + df.format(totalCost));
            pw.println("Net profit: " + df.format(netProfit));
            pw.println("Average wait duration: " + (int)avgWaitDuration + " seconds");
            pw.println("Max wait duration: " + (int)maxWaitDuration + " seconds");
            pw.println("Overflow rate: " + df.format(overflowRate) + "%");
            pw.close();

        }
        catch(FileNotFoundException e) {
        }
    }

    //Data calculation methods
    /**
     * @return    the total revenue in a day of the coffee shop
     */
    public float calculateTotalProfit() {
        totalProfit = cs.profit * (totalCustomers-overflowCustomers);
        return totalProfit;
    }

    /**
     * @return     the total cost in a day of the coffee shop
     */
    public float calculateTotalCost() {
        totalCost = cs.cashierCost * cs.numberOfCashiers;
        return totalCost;
    }

    /**
     * @return     the net profit in a day of the coffee shop
     */
    public float calculateNetProfit() {
        netProfit= totalProfit - totalCost;
        return netProfit;
    }

    /**
     * @return     the average wait duration of the customers
     */
    public float calculateAverageWaitDuration() {
        totalWaitDuration+= waitDuration;
        avgWaitDuration = totalWaitDuration/(totalCustomers-overflowCustomers);
        return avgWaitDuration;
    }

    /**
     * @return     the maximum wait duration of the customers
     */
    public float findMaxWaitDuration() {
        if (waitDuration > maxWaitDuration) maxWaitDuration = waitDuration;
        return maxWaitDuration;
    }

    /**
     * @return     the maximum wait duration of the customers
     */
    public float calculateOverflowRate() {
        overflowRate = (float)overflowCustomers/(float)totalCustomers * 100;
        return overflowRate;
    }
    
    /**
     * A method to calculate wait duration of the customers
     */
    public void calculateWaitData(Customer c) {
        //Assign the wait duration of the currently processed customer to waitDuration variable
        waitDuration = c.getWaitDuration();
        //Recalculate the average wait time 
        calculateAverageWaitDuration();
        //Reassign maxWaitDuration everytime a higher value is found
        findMaxWaitDuration();
    }
}