
/**
 * CoffeeShop is a class the simulates a coffee shop with a line of customers (LinkedList),
 * a queue of events (PriorityQueue) and keeps track of data on the cashiers
 *
 * Trang Le
 * Professor Xia
 * CS 150-02: Data Structures & Algorithms
 * v1.0 Sep 25, 2018
 */

import java.util.LinkedList;
import java.util.PriorityQueue;

public class CoffeeShop
{

    //Cashier variables
    public int numberOfCashiers; //Number of hired cashiers
    public static float cashierCost; //Cost of hiring per cashier

    //Event variable
    public static PriorityQueue<Event> events;

    //Customer variables
    public static LinkedList<Customer> customers; //Line of customers
    public static float profit; //Estimated profit of serving each customer

    /**
     * Constructor for objects of class CoffeeShop
     */
    public CoffeeShop()
    {
        events = new PriorityQueue<Event>(); //Set up a PriorityQueue of events
        customers = new LinkedList<Customer>(); //Set up a LinkedList of customers
    }

    /**
     * Add the first customer to the end of the customer LinkedList
     */
    //Method to enqueue a new customer to end of the LinkedList of customers
    public void addCustomer(Customer c) {
        customers.addLast(c);
    }

    /**
     * @return    the first customer from the beginning of the customer LinkedList
     */
    //Method to dequeue the first customer from the LinkedList of customers
    public Customer removeCustomer() {
        return customers.remove();
    }

    /**
     * Add a new event into the PriorityQueue based on order of time
     */
    //Method to add a new event into the PriorityQueue of events
    public static void addEvent(Event e) {
        events.add(e);
    }

    /**
     * @return    the earliest event from the PriorityQueue
     */
    //Method to poll the earliest event from the PriorityQueue of events
    public static Event removeEvent() {
        return events.poll();
    }

}
