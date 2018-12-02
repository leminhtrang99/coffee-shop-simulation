
/**
 * Event is a class that simulates an event at the coffee shop
 * and keeps track of the customer related to the event, the time of the event,
 * and the type (either arrival or departure) of the event
 *
 * Trang Le
 * Professor Xia
 * CS 150-02: Data Structures & Algorithms
 * v1.0 Sep 25, 2018
 */
public class Event implements Comparable<Event>
{
    protected int customerNum ; //The customer in the event
    protected int eventTime; //The time the event happens
    protected String eventType; //The type ofthe event
    
    /**
     * Constructor for objects of the class Event
     */
    public  Event(int customerNum_, int eventTime_, String eventType_) {
        customerNum = customerNum_;
        eventTime = eventTime_;
        eventType = eventType_; 
    }

    /** 
     * Getters for parameters of the class Event
     */
    //Getters for the arguments in the constructor
    public int getCustomerNum() {
        int num = this.customerNum;
        return num;
    }

    public int getEventTime() {
        int time = this.eventTime;
        return time;
    }

    public String getEventType() {
        String type = this.eventType;
        return type;
    }

    /**
     * @param   e   is an instance of the class Event being compared
     * @return  -1  if e happens before the other event
     * @return  0   if e happnes at the same time as the other event
     * @return  1   if e happnes later the other event
     */
    //Implement the compareTo() method for the Event class
    public int compareTo(Event e) {
        if(this.getEventTime() < e.getEventTime()) return -1;
        if(this.getEventTime() == e.getEventTime()) return 0;
        else return 1;
    }

}
