package ru.practicum.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.comment.repository.CommentRepository;
import ru.practicum.exceptions.CommentNotFoundException;

@Service
@RequiredArgsConstructor
public class CommentAdminService implements CommentAdminServiceInterface {
    private final CommentRepository commentRepository;

    @Transactional
    @Override
    public void delete(Long commentId) {
        commentRepository.findById(commentId).orElseThrow(
                () -> new CommentNotFoundException("Комментарий с id " + commentId + " не найден.")
        );

        commentRepository.deleteById(commentId);
    }
}