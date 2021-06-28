import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class Philosopher implements Runnable {
  private int id;
  @Setter @Getter private volatile boolean full;
  private Chopstick leftChopstick;
  private Chopstick rightChopstick;
  private Random random;
  @Getter private int eatingCounter;

  public Philosopher(int id, Chopstick leftChopstick, Chopstick rightChopstick) {
    this.id = id;
    this.leftChopstick = leftChopstick;
    this.rightChopstick = rightChopstick;
    this.random = new Random();
  }

  @Override
  public void run() {

    try {
      while (!full) {
        think();
        if (leftChopstick.pickUp(this, State.LEFT)) {
          if (rightChopstick.pickUp(this, State.RIGHT)) {
            eat();
            rightChopstick.putDown(this, State.RIGHT);
          }
          leftChopstick.putDown(this, State.LEFT);
        }
      }
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }

  private void think() throws InterruptedException {
    log.info("{} is thinking", this);
    Thread.sleep(random.nextInt(1000));
  }

  private void eat() throws InterruptedException {
    log.info("{} is eating", this);
    eatingCounter++;
    Thread.sleep(random.nextInt(1000));
  }

  @Override
  public String toString() {
    return "Philosopher " + "id=" + id;
  }
}
