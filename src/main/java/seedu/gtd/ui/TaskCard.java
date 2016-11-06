package seedu.gtd.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.gtd.model.task.ReadOnlyTask;

public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label doneStatus;
    @FXML
    private Label id;
    @FXML
<<<<<<< HEAD
    private Label isDone;
    @FXML
    private Label startDate;
    @FXML
    private Label dueDate;
=======
    private Label dateString;
>>>>>>> C3/recurring-tasks
    @FXML
    private Label address;
    @FXML
    private Label priority;
    @FXML
    private Label tags;

    private ReadOnlyTask task;
    private int displayedIndex;

    public TaskCard(){

    }

    public static TaskCard load(ReadOnlyTask task, int displayedIndex){
        TaskCard card = new TaskCard();
        card.task = task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        name.setText(task.getName().fullName);
        id.setText(displayedIndex + ". ");
        if (task.getisDone()) {
        	 isDone.setText(" [O]");
        } else {
        	isDone.setText(" [X]");
        }
        startDate.setText(task.getStartDate().value);
        dueDate.setText(task.getDueDate().value);
=======
        dateString.setText(task.dateString());
        if(task.getisDone())
        	doneStatus.setText(" [O]");
       	else
       		doneStatus.setText(" [X]");
>>>>>>> C3/recurring-tasks
        address.setText(task.getAddress().value);
        priority.setText(task.getPriority().value);
        tags.setText(task.tagsString());
    }

    public HBox getLayout() {
        return cardPane;
    }

    @Override
    public void setNode(Node node) {
        cardPane = (HBox)node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}