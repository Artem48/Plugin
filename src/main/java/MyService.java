import com.intellij.openapi.components.*;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

@State(
        name="MyService",
        storages = {
                @Storage("State.xml")}
)
public class MyService implements PersistentStateComponent<MyService> {

    private DefaultListModel CurrentTasks=new DefaultListModel();
    private DefaultListModel CompletedTasks=new DefaultListModel();
    public List<String> CurrentTasksList = new LinkedList<String>();
    public List<String> CompletedTasksList = new LinkedList<String>();

    MyService() {}

    @Override
    public MyService getState() {
        return this;
    }

    @Override
    public void loadState(MyService singleFileExecutionConfig) {
        XmlSerializerUtil.copyBean(singleFileExecutionConfig, this);
        CurrentTasks.clear();
        for (String s : CurrentTasksList) {
            CurrentTasks.addElement(s);
        }
        CompletedTasks.clear();
        for (String s : CompletedTasksList) {
            CompletedTasks.addElement(s);
        }
    }

    public static MyService getInstance(Project project) {
        return ServiceManager.getService(project, MyService.class);
    }

    public DefaultListModel CurrentTasks() { return CurrentTasks; }
    public DefaultListModel CompletedTasks() { return CompletedTasks; }
    public void addCurrentElement(String s) {
        CurrentTasks.addElement(s);
        CurrentTasksList.add(s);
    }
    public void addCompletedElement(String s) {
        CompletedTasks.addElement(s);
        CompletedTasksList.add(s);
    }
    public String getCurrentElement(int idx) {
        return CurrentTasksList.get(idx);
    }
    public String getCompletedElement(int idx) {
        return CompletedTasksList.get(idx);
    }
    public void removeCurrentElement(int idx) {
        CurrentTasks.removeElementAt(idx);
        CurrentTasksList.remove(idx);
    }
    public void removeCompletedElement(int idx) {
        CompletedTasks.removeElementAt(idx);
        CompletedTasksList.remove(idx);
    }
}