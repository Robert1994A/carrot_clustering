package ro.inf.ucv.service.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigurationUtils {

	@Value("${pagination.perPage:100}")
	private Integer paginationPerPage = 100;

	public Integer getPaginationPerPage() {
		return paginationPerPage;
	}
}
