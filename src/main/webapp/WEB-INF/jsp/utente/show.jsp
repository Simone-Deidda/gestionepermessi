<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="it" class="h-100">
<head>
	<!-- Common imports in pages -->
	<jsp:include page="../header.jsp" />
	<title>Visualizza elemento</title>
	
</head>
<body class="d-flex flex-column h-100">
	<!-- Fixed navbar -->
	<jsp:include page="../navbar.jsp" />
	
	<!-- Begin page content -->
	<main class="flex-shrink-0">
	  	<div class="container">
			
			<div class='card'>
			    <div class='card-header'>
			        Visualizza dettaglio
			    </div>
			
			    <div class='card-body'>
			    	<dl class="row">
					  <dt class="col-sm-3 text-right">Id:</dt>
					  <dd class="col-sm-9">${show_utente_attr.id}</dd>
			    	</dl>
			    	
			    	<dl class="row">
					  <dt class="col-sm-3 text-right">Username:</dt>
					  <dd class="col-sm-9">${show_utente_attr.username}</dd>
			    	</dl>
			    	
			    	<dl class="row">
					  <dt class="col-sm-3 text-right">Data di Creazione:</dt>
					  <dd class="col-sm-9"><fmt:formatDate type="date" value = "${show_utente_attr.dateCreated}" /></dd>
			    	</dl>
			    	
			    	<dl class="row">
					  <dt class="col-sm-3 text-right">Stato:</dt>
					  <dd class="col-sm-9">${show_utente_attr.stato}</dd>
			    	</dl>
			    	
			    	<p>
					  <a class="btn btn-primary btn-sm" data-bs-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
					    Info Ruolo
					  </a>
					</p>
					<div class="collapse" id="collapseExample">
						<div class="card card-body">
					    	<dl class="row">
							  <c:forEach items="${show_ruoli_utente_attr}" var="ruoloItem">
							  <dt class="col-sm-3 text-right">Codice:</dt>
							  <dd class="col-sm-9">${ruoloItem.codice}</dd>
							  <dt class="col-sm-3 text-right">Descrizione:</dt>
							  <dd class="col-sm-9">${ruoloItem.descrizione}</dd>
							  </c:forEach>
					    	</dl>
					    </div>
					
					</div>
			    	
					</div>
			    	
			    <!-- end card body -->
			    </div>
			    
			    <div class='card-footer'>
			        <a href="${pageContext.request.contextPath }/utente/" class='btn btn-outline-secondary' style='width:80px'>
			            <i class='fa fa-chevron-left'></i> Back
			        </a>
			    </div>
			<!-- end card -->
			</div>	
	
		<!-- end container -->  
		
	</main>
	<jsp:include page="../footer.jsp" />
	
</body>
</html>