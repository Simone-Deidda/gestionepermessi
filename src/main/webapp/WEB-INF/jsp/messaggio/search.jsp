<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!doctype html>
<html lang="it" class="h-100" >
<head>
	<jsp:include page="../header.jsp" />
	<title>Ricerca</title>
	
    
</head>
<body class="d-flex flex-column h-100">
	<!-- Fixed navbar -->
	<jsp:include page="../navbar.jsp"></jsp:include>
	
	<!-- Begin page content -->
	<main class="flex-shrink-0">
	  <div class="container">
	
			<div class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none': ''}" role="alert">
			  ${errorMessage}
			  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
			    <span aria-hidden="true">&times;</span>
			  </button>
			</div>
			
			<div class="alert alert-warning alert-dismissible fade show d-none" role="alert">
			  Attenzione!!! Funzionalità ancora non implementata. Sulla 'Conferma' per il momento parte il 'listAll'
			  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
			</div>
			
			<div class='card'>
			    <div class='card-header'>
			        <h5>Ricerca elementi</h5> 
			    </div>
			    <div class='card-body'>
	
						<form:form modelAttribute="search_messaggio_attr" method="post" action="list" novalidate="novalidate" class="row g-3">
						
							<div class="col-md-6">
								<label for="Oggetto" class="form-label">Oggetto</label>
								<input type="text" class="form-control" name="Oggetto" id="Oggetto" placeholder="Inserire Oggetto" >
							</div>
						
							<div class="col-md-6">
								<label for="testo" class="form-label">Testo</label>
								<input type="text" class="form-control" name="testo" id="testo" placeholder="Inserire testo" >
							</div>
							
							<div class="col-md-3">
								<label for="dataInserimento" class="form-label">Data Inserimento </label>
                       			<input class="form-control" id="dataInserimento" type="date" placeholder="dd/MM/yy"
                           			title="formato : gg/mm/aaaa"  name="dataInserimento"   >
							</div>
							
							<div class="col-md-3">
								<label for="dataLettura" class="form-label">Data Lettura </label>
                       			<input class="form-control" id="dataLettura" type="date" placeholder="dd/MM/yy"
                           			title="formato : gg/mm/aaaa"  name="dataLettura"   >
							</div>
							
							<div class="col-md-12">
							  <input class="form-check-input" type="checkbox"  id="letto" name="letto">
							  <label class="form-check-label" for="letto">Letto</label>
							</div>
							
							<div class="col-12">	
								<button type="submit" name="submit" value="submit" id="submit" class="btn btn-primary">Conferma</button>
								<input class="btn btn-outline-warning" type="reset" value="Ripulisci">
							</div>
	
							
						</form:form>
			    
				<!-- end card-body -->			   
			    </div>
			</div>	
	
		</div>
	<!-- end container -->	
	</main>
	<jsp:include page="../footer.jsp" />
	
</body>
</html>