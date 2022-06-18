<!doctype html>
<html lang="it">
	<head>
	  <meta charset="utf-8">
		<title>Accedi</title>
	
		<!-- Common imports in pages -->
	 	<jsp:include page="./header.jsp" />
	
	
		 <!-- Custom styles for login -->
	    <link href="assets/css/signin.css" rel="stylesheet">
	</head>
	
	<body class="text-center">
		<main class="form-signin">
			<form class="form-signin" name='reset' action="reset" method='POST' novalidate="novalidate">
		   	
			   	<div class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none': ''}" role="alert">
				  ${errorMessage}
				</div>
				
				<div class="alert alert-info alert-dismissible fade show ${infoMessage==null?'d-none': ''}" role="alert">
				  ${infoMessage}
				</div>
				
				
			  	<img class="mb-4" src="./assets/brand/bootstrap-logo.svg" alt="" width="72" height="57">
				<h1 class="h3 mb-3 fw-normal">Reset password</h1>
		    	
		    	
			  	<div class="form-floating">
			      <input type="password" name="oldPassword" class="form-control" id="oldPassword" placeholder="Inserisci vecchia password">
			      <label for="oldPassword">Vecchia Password</label>
			    </div>
			    <div class="form-floating">
			      <input type="password" name="newPassword" class="form-control" id="newPassword" placeholder="Inserisci nuova password">
			      <label for="newPassword">Nuova Password</label>
			    </div>
				<div class="form-floating">
			      <input type="password" name="confermaPassword" class="form-control" id="confermaPassword" placeholder="Inserisci password di conferma">
			      <label for="confermaPassword">Conferma Password</label>
			    </div>
				
			    <div class="checkbox mb-3">
			    </div>
			    <button class="w-100 btn btn-lg btn-primary" type="submit">Reset</button>
			    <p class="mt-5 mb-3 text-muted">&copy; 2017-2021</p>
			  
			  
			  
			</form>
		</main>
	</body>
</html>