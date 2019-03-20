import javax.swing.*;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;

public class MyToolWindow {
    private JPanel myToolWindowContent;
    private JButton showButton;
    public MyToolWindow(ToolWindow toolWindow, Project project) {
        myToolWindowContent = new JPanel();
        MyForm MyForm =new MyForm();
        myToolWindowContent = MyForm.getRootComponent();
    }
    public JPanel getContent() {
        return myToolWindowContent;
    }
}
