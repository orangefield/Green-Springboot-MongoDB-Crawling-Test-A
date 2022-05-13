package site.orangefield.mongocrudsample.domain;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface NaverRepository extends MongoRepository<Naver, String> {

    @Query("{title:?0, company:?1}") // 네이티브 쿼리
    List<Naver> mFindByTitleAndCompany(String title, String company);
}
