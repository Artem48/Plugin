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
    public MyForm() {
        DefaultListModel CurrentTasks = new DefaultListModel();
        DefaultListModel CompletedTasks = new DefaultListModel();
        CurrentTasksJList.setModel(CurrentTasks);
        CompletedTasksJList.setModel(CompletedTasks);
        AddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(CompletedEditFlag==false)
                    CurrentTasks.addElement(TextField.getText());
                else
                    CompletedTasks.addElement(TextField.getText());
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
                    CurrentTasks.removeElementAt(indices[i]);
                }
                int[] indices2 = CompletedTasksJList.getSelectedIndices();
                for (int i = indices2.length - 1; i >= 0; i--) {
                    CompletedTasks.removeElementAt(indices2[i]);
                }
            }
        });
        MarkAsCompletedJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] indices = CurrentTasksJList.getSelectedIndices();
                for (int i = 0; i < indices.length; i++) {
                    CompletedTasks.addElement(CurrentTasks.getElementAt(indices[i]));
                }
                for (int i = indices.length - 1; i >= 0; i--) {
                    CurrentTasks.removeElementAt(indices[i]);
                }
            }
        });
        MarkAsCurrentJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] indices = CompletedTasksJList.getSelectedIndices();
                for (int i = 0; i < indices.length; i++) {
                    CurrentTasks.addElement(CompletedTasks.getElementAt(indices[i]));
                }
                for (int i = indices.length - 1; i >= 0; i--) {
                    CompletedTasks.removeElementAt(indices[i]);
                }
            }
        });
        EditJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] indices1 = CurrentTasksJList.getSelectedIndices();
                int[] indices2 = CompletedTasksJList.getSelectedIndices();
                if(indices1.length==1&&indices2.length==0){
                    TextField.setText(CurrentTasks.getElementAt(indices1[0]).toString());
                    CurrentTasks.removeElementAt(indices1[0]);
                }
                if(indices1.length==0&&indices2.length==1){
                    TextField.setText(CompletedTasks.getElementAt(indices2[0]).toString());
                    CompletedTasks.removeElementAt(indices2[0]);
                    CompletedEditFlag=true;
                }
            }
        });
    }
}
