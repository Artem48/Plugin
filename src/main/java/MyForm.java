import com.intellij.openapi.project.Project;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyForm extends JPanel {

    private JTextField TextField;
    private JButton AddButton;
    private JList CurrentTasksJList;
    private JButton MarkAsCompletedJButton;
    private JButton EditJButton;
    private JButton DeleteJButton;
    private JButton MarkAsCurrentJButton;
    private JPanel rootPanel;
    private JList CompletedTasksJList;
    private boolean CompletedEditFlag=false;
    public JPanel getRootComponent(){
        return rootPanel;
    }
    public MyForm(Project project) {
        MyService.State State= MyService.getInstance(project);

        CurrentTasksJList.setModel(State.CurrentTasks);
        CompletedTasksJList.setModel(State.CompletedTasks);
        AddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(CompletedEditFlag==false)
                    State.CurrentTasks.addElement(TextField.getText());
                else
                    State.CompletedTasks.addElement(TextField.getText());
                TextField.setText("");
            }
        });
        CurrentTasksJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                CompletedTasksJList.clearSelection();
            }
        });

        CompletedTasksJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                CurrentTasksJList.clearSelection();
            }
        });
        DeleteJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] indices = CurrentTasksJList.getSelectedIndices();
                for (int i = indices.length - 1; i >= 0; i--) {
                    State.CurrentTasks.removeElementAt(indices[i]);
                }
                int[] indices2 = CompletedTasksJList.getSelectedIndices();
                for (int i = indices2.length - 1; i >= 0; i--) {
                    State.CompletedTasks.removeElementAt(indices2[i]);
                }
            }
        });
        MarkAsCompletedJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] indices = CurrentTasksJList.getSelectedIndices();
                for (int i = 0; i < indices.length; i++) {
                    State.CompletedTasks.addElement(State.CurrentTasks.getElementAt(indices[i]));
                }
                for (int i = indices.length - 1; i >= 0; i--) {
                    State.CurrentTasks.removeElementAt(indices[i]);
                }
            }
        });
        MarkAsCurrentJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] indices = CompletedTasksJList.getSelectedIndices();
                for (int i = 0; i < indices.length; i++) {
                    State.CurrentTasks.addElement(State.CompletedTasks.getElementAt(indices[i]));
                }
                for (int i = indices.length - 1; i >= 0; i--) {
                    State.CompletedTasks.removeElementAt(indices[i]);
                }
            }
        });
        EditJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] indices1 = CurrentTasksJList.getSelectedIndices();
                int[] indices2 = CompletedTasksJList.getSelectedIndices();
                if(indices1.length==1&&indices2.length==0){
                    TextField.setText(State.CurrentTasks.getElementAt(indices1[0]).toString());
                    State.CurrentTasks.removeElementAt(indices1[0]);
                }
                if(indices1.length==0&&indices2.length==1){
                    TextField.setText(State.CompletedTasks.getElementAt(indices2[0]).toString());
                    State.CompletedTasks.removeElementAt(indices2[0]);
                    CompletedEditFlag=true;
                }
            }
        });
    }
}
