import java.util.Random;

/**
 * The class which will act as the entrance for your nightclub, modelling
 * people arriving at and entering your nightclub. 
 */
public class Entrance implements Runnable{
    private NightClub nightClub;
    private int sleepScaler = 10;
    private String name = "Entrance";
    Entrance (NightClub nightClub){
        this.nightClub = nightClub;
    }
    Entrance (NightClub nightClub, String name){
        this.nightClub = nightClub;
        this.name = name;
    }
    public void run() {
        // TODO Enter code for how the entrance to be used.
        Random random =new Random();
        int people;
        while (!nightClub.enterInterrupted) {
          try {
              people = random.nextInt(nightClub.getCapacity() - nightClub.getPeopleCount());
              Thread.sleep(sleepScaler);
              System.out.println("Person entering...");
              nightClub.enter(people);
              /*while (nightClub.getPeopleCount() < nightClub.getCapacity()){

              }*/
          } catch (InterruptedException e) {
              System.out.println("Thread Interrupted.");
          }
        }
    }

}
