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
        MyService State= MyService.getInstance(project);

        CurrentTasksJList.setModel(State.CurrentTasks());
        CompletedTasksJList.setModel(State.CompletedTasks());
        AddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(CompletedEditFlag==false)
                    State.addCurrentElement(TextField.getText());
                else
                    State.addCompletedElement(TextField.getText());
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
                    State.removeCurrentElement(indices[i]);
                }
                int[] indices2 = CompletedTasksJList.getSelectedIndices();
                for (int i = indices2.length - 1; i >= 0; i--) {
                    State.removeCompletedElement(indices2[i]);
                }
            }
        });
        MarkAsCompletedJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] indices = CurrentTasksJList.getSelectedIndices();
                for (int i = 0; i < indices.length; i++) {
                    State.addCompletedElement(State.getCurrentElement(indices[i]));
                }
                for (int i = indices.length - 1; i >= 0; i--) {
                    State.removeCurrentElement(indices[i]);
                }
            }
        });
        MarkAsCurrentJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] indices = CompletedTasksJList.getSelectedIndices();
                for (int i = 0; i < indices.length; i++) {
                    State.addCurrentElement(State.getCompletedElement(indices[i]));
                }
                for (int i = indices.length - 1; i >= 0; i--) {
                    State.removeCompletedElement(indices[i]);
                }
            }
        });
        EditJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] indices1 = CurrentTasksJList.getSelectedIndices();
                int[] indices2 = CompletedTasksJList.getSelectedIndices();
                if(indices1.length==1&&indices2.length==0){
                    TextField.setText(State.getCurrentElement(indices1[0]));
                    State.removeCurrentElement(indices1[0]);
                }
                if(indices1.length==0&&indices2.length==1){
                    TextField.setText(State.getCompletedElement(indices2[0]));
                    State.removeCompletedElement(indices2[0]);
                    CompletedEditFlag=true;
                }
            }
        });
    }
}
