import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;

@Slf4j
public class App {
  public static void main(String[] args) throws InterruptedException {

    var executorService = Executors.newFixedThreadPool(Constants.NUMBER_OF_PHILOSOPHERS);
    var philosophers = new Philosopher[Constants.NUMBER_OF_PHILOSOPHERS];
    var chopsticks = new Chopstick[Constants.NUMBER_OF_CHOPSTICS];

    try {
      for (var i = 0; i < Constants.NUMBER_OF_CHOPSTICS; ++i) {
        chopsticks[i] = new Chopstick(i);
      }
      for (var i = 0; i < Constants.NUMBER_OF_PHILOSOPHERS; ++i) {
        philosophers[i] =
            new Philosopher(i, chopsticks[i], chopsticks[(i + 1) % Constants.NUMBER_OF_CHOPSTICS]);
        executorService.execute(philosophers[i]);
      }

      Thread.sleep(Constants.SIMULATION_RUNNING_TIME);

      for (Philosopher philosopher : philosophers) {
        philosopher.setFull(true);
      }
    } finally {
      executorService.shutdown();
      log.info("SHUTDOWN!");

      while (!executorService.isTerminated()) Thread.sleep(1000);

      for (Philosopher philosopher : philosophers)
        log.info(
            "Philosopher {} eat {} times", philosopher.toString(), philosopher.getEatingCounter());
    }
  }
}
