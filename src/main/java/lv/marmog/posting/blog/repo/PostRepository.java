package lv.marmog.posting.blog.repo;

import lv.marmog.posting.blog.models.Post;
import org.springframework.data.repository.CrudRepository; //vse funkcii dlya raboty s tablichkoj

public interface PostRepository extends CrudRepository <Post, Long>{ //post - model's kotoroj rabotaem
}
