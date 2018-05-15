package ro.inf.ucv.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ro.inf.ucv.entity.Record;

@Repository
public interface RecordRepository extends MongoRepository<Record, ObjectId> {

}
