import javax.swing.*;
import java.awt.*;

import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.wm.ToolWindow;

public class MyToolWindow {
    private JPanel myToolWindowContent;
    private JButton showButton;
    public MyToolWindow(ToolWindow toolWindow, Project project) {
        myToolWindowContent = new a123();
        //myToolWindowContent.setLayout(new BorderLayout());
        //showButton = new JButton("Show");
        //myToolWindowContent.add(showButton, BorderLayout.SOUTH);
    }
    public JPanel getContent() {
        return myToolWindowContent;
    }
}
