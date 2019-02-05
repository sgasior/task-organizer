package pl.edu.kopalniakodu.todoapp.domain;

public enum TaskWeight {

    IMPORTANT("important"),
    MD_IMPORTANT("md_important"),
    NOT_IMPORTANT("not_important");


    private String taskWeight;

    private TaskWeight(String taskWeight) {
        this.taskWeight = taskWeight;
    }

}
