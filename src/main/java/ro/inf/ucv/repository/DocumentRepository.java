package ro.inf.ucv.repository;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ro.inf.ucv.entity.Document;

@Repository
public interface DocumentRepository extends MongoRepository<Document, ObjectId> {

	Page<Document> findByTitleIgnoreCaseContainingOrContentUrlIgnoreCaseContaining(String search, String search2,
			Pageable pageable);

}
