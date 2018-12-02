
/**
 * Customer is a class that simulates customers coming to the coffee shop
 * and keeps track of their arrival time, departure time, waiting duration,
 * and serving duration
 *
 * Trang Le
 * Professor Xia
 * CS 150-02: Data Structures & Algorithms
 * v1.0 Sep 25, 2018
 */


public class Customer
{
    public int customerName; //The number of the customer
    public int arrivalTime; //The arrival time of the customer
    public int departureTime;//The departure time of the customer
    public int waitDuration; //The duration the customer has to wait before getting served
    
    public static int serveDuration; //The avg. time for a cashier to serve a customer
    /**
     * Constructor for objects of class Customer
     */
    public Customer(int customerName_, int arrivalTime_) {
        this.customerName = customerName_;
        this.arrivalTime = arrivalTime_;
    }
    
    /**
     * Getters for parameters of the class Customer
     */

    public int getWaitDuration() {
        this.waitDuration = (this.departureTime - this.arrivalTime) - serveDuration;
        return this.waitDuration;
    }

    public int getArrivalTime() {
        return this.arrivalTime;
    }

    public int getCustomerName() {
        return this.customerName;
    }

}
