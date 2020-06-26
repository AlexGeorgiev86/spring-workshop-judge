package judgev2.service.impl;

import judgev2.model.entity.Comment;
import judgev2.model.entity.Homework;
import judgev2.model.service.CommentServiceModel;
import judgev2.model.service.HomeworkServiceModel;
import judgev2.repository.CommentRepository;
import judgev2.service.CommentService;
import judgev2.service.HomeworkService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    private final HomeworkService homeworkService;

    public CommentServiceImpl(CommentRepository commentRepository, ModelMapper modelMapper, HomeworkService homeworkService) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
        this.homeworkService = homeworkService;
    }

    @Override
    public void addComment(CommentServiceModel commentServiceModel) {
        HomeworkServiceModel homework = this.homeworkService.findById(commentServiceModel.getId());
        commentServiceModel.setHomework(homework);
        Comment comment = this.modelMapper.map(commentServiceModel, Comment.class);

        this.commentRepository.saveAndFlush(comment);

    }
}
