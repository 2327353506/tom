<html>
<head>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8"> 
	<style>
		body
			{
				width:1024px;
				font-size: 14px;
			}
		table
			{
				width:100%;
				border: solid #ccc 1px;
			}	
		thead
			{
				width:100%;
			}		
		thead tr
			{
				width:100%;
				border: solid #ccc 1px;
			}	
		thead td
			{
				width:30%;
				border: solid #ccc 1px;
				text-align:center;
			}
		tbody tr:hover
			{
				color: #E33E06;
			}	
	</style>
</head>
<body>
	<h1>数据字典</h1>
	<table>
		<thead>
			<td>序号</td>
			<td>表名</td>
			<td>描述</td>
		</thead>
		<tbody>
		<#if data??>
		<#list data?keys as key>		
			<tr>
				<td>${key_index+1}</td>
				<td><a href='page/${key}.html'>${key}</a></td>
				<td>${data[key]}</td>
			</tr>	
		</#list>		
		</#if>
		</tbody>
	</table>
</body>
<html>