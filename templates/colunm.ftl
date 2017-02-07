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
				border: solid #ccc 1px;
				text-align:center;
			}
		thead .td_small
			{
				width:5%;
			}
		thead .td_defult
			{
				width:15%;
			}
		thead .td_big
			{
				width:50%;
			}
		tbody tr:hover
			{
				color: #E33E06;
			}	
		tbody td
			{
				border: solid #ccc 1px;
				text-align:center;
			}	
	</style>
</head>
<body>
	<h1><a href='../dictionary.html'>${tableName}</a></h1>
	<table>
		<thead>
			<td class="td_small">序号</td>
			<td class="td_defult">列名</td>
			<td class="td_big">描述</td>
			<td class="td_defult">类型</td>
			<td class="td_small">是否为空</td>
			<td class="td_small">默认值</td>
		</thead>
		<tbody>
		<#if tableColunm??>
		<#list tableColunm as value>		
			<tr>
				<td>${value_index+1}</td>
				<td>${value.columnName}</td>
				<td>${value.columnComment}</td>
				<td>${value.columnType}</td>
				<td>${value.isNullable}</td>
				<td>${value.columnDefault}</td>
			</tr>	
		</#list>		
		</#if>
		</tbody>
	</table>
</body>
<html>