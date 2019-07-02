/**
 * Acceptor interface for Visitor Pattern
 *
 * @author Yuki Yamada
 * @author Aleix Molla
 * @version 2018/03/30
 */
public interface Acceptor {
    public void accept(Visitor visitor);
}
