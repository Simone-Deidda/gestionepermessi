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
					  <dd class="col-sm-9">${show_messaggio_attr.id}</dd>
			    	</dl>
			    	
			    	<dl class="row">
					  <dt class="col-sm-3 text-right">Oggetto:</dt>
					  <dd class="col-sm-9">${show_messaggio_attr.oggetto}</dd>
			    	</dl>
			    	
			    	<dl class="row">
					  <dt class="col-sm-3 text-right">Testo:</dt>
					  <dd class="col-sm-9">${show_messaggio_attr.testo}</dd>
			    	</dl>
			    	

			    	
			    	<dl class="row">
					  <dt class="col-sm-3 text-right">Data Inserimento:</dt>
					  <dd class="col-sm-9"><fmt:formatDate type="date" value = "${show_messaggio_attr.dataInserimento}" /></dd>
			    	</dl>
			    	
			    	<dl class="row">
					  <dt class="col-sm-3 text-right">Data Lettura:</dt>
					  <dd class="col-sm-9"><fmt:formatDate type="date" value = "${show_messaggio_attr.dataLettura}" /></dd>
			    	</dl>
			    	
			    	<p>
					  <a class="btn btn-primary btn-sm" data-bs-toggle="collapse" href="#collapseRichiesta" role="button" aria-expanded="false" aria-controls="collapseExample">
					    Info Richiesta Permesso
					  </a>
					</p>
					<div class="collapse" id="collapseRichiesta">
						<div class="card card-body">
					    	<dl class="row">
							  <dt class="col-sm-3 text-right">Tipo Permesso:</dt>
							  <dd class="col-sm-9">${show_messaggio_richiestaPermesso_attr.tipoPermesso}</dd>
							  <dt class="col-sm-3 text-right">Data Inizio:</dt>
							  <dd class="col-sm-9"><fmt:formatDate type="date" value = "${show_messaggio_richiestaPermesso_attr.dataInizio}" /></dd>
							  <dt class="col-sm-3 text-right">Data Fine:</dt>
							  <dd class="col-sm-9"><fmt:formatDate type="date" value = "${show_messaggio_richiestaPermesso_attr.dataFine}" /></dd>
							  <dt class="col-sm-3 text-right">Stato:</dt>
					  		  <dd class="col-sm-9">${richiestaItem.approvato?'APPROVATO':'NON APPROVATO' }</dd>
					    	</dl>
					    </div>
					
					</div>
			    	
			    	<p>
					  <a class="btn btn-primary btn-sm" data-bs-toggle="collapse" href="#collapseDipendente" role="button" aria-expanded="false" aria-controls="collapseExample">
					    Info Dipendente
					  </a>
					</p>
					<div class="collapse" id="collapseDipendente">
						<div class="card card-body">
					    	<dl class="row">
							  <dt class="col-sm-3 text-right">Nome:</dt>
							  <dd class="col-sm-9">${show_messaggio_dipendente_attr.nome}</dd>
							  <dt class="col-sm-3 text-right">Cognome:</dt>
							  <dd class="col-sm-9">${show_messaggio_dipendente_attr.cognome}</dd>
							  <dt class="col-sm-3 text-right">Email:</dt>
							  <dd class="col-sm-9">${show_messaggio_dipendente_attr.email}</dd>
							  <dt class="col-sm-3 text-right">Sesso:</dt>
							  <dd class="col-sm-9">${show_messaggio_dipendente_attr.sesso}</dd>
					    	</dl>
					    </div>
					
					</div>
			    	
					</div>
			    	
			    <!-- end card body -->
			    </div>
			    
			    <div class='card-footer'>
			        <a href="${pageContext.request.contextPath }/messaggio/" class='btn btn-outline-secondary' style='width:80px'>
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