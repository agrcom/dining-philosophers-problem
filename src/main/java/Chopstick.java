import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class Chopstick {

  private final int id;
  private Lock lock;

  public Chopstick(int id) {
    this.id = id;
    this.lock = new ReentrantLock();
  }

  public boolean pickUp(Philosopher philosopher, State state) throws InterruptedException {
    if (lock.tryLock(10, TimeUnit.MILLISECONDS)) {
      log.info("{} picked up {} {}", philosopher, state.toString(), this);
      return true;
    }
    return false;
  }

  public void putDown(Philosopher philosopher, State state) {
    lock.unlock();
    log.info("{} put down {} {}", philosopher, state, this);
  }

  @Override
  public String toString() {
    return "Chopstick " + "id=" + id;
  }
}
