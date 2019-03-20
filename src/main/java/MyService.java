import com.intellij.openapi.components.*;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;

import javax.swing.*;

@State(
        name="MyService.State",
        storages = {
                @Storage("State.xml")}
)
class MyService implements PersistentStateComponent<MyService.State> {

    static class State {
        static DefaultListModel CurrentTasks=new DefaultListModel();
        static DefaultListModel CompletedTasks=new DefaultListModel();
        public String value;
    }

    State myState;

    public State getState() {
        return myState;
    }

    @Override
    public void loadState(MyService.State singleFileExecutionConfig) {
        XmlSerializerUtil.copyBean(singleFileExecutionConfig, this);
    }

    public static MyService.State getInstance(Project project) {
        return ServiceManager.getService(project, MyService.State.class);
    }

    public static DefaultListModel GetCurrentTasks() {
        return State.CurrentTasks;
    }
    public static DefaultListModel GetCompletedTasks() {
        return State.CompletedTasks;
    }

    public static void SetCurrentTasks(DefaultListModel CurrentTasks) {
        State.CurrentTasks=CurrentTasks;
    }

    public static void SetCompletedTasks(DefaultListModel CompletedTasks) {
        State.CompletedTasks=CompletedTasks;
    }

}