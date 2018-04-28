/**
 * Sets the page title according with the current url.
 */
function setPageTitle () {
	var pageTitle       = null;
	var pageAndLabelArr = new Array();
	var url = window.location.href;
	
	console.log ("url: " + url);
	
	pageAndLabelArr.push ("roles.page|Papéis");
	pageAndLabelArr.push ("updateRole.page|Adicionar Papel|Atualizar Papel");
	pageAndLabelArr.push ("profiles.page|Perfis");
	pageAndLabelArr.push ("updateProfile.page|Adicionar Perfil|Atualizar Perfil");
	pageAndLabelArr.push ("users.page|Usuários");
	pageAndLabelArr.push ("updateUser.page|Adicionar Usuário|Atualizar Usuário");
	pageAndLabelArr.push ("teachers.page|Professores");
	pageAndLabelArr.push ("updateTeacher.page|Adicionar Professor|Atualizar Professor");
	pageAndLabelArr.push ("courses.page|Cursos");
	pageAndLabelArr.push ("updateCourse.page|Adicionar Curso|Atualizar Curso");
	pageAndLabelArr.push ("updateClass.page|Adicionar Aula|Atualizar Aula");
	pageAndLabelArr.push ("updateSlide.page|Adicionar Slide|Atualizar Slide");
	pageAndLabelArr.push ("updateExercise.page|Adicionar Exercício|Atualizar Exercício");
	pageAndLabelArr.push ("updateQuestion.page|Adicionar Questão|Atualizar Questão");
	pageAndLabelArr.push ("careers.page|Profissões");
	pageAndLabelArr.push ("updateCareer.page|Adicionar Profissão|Atualizar Profissão");	
	pageAndLabelArr.push ("students.page|Alunos");
	pageAndLabelArr.push ("updateStudent.page|Atualizar Aluno");
	pageAndLabelArr.push ("certificates.page|Certificados");
	pageAndLabelArr.push ("updateCertificate.page|Visualizar Certificado");
	pageAndLabelArr.push ("payments.page|Pagamentos");
	pageAndLabelArr.push ("updatePayment.page|Visualizar Pagamento");
	pageAndLabelArr.push ("logs.page|Logs");
	pageAndLabelArr.push ("updateLog.page|Visualizar Log");
	
	for (var i = 0; i < pageAndLabelArr.length; i++) {		
		var pageAndLabel = pageAndLabelArr[i];
		var page = pageAndLabel.split("|")[0];
		if (url.indexOf (page) != -1) {			
			if (url.indexOf ("id=") != -1) {							
				var titleArr = pageAndLabel.split("|");				
				if (titleArr.length == 3) {
					pageTitle =  pageAndLabel.split("|")[2];
				} else {
					pageTitle =  pageAndLabel.split("|")[1];
				}
			} else {
				pageTitle =  pageAndLabel.split("|")[1];
			}
		}
	}
	
	$("#container-form-title").html (pageTitle);
	
}