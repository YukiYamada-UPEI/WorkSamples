/**
 * Subject is an interface for Observer Pattern.
 *
 * @author Yuki Yamada
 * @author Aleix Molla
 * @version 2018/03/30
 */
public interface Subject {
    public abstract void registerObserver(Observer observer);
    public abstract void removeObserver(Observer observer);
    public abstract void notifyObservers();
}
