package potato.server.community.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import potato.server.common.BaseTimeEntity;
import potato.server.common.CustomException;
import potato.server.common.ResultCode;
import potato.server.user.domain.User;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @OrderBy("createdDate ASC")
    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> childComments = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public String content;

    @Builder
    public Comment(Post post, User user, String content) {
        this.post = post;
        this.user = user;
        this.content = content;
    }

    public void addChildComment(Comment comment) {
        comment.setParentComment(comment);
        this.getChildComments().add(comment);
    }

    public void setParentComment(Comment comment) {
        this.parentComment = comment;
    }

    public void isCorrectPost(Post post) {
        if (!post.getId().equals(this.getPost().id)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, ResultCode.PARENT_COMMENT_POST_MISS_MATCH);
        }
    }

    public void updateComment(User user, String content) {
        if (!user.getId().equals(this.user.getId())) {
            throw new CustomException(HttpStatus.FORBIDDEN, ResultCode.USER_NOT_PERMISSION);
        }

        this.content = content;
    }

}
