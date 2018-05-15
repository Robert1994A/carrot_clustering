package ro.inf.ucv.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.bson.types.ObjectId;
import org.carrot2.core.Cluster;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ro.inf.ucv.entity.Document;
import ro.inf.ucv.wrapper.SearchModel;

@Controller
public class DocumentController extends BaseController {

	@PostMapping("/")
	public String cluster(@ModelAttribute("searchModel") SearchModel searchModel, Model model) throws Exception {
		boolean found = false;
		List<Cluster> clusters = this.documentService.clusterRecords(searchModel);
		if (clusters != null) {
			clusters = new ArrayList<Cluster>(clusters);
			Collections.sort(clusters, new Comparator<Cluster>() {
				@Override
				public int compare(final Cluster cluster1, final Cluster cluster2) {
					if (cluster1 != null && cluster2 != null && cluster1.getScore() != null
							&& cluster2.getScore() != null) {
						return Long.compare(cluster2.getScore().longValue(), cluster1.getScore().longValue());
					}

					return 0;
				}
			});
			found = true;
		}
		model.addAttribute("clusters", clusters);
		model.addAttribute("found", found);
		model.addAttribute("searchModel", searchModel);

		return "home";
	}

	@PostMapping("/documents")
	public String getDocumentsById(@RequestParam(value = "id", required = true) List<String> ids, Model model)
			throws Exception {
		boolean found = false;
		Iterable<Document> documents = null;
		if (ids != null && !ids.isEmpty()) {
			List<ObjectId> documentIds = new ArrayList<>();
			for (String id : ids) {
				documentIds.add(new ObjectId(id));
			}
			documents = this.documentService.findAllById(documentIds);
			if (documents != null) {
				found = true;
			}
		}

		model.addAttribute("documents", documents);
		model.addAttribute("found", found);

		return "documents";
	}

}