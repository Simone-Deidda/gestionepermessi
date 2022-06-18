<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<header>
  <!-- Fixed navbar -->
 <nav class="navbar navbar-expand-lg navbar-dark bg-primary" aria-label="Eighth navbar example">
    <div class="container">
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarsExample07" aria-controls="navbarsExample07" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navbarsExample07">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <li class="nav-item">
            <a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/home">Home</a>
          </li>
          
          <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" id="dropdown07" data-bs-toggle="dropdown" aria-expanded="false">Dropdown</a>
            <ul class="dropdown-menu" aria-labelledby="dropdown07">
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/home">Home</a></li>
              
              <sec:authorize access="hasRole('ADMIN')">
	              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/utente/search">Cerca Utenti</a></li>
	              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/dipendente/search">Cerca Dipendenti</a></li>
              </sec:authorize>
              <sec:authorize access="hasRole('BO_USER')">
	              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/dipendente/search">Cerca Dipendenti</a></li>
	              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/richiestapermesso/search">Cerca Richieste Permesso</a></li>
	              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/messaggio/search">Cerca Messaggi</a></li>
              </sec:authorize>
              <sec:authorize access="hasRole('DIPENDENTE_USER')">
	              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/richiestapermesso/search/">Cerca Richieste Permesso</a></li>
              </sec:authorize>
            </ul> 
          </li>
        </ul>
      </div>
      <sec:authorize access="isAuthenticated()">
      <div class="collapse navbar-collapse position-relative top-0 end-0" id="navbarsExample07">
      	<ul class="navbar-nav me-auto mb-2 mb-lg-0">
      	 
			   <li class="nav-item dropdown ">
		        <a class="nav-link dropdown-toggle " href="#" id="dropdown01" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">${userInfo.nome} ${userInfo.cognome}</a>
		        <div class="dropdown-menu " aria-labelledby="dropdown01">
		          <a class="dropdown-item" href="${pageContext.request.contextPath}/logout">Logout</a>
		          <a class="dropdown-item"  href="${pageContext.request.contextPath}/#">Reset Password</a>
		        </div>
		      </li>
      	 
      	</ul>
      </div>
      </sec:authorize>
      
    </div>
  </nav>
  
  
</header>
