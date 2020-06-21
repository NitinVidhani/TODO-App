package application.example.mytodo.model;

public class ToDoModel {

    private int id;
    private String userName, todoItem;

    public ToDoModel(String userName, String todoItem) {
        this.userName = userName;
        this.todoItem = todoItem;
    }

    public ToDoModel(int id, String userName, String todoItem) {
        this.id = id;
        this.userName = userName;
        this.todoItem = todoItem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTodoItem() {
        return todoItem;
    }

    public void setTodoItem(String todoItem) {
        this.todoItem = todoItem;
    }
}
