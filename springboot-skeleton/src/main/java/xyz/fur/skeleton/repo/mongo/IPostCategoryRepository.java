package xyz.fur.skeleton.repo.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import xyz.fur.skeleton.entity.PostCategory;

/**
 * @author Fururur
 * @date 2020-03-13-15:19
 */
public interface IPostCategoryRepository extends MongoRepository<PostCategory, String> {
}
