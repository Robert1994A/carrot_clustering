<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>500</title>
<jsp:include page="include/css.jsp"></jsp:include>
</head>

<body>

	<jsp:include page="include/navigation.jsp"></jsp:include>

	<section>
		<div class="container p-2">
			<div class="row">
				<div class="col-md-12">
					<div class="alert alert-danger" role="alert">
						<c:choose>
							<c:when test="${empty errorMessage}">
							A error occurred in processing your request. Please try again.
							</c:when>
							<c:otherwise>
								${errorMessage}
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
		</div>
	</section>



	<jsp:include page="include/footer.jsp"></jsp:include>


</body>
</html>
