<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Anagram Store !</title>
</head>

<style>
.boxed1 {
	border: 3px solid red;
}
.boxed2 {
	border: 3px solid green;
}
</style>

<body>
	<c:choose>
		<c:when test="${user != null}">
			<p style="text-align: center">
				Welcome ${user.email} <br />
			</p>
			<p style="text-align: center">
				You can signout <a href="${logout_url}">here</a><br />
			</p>

			<div class="boxed1" align="center" action="/">
				<form method="post">
					<br />
					<br />
					<h3>Add Anagrams</h3>
					<input type="text" name="anagram" /> <input type="submit" />
				</form><br /><br/>
			</div>
			<br/><br/><br/>
			<div class="boxed2" align="center" action="/">
				<form method="post">
					<br />
					<h3>Search Anagrams</h3>
					<input type="text" name="anagram_seach" /> 
					<input type="submit" name="Search" value="Search" /><br/><br/>
					<textarea readonly name="results" rows="5" cols="40">${result}</textarea>
				</form><br />
			</div>
		</c:when>

		<c:otherwise>
			<div align="center">
				<h2>~Welcome~</h2><br/><br/>
				<a href="${login_url}">Sign in or register</a>
			</div>
			<br />
			<br />

		</c:otherwise>
	</c:choose>
</body>
</html>