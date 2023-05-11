import java.util.concurrent.Semaphore;

/**
 * The primary class for your NightClub, which will have a name, capacity and
 * a manager. People can arrive and leave your club asynchronously once it is
 * opened. Once the manager tries to close the club, the entrance should be
 * closed, then close the exit once everyone has left.
 */
public class NightClub {
    // TODO You need a variable for counting people named: peopleCount
    // you need a variable here for the capacity of the club: capacity
    public String name = "default";
    public boolean isOpen = false;
    private Entrance entrance;
    private Exit exit;
    private Manager manager;
    private int capacity;
    private int peopleCount = 0;
    private Thread manThread;
    private Thread enterThread;
    private Thread exitThread;
    public boolean exitInterrupted = false;
    public boolean enterInterrupted = false;

    // TODO Appropriate Thread attributes here
    
    NightClub (String name, int capacity, Manager manager){
        this.name = name;
        this.capacity = capacity;
        this.manager = manager;
        manager.acceptJob(this);
    }
    public void start(){
        System.out.println("We are starting club:" + name);
        // TODO Create a thread for the manager
        manThread  = new Thread(manager);
        this.entrance = new Entrance(this);
        this.exit = new Exit(this);
        // TODO Start a thread for the manager
        manThread.start();
    }
    public void end() throws InterruptedException{

        manager.leaveJob();
        // kill the thread that is running the manager
        manThread.join();
        System.out.println("The simulation has ended.");
    }
    public void open() throws InterruptedException{
        if (!isOpen){
            isOpen = true;
            enterThread = new Thread(entrance);
            exitThread = new Thread(exit);
            // TODO Create threads so that users can access or leave the club.
            // Should you also start these threads here?

        }
        else{
            System.out.println("The club is already open!");

        }
        enterThread.start();
        exitThread.start();


    }
    public void close() throws InterruptedException{
        if (isOpen){
            isOpen = false;
            System.out.println("Closing night club....");
            System.out.println("Closing the entrance.");
            // TODO kill the threads that are facilitating accessing and
			// leaving feature.
            exitInterrupted = true;
            enterInterrupted = true;
            enterThread.interrupt();
            exitThread.interrupt();
            while(getPeopleCount()>0){
                leave();
                System.out.println("People are leaving:" + getPeopleCount());
            }
            System.out.println("Everyone has left the club.");
        }
        else{
            System.out.println("The club is already closed!");
        }
        end();
    }
    public int getPeopleCount() {
        return peopleCount;
    }
    public synchronized void enter(int numPeople) throws InterruptedException{
        // TODO Must wait for space in a while loop for space in the club.
        while (peopleCount == capacity){
            wait();
        }
        peopleCount += numPeople;
        System.out.println("Person entered. People Count:" + peopleCount);
        notifyAll();
    }
    public int leave() {
        // TODO Must have enough people to allow this feature. Is there a 
		// condition to check?
        if (peopleCount <= capacity && peopleCount > 0) {
            peopleCount--;
        } else {
            System.out.println("No one in club :/");
        }
        System.out.println("Number of people left: " + peopleCount);
        return peopleCount;
    }
    public int getCapacity() {
        return capacity;
    }

}
