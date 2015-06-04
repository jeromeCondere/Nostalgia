import org.nlogo.app.App;
import org.nlogo.headless.HeadlessWorkspace;
import org.nlogo.lite.InterfaceComponent;
public class Main {
  public static void main(String[] argv) {
    try {
      final javax.swing.JFrame frame = new javax.swing.JFrame();
      final InterfaceComponent comp = new InterfaceComponent(frame);
      java.awt.EventQueue.invokeAndWait(
    new Runnable() {
      public void run() {
        frame.setSize(1000, 700);
        frame.add(comp);
        frame.setVisible(true);
        try {
          comp.open("Wolf Sheep Predation (docked).nlogo");
        }
        catch(Exception ex) {
          ex.printStackTrace();
        }}});
      //comp.command("set density 62");
      //comp.command("random-seed 0");
      comp.command("setup");
      comp.command("repeat 5000 [ go ]");
      System.out.println(comp.report("burned-trees"));
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }
}