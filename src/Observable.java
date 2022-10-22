public abstract class  Observable{
    public void addObserver(Observer ob){}
    public void deleteObserver(Observer ob){}
    public void setChanged(){}
    public void notifyObservers(Observable arg0, Object arg1){}
}
