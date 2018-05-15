package ro.inf.ucv.service.utils;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class ClusteringUtils {

	private static final Logger logger = Logger.getLogger(ClusteringUtils.class);

	private static final String DEFAULT_ALGORITHM = "org.carrot2.clustering.lingo.LingoClusteringAlgorithm";

	private static final String STC_ALGORITHM = "org.carrot2.clustering.stc.STCClusteringAlgorithm";

	private static final String KMEANS_ALGORITHM = "org.carrot2.clustering.kmeans.BisectingKMeansClusteringAlgorithm";

	private static final String URL_ALGORITHM = "org.carrot2.clustering.synthetic.ByUrlClusteringAlgorithm";

	public static Class<?> getAlgorithm(String algorithm) {
		Class<?> algorithmClass = null;
		String algorithmClassName = DEFAULT_ALGORITHM;
		if (algorithm != null) {
			algorithm = algorithm.trim();
			if (algorithm.equalsIgnoreCase("LINGO")) {
				algorithmClassName = DEFAULT_ALGORITHM;
			} else if (algorithm.equalsIgnoreCase("STC")) {
				algorithmClassName = STC_ALGORITHM;
			} else if (algorithm.equalsIgnoreCase("KMEANS")) {
				algorithmClassName = KMEANS_ALGORITHM;
			} else if (algorithm.equalsIgnoreCase("URL")) {
				algorithmClassName = URL_ALGORITHM;
			}
		}

		try {
			algorithmClass = Class.forName(algorithmClassName);
		} catch (Exception e) {
			logger.error("Failed to create algorithm: " + algorithmClassName, e);
			try {
				algorithmClass = Class.forName(DEFAULT_ALGORITHM);
			} catch (ClassNotFoundException e1) {
				// This should not happen.
			}
		}

		return algorithmClass;
	}
}
