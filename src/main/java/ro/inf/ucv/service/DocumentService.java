package ro.inf.ucv.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.carrot2.core.Cluster;
import org.carrot2.core.Controller;
import org.carrot2.core.ControllerFactory;
import org.carrot2.core.LanguageCode;
import org.carrot2.core.ProcessingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ro.inf.ucv.entity.Document;
import ro.inf.ucv.repository.DocumentRepository;
import ro.inf.ucv.service.utils.ClusteringUtils;
import ro.inf.ucv.service.utils.PaginationUtils;
import ro.inf.ucv.wrapper.SearchModel;

@Service
public class DocumentService {

	private static final Logger logger = Logger.getLogger(DocumentService.class);

	@Autowired
	private DocumentRepository documentRepository;

	@Autowired
	private PaginationUtils paginationUtils;

	public Page<Document> findAll(SearchModel searchModel) {
		Page<Document> documents = null;
		try {
			Pageable pageable = this.paginationUtils.getPageRequest(searchModel);
			String search = searchModel.getSearch();
			if (search != null && !search.trim().isEmpty()) {
				documents = this.documentRepository
						.findByTitleIgnoreCaseContainingOrContentUrlIgnoreCaseContaining(search, search, pageable);
			} else {
				documents = this.documentRepository.findAll(pageable);
			}
		} catch (Exception e) {
			logger.error(e);
		}

		return documents;
	}

	public List<Document> findAll() {
		List<Document> documents = null;
		try {
			documents = this.documentRepository.findAll();
		} catch (Exception e) {
			logger.error(e);
		}

		return documents;
	}

	public Iterable<Document> findAllById(Iterable<ObjectId> ids) {
		Iterable<Document> documents = null;
		try {
			documents = this.documentRepository.findAllById(ids);
		} catch (Exception e) {
			logger.error(e);
		}

		return documents;
	}

	public Document save(Document document) {
		Document savedDocument = null;
		if (document != null) {
			try {
				savedDocument = this.documentRepository.save(document);
			} catch (Exception e) {
				logger.error(e);
			}
		}

		return savedDocument;
	}

	public void saveAll(List<Document> documents) {
		if (documents != null && !documents.isEmpty()) {
			try {
				documentRepository.saveAll(documents);
			} catch (Exception e) {
				logger.error(e);
			}
		}
	}

	public long count() {
		return documentRepository.count();
	}

	public List<Cluster> clusterRecords(SearchModel searchModel) {
		List<Cluster> obtainedClusters = null;
		Page<Document> documents = findAll(searchModel);
		if (documents != null && documents.hasContent()) {
			try {
				List<org.carrot2.core.Document> carrotDocuments = new ArrayList<>();
				for (Document document : documents.getContent()) {
					org.carrot2.core.Document carrotDocument = new org.carrot2.core.Document(document.getTitle(), null,
							document.getContentUrl(), LanguageCode.forISOCode(document.getLanguage()),
							document.getId().toHexString());
					carrotDocuments.add(carrotDocument);
				}
				
				Controller controller = ControllerFactory.createSimple();
				Class<?> algorithmClass = ClusteringUtils.getAlgorithm(searchModel.getAlgorithm());
				ProcessingResult byTopicClusters = controller.process(carrotDocuments, searchModel.getSearch(),
						algorithmClass);
				List<Cluster> clusters = byTopicClusters.getClusters();
				if (clusters != null && !clusters.isEmpty()) {
					obtainedClusters = clusters;
				}
			} catch (Exception e) {
				logger.error(e);
			}
		}

		return obtainedClusters;
	}
}
