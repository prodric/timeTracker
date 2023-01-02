package core;

public class Id {
  private static Id uniqueInstance = null;
  private int id = -1;

  public static Id getInstance() {
    if (uniqueInstance == null) {
      uniqueInstance = new Id();
    }
    //logger.trace("Instance of main.Clock is being used");
    return uniqueInstance;
  }
  public int generateId() {
    return id++;
  }
}
