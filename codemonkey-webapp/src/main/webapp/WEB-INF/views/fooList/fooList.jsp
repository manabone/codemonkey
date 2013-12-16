<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<header>
	<script type="text/javascript">
	
	</script>
</header>
<body>
	<table class="table panel-heading">
		<tr>
			<th>id</th>
			<th>code</th>
			<th>name</th>
			<th>fstring</th>
			<th>fnumber</th>
			<th>fbool</th>
			<th>fstatus</th>
			<th>fdate</th>
			<th>appRole</th>
			<th>appUserGroup</th>
		</tr>
		</thead>
		<tbody>
			<c:forEach items="${list}" var="foo" varStatus="status">
				<tr class="${status.count % 2 == 0 ? 'odd' : 'even'}">
					<td>${foo.id}</td>
					<td>${foo.code}</td>
					<td>${foo.name}</td>
					<td>${foo.fstring}</td>
					<td>${foo.fnumber}</td>
					<td>${foo.fbool}</td>
					<td>${foo.fstatus}</td>
					<td>${foo.fdate}</td>
					<td>${foo.appRole}</td>
					<td>${foo.appUserGroup}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
