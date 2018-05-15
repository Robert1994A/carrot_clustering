<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div class="container p-2">
	<div class="row">
		<div class="col-md-12">
			<c:if test="${found == true}">
				<div class="card">
					<h5 class="card-header">Documents</h5>
					<div class="card-body">
						<div class="card-title">
							<div class="alert alert-success" role="alert">${fn:length(documents)}
								documents was found.</div>
						</div>
						<ul class="list-group">
							<c:forEach items="${documents}" var="document">
								<li class="list-group-item"><a
									href="${document.contentUrl}" target="_blank">${document.title}</a></li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</c:if>
			<c:if test="${found == false}">
				<div class="alert alert-warning" role="alert">No document was
					found.</div>
			</c:if>
		</div>
	</div>
</div>