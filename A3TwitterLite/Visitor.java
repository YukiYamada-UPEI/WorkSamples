/**
 * Visitor interface for Visitor Pattern
 *
 * @author Yuki Yamada
 * @author Aleix Molla
 * @version 2018/03/30
 */
public interface Visitor {
    public void visit(User user);
    public void visit(UserGroup group);
    public void visit(AdminController adminController);
    public void visit(UserPanelController userPanelController);
}
