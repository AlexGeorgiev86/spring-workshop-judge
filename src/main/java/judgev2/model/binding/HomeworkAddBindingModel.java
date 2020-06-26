package judgev2.model.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

public class HomeworkAddBindingModel {

        private String gitAddress;
        private String exercise;

    public HomeworkAddBindingModel() {
    }

    @Pattern(regexp = "^https:\\/\\/github.com\\/.*",message = "Invalid github address")
    public String getGitAddress() {
        return gitAddress;
    }

    public void setGitAddress(String gitAddress) {
        this.gitAddress = gitAddress;
    }
    @Length(min = 1, message = "exercise cannot be empty")
    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }
}
