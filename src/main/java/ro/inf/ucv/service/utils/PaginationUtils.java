package ro.inf.ucv.service.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ro.inf.ucv.wrapper.SearchModel;

@Service
public class PaginationUtils {

	@Autowired
	private ConfigurationUtils configurationUtils;

	public Pageable getPageRequest(SearchModel searchModel) {
		int pageNumber = searchModel.getPageNumber();
		int pageSize = searchModel.getPageSize();

		return getPageRequest(pageNumber, pageSize);
	}

	public Pageable getPageRequest(Integer pageNumber, Integer pageSize) {
		if (pageNumber == null || pageNumber < 0) {
			pageNumber = 0;
		} else if (pageNumber > 0) {
			pageNumber = pageNumber - 1;
		}
		if (pageSize == null || pageSize < 1) {
			pageSize = configurationUtils.getPaginationPerPage();
		}

		return PageRequest.of(pageNumber, pageSize);
	}
}