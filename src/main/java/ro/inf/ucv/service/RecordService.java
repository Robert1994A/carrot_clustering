package ro.inf.ucv.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import ro.inf.ucv.entity.Record;
import ro.inf.ucv.repository.RecordRepository;
import ro.inf.ucv.service.utils.PaginationUtils;
import ro.inf.ucv.wrapper.SearchModel;

@Service
public class RecordService {

	private static final Logger logger = Logger.getLogger(RecordService.class);

	@Autowired
	private RecordRepository recordRepository;

	@Autowired
	private PaginationUtils paginationUtils;

	public Page<Record> findAll(SearchModel searchModel) {
		Page<Record> records = null;
		try {
			records = recordRepository.findAll(paginationUtils.getPageRequest(searchModel));
		} catch (Exception e) {
			logger.error(e);
		}
		return records;
	}

	public List<Record> findAll() {
		List<Record> records = null;
		try {
			records = recordRepository.findAll();
		} catch (Exception e) {
			logger.error(e);
		}
		return records;
	}

	public Long count() {
		return recordRepository.count();
	}

}
