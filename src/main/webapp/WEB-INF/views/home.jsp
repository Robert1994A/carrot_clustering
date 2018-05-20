<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="java.util.List"%>
<%@ page import="org.carrot2.core.Document"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Home</title>
<jsp:include page="include/css.jsp"></jsp:include>
</head>

<body>
	<%!public String getDocumentIds(List<Document> documents) {
		StringBuilder builder = new StringBuilder();
		for (Document document : documents) {
			builder.append("&id=").append(document.getStringId());
		}
		return builder.toString().trim();
	}%>

	<jsp:include page="include/navigation.jsp"></jsp:include>

	<section>
		<div class="container p-2">
			<div class="row">
				<div class="col-md-12">
					<form action="/" method="POST">
						<div class="form-group">
							<label>Search:</label> <input type="text" name="search"
								class="form-control" value="${searchModel.search}"
								placeholder="Text to search" />
						</div>
						<div class="form-group">
							<label>Number of documents:</label> <select class="form-control"
								name="pageSize">
								<option value="50" selected="selected">50</option>
								<option value="100">100</option>
								<option value="300">300</option>
								<option value="500">500</option>
								<option value="1000">1000</option>
							</select>
						</div>
						<div class="form-group">
							<label>Algorithm:</label> <select class="form-control"
								name="algorithm">
								<option value="LINGO" selected="selected">LINGO</option>
								<option value="STC">STC</option>
								<option value="KMEANS">KMEANS</option>
								<option value="URL">URL</option>
							</select>
						</div>
						<button type="submit" class="btn btn-primary">Search</button>
					</form>
				</div>
			</div>
		</div>
	</section>

	<section>
		<div class="container p-2">
			<div class="row">
				<div class="col-md-12">
					<c:if test="${found == true}">
						<div class="card">
							<h5 class="card-header">Clusters</h5>
							<div class="card-body">
								<div class="card-title">
									<div class="alert alert-success" role="alert">${fn:length(clusters)}
										clusters was found.</div>
								</div>
								<ul class="list-group">
									<c:forEach items="${clusters}" var="cluster">
										<li class="list-group-item d-flex justify-content-between">
											<c:set var="documents" value="${cluster.documents}"
												scope="request" /> <a class="cluster-label" href="#"
											document-ids="<%=getDocumentIds((List<Document>) request.getAttribute("documents"))%>">${cluster.label}
												<span class="badge badge-light"><fmt:formatNumber
														value="${cluster.score}" minFractionDigits="0"
														maxFractionDigits="0" /></span>
										</a> <span class="badge badge-primary badge-pill">${fn:length(cluster.documents)}</span>
										</li>
									</c:forEach>
								</ul>
							</div>
						</div>
					</c:if>
					<c:if test="${found == false}">
						<div class="alert alert-warning" role="alert">No clusters
							was found.</div>
					</c:if>
				</div>
			</div>
		</div>
	</section>

	<!-- Modal -->
	<div class="modal fade" id="documentsModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Documents</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body" id="documents-location"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="include/footer.jsp"></jsp:include>

	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							$(".cluster-label")
									.on(
											"click",
											function(event) {
												var documentIds = $(this).attr(
														"document-ids");
												if (documentIds !== null
														&& documentIds !== "") {
													$
															.ajax({
																url : '/documents',
																type : 'POST',
																data : documentIds,
																contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
																success : function(
																		response) {
																	$(
																			"#documents-location")
																			.html(
																					response);
																	$(
																			"#documentsModal")
																			.modal();
																},
																error : function() {
																	alert("error");
																}
															});
												}

												event.preventDefault();
											});
						});
	</script>
</body>
</html>
