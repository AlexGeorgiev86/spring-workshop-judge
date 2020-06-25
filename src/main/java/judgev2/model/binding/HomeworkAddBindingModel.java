package judgev2.model.binding;

import javax.validation.constraints.NotNull;

public class HomeworkAddBindingModel {

    private String gitAddress;
    private String exercise;

    public HomeworkAddBindingModel() {
    }

    @NotNull(message = " Must be like: https:/github.com/{username}/{homeworkExample}/")
    public String getGitAddress() {
        return gitAddress;
    }

    public void setGitAddress(String gitAddress) {
        this.gitAddress = gitAddress;
    }
    @NotNull(message = "exercise cannot be null")
    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }
}
